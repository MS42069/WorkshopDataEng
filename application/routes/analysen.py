from flask import Blueprint, render_template, send_file, url_for
from application.database_tools import DatabaseAnalyzer, DataVisualizer
from sklearn.preprocessing import StandardScaler
from sklearn.cluster import KMeans
import plotly
import plotly.express as px
from sklearn.decomposition import PCA
import json

analysen_bp = Blueprint('analysen', __name__)

# Verbindung zur Datenbank herstellen
analyzer = DatabaseAnalyzer(dbname="postgres", user="postgres", password="password")
visualizer = DataVisualizer()

@analysen_bp.route('/analysen')
def home():
    return render_template('analysen.html')

@analysen_bp.route('/analysen/zeitslots')
def analyse_timeslots():
    # Anzahl der Kurse
    data_courses = analyzer.count_course_timeslots_2022()
    data_courses_sorted = data_courses.sort_values(by='course_count', ascending=False).dropna()

    # Anzahl der Events
    data_events = analyzer.count_event_timeslots_2022()
    data_events_sorted = data_events.sort_values(by='event_count', ascending=False).dropna()
    # Anzahl der Kurse und Events
    data_courses_events = analyzer.count_event_and_course_timeslots_2022()
    data_courses_events_sorted = data_courses_events.sort_values(by='combined_count', ascending=False).dropna()

    # Daten für das Top 3 Ranking ziehen
    top3_slots = data_courses_events_sorted.head(3)
    # Neue Spalte 'time_range' erstellen
    top3_slots['time_range'] = top3_slots['start_time'].astype(str) + " - " + top3_slots['end_time'].astype(str)
    time_ranges = top3_slots['time_range'].tolist()

    # Visualisierung der Daten
    visualizer.barplot_timeslots(data_courses_sorted, 'course_count', './static/img/graphs/course_count_timeslots.png', 'Anzahl tatsächlicher Kurse pro Zeitslot')
    visualizer.barplot_timeslots(data_events_sorted, 'event_count', './static/img/graphs/event_count_timeslots.png', 'Anzahl verfügbarer Kurse pro Zeitslot')
    visualizer.barplot_timeslots(data_courses_events_sorted, 'combined_count', './static/img/graphs/combined_count_timeslots.png', 'Anzahl tatsächlicher und verfügbarer Kurse pro Zeitslot')

    # URL für die Bilder generieren
    image_url_courses = url_for('static', filename='img/graphs/course_count_timeslots.png')
    image_url_events = url_for('static', filename='img/graphs/event_count_timeslots.png')
    image_url_combined = url_for('static', filename='img/graphs/combined_count_timeslots.png')

    # Titel festlegen
    page_titel = "Ergebnisse: Zeitslots"
    return render_template('partials/timeslots_1.html', image_url_courses=image_url_courses,image_url_events=image_url_events, image_url_combined=image_url_combined, page_title=page_titel, top_3_list=time_ranges)


@analysen_bp.route('/analysen/employees')

def employee_preferences_clustering(): # TODO: Bessere Visualisierung -> Ganz viele Dozenten haben keine Angaben und liegen daher identisch an eineem Punkt
    # Daten laden
    data = analyzer.collect_preferences_without_timeslots()

    # Daten aufbereiten für Clustering
    # Mapping von constraint_value zu numerischen Werten
    mapping = {
        'WANTED': 1,
        'NOT_WANTED': -1,
        None: 0
    }

    # Transformation der relevanten Spalten
    for col in ['lunch_break', 'free_day', 'breaks_between', 'course_distribution', 'subsequent_timeslots']:
        data[col] = data[col].map(mapping)

    data.fillna(0, inplace=True)

    # Relevante Spalten für das Clustering
    features = ['lunch_break', 'free_day', 'breaks_between', 'course_distribution', 'subsequent_timeslots', 'timeslot_amount']

    # Feature-Matrix extrahieren
    X = data[features]

    # Daten skalieren
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)

    import numpy as np

    # Rauschen hinzufügen, nur für Dozenten mit ausschließlich 0-Werten
    noise = np.random.normal(0, 0.01, X_scaled.shape)  # Rauschen mit Standardabweichung 0.01
    X_noisy = X_scaled + (X_scaled == 0) * noise


    # K-Means Clustering
    kmeans = KMeans(n_clusters=3, random_state=42)
    data['cluster'] = kmeans.fit_predict(X_noisy)

    # Visualisierung
    import plotly.express as px
    from sklearn.decomposition import PCA

    # PCA für 2D-Visualisierung
    pca = PCA(n_components=2)
    X_pca = pca.fit_transform(X_scaled)
    data['PCA1'] = X_pca[:, 0]
    data['PCA2'] = X_pca[:, 1]

    # Visualisierung der Cluster
    fig = visualizer.employee_clustering(data)

    # Grafik als JSON exportieren
    graph_json = json.dumps(fig, cls=plotly.utils.PlotlyJSONEncoder)
    print(graph_json)

    # HTML-Seite mit der Grafik rendern
    return render_template('partials/employees.html', graph_json=graph_json)


