# programmation_fonctionelle
Web serveur HTTP


**Part 0 - Reccuperer le git**
-> `git clone`

**Part 1 - Le serveur**
`cd programmation_fonctionelle`
`sbt run`

Il est possible de le tester dans un autre terminal avec "curl". Mais pour le tester plus simplement : 

**Part 2 - Tester le serveur sur une app Streamlit**

-> Avant de pouvoir lancer l'app : 

(Il faut être à la racine du projet)

`python -m venv .venv`
`source .venv/bin/activate`
(.venv) `pip install streamlit`

->Lancer l'app

(.venv) `streamlit run app.py`