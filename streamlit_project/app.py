import streamlit as st
import my_pages.home as home
import my_pages.dozenten as dozenten
import my_pages.zeitslots as zeitslots
import sys
import os

# Hauptverzeichnis zum Python-Suchpfad hinzufügen
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))
# Streamlit-Konfiguration
st.set_page_config(page_title="Analyse-App", layout="centered", initial_sidebar_state="expanded")

# Seiten-Navigation
page = st.sidebar.selectbox("Navigation", ["Startseite", "Dozenten Analyse", "Zeitslots Analyse"])

if page == "Startseite":
    home.show()

elif page == "Dozenten Analyse":
    dozenten.show()

elif page == "Zeitslots Analyse":
    zeitslots.show()