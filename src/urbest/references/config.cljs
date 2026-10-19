(ns urbest.references.config)

;; ── Formulaire de démo ──────────────────────────────────────────────────────
;; Même script Google Apps Script que les autres pages d'urbest.io (landings.js).
;; Le build de dev le remplace par "" : le formulaire n'envoie alors rien.

(goog-define DEMO_ENDPOINT
  "https://script.google.com/macros/s/AKfycbyGeF4GqNzD54UgRsnse5oPaETxMV-cARolW62yQLaWMox1oAkxWGWamP2hXAb7141OCg/exec")

;; Au-delà, l'envoi est abandonné et un message d'erreur s'affiche
;; (le script Google peut mettre plusieurs secondes à répondre à froid).
(goog-define DEMO_TIMEOUT_MS 20000)

;; ── Adresses ────────────────────────────────────────────────────────────────

(def site-url "https://urbest.io")

;; Chemin de la page une fois intégrée au site (balises canonical et hreflang).
(def page-path "/references/")

;; Ressources (CSS, polices, logos) relatives aux pages /fr/ et /en/.
(def assets-path "../assets/")

(def getapp-url "https://www.getapp.com/operations-management-software/a/urbest/")

(def govlaunch-url
  "https://govlaunch.com/fr/projects/paris-la-defenses-workplace-experience-improved-with-urbest")

;; Adresse publique de l'aperçu GitHub Pages : les images de partage (LinkedIn)
;; doivent être données en adresse absolue.
(def preview-url "https://hugourbest.github.io/urbest-references")

;; Date affichée sous la FAQ et dans les données structurées : à mettre à jour
;; à chaque modification de contenu.
(def page-updated {:iso "2026-10-19" :fr "19 octobre 2026" :en "19 October 2026"})

(defn asset [& parts]
  (apply str assets-path parts))

(defn page-url [lang]
  (str site-url "/" (name lang) page-path))

(defn site-page [lang path]
  (str site-url "/" (name lang) path))

(defn demo-success-url [lang]
  (site-page lang "/demo-request-success/"))
