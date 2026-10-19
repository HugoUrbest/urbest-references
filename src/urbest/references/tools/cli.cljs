(ns urbest.references.tools.cli
  "Point d'entrée des outils de build : node target/tools.js (logos|prerender)."
  (:require [urbest.references.tools.logos :as logos]
            [urbest.references.tools.prerender :as prerender]))

(defn main [& [command]]
  (case command
    "logos"     (logos/run)
    "prerender" (prerender/run)
    (do (println "Usage : node target/tools.js logos|prerender")
        (set! (.-exitCode js/process) 1))))
