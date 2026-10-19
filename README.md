# Urbest · Références clients

Landing page bilingue (FR/EN) présentant les références clients d'Urbest en deux blocs,
**Services généraux & FM** et **Éducation**, chacun avec un texte court, des chiffres
et un bandeau de logos qui défile. La page se termine par le bloc de demande de démo
des autres pages d'urbest.io.

- ClojureScript : shadow-cljs 2.27, reagent 1.2, re-frame 1.3 (mêmes conventions que les modules Urbest) ;
- pages **pré-rendues** en HTML (référencement, affichage immédiat), puis hydratées par le bundle ;
- logos harmonisés en bleu nuit Urbest (#130046), blancs sur le bloc Éducation ;
- charte du brandbook : palette officielle, Proxima Nova, bulles de dialogue.

## Démarrer

Prérequis : Node 18+ et Java 11+ (pour shadow-cljs). Le CLI `clojure` n'est pas nécessaire.

```bash
npm install
npm run dev        # http://localhost:8280/dev.html (et ?lang=en), rechargement à chaud
npm run build      # logos + bundle de production + pages docs/fr et docs/en
npm run serve      # sert docs/ sur http://localhost:8281/fr/
```

En développement, le formulaire de démo n'envoie rien (message « Mode développement »).

## Structure

```
src/urbest/references/
  core.cljs        point d'entrée navigateur (hydratation)
  db.cljs          état initial re-frame
  events.cljs      événements et effets (pause des bandeaux, envoi du formulaire)
  subs.cljs        abonnements
  views.cljs       composants reagent de la page
  i18n.cljs        tous les textes FR/EN
  data.cljs        les 116 lignes de « références.xlsx », avec le logo associé
  logos.cljs       dimensions des logos (généré par npm run logos)
  config.cljs      URL du formulaire, chemins, liens
  tools/           scripts Node : harmonisation des logos, pré-rendu
logos/source/      logos d'origine (provenance : logos/SOURCES.md)
docs/              site publié : fr/, en/, assets/ (css, polices, logos, js)
dev/dev.html       page de développement
```

## Modifier le contenu

- **Textes et chiffres** : `src/urbest/references/i18n.cljs`. Dans le bloc Éducation, le nombre
  de Régions et d'établissements La Salle est calculé depuis `data.cljs`.
- **Ajouter une référence** : ajouter une ligne dans `data.cljs`, déposer le logo dans
  `logos/source/<identifiant>.(svg|png|jpg|webp)`, puis `npm run build`.
  Sans logo (`:logo nil`), le nom s'affiche en toutes lettres.
- Les lignes qui partagent un logo (plusieurs sites CBRE, Engie, Advantail…) n'apparaissent
  qu'une fois dans le bandeau.
- Le build échoue, sans réécrire les pages, si une référence pointe vers un logo absent de
  `logos.cljs` (relancer `npm run logos`) ou si une traduction manque dans l'une des langues.

## Harmonisation des logos

`npm run logos` transforme chaque logo en silhouette monochrome :
le fond est détecté, l'écart de chaque pixel au fond devient l'opacité d'un aplat bleu nuit,
puis le logo est recadré et dimensionné pour que tous aient le même poids visuel
(PNG 16 niveaux, à 2× pour les écrans Retina, ~2,7 Ko par logo).
Une planche de contrôle est générée dans `target/logos-planche.png`.

Réglages par logo dans `src/urbest/references/tools/logos.cljs` (`options`) :
`:mode :alpha` pour un logo blanc, `:crop` pour recadrer, `:gamma` pour renforcer un logo pâle.

## Publication sur GitHub Pages

Le dossier `docs/` est le site : *Settings → Pages → Deploy from a branch → main / docs*.
La racine redirige vers `fr/` ou `en/` selon la langue du navigateur.
Les pages portent une balise `noindex` tant qu'elles servent de prévisualisation.
Les adresses du JS et du CSS portent une empreinte de leur contenu (`?v=…`), recalculée à chaque
build : un visiteur ne peut pas combiner une page à jour avec un ancien bundle resté en cache.

## Intégration à urbest.io

- Pages cibles prévues : `/fr/references/` et `/en/references/` (balises canonical et hreflang,
  voir `page-path` dans `config.cljs`).
- Pour la production : `URBEST_INDEX=1 npm run build` retire le `noindex`.
- Les ressources sont appelées en relatif (`../assets/`, voir `assets-path` dans `config.cljs`) :
  à adapter selon l'emplacement final.
- L'en-tête et le pied de page sont volontairement simples : à remplacer par ceux du site.
- Le formulaire utilise le même script Google Apps Script que `landings.js` et redirige vers
  `/{lang}/demo-request-success/`. Le suivi Capterra est appelé s'il est présent sur la page.
  Le bouton « Envoyer » est désactivé dans le HTML pré-rendu et ne s'active qu'une fois le
  JavaScript chargé ; le formulaire est en `method="post"`, si bien que les coordonnées ne
  passent jamais dans l'URL. L'envoi est abandonné au bout de 20 s avec un message d'erreur
  (`DEMO_TIMEOUT_MS` dans `config.cljs`).
- Police : la page utilise Proxima Nova (fichiers du brandbook convertis en WOFF2), avec
  CircularStd, la police d'urbest.io, en secours. **Vérifier que la licence Proxima Nova
  couvre l'usage web** ; sinon, supprimer les `@font-face` de `references.css` et la page
  basculera sur CircularStd une fois intégrée au site.
