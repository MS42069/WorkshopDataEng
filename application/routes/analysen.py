from flask import Blueprint, render_template, send_file, url_for
from application.database_tools import DatabaseAnalyzer, DataVisualizer
from io import BytesIO
import matplotlib.pyplot as plt

analysen_bp = Blueprint('analysen', __name__)

# Verbindung zur Datenbank herstellen
analyzer = DatabaseAnalyzer(dbname="postgres", user="postgres", password="password")
visualizer = DataVisualizer()

@analysen_bp.route('/analysen')
def home():
    return render_template('analysen.html')

@analysen_bp.route('/analysen/timeslots_1')
def timeslots_1():
    # Datenbankabfrage
    data = analyzer.analyze_unpopular_timeslots()
    data_sorted = data.sort_values(by='belegung', ascending=True)

    # Daten für das Top 3 Ranking ziehen
    top3_slots = data_sorted.head(3)
    # Neue Spalte 'time_range' erstellen
    top3_slots['time_range'] = top3_slots['start_time'].astype(str) + " - " + top3_slots['end_time'].astype(str)
    time_ranges = top3_slots['time_range'].tolist()

    # Plot erstellen und speichern
    visualizer.plot_unpopular_timeslots(data_sorted)
    # Analysen-Seite mit eingebettetem Bild rendern
    image_url = url_for('static', filename='img/graphs/unpopular_timeslots.png')
    # Titel festlegen
    page_titel = "Ergebnisse: Zeitslots"
    return render_template('partials/timeslots_1.html', image_url=image_url, page_title=page_titel, top_3_list=time_ranges)


@analysen_bp.route('/analysen/timeslots_2')
def timeslots_2():
    data = analyzer.analyze_employee_preferences()  # Beispielhafte zweite Analyse
    print(data)
    return render_template('timeslots_2.html', image_url=image_url)
