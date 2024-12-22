import psycopg2
import pandas as pd
import matplotlib.pyplot as plt

class DatabaseAnalyzer:
    def __init__(self, dbname, user, password, host="localhost", port=5432):
        """
        Initialisiert die Verbindung zur PostgreSQL-Datenbank.
        """
        self.conn = psycopg2.connect(
            dbname=dbname,
            user=user,
            password=password,
            host=host,
            port=port
        )
        print("Datenbankverbindung hergestellt.")

    def fetch_table_overview(self):
        """
        Gibt eine Übersicht aller Tabellen und deren Spalten.
        """
        query = """
        SELECT table_name, column_name, data_type
        FROM information_schema.columns
        WHERE table_schema = 'public'
        ORDER BY table_name, ordinal_position;
        """
        return pd.read_sql_query(query, self.conn)

    def fetch_data(self, query):
        """
        Führt eine benutzerdefinierte SQL-Abfrage aus und gibt das Ergebnis zurück.
        """
        return pd.read_sql_query(query, self.conn)

    def fetch_event_timeslots(self): #Test function for unpopular_timeslots
        """
        Gibt alle Kurse und deren zugeordnete Zeitslots zurück.
        """
        query = """
        WITH event_counts AS (
            SELECT 
                timeslot_id,
                COUNT(*) AS event_belegung
            FROM week_events_timeslots
            GROUP BY timeslot_id
        )
        SELECT 
            t.id AS timeslot_id,
            t.start_time,
            t.end_time,
            t.index,
            COALESCE(ec.event_belegung, 0) AS event_belegung
        FROM timeslots t
        LEFT JOIN event_counts ec ON t.id = ec.timeslot_id;
        """
        data = self.fetch_data(query)
        return data

    def fetch_course_timeslots(self): #Test function for unpopular_timeslots
        """
        Gibt alle Kurse und deren zugeordnete Zeitslots zurück.
        """
        query = """
        WITH course_counts AS (
            SELECT 
                fk_timeslot_id AS timeslot_id,
                COUNT(*) AS kurs_belegung
            FROM course_timeslots
            GROUP BY fk_timeslot_id
        )
        SELECT 
            t.id AS timeslot_id,
            t.start_time,
            t.end_time,
            t.index,
            COALESCE(cc.kurs_belegung, 0) AS kurs_belegung
        FROM timeslots t
        LEFT JOIN course_counts cc ON t.id = cc.timeslot_id;
        """
        data = self.fetch_data(query)
        return data

    def analyze_unpopular_timeslots(self): #TODO Klären ob Wochentage erücksichtig werden solen pder ob es nur um Uhrzeit geht
        """
        Findet die unbeliebtesten Zeitslots basierend auf Belegungszahlen und ergänzt diese mit lesbaren Informationen.
        Gruppierung nach Timeslot Wochentage werden aktuell ignoriert!
        """
        #self.fetch_course_timeslots()
        query = """
        WITH course_counts AS (
            SELECT 
                fk_timeslot_id AS timeslot_id,
                COUNT(*) AS kurs_belegung
            FROM course_timeslots
            GROUP BY fk_timeslot_id
        ),
        event_counts AS (
            SELECT 
                timeslot_id,
                COUNT(*) AS event_belegung
            FROM week_events_timeslots
            GROUP BY timeslot_id
        ),
        combined_counts AS (
            SELECT 
                t.id AS timeslot_id,
                t.start_time,
                t.end_time,
                t.index,
                COALESCE(cc.kurs_belegung, 0) AS kurs_belegung,
                COALESCE(ec.event_belegung, 0) AS event_belegung,
                COALESCE(cc.kurs_belegung, 0) + COALESCE(ec.event_belegung, 0) AS belegung
            FROM timeslots t
            LEFT JOIN course_counts cc ON t.id = cc.timeslot_id
            LEFT JOIN event_counts ec ON t.id = ec.timeslot_id
        )
        SELECT 
            combined_counts.start_time,
            combined_counts.end_time,
            combined_counts.index,
            SUM(combined_counts.belegung) AS belegung
        FROM combined_counts
        GROUP BY combined_counts.start_time, combined_counts.end_time, combined_counts.index
        ORDER BY combined_counts.start_time, combined_counts.end_time;

        """
        data = self.fetch_data(query)
        return data

    def analyze_employee_preferences(self): #TODO Aktuell 60000 Präferenzen pro Slot & keine um 8:00 -> SQL überarbeiten
        """
        Analysiert die Präferenzen der Mitarbeiter für bestimmte Zeiten (z. B. 8:00 Uhr).
        """
        query = """
        SELECT c.weekday AS weekday, t.start_time, t.end_time, COUNT(*) AS likes
        FROM worktimes wt
        INNER JOIN timeslots t ON wt.fk_timeslot_id = t.id
        INNER JOIN course_timeslots c ON t.id = c.fk_timeslot_id
        WHERE t.start_time = '08:00:00'
        GROUP BY c.weekday, t.start_time, t.end_time;
        """
        return self.fetch_data(query)

    def close_connection(self):
        """
        Schließt die Verbindung zur Datenbank.
        """
        self.conn.close()
        print("Datenbankverbindung geschlossen.")

