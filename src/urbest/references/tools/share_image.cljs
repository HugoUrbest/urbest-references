(ns urbest.references.tools.share-image
  "Images de partage LinkedIn / réseaux sociaux (`npm run share-image`) :
  docs/assets/img/og-references-fr.png et -en.png, 1200 × 630.
  Le texte est rendu en Proxima Nova, qui doit être installée sur la machine."
  (:require ["fs" :as fs]
            ["sharp" :as sharp]
            [clojure.string :as str]
            [urbest.references.i18n :as i18n]
            [urbest.references.views :as views]))

(def width 1200)
(def height 630)

(defn- esc [s]
  (-> s (str/replace "&" "&amp;") (str/replace "<" "&lt;") (str/replace ">" "&gt;")))

(defn- wrap
  "Coupe un texte en lignes d'au plus max-chars caractères."
  [text max-chars]
  (reduce (fn [lines word]
            (let [last-line (peek lines)]
              (if (and last-line (<= (+ (count last-line) 1 (count word)) max-chars))
                (conj (pop lines) (str last-line " " word))
                (conj lines word))))
          []
          (str/split text #" ")))

(defn- bubble [x y size fill & [flip?]]
  (let [s (/ size 100)]
    (str "<path d='" views/bubble-path "' fill='" fill "' transform='"
         (if flip?
           (str "translate(" x " " y ") scale(" (- s) " " s ") translate(-100 0)")
           (str "translate(" x " " y ") scale(" s ")"))
         "'/>")))

(defn- svg [lang]
  (let [t       #(i18n/t lang %)
        title   (wrap (t :hero-title) 30)
        stats   (concat (t :hero-stats) [(t :edu-schools)])
        font    "font-family='Proxima Nova, CircularStd, Helvetica, Arial, sans-serif'"]
    (str "<svg xmlns='http://www.w3.org/2000/svg' width='" width "' height='" height "'>"
         "<rect width='100%' height='100%' fill='#130046'/>"
         ;; Bulles de la charte, à droite
         (bubble 860 70 230 "#1d6fff" true)
         (bubble 1010 30 120 "#a9f7ae" true)
         (bubble 930 300 150 "#38d1dd")
         (bubble 1080 250 90 "#5ae5b7" true)
         (bubble 1050 430 140 "#5ae5b7")
         (bubble 880 480 70 "#a9f7ae")
         ;; Sur-titre
         "<text x='72' y='196' " font " font-weight='700' font-size='22' letter-spacing='3' fill='#a9f7ae'>"
         (esc (str/upper-case (t :hero-eyebrow))) "</text>"
         ;; Titre
         (str/join (map-indexed (fn [i line]
                                  (str "<text x='72' y='" (+ 262 (* i 64)) "' " font
                                       " font-weight='900' font-size='56' fill='#ffffff'>" (esc line) "</text>"))
                                title))
         ;; Chiffres clés
         (str/join (map (fn [x {:keys [value label]}]
                                  (let []
                                    (str "<text x='" x "' y='520' " font " font-weight='900' font-size='34' fill='#ffffff'>"
                                         (esc value) "</text>"
                                         (str/join (map-indexed (fn [j l]
                                                                  (str "<text x='" x "' y='" (+ 550 (* j 22)) "' " font
                                                                       " font-size='17' fill='#ffffff' fill-opacity='0.75'>"
                                                                       (esc l) "</text>"))
                                                                (wrap label 18))))))
                        ;; Colonnes à largeur variable : un chiffre long pousse le suivant.
                        (reductions (fn [x {:keys [value]}] (+ x (max 150 (* 22 (count value))) 40))
                                    72 stats)
                        stats))
         "</svg>")))

(defn- render [lang]
  (let [out (str "docs/assets/img/og-references-" (name lang) ".png")]
    (-> (.toBuffer (.resize (sharp "docs/assets/img/urbest-blanc.svg" #js {:density 300}) #js {:width 236}))
        (.then (fn [logo]
                 (-> (sharp (js/Buffer.from (svg lang)))
                     (.composite #js [#js {:input logo :left 72 :top 64}])
                     (.png #js {:compressionLevel 9})
                     (.toFile out))))
        (.then (fn [_] (println "Image de partage :" out))))))

(defn run []
  (fs/mkdirSync "docs/assets/img" #js {:recursive true})
  (-> (js/Promise.all #js [(render :fr) (render :en)])
      (.catch (fn [e] (js/console.error e) (set! (.-exitCode js/process) 1)))))
