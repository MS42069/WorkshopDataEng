
import streamlit as st
import numpy as np
from streamlit_project.tools.data import DatabaseAnalyzer, DataVisualizer
from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA


def show():
    # Daten laden
    analyzer = DatabaseAnalyzer(dbname="postgres", user="postgres", password="password")
    visualizer = DataVisualizer()
    st.title("Dozenten Analyse")
    st.write("Hier kannst du die Präferenzen analysieren und Cluster visualisieren.")

    # Datenbankabfrage
    def load_data():
        return analyzer.collect_preferences_without_timeslots()

    data = load_data()

    # Daten aufbereiten
    st.subheader("Datenaufbereitung")
    mapping = {'WANTED': 1, 'NOT_WANTED': -1, None: 0}
    for col in ['lunch_break', 'free_day', 'breaks_between', 'course_distribution', 'subsequent_timeslots']:
        data[col] = data[col].map(mapping)
    data.fillna(0, inplace=True)

    features = ['lunch_break', 'free_day', 'breaks_between', 'course_distribution', 'subsequent_timeslots', 'timeslot_amount']
    X = data[features]

    # Daten skalieren
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)

    # Rauschen hinzufügen
    noise = np.random.normal(0, 0.01, X_scaled.shape)
    X_noisy = X_scaled + (X_scaled == 0) * noise

    # K-Means Clustering
    kmeans = KMeans(n_clusters=3, random_state=42)
    data['cluster'] = kmeans.fit_predict(X_noisy)

    # Ergebnisse anzeigen
    st.write("Daten mit Clustern:")
    st.dataframe(data)

    # PCA für 2D-Visualisierung
    pca = PCA(n_components=2)
    X_pca = pca.fit_transform(X_scaled)
    data['PCA1'] = X_pca[:, 0]
    data['PCA2'] = X_pca[:, 1]

    # Cluster-Visualisierung
    st.subheader("Cluster-Visualisierung")
    fig = visualizer.employee_clustering(data)

    st.plotly_chart(fig)