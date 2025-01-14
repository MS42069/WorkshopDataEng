import streamlit as st
from frontend import home
from frontend import dozenten
from frontend import zeitslots
import sys
import os

# Hauptverzeichnis zum Python-Suchpfad hinzufügen
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))
# Streamlit-Konfiguration
st.set_page_config(page_title="Analyse-App", layout="centered", initial_sidebar_state="expanded")

# Seiten-Navigation

# Absoluter Pfad zum Bild
logo_path = "assets/images/logo_ohne_hintergrund.png"
st.sidebar.image(logo_path, use_container_width=True)
page = st.sidebar.selectbox("Navigation", ["Startseite", "Dozenten Analyse", "Zeitslots Analyse"])

if page == "Startseite":
    home.show()

elif page == "Dozenten Analyse":
    dozenten.show()

elif page == "Zeitslots Analyse":
    zeitslots.show()