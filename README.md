# programmation_fonctionelle
Web serveur HTTP

Ce serveur HTTP (avec ZIO) permet de construire sa to do list. On peut ajouter une tâche avec un nom et une description (POST), recuperer toutes les tâches de la to do list (GET), et même en supprimer avec l'id de la tâche (DELETE). 
Comme la partie DELETE n'était pas demandé, elle n'est pas disponible à tester sur l'application web streamlit mais peut être tester avec un curl par exemple. 

**Part 0 - Reccuperer le git**
-> `git clone`

**Part 1 - Le serveur** \n
`cd programmation_fonctionelle`\n
`sbt run`\n

Il est possible de le tester dans un autre terminal avec "curl". Mais pour le tester plus simplement : 

**Part 2 - Tester le serveur sur une app Streamlit**\n

-> Avant de pouvoir lancer l'app : \n

(Il faut être à la racine du projet)\n
\n
`python -m venv .venv`\n
`source .venv/bin/activate`\n
(.venv) `pip install streamlit`\n

->Lancer l'app\n

(.venv) `streamlit run app.py`\n
