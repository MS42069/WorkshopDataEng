import streamlit as st
#from streamlit_project.tools.data import DatabaseAnalyzer, DataVisualizer # import für lokale Ausführung
from tools.data import DatabaseAnalyzer, DataVisualizer # import für Docker Ausführung


def show():
    # Daten laden
    analyzer = DatabaseAnalyzer(dbname="postgres", user="postgres", password="password")
    visualizer = DataVisualizer()
    st.title("Zeitslot Analyse")

    # Datenbankabfrage
    def load_data():
        # Anzahl der Kurse
        data_courses = analyzer.count_course_timeslots_2022()
        data_courses_sorted = data_courses.sort_values(by='course_count', ascending=False).dropna()
        # Anzahl der Events
        data_events = analyzer.count_event_timeslots_2022()
        data_events_sorted = data_events.sort_values(by='event_count', ascending=False).dropna()
        # Anzahl der Kurse und Events
        data_courses_events = analyzer.count_event_and_course_timeslots_2022()
        data_courses_events_sorted = data_courses_events.sort_values(by='combined_count', ascending=False).dropna()
        return data_courses_sorted, data_events_sorted, data_courses_events_sorted

    data_courses_sorted , data_events_sorted, data_courses_events_sorted = load_data()

    # Top 3 Zeitslots mit den meisten Kursen
    st.subheader("Top 3 Zeitslots mit den meisten Kursen")
    top3 = data_courses_events_sorted.head(3)
    top3['Platzierung'] = range(1, len(top3) + 1)
    top3_filtered = top3[['Platzierung', 'start_time', 'end_time']].reset_index(drop=True)

    st.write(top3_filtered)
    #Visualisierung der Daten
    visualizer.barplot_timeslots(data_courses_sorted, 'course_count', 'Anzahl tatsächlicher Kurse pro Zeitslot')
    visualizer.barplot_timeslots(data_events_sorted, 'event_count', 'Anzahl verfügbarer Kurse pro Zeitslot')
    visualizer.barplot_timeslots(data_courses_events_sorted, 'combined_count', 'Anzahl tatsächlicher und verfügbarer Kurse pro Zeitslot')