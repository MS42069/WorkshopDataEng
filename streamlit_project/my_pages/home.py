import streamlit as st

def show():
    # Willkommens-Bereich
    st.title("Willkommen zur Analyse-App")
    st.header("Erkunde deine Daten")
    st.write("""
            Diese App ermöglicht es dir, Daten zu analysieren, Zeitslots zu visualisieren und interaktive Ergebnisse zu betrachten.
            Wähle eine Funktion aus der Navigation aus, um loszulegen.
        """)

    # Banner oder Bild
    st.image("https://via.placeholder.com/800x200?text=Willkommen+zur+Analyse-App", use_column_width=True)

    # Call-to-Action Bereich
    st.markdown("### Starte jetzt!")
    if st.button("Zu den Zeitslots Analysen"):
        st.session_state["page"] = "Zeitslots Analyse"  # Wechsel zur Zeitslots-Seite