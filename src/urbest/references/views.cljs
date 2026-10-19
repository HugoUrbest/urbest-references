(ns urbest.references.views
  (:require [re-frame.core :as rf]
            [urbest.references.config :as config]
            [urbest.references.data :as data]
            [urbest.references.i18n :as i18n]
            [urbest.references.logos :as logos]))

;; ── Bulles de dialogue ──────────────────────────────────────────────────────
;; Élément graphique de la charte : la bulle issue du logo déconstruit
;; (boîte de 100 × 92, pointe en bas à gauche).

(def bubble-path "M54 92A46 46 0 1 0 8 46C8 66 6 81 0 90Q-1.5 92 2 92Z")

(defn- bubble
  "Bulle placée en (x, y), de largeur size ; flip? retourne la pointe vers la droite."
  [{:keys [x y size fill flip? opacity]}]
  (let [s (/ size 100)]
    [:path {:d         bubble-path
            :fill      fill
            :opacity   opacity
            :transform (if flip?
                         (str "translate(" x " " y ") scale(" (- s) " " s ") translate(-100 0)")
                         (str "translate(" x " " y ") scale(" s ")"))}]))

(defn- glyph
  "Pictogramme blanc (tracés 24 × 24) centré en (cx, cy)."
  [cx cy size & paths]
  (into [:g {:transform    (str "translate(" (- cx (/ size 2)) " " (- cy (/ size 2)) ") scale(" (/ size 24) ")")
             :fill         "none"
             :stroke       "#fff"
             :stroke-width 2.4
             :stroke-linecap  "round"
             :stroke-linejoin "round"}]
        (for [d paths] [:path {:d d}])))

(defn- hero-art []
  [:svg.hero__art {:view-box "0 0 520 470" :aria-hidden true :focusable false}
   [bubble {:x 22 :y 18 :size 470 :fill "#F5F5F5"}]
   [bubble {:x 300 :y 26 :size 150 :fill "#a9f7ae" :flip? true}]
   [bubble {:x 92 :y 92 :size 128 :fill "#5ae5b7"}]
   [glyph 161 151 50 "M5 12h.01M12 12h.01M19 12h.01"]
   [bubble {:x 248 :y 186 :size 196 :fill "#1d6fff" :flip? true}]
   [glyph 335 276 74 "M20 6 9 17l-5-5"]
   [bubble {:x 120 :y 286 :size 104 :fill "#38d1dd"}]
   [glyph 176 333 42 "M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"]
   [bubble {:x 430 :y 382 :size 60 :fill "#130046" :flip? true}]
   [bubble {:x 52 :y 236 :size 44 :fill "#a9f7ae"}]])

(defn- bubble-pattern
  "Motif de bulles en filigrane (bloc Éducation, bloc démo)."
  [id]
  [:svg.pattern {:aria-hidden true :focusable false}
   [:defs
    [:pattern {:id id :width 260 :height 240 :pattern-units "userSpaceOnUse"}
     [bubble {:x 8 :y 10 :size 70 :fill "#fff"}]
     [bubble {:x 118 :y 4 :size 44 :fill "#fff" :flip? true}]
     [bubble {:x 178 :y 40 :size 66 :fill "#fff" :flip? true}]
     [bubble {:x 40 :y 92 :size 40 :fill "#fff" :flip? true}]
     [bubble {:x 96 :y 76 :size 84 :fill "#fff"}]
     [bubble {:x 4 :y 156 :size 58 :fill "#fff"}]
     [bubble {:x 196 :y 132 :size 46 :fill "#fff"}]
     [bubble {:x 86 :y 170 :size 52 :fill "#fff" :flip? true}]
     [bubble {:x 160 :y 188 :size 36 :fill "#fff"}]]]
   [:rect {:width "100%" :height "100%" :fill (str "url(#" id ")")}]])

(defn- bubble-icon
  "Petite bulle pleine, utilisée comme puce."
  [fill]
  [:svg.bubble-icon {:view-box "-2 0 102 92" :aria-hidden true :focusable false}
   [:path {:d bubble-path :fill fill}]])

;; ── Éléments communs ────────────────────────────────────────────────────────

(defn- t [k]
  (i18n/t @(rf/subscribe [:lang]) k))

(def accents ["#38d1dd" "#5ae5b7" "#1d6fff"])

(defn- stats [items {:keys [modifier]}]
  [:ul.stats {:class modifier}
   (for [[i {:keys [value label link]}] (map-indexed vector items)]
     ^{:key i}
     [:li.stat
      [bubble-icon (nth accents (mod i 3))]
      [:strong.stat__value value]
      [:span.stat__label
       (if (= :getapp link)
         [:a {:href config/getapp-url :rel "noopener" :target "_blank"} label]
         label)]])])

