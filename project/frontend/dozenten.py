
import streamlit as st
import numpy as np
# from tools.data import DatabaseAnalyzer, DataVisualizer #import für lokale Ausführung
#from tools.data import DatabaseAnalyzer, DataVisualizer #import für Docker Ausführung
from backend.database.data import DatabaseAnalyzer, DataVisualizer
from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA

analyzer = DatabaseAnalyzer()
visualizer = DataVisualizer()
def show():

    st.title("Dozenten Analyse")
    preferences = ['lunch_break', 'free_day', 'breaks_between', 'course_distribution', 'subsequent_timeslots', 'timeslot_amount']
    meaning_preferences = ['Mittagspause', 'Freier Tag', 'Pausen zwischen den Kursen', 'Kursverteilung', 'aufeinanderfolgende Timeslots', 'Anzahl der Timeslots']

    # Daten laden# Einführung
    st.subheader("Erläuterung der Präferenzen")
    st.write("Hier sind die Präferenzen, die in der Dozenten-Analyse berücksichtigt werden, sowie deren Bedeutung:")

    # Darstellung als Tabelle
    preferences_table = {"Präferenz": preferences, "Bedeutung": meaning_preferences}
    st.table(preferences_table)

    data_pref, data_pref_readable = clustering_only_preferences()
    data_pref_timeslots, data_pref_timeslots_readable = clustering_preferences_and_timeslots()

    # Cluster-Visualisierung Mitarbeiterpräferenzen
    st.subheader("Cluster basierend auf Mitarbeiterpräferenzen")
    fig = visualizer.employee_clustering(data_pref)
    st.plotly_chart(fig)

    # Daten anzeigen
    st.write("Einblick in die Daten:")
    st.dataframe(data_pref_readable)
    print(clustering_preferences_and_timeslots())

    # Cluster-Visualisierung Mitarbeiterpräferenzen und Timeslots
    st.subheader("Cluster basierend auf Mitarbeiterpräferenzen und Timeslots")
    fig = visualizer.employee_clustering(data_pref_timeslots)
    st.plotly_chart(fig)

    # Daten anzeigen
    st.write("Einblick in die Daten:")
    st.dataframe(data_pref_timeslots_readable)

    # Durchschnittliche Präferenzen
    st.subheader("Durchschnittliche Präferenzen")
    av = average_dozent()
    st.write(av)

    # Zwei Spalten erstellen
    col1, col2 = st.columns(2)

    # Tabelle für Dozenten, die nicht um 8 Uhr arbeiten wollen
    with col1:
        st.subheader("Dozenten, die nicht um 8 Uhr arbeiten wollen")
        not_worktimes = dont_want_to_work_at_8()[['firstname', 'lastname']]
        st.write(not_worktimes)

    # Tabelle für Dozenten, die um 8 Uhr arbeiten
    with col2:
        st.subheader("Dozenten, die um 8 Uhr arbeiten")
        worktimes = work_at_8()[['firstname', 'lastname']]
        st.write(worktimes)


def clustering_only_preferences():
    # Daten laden
    # Datenbankabfrage
    def load_data():
        return analyzer.collect_preferences_without_timeslots()

    data_pref = load_data()
    data_readable = data_pref.copy()

    # Daten aufbereiten
    mapping = {'WANTED': 1, 'NOT_WANTED': -1, None: 0}
    for col in ['lunch_break', 'free_day', 'breaks_between', 'course_distribution', 'subsequent_timeslots']:
        data_pref[col] = data_pref[col].map(mapping)
    data_pref.fillna(0, inplace=True)

    mapping = {'WANTED': 'Ja', 'NOT_WANTED': 'Nein', None: 'Keine Präferenz'}
    for col in ['lunch_break', 'free_day', 'breaks_between', 'course_distribution', 'subsequent_timeslots']:
        data_readable[col] = data_readable[col].map(mapping)
    data_readable.fillna(0, inplace=True)

    features = ['lunch_break', 'free_day', 'breaks_between', 'course_distribution', 'subsequent_timeslots', 'timeslot_amount']
    X = data_pref[features]

    # Daten skalieren
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)

    # Rauschen hinzufügen
    noise = np.random.normal(0, 0.01, X_scaled.shape)
    X_noisy = X_scaled + (X_scaled == 0) * noise

    # K-Means Clustering
    kmeans = KMeans(n_clusters=3, random_state=42)
    data_pref['cluster'] = kmeans.fit_predict(X_noisy)

    # PCA für 2D-Visualisierung
    pca = PCA(n_components=2)
    X_pca = pca.fit_transform(X_scaled)
    data_pref['PCA1'] = X_pca[:, 0]
    data_pref['PCA2'] = X_pca[:, 1]

    return data_pref, data_readable

