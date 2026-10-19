(ns urbest.references.db)

;; ── État initial de la page ─────────────────────────────────────────────────

(def default-db
  {:lang   :fr         ;; :fr | :en, fixé par la page (attribut lang de <html>)
   :paused #{}         ;; bandeaux de logos mis en pause : #{:sg-fm :education}
   :demo   {:status :idle}}) ;; :idle | :sending | :error | :dev-sent