(defn- tags [items]
  [:ul.tags
   (for [item items] ^{:key item} [:li.tag item])])

;; ── Logos ───────────────────────────────────────────────────────────────────

(defn- logo
  "Logo harmonisé, ou nom en toutes lettres quand aucun logo n'est disponible.
  decorative? : copie du bandeau, masquée aux lecteurs d'écran."
  [{:keys [label] :as ref} decorative?]
  (if-let [{:keys [file width height]} (get logos/manifest (:logo ref))]
    [:img.logo {:src      (config/asset "logos/" file)
                :alt      (if decorative? "" label)
                :title    label
                :width    width
                :height   height
                :decoding "async"
                :draggable false}]
    [:span.logo.logo--text {:title label} label]))

(defn- marquee-row [refs reverse?]
  (let [items (fn [decorative?]
                (for [r refs] ^{:key (:id r)} [:li.marquee__item [logo r decorative?]]))]
    [:div.marquee__row {:class (when reverse? "marquee__row--reverse")
                        ;; Vitesse constante quel que soit le nombre de logos.
                        :style {"--duration" (str (js/Math.round (* 3.4 (count refs))) "s")}}
     [:ul.marquee__track (items false)]
     [:ul.marquee__track {:aria-hidden true} (items true)]]))

(defn- marquee [id label-key]
  (let [lang    @(rf/subscribe [:lang])
        paused? @(rf/subscribe [:paused? id])
        refs    (data/logos-for id)]
    [:div.marquee {:class      (when paused? "is-paused")
                   :role       "region"
                   :aria-label (i18n/t lang label-key)}
     [:button.marquee__toggle {:type         "button"
                               :aria-pressed paused?
                               :title        (i18n/t lang (if paused? :marquee-play :marquee-pause))
                               :on-click     #(rf/dispatch [:toggle-marquee id])}
      [:span.visually-hidden (i18n/t lang :marquee-pause)]
      (if paused?
        [:svg {:view-box "0 0 24 24" :aria-hidden true} [:path {:d "M8 5.5v13l10.5-6.5z" :fill "currentColor"}]]
        [:svg {:view-box "0 0 24 24" :aria-hidden true}
         [:path {:d "M7.5 5.5h3v13h-3zM13.5 5.5h3v13h-3z" :fill "currentColor"}]])]
     [marquee-row (take-nth 2 refs) false]
     [marquee-row (take-nth 2 (rest refs)) true]]))

;; ── En-tête ─────────────────────────────────────────────────────────────────

(defn- site-header []
  (let [lang  @(rf/subscribe [:lang])
        other (i18n/other-lang lang)]
    [:header.site-header
     [:div.container.site-header__inner
      [:a.site-header__brand {:href (config/site-page lang "/")}
       [:img {:src (config/asset "img/urbest.svg") :alt "Urbest" :width 135 :height 35}]]
      [:nav.site-header__nav {:aria-label (t :nav-label)}
       [:a.lang-switch {:href       (str "../" (name other) "/")
                        :href-lang  (name other)
                        :lang       (name other)
                        :aria-label (t :lang-switch-label)}
        (t :lang-switch)]
       [:a.button.button--small {:href "#demo"} (t :nav-demo)]]]]))

;; ── Introduction ────────────────────────────────────────────────────────────

(defn- hero []
  [:section.hero {:aria-labelledby "hero-title"}
   [:div.container.hero__inner
    [:div.hero__content
     [:p.eyebrow (t :hero-eyebrow)]
     [:h1#hero-title.hero__title (t :hero-title)]
     [:p.hero__lead (t :hero-lead)]
     [stats (t :hero-stats) {:modifier "stats--hero"}]
     [:nav.jump {:aria-label (t :jump-label)}
      [:a.jump__link {:href "#services-generaux"} (t :jump-sgfm)]
      [:a.jump__link {:href "#education"} (t :jump-education)]]]
    [hero-art]]])

;; ── Blocs de références ─────────────────────────────────────────────────────

(defn- references-block [{:keys [id anchor modifier eyebrow title text tag-list figures source pattern]}]
  [:section.block {:id anchor :class modifier :aria-labelledby (str anchor "-title")}
   (when pattern [bubble-pattern pattern])
   [:div.container.block__inner
    [:div.block__intro
     [:p.eyebrow eyebrow]
     [:h2.block__title {:id (str anchor "-title")} title]
     [:p.block__text text]
     [tags tag-list]]
    [:div.block__figures
     [stats figures {:modifier "stats--cards"}]
     (when source [:p.block__source source])]]
   [marquee id (if (= :education id) :edu-marquee :sgfm-marquee)]])

(defn- sgfm-block []
  [references-block {:id       :sg-fm
                     :anchor   "services-generaux"
                     :modifier "block--sgfm"
                     :eyebrow  (t :sgfm-eyebrow)
                     :title    (t :sgfm-title)
                     :text     (t :sgfm-text)
                     :tag-list (t :sgfm-tags)
                     :figures  (t :sgfm-stats)
                     :source   (t :sgfm-source)}])

(defn- education-figures [lang]
  (let [education (filter #(= :education (data/block %)) data/references)
        regions   (count (filter #(re-find #"^Région" (:label %)) education))]
    [{:value (str regions) :label (i18n/t lang :edu-regions)}
     (i18n/t lang :edu-schools)
     {:value (str (data/network-count :la-salle)) :label (i18n/t lang :edu-la-salle)}]))

(defn- education-block []
  (let [lang @(rf/subscribe [:lang])]
    [references-block {:id       :education
                       :anchor   "education"
                       :modifier "block--education"
                       :pattern  "bubbles-education"
                       :eyebrow  (t :edu-eyebrow)
                       :title    (t :edu-title)
                       :text     (t :edu-text)
                       :tag-list (t :edu-tags)
                       :figures  (education-figures lang)}]))

;; ── Démo ────────────────────────────────────────────────────────────────────
;; Mêmes champs (noms et contrôles) que le formulaire des autres pages d'urbest.io.

(defn- field [{:keys [id label type required? attrs]}]
  [:div.field
   [:label.field__label {:for id} label]
   [:input.field__input (merge {:id id :name id :type type :required required?} attrs)]])

(defn- demo []
  (let [lang   @(rf/subscribe [:lang])
        status @(rf/subscribe [:demo-status])]
    [:section.demo {:id "demo" :aria-labelledby "demo-title"}
     [:div.container.demo__inner
      [:div.demo__intro
       [bubble-pattern "bubbles-demo"]
       [:div.demo__intro-content
        [:p.eyebrow (t :demo-eyebrow)]
        [:h2#demo-title.demo__title (t :demo-title)]
        [:p.demo__includes (t :demo-includes)]
        [:ul.demo__list
         (for [item (t :demo-items)] ^{:key item} [:li item])]]]
      [:form.demo__form {:id         "demo-form"
                         :on-submit  (fn [e]
                                       (.preventDefault e)
                                       (rf/dispatch [:submit-demo (js/FormData. (.-currentTarget e))]))}
       [:h3.demo__form-title (t :form-title)]
       [field {:id "full-name" :label (t :form-full-name) :type "text" :required? true
               :attrs {:auto-complete "name"}}]
       [field {:id "organization-title" :label (t :form-job) :type "text"
               :attrs {:auto-complete "organization-title"}}]
       [field {:id "organization" :label (t :form-company) :type "text"
               :attrs {:auto-complete "organization"}}]
       [field {:id "email" :label (t :form-email) :type "email" :required? true
               :attrs {:auto-complete "email"
                       :pattern       "^[^@]+@(?!gmail\\.com$|outlook\\.com$|yahoo\\.com$|hotmail\\.com$).+"
                       :title         (t :form-email-title)}}]
       [field {:id "phone" :label (t :form-phone) :type "tel" :required? true
               :attrs {:auto-complete "tel"}}]
       [:input {:type "hidden" :name "lang" :value (name lang)}]
       [:p.demo__privacy
        (t :form-privacy)
        [:a {:href (config/site-page lang "/privacy-policy/")} (t :form-privacy-link)]]
       (case status
         :error    [:p.demo__message.demo__message--error {:role "alert"} (t :form-error)]
         :dev-sent [:p.demo__message {:role "status"} (t :form-dev-sent)]
         nil)
       [:button.button.button--block {:type      "submit"
                                      :disabled  (= :sending status)
                                      :aria-busy (= :sending status)}
        (if (= :sending status) (t :form-sending) (t :form-submit))]]]]))

;; ── Pied de page ────────────────────────────────────────────────────────────

(defn- site-footer []
  (let [lang @(rf/subscribe [:lang])]
    [:footer.site-footer
     [:div.container.site-footer__inner
      [:a.site-footer__brand {:href (config/site-page lang "/")}
       [:img {:src (config/asset "img/urbest-blanc.svg") :alt "Urbest, simplify future" :width 168 :height 60}]]
      [:p.site-footer__tagline (t :footer-tagline)]
      [:nav.site-footer__nav {:aria-label (t :footer-label)}
       [:a {:href (config/site-page lang "/")} "urbest.io"]
       [:a {:href (config/site-page lang "/contact/")} (t :footer-contact)]
       [:a {:href (config/site-page lang "/privacy-policy/")} (t :footer-privacy)]]]]))

;; ── Page ────────────────────────────────────────────────────────────────────

(defn root []
  [:<>
   [site-header]
   [:main
    [hero]
    [sgfm-block]
    [education-block]
    [demo]]
   [site-footer]])
