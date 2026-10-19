(ns urbest.references.tools.logos
  "Harmonisation des logos clients (`npm run logos`).

  Chaque fichier de logos/source devient une silhouette monochrome bleu nuit
  Urbest (#130046), recadrée au plus juste et dimensionnée pour que tous les
  logos aient le même poids visuel dans les bandeaux. Sorties :
  - docs/assets/logos/<logo>.webp, à 2× la taille d'affichage (écrans Retina) ;
  - src/urbest/references/logos.cljs, les dimensions d'affichage lues par les vues ;
  - target/logos-planche.png, une planche de contrôle de tous les logos."
  (:require ["fs" :as fs]
            ["path" :as path]
            ["sharp" :as sharp]
            [clojure.string :as str]
            [urbest.references.data :as data]))

(def source-dir "logos/source")
(def output-dir "docs/assets/logos")
(def manifest-file "src/urbest/references/logos.cljs")
(def sheet-file "target/logos-planche.png")

(def navy [19 0 70])

;; ── Réglages par logo ───────────────────────────────────────────────────────
;; :mode :alpha   logo blanc (ou à texte blanc) sur fond transparent : la silhouette
;;                suit la transparence de l'image plutôt que ses couleurs.
;; :crop [x y l h] zone à conserver, en fractions de l'image (de 0 à 1).
;; :lo            nuance en dessous de laquelle un pixel compte comme du fond.
;; :gamma         < 1 renforce les parties claires d'un logo (texte pâle, dégradés).
;; :density       résolution de rastérisation imposée pour un SVG mal dimensionné.

(def options
  {"geodis"                  {:density 300}           ; dimensions en mm
   "penelope"                {:mode :alpha}
   "puissance-5"             {:mode :alpha}
   "nailloux-outlet-village" {:mode :alpha}
   "neinver"                 {:mode :alpha}
   "vallair"                 {:mode :alpha}
   "mont-roland"             {:mode :alpha}
   "la-providence-orchies"   {:mode :alpha}
   "roofinders"              {:mode :alpha}
   "la-salle-lorient"        {:mode :alpha}
   "saint-louis-saint-bruno" {:mode :alpha}
   "advantail"               {:crop [0 0 0.83 1]}      ; sans le badge B Corp
   "fsm"                     {:crop [0 0 0.19 1]}      ; le symbole, sans la signature
   "ginette"                 {:crop [0 0 1 0.5] :gamma 0.7}
   "la-salle-pantin"         {:gamma 0.6}
   "institut-francais-grece" {:gamma 0.6}
   "cefppa-adrien-zeller"    {:gamma 0.7}
   "chateau-margaux"         {:gamma 0.8}})

;; ── Dimensions d'affichage ──────────────────────────────────────────────────
;; Tous les logos occupent à peu près la même surface : un logo carré est plus
;; haut qu'un logo très horizontal, ce qui équilibre leur présence à l'écran.

(def target-area 4900)
(def max-height 54)
(def max-width 172)

(defn display-size [bw bh coverage]
  (let [ratio  (/ bw bh)
        ;; Un logo très encré (aplats) paraît plus gros qu'un logo filaire.
        weight (-> (js/Math.pow (/ 0.3 (max coverage 0.05)) 0.2) (max 0.88) (min 1.12))
        h      (* weight (js/Math.sqrt (/ target-area ratio)))
        w      (* ratio h)
        scale  (min 1 (/ max-height h) (/ max-width w))]
    [(js/Math.round (* w scale)) (js/Math.round (* h scale))]))

;; ── Lecture ─────────────────────────────────────────────────────────────────

(defn- source-file [logo]
  (some (fn [f] (when (= logo (.-name (path/parse f))) (path/join source-dir f)))
        (fs/readdirSync source-dir)))

(defn- load-rgba
  "Image en RGBA 8 bits. Les SVG sont rastérisés vers 1600 px sur leur grand côté,
  sauf densité imposée par les réglages."
  [file {forced-density :density}]
  (-> (.metadata (sharp file))
      (.then (fn [^js meta]
               (let [density (when (= "svg" (.-format meta))
                               (or forced-density
                                   (-> (/ (* 72 1600) (max 1 (.-width meta) (.-height meta)))
                                       (max 1) (min 2400))))]
                 (-> (sharp file (clj->js (cond-> {:limitInputPixels false} density (assoc :density density))))
                     (.toColourspace "srgb")
                     (.ensureAlpha)
                     (.raw)
                     (.toBuffer #js {:resolveWithObject true})))))
      (.then (fn [^js res]
               (when (not= 4 (.. res -info -channels))
                 (throw (js/Error. (str file " : " (.. res -info -channels) " canaux"))))
               {:px (.-data res) :w (.. res -info -width) :h (.. res -info -height)}))))

(defn- crop [{:keys [^js px w h]} [fx fy fw fh]]
  (let [x0  (js/Math.round (* fx w))
        y0  (js/Math.round (* fy h))
        cw  (js/Math.round (* fw w))
        ch  (js/Math.round (* fh h))
        out (js/Buffer.alloc (* cw ch 4))]
    (dotimes [y ch]
      (let [start (* 4 (+ (* (+ y0 y) w) x0))]
        (.copy px out (* 4 y cw) start (+ start (* 4 cw)))))
    {:px out :w cw :h ch}))

;; ── Encre ───────────────────────────────────────────────────────────────────
;; L'« encre » d'un pixel mesure son écart au fond (0 = fond, 1 = trait plein).

(defn- luma [r g b]
  (/ (+ (* 0.2126 r) (* 0.7152 g) (* 0.0722 b)) 255))

(defn- median [^js arr]
  (aget (.sort arr) (quot (.-length arr) 2)))

(defn- background
  "Couleur du fond : médiane des pixels du cadre de l'image."
  [^js rgb w h]
  (let [t  (max 1 (js/Math.round (* 0.02 (min w h))))
        rs #js [] gs #js [] bs #js []
        add! (fn [x y] (let [i (* 3 (+ (* y w) x))]
                         (.push rs (aget rgb i))
                         (.push gs (aget rgb (+ i 1)))
                         (.push bs (aget rgb (+ i 2)))))]
    (dotimes [y h]
      (if (or (< y t) (>= y (- h t)))
        (dotimes [x w] (add! x y))
        (do (dotimes [x t] (add! x y))
            (dotimes [x t] (add! (- w 1 x) y)))))
    [(median (js/Float32Array.from rs)) (median (js/Float32Array.from gs)) (median (js/Float32Array.from bs))]))

(defn- ink-from-colours
  "Écart de chaque pixel (composé sur blanc) à la couleur du fond : différence de
  luminosité, complétée par l'écart de teinte pour les couleurs claires et vives."
  [{:keys [px w h]}]
  (let [n   (* w h)
        rgb (js/Float32Array. (* 3 n))
        ink (js/Float32Array. n)]
    (dotimes [i n]
      (let [a (/ (aget px (+ (* 4 i) 3)) 255)]
        (dotimes [c 3]
          (aset rgb (+ (* 3 i) c) (+ (* a (aget px (+ (* 4 i) c))) (* (- 1 a) 255))))))
    (let [[br bg bb] (background rgb w h)
          ybg        (luma br bg bb)]
      (dotimes [i n]
        (let [r  (aget rgb (* 3 i))
              g  (aget rgb (+ (* 3 i) 1))
              b  (aget rgb (+ (* 3 i) 2))
              dy (js/Math.abs (- (luma r g b) ybg))
              dc (/ (js/Math.sqrt (/ (+ (* (- r br) (- r br)) (* (- g bg) (- g bg)) (* (- b bb) (- b bb))) 3)) 255)]
          (aset ink i (max dy (* 0.5 dc))))))
    ink))

(defn- ink-from-alpha [{:keys [px w h]}]
  (let [n (* w h) ink (js/Float32Array. n)]
    (dotimes [i n] (aset ink i (/ (aget px (+ (* 4 i) 3)) 255)))
    ink))

(defn- normalize!
  "Étire l'encre pour que le trait le plus marqué devienne plein : un logo beige
  pâle finit aussi lisible qu'un logo noir."
  [^js ink {:keys [lo gamma] :or {lo 0.06 gamma 1}}]
  (let [strokes (.sort (.filter ink #(> % 0.08)))
        p98     (if (pos? (.-length strokes))
                  (aget strokes (js/Math.floor (* 0.98 (dec (.-length strokes)))))
                  1)
        hi      (max (+ lo 0.12) (* 0.92 p98))]
    (dotimes [i (.-length ink)]
      (aset ink i (-> (/ (- (aget ink i) lo) (- hi lo)) (max 0) (min 1) (js/Math.pow gamma))))
    ink))

(defn- bounding-box [^js alpha w h]
  (loop [i 0 x0 w y0 h x1 -1 y1 -1]
    (if (< i (* w h))
      (if (> (aget alpha i) 0.04)
        (let [x (mod i w) y (quot i w)]
          (recur (inc i) (min x0 x) (min y0 y) (max x1 x) (max y1 y)))
        (recur (inc i) x0 y0 x1 y1))
      (when (>= x1 0) [x0 y0 x1 y1]))))

(defn- coverage
  "Part encrée de la zone utile (0 à 1)."
  [^js alpha w [x0 y0 x1 y1]]
  (loop [y y0 sum 0]
    (if (> y y1)
      (/ sum (* (inc (- x1 x0)) (inc (- y1 y0))))
      (recur (inc y) (loop [x x0 s sum]
                       (if (> x x1) s (recur (inc x) (+ s (aget alpha (+ (* y w) x))))))))))

;; ── Écriture ────────────────────────────────────────────────────────────────

(defn- silhouette
  "Buffer RGBA de la zone utile : couleur unie, transparence = encre."
  [^js alpha w [x0 y0 x1 y1] [r g b]]
  (let [bw  (inc (- x1 x0))
        bh  (inc (- y1 y0))
        buf (js/Buffer.alloc (* 4 bw bh))]
    (dotimes [y bh]
      (dotimes [x bw]
        (let [o (* 4 (+ (* y bw) x))]
          (aset buf o r)
          (aset buf (+ o 1) g)
          (aset buf (+ o 2) b)
          (aset buf (+ o 3) (js/Math.round (* 255 (aget alpha (+ (* (+ y0 y) w) x0 x))))))))
    {:buf buf :w bw :h bh}))

(defn- resized [{:keys [buf w h]} width height]
  (-> (sharp buf #js {:raw #js {:width w :height h :channels 4}})
      (.resize width height #js {:fit "fill" :kernel "lanczos3"})))

(defn- process [logo]
  (let [opts (get options logo {})
        file (source-file logo)]
    (if-not file
      (js/Promise.resolve {:logo logo :error "fichier source introuvable"})
      (-> (load-rgba file opts)
          (.then (fn [img]
                   (let [{:keys [w h] :as img} (if-let [c (:crop opts)] (crop img c) img)
                         alpha (-> (if (= :alpha (:mode opts)) (ink-from-alpha img) (ink-from-colours img))
                                   (normalize! (if (= :alpha (:mode opts)) (merge {:lo 0.04} opts) opts)))
                         box   (bounding-box alpha w h)]
                     (if-not box
                       {:logo logo :error "image vide après traitement"}
                       (let [[x0 y0 x1 y1] box
                             [dw dh] (display-size (inc (- x1 x0)) (inc (- y1 y0)) (coverage alpha w box))
                             out     (path/join output-dir (str logo ".png"))
                             shape   (silhouette alpha w box navy)]
                         (-> (resized shape (* 2 dw) (* 2 dh))
                             (.png #js {:palette true :colours 16 :dither 0 :compressionLevel 9 :effort 10})
                             (.toFile out)
                             (.then (fn [^js info]
                                      {:logo logo :file (str logo ".png") :width dw :height dh
                                       :bytes (.-size info) :shape shape}))))))))
          (.catch (fn [e] {:logo logo :error (.-message e)}))))))

(defn- manifest-source [results]
  (str ";; Généré par `npm run logos` (urbest.references.tools.logos) : ne pas modifier à la main.\n"
       "(ns urbest.references.logos)\n\n"
       "(def manifest\n  {"
       (str/join "\n   "
                 (for [{:keys [logo file width height]} (sort-by :logo results)]
                   (str (pr-str logo) " {:file " (pr-str file) " :width " width " :height " height "}")))
       "})\n"))

(defn- escape-xml [s]
  (-> s (str/replace "&" "&amp;") (str/replace "<" "&lt;") (str/replace ">" "&gt;")))

(defn- contact-sheet
  "Planche de contrôle : chaque logo à sa taille d'affichage, bleu nuit sur blanc."
  [results]
  (let [cols 6 cw 210 ch 104 n (count results)
        rows (js/Math.ceil (/ n cols))]
    (-> (js/Promise.all
         (clj->js
          (map-indexed
           (fn [i {:keys [logo width height shape]}]
             (let [x (* cw (mod i cols)) y (* ch (quot i cols))]
               (-> (.toBuffer (.png (resized shape width height)))
                   (.then (fn [buf]
                            #js [#js {:input buf
                                      :left  (+ x (quot (- cw width) 2))
                                      :top   (+ y (quot (- ch 20 height) 2))}
                                 #js {:input (js/Buffer.from
                                              (str "<svg width='" cw "' height='18'><text x='6' y='13' font-family='Helvetica' font-size='11' fill='#666'>"
                                                   (escape-xml logo) "</text></svg>"))
                                      :left x :top (+ y (- ch 18))}])))))
           results)))
        (.then (fn [parts]
                 (-> (sharp #js {:create #js {:width (* cols cw) :height (* rows ch) :channels 4
                                              :background "#ffffff"}})
                     (.composite (.flat parts))
                     (.png)
                     (.toFile sheet-file)))))))

;; ── Point d'entrée ──────────────────────────────────────────────────────────

(defn run []
  (let [logos (->> data/references (keep :logo) distinct sort)]
    (fs/mkdirSync output-dir #js {:recursive true})
    (fs/mkdirSync "target" #js {:recursive true})
    (-> (reduce (fn [p logo] (.then p (fn [acc] (.then (process logo) #(conj acc %)))))
                (js/Promise.resolve [])
                logos)
        (.then (fn [results]
                 (let [ok     (remove :error results)
                       errors (filter :error results)
                       orphans (remove (set (map :file ok)) (fs/readdirSync output-dir))]
                   (fs/writeFileSync manifest-file (manifest-source ok))
                   (doseq [{:keys [logo error]} errors]
                     (println "ERREUR" logo ":" error))
                   (doseq [f orphans]
                     (println "Fichier sans référence (à supprimer ?) :" (path/join output-dir f)))
                   (println (count ok) "logos générés,"
                            (js/Math.round (/ (reduce + (map :bytes ok)) 1024)) "Ko au total.")
                   (-> (contact-sheet ok)
                       (.then #(println "Planche de contrôle :" sheet-file))
                       (.then #(when (seq errors) (set! (.-exitCode js/process) 1)))))))
        (.catch (fn [e] (js/console.error e) (set! (.-exitCode js/process) 1))))))