class DataVisualizer:
    def __init__(self):
        """
        Initialisiert die Visualisierungskomponente.
        """
        pass

    def plot_unpopular_timeslots(self, data):
        """
        Erstellt ein Balkendiagramm der unbeliebtesten Zeitslots.
        """
        labels = [f"{row['start_time']} - {row['end_time']}" for _, row in data.iterrows()]
        plt.bar(labels, data['belegung'])
        plt.xlabel('Zeitslot')
        plt.ylabel('Belegungen')
        plt.title('Unbeliebteste Zeitslots')
        plt.xticks(rotation=45, ha='right')
        plt.tight_layout()
        plt.show()

    def plot_unpopular_timeslots_wd_ts(self, data): # Not used yet
        """
        Erstellt ein Balkendiagramm der unbeliebtesten Zeitslots.
        """
        labels = [f"{row['weekday']} {row['start_time']} - {row['end_time']}" for _, row in data.iterrows()]
        plt.bar(labels, data['belegungen'])
        plt.xlabel('Zeitslot')
        plt.ylabel('Belegungen')
        plt.title('Unbeliebteste Zeitslots')
        plt.xticks(rotation=45, ha='right')
        plt.tight_layout()
        plt.show()

    def plot_employee_preferences(self, data):
        """
        Visualisiert die Anzahl der Mitarbeiter, die bestimmte Zeiten bevorzugen.
        """
        labels = [f"{row['weekday']} {row['start_time']} - {row['end_time']}" for _, row in data.iterrows()]
        plt.bar(labels, data['likes'], color='skyblue')
        plt.xlabel('Wochentag und Zeit')
        plt.ylabel('Anzahl Präferenzen')
        plt.title('Präferenzen für 8:00 Uhr nach Wochentag')
        plt.xticks(rotation=45, ha='right')
        plt.tight_layout()
        plt.show()
    # TODO: Wie viele Dozenten mögen/mögen nicht um 8:00 aufstehen? -> Was soll das heißte/WIe sollen wir das beantworten?
    # TODO Clustering der Dozentenwünsche
    # TODO: Durchschnittlicher Dozentenwunsch (???)
def main():
    # Verbindung zur Datenbank herstellen
    analyzer = DatabaseAnalyzer(dbname="postgres", user="postgres", password="password")
    visualizer = DataVisualizer()

    # Analyse der unbeliebtesten Zeitslots
    print("Unbeliebteste Zeitslots:")
    unpopular_timeslots = analyzer.analyze_unpopular_timeslots()
    print(unpopular_timeslots)
    visualizer.plot_unpopular_timeslots(unpopular_timeslots)

    # Analyse der Mitarbeiterpräferenzen
    print("Präferenzen der Mitarbeiter für 8:00 Uhr:")
    employee_preferences = analyzer.analyze_employee_preferences()
    print(employee_preferences)
    visualizer.plot_employee_preferences(employee_preferences)

    # Verbindung schließen
    analyzer.close_connection()

if __name__ == "__main__":
    main()

#%%

#%%
