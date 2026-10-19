(ns urbest.references.tools.cli
  "Point d'entrée des outils de build : node target/tools.js (logos|prerender|share-image)."
  (:require [urbest.references.tools.logos :as logos]
            [urbest.references.tools.prerender :as prerender]
            [urbest.references.tools.share-image :as share-image]))

(defn main [& [command]]
  (case command
    "logos"     (logos/run)
    "prerender" (prerender/run)
    "share-image" (share-image/run)
    (do (println "Usage : node target/tools.js logos|prerender|share-image")
        (set! (.-exitCode js/process) 1))))
