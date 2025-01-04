from flask import Flask
from routes.home import home_bp
from routes.db import db_bp  # Importiere das db Blueprint
from routes.analysen import analysen_bp
# TODO Weboberfläche(n) bauen -> Kann man Bausteine der Stundenplansoftware nutzen, um Einheitlichkeit zu wahren und Zeit zu sparen?
# TODO Funktionen der Analyse implementieren

def create_app():
    """Erstellt und konfiguriert die Flask-App."""
    app = Flask(__name__)
    # Blueprints registrieren
    app.register_blueprint(home_bp)
    app.register_blueprint(db_bp)
    app.register_blueprint(analysen_bp)

    # Weitere Konfigurationen (z. B. Datenbank, Logging) hier hinzufügen
    app.config.from_pyfile('config.py')  # Lädt die Konfiguration aus config.py

    return app