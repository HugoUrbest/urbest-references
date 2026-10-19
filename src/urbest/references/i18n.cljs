(ns urbest.references.i18n
  "Textes de la page en français et en anglais.
  Les chiffres « utilisateurs » et « établissements » ont été validés par Hugo ;
  les chiffres de résultat (15 %, 2 à 3×) sont ceux du discours Urbest.
  Le nombre de Régions et d'établissements La Salle est calculé à partir de data/references.")

;; Espaces insécables : U+00A0 (normale) et U+202F (fine, avant %, ×, : et dans les milliers).

(def translations
  {:fr
   {:meta-title        "Références GMAO : services généraux et lycées | Urbest"
    :meta-description  "Plus de 20 000 utilisateurs gèrent leurs demandes d'intervention avec Urbest : sièges, santé, commerce, collectivités et plus de 300 établissements scolaires."
    :nav-label         "Navigation principale"
    :nav-demo          "Réserver une démo"
    :lang-switch       "English"
    :lang-switch-label "Read this page in English"

    :hero-eyebrow      "Références clients"
    :hero-title        "Ils pilotent la maintenance de leurs bâtiments avec Urbest."
    :hero-subtitle     "Un outil n'a de valeur que si les équipes s'en servent."
    :hero-lead         "Urbest est un logiciel de GMAO et de gestion des demandes d'intervention. Plus de 20 000 personnes l'utilisent pour signaler un problème, suivre une intervention et piloter la maintenance de leurs bâtiments. Voici une partie des organisations qui nous font confiance."
    :hero-stats        [{:value "+20 000" :label "utilisateurs"}
                        {:value "+7 millions" :label "de m² gérés avec Urbest"}
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

    :refs-list         "Voir la liste des références"

    :marquee-pause     "Mettre en pause le défilement des logos"
    :marquee-play      "Reprendre le défilement des logos"

    :testimonials-eyebrow "Témoignages"
    :testimonials-title "Ce qui a changé chez eux"
    :testimonials      [{:tag     "Éducation"
                         :title   "Un lycée public de Caen, en Normandie"
                         :text    "Environ 1 200 élèves. Toutes les demandes de maintenance passent désormais par Urbest, du signalement à la clôture."
                         :figures [{:value "6 à 8" :label "demandes traitées par jour"}
                                   {:value "≈ 1 h" :label "gagnée par technicien et par jour"}]
                         :quote   "Je peux montrer que notre équipe est réactive."
                         :author  "Responsable de site"}
                        {:tag     "Services généraux"
                         :title   "Paris La Défense"
                         :text    "Employés, gardiens et responsables des bureaux utilisent Urbest au quotidien pour l'exploitation et la maintenance, avec un retour à chaque demandeur."
                         :quote   "Nous avons constaté une vraie amélioration dans la gestion des demandes d'intervention."
                         :author  "Responsable workplace, Paris La Défense"}]
    :source            "Source"

    :faq-eyebrow       "Questions fréquentes"
    :faq-title         "Urbest en quelques questions"
    :faq               [{:q "Qu'est-ce qu'Urbest ?"
                         :a "Urbest est un logiciel de GMAO et de gestion des demandes d'intervention pour les organisations qui gèrent un ou plusieurs bâtiments. Occupants, équipes techniques et prestataires y signalent une demande avec photo, la suivent et la clôturent, depuis le web ou l'application mobile."}
                        {:q "Qui utilise Urbest ?"
                         :a "Plus de 20 000 utilisateurs dans 8 pays : services généraux d'entreprise, santé, collectivités, commerce, résidences, et plus de 300 établissements scolaires, dont les lycées des Régions Normandie, Centre-Val de Loire et Auvergne-Rhône-Alpes."}
                        {:q "Urbest est-il adapté aux lycées et aux Régions ?"
                         :a "Oui. Enseignants et personnels signalent depuis leur téléphone, les agents savent quoi faire, et le gestionnaire suit tout au même endroit. Urbest sert aussi bien un établissement seul qu'une Région pour l'ensemble de ses lycées."}
                        {:q "Quelle différence avec une GMAO industrielle ?"
                         :a "Une GMAO industrielle est construite autour des équipements et de la maintenance des machines. Urbest est construit autour du flux de demandes entre occupants, responsables et prestataires, sur un ou plusieurs sites."}
                        {:q "Comment démarrer avec Urbest ?"
                         :a "Demandez une démo : nous répondons à vos questions et paramétrons votre premier bâtiment ou votre patrimoine. Une période d'essai de 30 jours est incluse."}
                        {:q "Quelle est la note d'Urbest sur les plateformes d'avis ?"
                         :a "4,8/5 sur GetApp, sur 33 avis."}]
    :updated           "Dernière mise à jour : "
    :breadcrumb-page   "Références clients"

    :demo-eyebrow      "Rejoignez-les"
    :demo-title        "Voyez Urbest sur votre propre bâtiment"
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
   {:meta-title        "CMMS customer references: facility services and schools | Urbest"
    :meta-description  "More than 20,000 users manage their work requests with Urbest: headquarters, healthcare, retail, public sector and over 300 schools."
    :nav-label         "Main navigation"
    :nav-demo          "Book a demo"
    :lang-switch       "Français"
    :lang-switch-label "Lire cette page en français"

    :hero-eyebrow      "Customer references"
    :hero-title        "They run maintenance across their buildings with Urbest."
    :hero-subtitle     "Software only matters if teams actually use it."
    :hero-lead         "Urbest is CMMS and work request management software. More than 20,000 people use it to report issues, follow up on work orders and run maintenance across their buildings. Here are some of the organisations that trust us."
    :hero-stats        [{:value "20,000+" :label "users"}
                        {:value "7M+" :label "m² managed with Urbest"}
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

    :refs-list         "See the full list of references"

    :marquee-pause     "Pause the logo animation"
    :marquee-play      "Resume the logo animation"

    :testimonials-eyebrow "Testimonials"
    :testimonials-title "What changed for them"
    :testimonials      [{:tag     "Education"
                         :title   "A public high school in Caen, Normandy"
                         :text    "About 1,200 students. Every maintenance request now goes through Urbest, from report to closure."
                         :figures [{:value "6–8" :label "requests handled per day"}
                                   {:value "≈ 1 h" :label "saved per technician per day"}]
                         :quote   "I can show that our team is responsive."
                         :author  "Site manager (translated from French)"}
                        {:tag     "Facility services"
                         :title   "Paris La Défense"
                         :text    "Employees, caretakers and office managers use Urbest every day for operations and maintenance, with feedback to every requester."
                         :quote   "We noticed a real improvement in job requests management."
                         :author  "Workplace manager, Paris La Défense"}]
    :source            "Source"

    :faq-eyebrow       "Frequently asked questions"
    :faq-title         "Urbest in a few questions"
    :faq               [{:q "What is Urbest?"
                         :a "Urbest is CMMS and work request management software for organisations that manage one or more buildings. Occupants, technical teams and service providers report a request with a photo, track it and close it, on the web or in the mobile app."}
                        {:q "Who uses Urbest?"
                         :a "More than 20,000 users in 8 countries: corporate facility teams, healthcare, public sector, retail, residential, and over 300 schools, including the high schools of the Normandy, Centre-Val de Loire and Auvergne-Rhône-Alpes regions."}
                        {:q "Is Urbest suited to schools and regional authorities?"
                         :a "Yes. Teachers and staff report issues from their phone, technicians know what to do, and the business manager follows everything in one place. Urbest works for a single school as well as for a region running all of its high schools."}
                        {:q "How is it different from an industrial CMMS?"
                         :a "An industrial CMMS is built around equipment and machine maintenance. Urbest is built around the flow of requests between occupants, managers and service providers, across one or more sites."}
                        {:q "How do I get started with Urbest?"
                         :a "Book a demo: we answer your questions and set up your first building or your portfolio. A 30-day trial is included."}
                        {:q "How is Urbest rated on review platforms?"
                         :a "4.8/5 on GetApp, from 33 reviews."}]
    :updated           "Last updated: "
    :breadcrumb-page   "Customer references"

    :demo-eyebrow      "Join them"
    :demo-title        "See Urbest on your own building"
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
