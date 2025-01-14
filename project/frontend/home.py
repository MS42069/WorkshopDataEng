import streamlit as st
import os

def show():
    # Willkommens-Bereich
    st.title("Stundenplan Insights")
    st.header("Stundenpläne analysieren, visualisieren und besser verstehen.")
    st.write("""
            Willkommen! Diese App bietet dir eine umfassende Analyse der Stundenpläne der Fachhochschule Wedel. Erfahre mehr über Dozenten, Zeitslots und Optimierungsmöglichkeiten.
        """)

    # Absoluter Pfad zum Bild
    current_dir = os.path.dirname(__file__)
    image_path = os.path.abspath(os.path.join(current_dir, "../assets/images/homepage_background.jpg"))

    # Bild anzeigen
    st.image(image_path, use_container_width=True)


    # Call-to-Action Bereich
    st.markdown("### Starte jetzt!")
    if st.button("Zu den Zeitslots Analysen"):
        st.session_state["page"] = "Zeitslots Analyse"  # Wechsel zur Zeitslots-Seite