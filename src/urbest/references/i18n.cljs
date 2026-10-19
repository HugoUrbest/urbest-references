(ns urbest.references.i18n
  "Textes de la page en français et en anglais.
  Les chiffres « utilisateurs » et « établissements » ont été validés par Hugo ;
  les chiffres de résultat (15 %, 2 à 3×) sont ceux du discours Urbest.
  Le nombre de Régions et d'établissements La Salle est calculé à partir de data/references.")

;; Espaces insécables : U+00A0 (normale) et U+202F (fine, avant %, ×, : et dans les milliers).

(def translations
  {:fr
   {:meta-title        "Références clients | Urbest"
    :meta-description  "Plus de 20 000 utilisateurs, des services généraux d'entreprise à plus de 300 établissements scolaires, gèrent leur maintenance avec Urbest."
    :nav-label         "Navigation principale"
    :nav-demo          "Réserver une démo"
    :lang-switch       "English"
    :lang-switch-label "Read this page in English"

    :hero-eyebrow      "Références clients"
    :hero-title        "Un outil n'a de valeur que si les équipes s'en servent."
    :hero-lead         "Plus de 20 000 personnes utilisent Urbest pour signaler un problème, suivre une intervention et piloter la maintenance de leurs bâtiments. Voici une partie des organisations qui nous font confiance."
    :hero-stats        [{:value "+20 000" :label "utilisateurs"}
                        {:value "8" :label "pays"}]
    :jump-label        "Aller aux références"
    :jump-sgfm         "Services généraux & FM"
    :jump-education    "Éducation"

    :sgfm-eyebrow      "Services généraux & facility management"
    :sgfm-title        "Moins de relances, plus d'interventions bouclées."
    :sgfm-text         "Une fuite signalée par mail, un devis perdu dans un fil WhatsApp, un occupant qui relance pour la troisième fois : c'est le quotidien de beaucoup d'équipes. Avec Urbest, occupants, responsables et prestataires partagent le même fil. La demande arrive complète, photo à l'appui, et chacun voit où elle en est."
    :sgfm-tags         ["Bureaux et sièges" "Santé" "Collectivités" "Hôtellerie et résidences" "Commerce" "Industrie"]
    :sgfm-stats        [{:value "2 à 3×" :label "plus rapide pour résoudre les demandes"}
                        {:value "15 %" :label "de temps administratif gagné"}
                        {:value "4,8/5" :label "sur GetApp, 33 avis" :link :getapp}]
    :sgfm-source       "Sources : Urbest ; GetApp, octobre 2026."
    :sgfm-marquee      "Logos des clients services généraux et facility management"

    :edu-eyebrow       "Éducation"
    :edu-title         "Un lycée n'est pas un immeuble de bureaux."
    :edu-text          "Un radiateur froid un lundi matin, un vidéoprojecteur en panne avant un examen : dans un établissement scolaire, chaque demande touche des élèves. Avec Urbest, enseignants et personnels signalent depuis leur téléphone, les agents savent quoi faire, et le gestionnaire n'a plus à faire le lien. Que vous gériez un établissement ou les lycées de toute une Région."
    :edu-tags          ["Écoles et collèges" "Lycées généraux, professionnels et agricoles" "Enseignement supérieur" "CFA"]
    :edu-regions       "Régions déploient Urbest dans leurs lycées"
    :edu-schools       {:value "+300" :label "établissements scolaires"}
    :edu-la-salle      "établissements du réseau La Salle"
    :edu-marquee       "Logos des clients du secteur de l'éducation"

    :marquee-pause     "Mettre en pause le défilement des logos"
    :marquee-play      "Reprendre le défilement des logos"

    :demo-eyebrow      "Rejoignez-les"
    :demo-title        "Démarrez en moins de 30 minutes"
    :demo-includes     "La démo comprend :"
    :demo-items        ["Questions et réponses"
                        "Le paramétrage de votre patrimoine ou bâtiment"
                        "Une période d'essai de 30 jours"]
    :form-title        "Je souhaite une démo aujourd'hui"
    :form-full-name    "Nom complet"
    :form-job          "Poste"
    :form-company      "Entreprise"
    :form-email        "Veuillez utiliser une adresse e-mail professionnelle ou contactez-nous si vous n'en avez pas."
    :form-email-title  "Les adresses e-mail de gmail.com, outlook.com, yahoo.com et hotmail.com ne sont pas autorisées."
    :form-phone        "Numéro de téléphone"
    :form-privacy      "* Nous ne partageons pas ces informations avec des tiers comme écrit dans notre "
    :form-privacy-link "Politique de confidentialité"
    :form-submit       "Envoyer"
    :form-sending      "Envoi en cours…"
    :form-error        "L'envoi n'a pas abouti. Réessayez dans un instant."
    :form-dev-sent     "Mode développement : la demande n'a pas été envoyée."

    :footer-tagline    "Logiciel collaboratif pour la maintenance et les services généraux."
    :footer-label      "Liens Urbest"
    :footer-privacy    "Confidentialité"
    :footer-contact    "Contact"}

   :en
   {:meta-title        "Customer references | Urbest"
    :meta-description  "More than 20,000 users, from corporate facility teams to over 300 schools, run their maintenance and facility services with Urbest."
    :nav-label         "Main navigation"
    :nav-demo          "Book a demo"
    :lang-switch       "Français"
    :lang-switch-label "Lire cette page en français"

    :hero-eyebrow      "Customer references"
    :hero-title        "Software only matters if teams actually use it."
    :hero-lead         "More than 20,000 people use Urbest to report issues, follow up on work orders and run maintenance across their buildings. Here are some of the organisations that trust us."
    :hero-stats        [{:value "20,000+" :label "users"}
                        {:value "8" :label "countries"}]
    :jump-label        "Go to references"
    :jump-sgfm         "Facility services & FM"
    :jump-education    "Education"

    :sgfm-eyebrow      "Facility services & facility management"
    :sgfm-title        "Fewer follow-ups, more jobs closed."
    :sgfm-text         "A leak reported by email, a quote lost in a WhatsApp thread, an occupant chasing for the third time: that is everyday life for many teams. With Urbest, occupants, managers and service providers share one thread. Requests arrive complete, with photos, and everyone can see where things stand."
    :sgfm-tags         ["Offices & headquarters" "Healthcare" "Public sector" "Hospitality & residential" "Retail" "Industry"]
    :sgfm-stats        [{:value "2–3×" :label "faster request resolution"}
                        {:value "15%" :label "less time spent on admin"}
                        {:value "4.8/5" :label "on GetApp, 33 reviews" :link :getapp}]
    :sgfm-source       "Sources: Urbest; GetApp, October 2026."
    :sgfm-marquee      "Logos of facility services and facility management customers"

    :edu-eyebrow       "Education"
    :edu-title         "A school is not an office building."
    :edu-text          "A cold radiator on a Monday morning, a broken projector right before an exam: in a school, every request affects students. With Urbest, teachers and staff report issues from their phone, technicians know what to do, and the business manager no longer has to relay everything. Whether you run one school or every high school in a region."
    :edu-tags          ["Primary & middle schools" "General, vocational & agricultural high schools" "Higher education" "Apprenticeship centres"]
    :edu-regions       "French regions roll out Urbest in their high schools"
    :edu-schools       {:value "300+" :label "schools"}
    :edu-la-salle      "schools in the La Salle network"
    :edu-marquee       "Logos of education customers"

    :marquee-pause     "Pause the logo animation"
    :marquee-play      "Resume the logo animation"

    :demo-eyebrow      "Join them"
    :demo-title        "Get started in less than 30 minutes"
    :demo-includes     "Demo includes:"
    :demo-items        ["Questions and answers"
                        "Customisation of your first building"
                        "A free 30 day trial"]
    :form-title        "Request a demo today"
    :form-full-name    "Full name"
    :form-job          "Job title"
    :form-company      "Company"
    :form-email        "Please use a professional email or email us if you don't have one."
    :form-email-title  "Emails from gmail.com, outlook.com, yahoo.com, and hotmail.com are not allowed."
    :form-phone        "Phone number"
    :form-privacy      "* We do not share this information with third parties as written in our "
    :form-privacy-link "Privacy Policy"
    :form-submit       "Submit Request"
    :form-sending      "Sending…"
    :form-error        "Something went wrong. Please try again in a moment."
    :form-dev-sent     "Development mode: the request was not sent."

    :footer-tagline    "Collaborative software for maintenance and facility services."
    :footer-label      "Urbest links"
    :footer-privacy    "Privacy"
    :footer-contact    "Contact"}})

(defn t [lang k]
  (get-in translations [lang k] (str "⟨" (name k) "⟩")))

(defn other-lang [lang]
  (if (= :fr lang) :en :fr))
