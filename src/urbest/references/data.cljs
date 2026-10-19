(ns urbest.references.data
  "Références clients, une entrée par ligne du fichier « références.xlsx » (même ordre).

  - :nom     libellé tel qu'il apparaît dans le fichier Excel (traçabilité) ;
  - :secteur colonne « secteur » du fichier : SG, Tertiaire, Residence ou Education ;
  - :label   nom affiché (texte alternatif du logo, ou logo typographique) ;
  - :logo    identifiant du fichier dans logos/source (sans extension),
             nil quand aucun logo exploitable n'existe : le nom s'affiche alors en texte ;
  - :reseau  :la-salle pour les établissements du réseau lasallien.

  Pour ajouter une référence : ajouter une ligne ici, déposer le logo dans
  logos/source/<logo>.(svg|png|jpg|webp) puis lancer `npm run logos`.")

(def references
  [{:nom "Région Normandie" :secteur "SG" :label "Région Normandie" :logo "region-normandie"}
   {:nom "Paris la Défense" :secteur "SG" :label "Paris La Défense" :logo "paris-la-defense"}
   {:nom "Lycée Jules Ferry (06)" :secteur "Education" :label "Lycée Jules Ferry" :logo nil}
   {:nom "68 Saint Honoré (Engie)" :secteur "Tertiaire" :label "Engie" :logo "engie"}
   {:nom "Hôpital la Musse" :secteur "SG" :label "Hôpital La Musse – La Renaissance Sanitaire" :logo "la-renaissance-sanitaire"}
   {:nom "Pénélope" :secteur "Tertiaire" :label "Pénélope" :logo "penelope"}
   {:nom "Pénélope - Carsat Amiens" :secteur "Tertiaire" :label "Carsat" :logo "carsat"}
   {:nom "Athena Care home" :secteur "SG" :label "Athena Care Group" :logo "athena-care-group"}
   {:nom "Région Normandie (lycées)" :secteur "Education" :label "Région Normandie" :logo "region-normandie"}
   {:nom "Advantail - Usine Center" :secteur "Tertiaire" :label "Usines Center" :logo "usines-center"}
   {:nom "LaCasa.io" :secteur "Residence" :label "La Casa" :logo "la-casa"}
   {:nom "Puissance 5 - Organet" :secteur "Tertiaire" :label "Puissance 5" :logo "puissance-5"}
   {:nom "Lycée Guez de Balzac (16)" :secteur "Education" :label "Lycée Guez de Balzac" :logo nil}
   {:nom "Advantail" :secteur "Tertiaire" :label "Advantail" :logo "advantail"}
   {:nom "Advantail -Nailloux" :secteur "Tertiaire" :label "Nailloux Outlet Village" :logo "nailloux-outlet-village"}
   {:nom "Advantail - La Séguinière" :secteur "Tertiaire" :label "La Séguinière Outlet" :logo "la-seguiniere-outlet"}
   {:nom "Advantail" :secteur "Tertiaire" :label "Advantail" :logo "advantail"}
   {:nom "Advantail" :secteur "Tertiaire" :label "Advantail" :logo "advantail"}
   {:nom "Advantail" :secteur "Tertiaire" :label "Advantail" :logo "advantail"}
   {:nom "CBRE" :secteur "Tertiaire" :label "CBRE" :logo "cbre"}
   {:nom "CBRE" :secteur "Tertiaire" :label "CBRE" :logo "cbre"}
   {:nom "CBRE" :secteur "Tertiaire" :label "CBRE" :logo "cbre"}
   {:nom "Gladhome Gmbh" :secteur "Tertiaire" :label "gladhome" :logo "gladhome"}
   {:nom "Airelior (FM) Sinouhé Immobilier" :secteur "Tertiaire" :label "Sinouhé Immobilier" :logo nil}
   {:nom "Lycée Monge" :secteur "Education" :label "Lycée Monge" :logo "lycee-monge"}
   {:nom "CD 22 (Côte d'Armor)" :secteur "SG" :label "Côtes d'Armor – le Département" :logo "cotes-darmor"}
   {:nom "CBRE" :secteur "Tertiaire" :label "CBRE" :logo "cbre"}
   {:nom "Hopital Guebwiller +" :secteur "SG" :label "Hôpital de Guebwiller" :logo nil}
   {:nom "La cinémathèque" :secteur "SG" :label "La Cinémathèque française" :logo "cinematheque-francaise"}
   {:nom "Lycée Hotelier Notre dame" :secteur "Education" :label "Lycée hôtelier Notre-Dame" :logo "lycee-hotelier-notre-dame"}
   {:nom "Portalis (Engie AEW)" :secteur "Tertiaire" :label "AEW" :logo "aew"}
   {:nom "Daviel (Engie)" :secteur "Tertiaire" :label "Engie" :logo "engie"}
   {:nom "Lycée le chesnoy" :secteur "Education" :label "Lycée Le Chesnoy" :logo nil}
   {:nom "Général Foy (Engie)" :secteur "Tertiaire" :label "Engie" :logo "engie"}
   {:nom "Altarea Cogedim" :secteur "Tertiaire" :label "Altarea" :logo "altarea"}
   {:nom "Airelior (FM) Pierre Grenier" :secteur "Tertiaire" :label "Airelior" :logo nil}
   {:nom "Groupama Immobilier" :secteur "Tertiaire" :label "Groupama Immobilier" :logo "groupama"}
   {:nom "Association Marie Durand" :secteur "Residence" :label "Association Marie Durand" :logo "marie-durand"}
   {:nom "Centre Petite Enfance Nivelles" :secteur "SG" :label "Petite Enfance Nivelles" :logo nil}
   {:nom "SPSTI" :secteur "SG" :label "SPSTI2A" :logo "spsti2a"}
   {:nom "Lycée Privé Charles Peguy" :secteur "Education" :label "Charles Péguy Paris" :logo "charles-peguy"}
   {:nom "Neinver" :secteur "Tertiaire" :label "Neinver" :logo "neinver"}
   {:nom "SCC - Passage du Havre" :secteur "Tertiaire" :label "Passage du Havre" :logo "passage-du-havre"}
   {:nom "Inowai" :secteur "Tertiaire" :label "Inowai" :logo "inowai"}
   {:nom "Open House coliving" :secteur "Residence" :label "Openhouse Coliving" :logo nil}
   {:nom "So villas" :secteur "Residence" :label "So Villas" :logo "so-villas"}
   {:nom "Lycée de Bellegarde" :secteur "Education" :label "Notre-Dame de Bellegarde" :logo "notre-dame-de-bellegarde"}
   {:nom "CFA Loiret" :secteur "Education" :label "CFA agricole du Loiret" :logo "cfa-loiret"}
   {:nom "Rayons de soleil de l'enfance" :secteur "Residence" :label "Rayons de soleil de l'enfance" :logo "rayons-de-soleil"}
   {:nom "Hotel Hor" :secteur "Residence" :label "Hôtel Hor" :logo "hotel-hor"}
   {:nom "Rosny 2" :secteur "Tertiaire" :label "Westfield Rosny 2" :logo "westfield-rosny-2"}
   {:nom "Ecoris" :secteur "Education" :label "Ecoris" :logo "ecoris"}
   {:nom "Roofinders" :secteur "Residence" :label "RooFinders" :logo "roofinders"}
   {:nom "ISC la ville du bois" :secteur "Education" :label "Institution du Sacré-Cœur" :logo "isc-ville-du-bois"}
   {:nom "Geodis - Bureau" :secteur "SG" :label "Geodis" :logo "geodis"}
   {:nom "Groupe Saint Genès la Salle" :secteur "Education" :label "Saint-Genès La Salle" :logo "saint-genes-la-salle" :reseau :la-salle}
   {:nom "Engie - Rue Scribe" :secteur "Tertiaire" :label "Engie" :logo "engie"}
   {:nom "Foyer de la Jaunaie" :secteur "Residence" :label "Foyer de la Jaunaie – ADAES 44" :logo "adaes44"}
   {:nom "Twenty Seven Real Estate" :secteur "Residence" :label "Twenty Seven Real Estate" :logo "twenty-seven"}
   {:nom "Groupe Scolaire La Providence" :secteur "Education" :label "La Providence Orchies" :logo "la-providence-orchies"}
   {:nom "Lycée Jeanne d'Arc" :secteur "Education" :label "Lycée Jeanne d'Arc – Saint-François d'Assise" :logo "st-francois-dassise"}
   {:nom "Ville d'Ettelbruck" :secteur "SG" :label "Ville d'Ettelbruck" :logo "ettelbruck"}
   {:nom "Dedalus HEALTHCARE France" :secteur "SG" :label "Dedalus" :logo "dedalus"}
   {:nom "Institut Saint Dominique" :secteur "Education" :label "Institut Saint-Dominique" :logo "saint-dominique-pau"}
   {:nom "Vallair" :secteur "SG" :label "Vallair" :logo "vallair"}
   {:nom "Setec AS" :secteur "SG" :label "Setec" :logo "setec"}
   {:nom "Pénélope" :secteur "SG" :label "Pénélope" :logo "penelope"}
   {:nom "Hermione Retail Amiens" :secteur "Tertiaire" :label "Hermione Retail" :logo "hermione-retail"}
   {:nom "Pénélope" :secteur "SG" :label "Pénélope" :logo "penelope"}
   {:nom "Disportex" :secteur "SG" :label "Disportex" :logo "disportex"}
   {:nom "Prefecture des Hauts-de-Seine" :secteur "SG" :label "Préfecture des Hauts-de-Seine" :logo "prefet-hauts-de-seine"}
   {:nom "Groupe Scolaire Saint Joseph La Salle - Pantin" :secteur "Education" :label "Saint-Joseph La Salle Pantin" :logo "la-salle-pantin" :reseau :la-salle}
   {:nom "Hoppen" :secteur "Education" :label "Hoppen" :logo "hoppen"}
   {:nom "Fresenius Medical Care" :secteur "SG" :label "Fresenius Medical Care" :logo "fresenius-medical-care"}
   {:nom "Region Auvergne Rhone Alpes" :secteur "Education" :label "Région Auvergne-Rhône-Alpes" :logo "region-aura"}
   {:nom "ICOF" :secteur "Education" :label "ICOF" :logo "icof"}
   {:nom "Groupe Scolaire La Croix Rouge La Salle" :secteur "Education" :label "La Croix Rouge La Salle" :logo "la-croix-rouge-la-salle" :reseau :la-salle}
   {:nom "IUT d'Evry Val d'Essonne" :secteur "Education" :label "IUT d'Évry – Université Évry Paris-Saclay" :logo "universite-evry"}
   {:nom "Lycée Le Cleusmeur" :secteur "Education" :label "Lycée Le Cleusmeur" :logo "le-cleusmeur"}
   {:nom "Château Margaux" :secteur "SG" :label "Château Margaux" :logo "chateau-margaux"}
   {:nom "Rivoli Avenir Patrimoine" :secteur "Tertiaire" :label "Rivoli Avenir Patrimoine – Amundi" :logo "amundi"}
   {:nom "SCI SEO C/O CONSTRUCTA ASSET MANAGEMENT" :secteur "Tertiaire" :label "Constructa Asset Management" :logo "constructa"}
   {:nom "Institution Deforest de Lewarde" :secteur "Education" :label "Deforest de Lewarde" :logo nil :reseau :la-salle}
   {:nom "Groupe scolaire Lazaristes Lyon" :secteur "Education" :label "Aux Lazaristes La Salle" :logo "aux-lazaristes" :reseau :la-salle}
   {:nom "Groupe scolaire Lagny" :secteur "Education" :label "Saint-Laurent La Paix Notre-Dame" :logo "maristes-lagny"}
   {:nom "Indigo Publications" :secteur "SG" :label "Indigo Publications" :logo "indigo-publications"}
   {:nom "Foyer Seine et Marne" :secteur "SG" :label "Foyer de Seine-et-Marne" :logo "fsm"}
   {:nom "Ecole Mariste Bury-Rosaire" :secteur "Education" :label "Bury-Rosaire" :logo "bury-rosaire"}
   {:nom "Ecole Adrien Zeller" :secteur "Education" :label "CEFPPA Adrien Zeller" :logo "cefppa-adrien-zeller"}
   {:nom "Jeantet (avocats)" :secteur "SG" :label "Jeantet" :logo "jeantet"}
   {:nom "Comme des garçons" :secteur "SG" :label "Comme des Garçons" :logo "comme-des-garcons"}
   {:nom "3 Hôtels : Dauphine - de Launay and Bastille" :secteur "Residence" :label "Hôtel Marais de Launay" :logo "hotel-marais-de-launay"}
   {:nom "Région Centre - Val de Loire" :secteur "Education" :label "Région Centre-Val de Loire" :logo "region-cvdl"}
   {:nom "MC habitat" :secteur "Residence" :label "MC Habitat" :logo "mc-habitat"}
   {:nom "Ecole Ginette - versailles" :secteur "Education" :label "Sainte-Geneviève (Ginette)" :logo "ginette"}
   {:nom "Lycée des Calanques" :secteur "Education" :label "Lycée des Calanques" :logo nil}
   {:nom "Max Mara" :secteur "SG" :label "Max Mara" :logo "max-mara"}
   {:nom "Centre scolaire Saint Louis - Lyon" :secteur "Education" :label "Saint-Louis Saint-Bruno" :logo "saint-louis-saint-bruno"}
   {:nom "Lycée La Salle - Pruillé le Chétif" :secteur "Education" :label "Saint-Joseph La Salle Pruillé" :logo "la-salle-pruille" :reseau :la-salle}
   {:nom "AEFE Athènes" :secteur "SG" :label "AEFE" :logo "aefe"}
   {:nom "Hotel Dar El MArsa" :secteur "Residence" :label "Dar El Marsa" :logo nil}
   {:nom "Endress" :secteur "SG" :label "Endress+Hauser" :logo "endress-hauser"}
   {:nom "Sainte Marie - Neuilly" :secteur "Education" :label "Sainte-Marie de Neuilly" :logo "sainte-marie-neuilly"}
   {:nom "Ensemble Scolaire La Salle - Alès" :secteur "Education" :label "La Salle Alès" :logo "la-salle-ales" :reseau :la-salle}
   {:nom "Collège Saint-Bruno La Salle - Marseille" :secteur "Education" :label "La Salle Saint-Bruno Marseille" :logo "la-salle-saint-bruno" :reseau :la-salle}
   {:nom "Institut Français de Grèce" :secteur "Education" :label "Institut français de Grèce" :logo "institut-francais-grece"}
   {:nom "Lycée La Salle - Nîmes" :secteur "Education" :label "La Salle Nîmes" :logo "la-salle-nimes" :reseau :la-salle}
   {:nom "Chancellerie des universités de Paris" :secteur "Education" :label "Chancellerie des universités de Paris" :logo nil}
   {:nom "EANM Mas Saint Pierre" :secteur "Education" :label "Le Mas Saint-Pierre" :logo nil}
   {:nom "Institution Saint Jean-Baptiste de La Salle - Valenciennes" :secteur "Education" :label "Saint-Jean-Baptiste de La Salle Valenciennes" :logo "la-salle-valenciennes" :reseau :la-salle}
   {:nom "Groupe Scolaire Saint Joseph La Salle - Troyes" :secteur "Education" :label "Saint-Joseph La Salle Troyes" :logo "la-salle-troyes" :reseau :la-salle}
   {:nom "GROUPE MONT ROLAND LA SALLE - DOLE" :secteur "Education" :label "Mont-Roland La Salle Dole" :logo "mont-roland" :reseau :la-salle}
   {:nom "Ensemble Scolaire Saint Joseph-La Salle - Lorient" :secteur "Education" :label "Saint-Joseph La Salle Lorient" :logo "la-salle-lorient" :reseau :la-salle}
   {:nom "Établissement Sainte-Thérèse - Écouen" :secteur "Education" :label "Sainte-Thérèse Écouen" :logo "sainte-therese-ecouen"}
   {:nom "ATEQ" :secteur "SG" :label "ATEQ" :logo "ateq"}
   {:nom "BTK" :secteur "SG" :label "BTK Bank" :logo "btk"}])

;; ── Lecture ─────────────────────────────────────────────────────────────────

(defn block
  "Les deux blocs de la page : :education, et :sg-fm pour tout le reste
  (services généraux, tertiaire et résidentiel)."
  [{:keys [secteur]}]
  (if (= "Education" secteur) :education :sg-fm))

(defn logos-for
  "Références d'un bloc, dans l'ordre du fichier. Les lignes qui partagent le même
  logo (ex. plusieurs sites CBRE) n'apparaissent qu'une fois."
  [b]
  (->> references
       (filter #(= b (block %)))
       (map #(assoc % :id (or (:logo %) (:label %))))
       (reduce (fn [acc r] (if (some #(= (:id r) (:id %)) acc) acc (conj acc r))) [])))

(defn network-count [r]
  (count (filter #(= r (:reseau %)) references)))