def clustering_preferences_and_timeslots():
    # Datenbankabfrage
    def load_data():
        return analyzer.collect_all_employee_preferences()

    data_pref = load_data()
    data_readable = data_pref.copy()

    # Mapping für alle relevanten Spalten
    mapping = {'WANTED': 1, 'NOT_WANTED': -1, None: 0}
    for col in data_pref.columns:
        if col not in ['abbreviation', 'firstname', 'lastname']:  # Nicht-feature-Spalten ignorieren
            data_pref[col] = data_pref[col].map(mapping)
    data_pref.fillna(0, inplace=True)

    mapping = {'WANTED': 'Ja', 'NOT_WANTED': 'Nein', None: 'Keine Präferenz'}
    for col in data_readable.columns:
        if col not in ['abbreviation', 'firstname', 'lastname']:  # Nicht-feature-Spalten ignorieren
            data_readable[col] = data_readable[col].map(mapping)
    data_readable.fillna(0, inplace=True)

    # Features dynamisch auswählen
    features = [col for col in data_pref.columns if col not in ['abbreviation', 'firstname', 'lastname', 'cluster', 'PCA1', 'PCA2']]
    X = data_pref[features]

    # Daten skalieren
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)

    # Rauschen hinzufügen
    noise = np.random.normal(0, 0.01, X_scaled.shape)
    X_noisy = X_scaled + (X_scaled == 0) * noise

    # K-Means Clustering
    kmeans = KMeans(n_clusters=5, random_state=42)
    data_pref['cluster'] = kmeans.fit_predict(X_noisy)

    # PCA für 2D-Visualisierung
    pca = PCA(n_components=2)
    X_pca = pca.fit_transform(X_scaled)
    data_pref['PCA1'] = X_pca[:, 0]
    data_pref['PCA2'] = X_pca[:, 1]

    return data_pref, data_readable

def average_dozent():
    # Datenbankabfrage
    def load_data():
        return analyzer.collect_all_employee_preferences()

    data_pref = load_data()
    data_readable = data_pref.copy()

    # Mapping für alle relevanten Spalten
    mapping = {'WANTED': 1, 'NOT_WANTED': -1, None: 0}
    for col in data_pref.columns:
        if col not in ['abbreviation', 'firstname', 'lastname']:  # Nicht-feature-Spalten ignorieren
            data_pref[col] = data_pref[col].map(mapping)
    data_pref.fillna(0, inplace=True)

    average_preferences = data_pref[['lunch_break', 'free_day', 'breaks_between', 'course_distribution', 'subsequent_timeslots']].mean()

    # Anzahl der 0-Werte berechnen
    zero_counts = (data_pref[['lunch_break', 'free_day', 'breaks_between', 'course_distribution', 'subsequent_timeslots']] == 0).sum()

    # DataFrame mit zusätzlichen Informationen erstellen
    average_preferences_df = average_preferences.reset_index()
    average_preferences_df.columns = ['Präferenz Kategorie', 'Durchschnittlicher Score']
    average_preferences_df['Anzahl keiner Präferenzen'] = zero_counts.values

    return average_preferences_df

def work_at_8():
    # Datenbankabfrage
    def load_data():
        return analyzer.worktimes_8_2022()

    data = load_data()
    return data

def dont_want_to_work_at_8():
    # Datenbankabfrage
    def load_data():
        return analyzer.employee_preferences_not_8()

    data = load_data()
    return data



