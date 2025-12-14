import streamlit as st
import requests

st.title("Teste ta to do list :")
st.subheader("Ici tu peux voir toutes tes tâches, en ajouter et en supprimer !")

# URL de base de ton serveur Scala
BASE_URL = "http://localhost:8090"

# Fonction pour récupérer toutes les tâches
def get_all_todos():
    try:
        response = requests.get(f"{BASE_URL}/todo")
        if response.status_code == 200:
            return response.json()
        else:
            st.error(f"Erreur: {response.status_code}")
            return []
    except Exception as e:
        st.error(f"Erreur de connexion: {e}")
        return []

# Afficher les tâches

todos = get_all_todos()
if todos:
    for todo in todos:
        st.checkbox(f"**{todo['title']}**: {todo['description']}")
else:
    st.write("Aucune tâche trouvée.")

# Ajouter une nouvelle tâche
st.subheader("Ajouter une nouvelle tâche")
title = st.text_input("Titre")
description = st.text_input("Description")

if st.button("Ajouter"):
    if title and description:
        data = {"title": title, "description": description}
        try:
            response = requests.post(f"{BASE_URL}/todo", json=data)
            if response.status_code == 200:
                st.success("Tâche ajoutée !")
                st.rerun()  # Rafraîchir pour mettre à jour la liste
            else:
                st.error(f"Erreur: {response.status_code}")
        except Exception as e:
            st.error(f"Erreur: {e}")
    else:
        st.error("Remplis tous les champs.")




