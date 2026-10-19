(ns urbest.references.config)

;; ── Formulaire de démo ──────────────────────────────────────────────────────
;; Même script Google Apps Script que les autres pages d'urbest.io (landings.js).
;; Le build de dev le remplace par "" : le formulaire n'envoie alors rien.

(goog-define DEMO_ENDPOINT
  "https://script.google.com/macros/s/AKfycbyGeF4GqNzD54UgRsnse5oPaETxMV-cARolW62yQLaWMox1oAkxWGWamP2hXAb7141OCg/exec")

;; ── Adresses ────────────────────────────────────────────────────────────────

(def site-url "https://urbest.io")

;; Chemin de la page une fois intégrée au site (balises canonical et hreflang).
(def page-path "/references/")

;; Ressources (CSS, polices, logos) relatives aux pages /fr/ et /en/.
(def assets-path "../assets/")

(def getapp-url "https://www.getapp.com/operations-management-software/a/urbest/")

(defn asset [& parts]
  (apply str assets-path parts))

(defn page-url [lang]
  (str site-url "/" (name lang) page-path))

(defn site-page [lang path]
  (str site-url "/" (name lang) path))

(defn demo-success-url [lang]
  (site-page lang "/demo-request-success/"))
