(ns urbest.references.tools.prerender
  "Génère les pages statiques docs/fr/index.html et docs/en/index.html (`npm run prerender`).

  Tout le contenu est déjà dans le HTML (référencement, affichage immédiat) ;
  le bundle navigateur l'« hydrate » ensuite pour les interactions.
  Par défaut les pages portent une balise noindex (prévisualisation GitHub Pages) :
  URBEST_INDEX=1 npm run build la retire pour la mise en ligne sur urbest.io."
  (:require ["crypto" :as crypto]
            ["fs" :as fs]
            ["path" :as path]
            [clojure.string :as str]
            [re-frame.core :as rf]
            [reagent.dom.server :as rds]
            [urbest.references.config :as config]
            [urbest.references.data :as data]
            [urbest.references.events]
            [urbest.references.i18n :as i18n]
            [urbest.references.logos :as logos]
            [urbest.references.subs]
            [urbest.references.views :as views]))

(def out-dir "docs")

(defn- esc [s]
  (-> s
      (str/replace "&" "&amp;")
      (str/replace "\"" "&quot;")
      (str/replace "<" "&lt;")
      (str/replace ">" "&gt;")))

(defn- indexable? []
  (= "1" (.. js/process -env -URBEST_INDEX)))

(defn- versioned
  "Adresse d'une ressource suivie de l'empreinte de son contenu (?v=…).
  Chaque build change l'adresse du JS et du CSS : un navigateur ne peut plus
  associer une page à jour avec un ancien bundle resté en cache (GitHub Pages
  met les fichiers en cache 10 minutes)."
  [rel]
  (let [content (fs/readFileSync (path/join out-dir "assets" rel))]
    (str (config/asset rel) "?v=" (-> (crypto/createHash "sha1") (.update content) (.digest "hex") (.slice 0 10)))))

(defn- share-image
  "Image de partage (LinkedIn, etc.) : adresse absolue obligatoire."
  [lang]
  (str config/preview-url "/assets/img/og-references-" (name lang) ".png"))

(defn- structured-data
  "Données structurées schema.org : l'organisation, la page (avec fil d'Ariane)
  et la FAQ, reprise mot pour mot du texte visible."
  [lang]
  (let [t      #(i18n/t lang %)
        url    (config/page-url lang)
        org-id (str config/site-url "/#organization")
        data   {"@context" "https://schema.org"
                "@graph"
                [{"@type"       "Organization"
                  "@id"         org-id
                  "name"        "Urbest"
                  "legalName"   "Urbest SAS"
                  "url"         (str config/site-url "/")
                  "logo"        (str config/site-url "/images/landing/urbest-brand.svg")
                  "description" (t :footer-tagline)
                  "sameAs"      [config/getapp-url "https://www.capterra.com/p/193375/Urbest/"]}
                 {"@type"        "WebPage"
                  "@id"          url
                  "url"          url
                  "name"         (t :meta-title)
                  "description"  (t :meta-description)
                  "inLanguage"   (name lang)
                  "dateModified" (:iso config/page-updated)
                  "about"        {"@id" org-id}
                  "publisher"    {"@id" org-id}
                  "breadcrumb"   {"@type" "BreadcrumbList"
                                  "itemListElement"
                                  [{"@type" "ListItem" "position" 1 "name" "Urbest" "item" (config/site-page lang "/")}
                                   {"@type" "ListItem" "position" 2 "name" (t :breadcrumb-page) "item" url}]}}
                 {"@type"      "FAQPage"
                  "@id"        (str url "#faq")
                  "inLanguage" (name lang)
                  "mainEntity" (for [{:keys [q a]} (t :faq)]
                                 {"@type" "Question" "name" q
                                  "acceptedAnswer" {"@type" "Answer" "text" a}})}]}]
    ;; « </ » échappé pour ne jamais fermer la balise <script> par accident.
    (str/replace (js/JSON.stringify (clj->js data)) "</" "<\\/")))

