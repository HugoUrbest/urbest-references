(ns urbest.references.tools.prerender
  "Génère les pages statiques docs/fr/index.html et docs/en/index.html (`npm run prerender`).

  Tout le contenu est déjà dans le HTML (référencement, affichage immédiat) ;
  le bundle navigateur l'« hydrate » ensuite pour les interactions.
  Par défaut les pages portent une balise noindex (prévisualisation GitHub Pages) :
  URBEST_INDEX=1 npm run build la retire pour la mise en ligne sur urbest.io."
  (:require ["fs" :as fs]
            ["path" :as path]
            [clojure.string :as str]
            [re-frame.core :as rf]
            [reagent.dom.server :as rds]
            [urbest.references.config :as config]
            [urbest.references.events]
            [urbest.references.i18n :as i18n]
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
      (str "<link rel=\"icon\" href=\"" (config/asset "img/favicon.svg") "\" type=\"image/svg+xml\">")
      (str "<link rel=\"preload\" href=\"" (config/asset "fonts/proxima-nova-black.woff2") "\" as=\"font\" type=\"font/woff2\" crossorigin>")
      (str "<link rel=\"preload\" href=\"" (config/asset "fonts/proxima-nova-regular.woff2") "\" as=\"font\" type=\"font/woff2\" crossorigin>")
      (str "<link rel=\"stylesheet\" href=\"" (config/asset "css/references.css") "\">")
      (str "<script src=\"" (config/asset "js/main.js") "\" defer></script>")])))

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

(defn run []
  (doseq [lang [:fr :en]]
    (let [file (path/join out-dir (name lang) "index.html")]
      (fs/mkdirSync (path/dirname file) #js {:recursive true})
      (fs/writeFileSync file (page lang))
      (println "Page générée :" file)))
  (fs/writeFileSync (path/join out-dir "index.html") redirect-page)
  (fs/writeFileSync (path/join out-dir ".nojekyll") "")
  (println (if (indexable?)
             "Pages indexables (URBEST_INDEX=1)."
             "Pages en noindex (prévisualisation). URBEST_INDEX=1 pour la production.")))
