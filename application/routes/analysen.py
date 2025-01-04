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

@analysen_bp.route('/analysen/unbeliebte-zeitslots')
def unpopular_timeslots():
    # Datenbankabfrage
    print("Datenbankabfrage")
    data = analyzer.analyze_unpopular_timeslots()
    print(data)
    # Plot erstellen und speichern
    visualizer.plot_unpopular_timeslots(data)
    print("url für Bild rendern")
    # Analysen-Seite mit eingebettetem Bild rendern
    image_url = url_for('static', filename='img/graphs/unpopular_timeslots.png')
    return render_template('analysen.html', image_url=image_url)