(defn- head [lang]
  (let [t #(esc (i18n/t lang %))]
    (str/join
     "\n"
     ["<meta charset=\"utf-8\">"
      "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
      (str "<title>" (t :meta-title) "</title>")
      (str "<meta name=\"description\" content=\"" (t :meta-description) "\">")
      (if (indexable?)
        "<meta name=\"robots\" content=\"index, follow\">"
        "<meta name=\"robots\" content=\"noindex\">")
      (str "<link rel=\"canonical\" href=\"" (config/page-url lang) "\">")
      (str "<link rel=\"alternate\" hreflang=\"fr\" href=\"" (config/page-url :fr) "\">")
      (str "<link rel=\"alternate\" hreflang=\"en\" href=\"" (config/page-url :en) "\">")
      (str "<link rel=\"alternate\" hreflang=\"x-default\" href=\"" (config/page-url :en) "\">")
      "<meta name=\"theme-color\" content=\"#1D6FFF\">"
      "<meta property=\"og:type\" content=\"website\">"
      (str "<meta property=\"og:title\" content=\"" (t :meta-title) "\">")
      (str "<meta property=\"og:description\" content=\"" (t :meta-description) "\">")
      (str "<meta property=\"og:url\" content=\"" (config/page-url lang) "\">")
      (str "<meta property=\"og:locale\" content=\"" (if (= :fr lang) "fr_FR" "en_GB") "\">")
      "<meta property=\"og:site_name\" content=\"Urbest\">"
      (str "<meta property=\"og:image\" content=\"" (share-image lang) "\">")
      "<meta property=\"og:image:width\" content=\"1200\">"
      "<meta property=\"og:image:height\" content=\"630\">"
      (str "<meta property=\"og:image:alt\" content=\"" (t :meta-title) "\">")
      "<meta name=\"twitter:card\" content=\"summary_large_image\">"
      (str "<meta name=\"twitter:title\" content=\"" (t :meta-title) "\">")
      (str "<meta name=\"twitter:description\" content=\"" (t :meta-description) "\">")
      (str "<meta name=\"twitter:image\" content=\"" (share-image lang) "\">")
      (str "<script type=\"application/ld+json\">" (structured-data lang) "</script>")
      (str "<link rel=\"icon\" href=\"" (config/asset "img/favicon.svg") "\" type=\"image/svg+xml\">")
      (str "<link rel=\"preload\" href=\"" (config/asset "fonts/proxima-nova-black.woff2") "\" as=\"font\" type=\"font/woff2\" crossorigin>")
      (str "<link rel=\"preload\" href=\"" (config/asset "fonts/proxima-nova-regular.woff2") "\" as=\"font\" type=\"font/woff2\" crossorigin>")
      (str "<link rel=\"stylesheet\" href=\"" (versioned "css/references.css") "\">")
      (str "<script src=\"" (versioned "js/main.js") "\" defer></script>")])))

;; Au pré-rendu, les abonnements re-frame sont lus hors composant réactif :
;; c'est attendu, on masque donc cet avertissement-là.
(rf/set-loggers!
 {:warn (fn [& args]
          (when-not (str/includes? (str (first args)) "outside of a reactive context")
            (apply js/console.warn args)))})

(defn page [lang]
  (rf/dispatch-sync [:init lang])
  (str "<!doctype html>\n<html lang=\"" (name lang) "\">\n<head>\n" (head lang) "\n</head>\n<body>\n"
       "<div id=\"app\">" (rds/render-to-string [views/root]) "</div>\n"
       "</body>\n</html>\n"))

;; Racine du site GitHub Pages : redirige vers la langue du navigateur.
(def redirect-page
  "<!doctype html>
<html lang=\"fr\">
<head>
<meta charset=\"utf-8\">
<meta name=\"robots\" content=\"noindex\">
<meta http-equiv=\"refresh\" content=\"0; url=fr/\">
<title>Références clients | Urbest</title>
<script>location.replace(/^fr\\b/i.test(navigator.language || '') ? 'fr/' : 'en/');</script>
</head>
<body><p><a href=\"fr/\">Français</a> · <a href=\"en/\">English</a></p></body>
</html>
")

;; ── Contrôles ───────────────────────────────────────────────────────────────
;; Les vues se rabattent sur le nom en toutes lettres (logo absent) ou sur
;; « ⟨clé⟩ » (traduction absente) : le build échoue plutôt que de publier ça.

(defn- content-errors []
  (let [missing (->> data/references (keep :logo) distinct (remove #(contains? logos/manifest %)) sort)
        fr      (set (keys (:fr i18n/translations)))
        en      (set (keys (:en i18n/translations)))]
    (cond-> []
      (seq missing)
      (conj (str "logos absents de src/urbest/references/logos.cljs (lancer npm run logos) : "
                 (str/join ", " missing)))
      (seq (remove en fr))
      (conj (str "traductions anglaises manquantes : " (str/join ", " (map name (sort (remove en fr))))))
      (seq (remove fr en))
      (conj (str "traductions françaises manquantes : " (str/join ", " (map name (sort (remove fr en)))))))))

(defn- page-errors [lang html]
  (when-let [k (re-find #"⟨[^⟩]*⟩" html)]
    [(str "clé de traduction inconnue " k " dans la page " (name lang))]))

;; ── Point d'entrée ──────────────────────────────────────────────────────────

(defn run []
  (let [pages  (into {} (for [lang [:fr :en]] [lang (page lang)]))
        errors (concat (content-errors) (mapcat (fn [[lang html]] (page-errors lang html)) pages))]
    (if (seq errors)
      (do (doseq [e errors] (println "ERREUR" e))
          (println "Pages non générées.")
          (set! (.-exitCode js/process) 1))
      (do (doseq [[lang html] pages]
            (let [file (path/join out-dir (name lang) "index.html")]
              (fs/mkdirSync (path/dirname file) #js {:recursive true})
              (fs/writeFileSync file html)
              (println "Page générée :" file)))
          (fs/writeFileSync (path/join out-dir "index.html") redirect-page)
          (fs/writeFileSync (path/join out-dir ".nojekyll") "")
          (println (if (indexable?)
                     "Pages indexables (URBEST_INDEX=1)."
                     "Pages en noindex (prévisualisation). URBEST_INDEX=1 pour la production."))))))
