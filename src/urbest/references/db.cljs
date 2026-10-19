(ns urbest.references.db)

;; ── État initial de la page ─────────────────────────────────────────────────

(def default-db
  {:lang   :fr         ;; :fr | :en, fixé par la page (attribut lang de <html>)
   :paused #{}         ;; bandeaux de logos mis en pause : #{:sg-fm :education}
   :demo   {:status :idle    ;; :idle | :sending | :error | :dev-sent
            ;; false dans le HTML pré-rendu : le bouton d'envoi reste désactivé
            ;; tant que le JavaScript n'a pas pris la main sur le formulaire.
            :ready? false}})
