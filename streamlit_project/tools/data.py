from contextlib import nullcontext

import psycopg2
import pandas as pd
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
import seaborn as sns
import streamlit as st
import plotly.express as px

class DatabaseAnalyzer:
    def __init__(self, dbname="postgres", user="postgres", password="password", host="dev-stundenplan-postgres", port=5432):
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

    def count_event_timeslots_2022(self):
        """
        Findet wie viele Kurse zu welchen Uhrzeiten STATTFINDEN. Als ein Kurs zählt eine Komposition aus Kursname, Start- und Endzeit.
        Wöchentliche Daten oder mehrere Dozenten pro Kurs werden ignoriert.
        """
        query = """
        SELECT 
            t.start_time,
            t.end_time,
            COUNT(DISTINCT CONCAT(c.name, '-', tt.name, '-', t.start_time, '-', t.end_time)) AS event_count
        FROM courses c
        LEFT JOIN timetables tt
        ON c.fk_timetable_id = tt.id
        LEFT JOIN course_lecturers cl
        ON c.id = cl.fk_course_id
        LEFT JOIN employees e
        ON cl.fk_employee_id = e.id
        LEFT JOIN course_events ce
        ON c.id = ce.course_id
        LEFT JOIN week_events_timeslots wet
        ON wet.week_event_id = ce.id
        LEFT JOIN timeslots t
        ON wet.timeslot_id = t.id
        WHERE tt.name = '2022'
        GROUP BY t.start_time, t.end_time
        ORDER BY t.start_time, t.end_time;
        """
        data = self.fetch_data(query)
        return data

    def count_course_timeslots_2022(self):
        """
        Findet wie viele Kurse zu welchen Uhrzeiten MÖGLICH SIND. Als ein Kurs zählt eine Komposition aus Kursname, Wochentag Start- und Endzeit.
        Über die course_timeslots Tabelle.
        """
        query = """
        SELECT 
            subquery.start_time,
            subquery.end_time,
            COUNT(*) AS course_count
        FROM (
            SELECT DISTINCT
                c.name AS course, 
                ct.weekday,
                t.start_time,
                t.end_time
            FROM courses c
            LEFT JOIN timetables tt
            ON c.fk_timetable_id = tt.id
            LEFT JOIN course_timeslots ct
            ON c.id = ct.fk_course_id
            LEFT JOIN timeslots t
            ON ct.fk_timeslot_id = t.id
            WHERE tt.name = '2022'
        ) AS subquery
        GROUP BY subquery.start_time, subquery.end_time
        ORDER BY course_count DESC, subquery.start_time, subquery.end_time;
        """
        data = self.fetch_data(query)
        return data

    def analyze_event_timeslots_2022(self):
        """
        Findet welche Kurse zu welchen Uhrzeiten STATTFINDEN. Als ein Kurs zählt eine Komposition aus Kursname, Start- und Endzeit.
        Kann genutzt werden, um im Dropdown-Menü eine Uhrzeit auszuwählen und dann die entsprechenden Kurse zu filtern.
        """

        query = """
        SELECT DISTINCT
            c.name as course,
            tt.name as timetable,
            t.start_time,
            t.end_time
        FROM courses c
        LEFT JOIN timetables tt
        ON c.fk_timetable_id = tt.id
        LEFT JOIN course_lecturers cl
        ON c.id = cl.fk_course_id
        LEFT JOIN employees e
        ON cl.fk_employee_id = e.id
        LEFT JOIN course_events ce
        ON c.id = ce.course_id
        LEFT JOIN week_events_timeslots wet
        ON wet.week_event_id = ce.id
        LEFT JOIN timeslots t
        ON wet.timeslot_id = t.id
        WHERE tt.name = '2022';
        """
        data = self.fetch_data(query)
        return data

    def count_event_and_course_timeslots_2022(self): #TODO: Unterschied in Zahlen zu analyze_unpopular_timeslots_usage_planning klären
        """
        Zählt wie viele Kurse zu welchen Uhrzeiten STATTFINDEN und wie viele Kurse zu welchen Uhrzeiten MÖGLICH SIND.
        """

        query = """
        SELECT 
            q1.start_time,
            q1.end_time,
            q1.course_count AS count_from_query1,
            q2.occurrence_count AS count_from_query2,
            COALESCE(q1.course_count, 0) + COALESCE(q2.occurrence_count, 0) AS combined_count
        FROM (
            SELECT 
                t.start_time,
                t.end_time,
                COUNT(DISTINCT CONCAT(c.name, '-', tt.name, '-', t.start_time, '-', t.end_time)) AS course_count
            FROM courses c
            LEFT JOIN timetables tt
            ON c.fk_timetable_id = tt.id
            LEFT JOIN course_lecturers cl
            ON c.id = cl.fk_course_id
            LEFT JOIN employees e
            ON cl.fk_employee_id = e.id
            LEFT JOIN course_events ce
            ON c.id = ce.course_id
            LEFT JOIN week_events_timeslots wet
            ON wet.week_event_id = ce.id
            LEFT JOIN timeslots t
            ON wet.timeslot_id = t.id
            WHERE tt.name = '2022'
            GROUP BY t.start_time, t.end_time
        ) AS q1
        FULL OUTER JOIN (
            SELECT 
                subquery.start_time,
                subquery.end_time,
                COUNT(*) AS occurrence_count
            FROM (
                SELECT DISTINCT
                    c.name AS course, 
                    ct.weekday,
                    t.start_time,
                    t.end_time
                FROM courses c
                LEFT JOIN timetables tt
                ON c.fk_timetable_id = tt.id
                LEFT JOIN course_timeslots ct
                ON c.id = ct.fk_course_id
                LEFT JOIN timeslots t
                ON ct.fk_timeslot_id = t.id
                WHERE tt.name = '2022'
            ) AS subquery
            GROUP BY subquery.start_time, subquery.end_time
        ) AS q2
        ON q1.start_time = q2.start_time AND q1.end_time = q2.end_time
        ORDER BY q1.start_time, q1.end_time;
        """
        data = self.fetch_data(query)
        return data

    def analyze_unpopular_timeslots_usage_planning(self): #TODO Klären ob Wochentage erücksichtig werden solen pder ob es nur um Uhrzeit geht
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

    def employee_preferences_not_8(self):
        """Zählt welche Mitarbeiter an wie vielen Tagen NICHT um 8:00 Uhr arbeiten möchten.
        Angaben aus der employee_timeslot_constraints Tabelle."""

        query_timeslot_contraints = """
        WITH counts AS (
            SELECT 
                employee_abbreviation,
                COUNT(*) AS count
            FROM employee_timeslot_constraints
            WHERE start_time = '08:00:00'
              AND constraint_value = 'NOT_WANTED'
            GROUP BY employee_abbreviation
        )
        SELECT DISTINCT
            c.employee_abbreviation,
            LOWER(e.firstname)AS firstname,
            LOWER(e.lastname) AS lastname,
            c.count
        FROM counts c
        LEFT JOIN employees e
        ON c.employee_abbreviation = e.abbreviation
        ORDER BY c.count DESC;
        """
        return self.fetch_data(query_timeslot_contraints)

    def worktimes_8_2022(self):
        """
        Listet alle Mitarbeiter auf, die um 8:00 Uhr arbeiten müssen.
        """
        query = """
        SELECT DISTINCT
            tt.name as timetable,
			e.firstname,
			e.lastname,
            t.start_time,
            t.end_time
        FROM courses c
        LEFT JOIN timetables tt
        ON c.fk_timetable_id = tt.id
        LEFT JOIN course_lecturers cl
        ON c.id = cl.fk_course_id
        LEFT JOIN employees e
        ON cl.fk_employee_id = e.id
        LEFT JOIN course_events ce
        ON c.id = ce.course_id
        LEFT JOIN week_events_timeslots wet
        ON wet.week_event_id = ce.id
        LEFT JOIN timeslots t
        ON wet.timeslot_id = t.id
        WHERE tt.name = '2022' AND start_time = '08:00:00';
        """
        return self.fetch_data(query)

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

    def collect_all_employee_preferences(self):
        """"
        Erstellt eine Tabelle mit allen Präferenzen der Mitarbeiter. Jeder Mitarbeiter hat eine Zeile mit allen Präferenzen
        und eine Zeile pro Wochentag und Timeslot
        """

        query = """
            SELECT 
                e.abbreviation,
                e.firstname,
                e.lastname,
                lbc.constraint_value AS lunch_break,
                fdc.constraint_value AS free_day,
                bbc.constraint_value AS breaks_between,
                cdc.constraint_value AS course_distribution,
                stc.constraint_value AS subsequent_timeslots,
                stc.timeslot_amount,
                -- Montag: Timeslots 0 bis 6
                MAX(CASE WHEN etc.weekday = 'MONDAY' AND etc.timeslot_index = 0 THEN etc.constraint_value END) AS monday_slot_0,
                MAX(CASE WHEN etc.weekday = 'MONDAY' AND etc.timeslot_index = 1 THEN etc.constraint_value END) AS monday_slot_1,
                MAX(CASE WHEN etc.weekday = 'MONDAY' AND etc.timeslot_index = 2 THEN etc.constraint_value END) AS monday_slot_2,
                MAX(CASE WHEN etc.weekday = 'MONDAY' AND etc.timeslot_index = 3 THEN etc.constraint_value END) AS monday_slot_3,
                MAX(CASE WHEN etc.weekday = 'MONDAY' AND etc.timeslot_index = 4 THEN etc.constraint_value END) AS monday_slot_4,
                MAX(CASE WHEN etc.weekday = 'MONDAY' AND etc.timeslot_index = 5 THEN etc.constraint_value END) AS monday_slot_5,
                MAX(CASE WHEN etc.weekday = 'MONDAY' AND etc.timeslot_index = 6 THEN etc.constraint_value END) AS monday_slot_6,
                -- Dienstag: Timeslots 0 bis 6
                MAX(CASE WHEN etc.weekday = 'TUESDAY' AND etc.timeslot_index = 0 THEN etc.constraint_value END) AS tuesday_slot_0,
                MAX(CASE WHEN etc.weekday = 'TUESDAY' AND etc.timeslot_index = 1 THEN etc.constraint_value END) AS tuesday_slot_1,
                MAX(CASE WHEN etc.weekday = 'TUESDAY' AND etc.timeslot_index = 2 THEN etc.constraint_value END) AS tuesday_slot_2,
                MAX(CASE WHEN etc.weekday = 'TUESDAY' AND etc.timeslot_index = 3 THEN etc.constraint_value END) AS tuesday_slot_3,
                MAX(CASE WHEN etc.weekday = 'TUESDAY' AND etc.timeslot_index = 4 THEN etc.constraint_value END) AS tuesday_slot_4,
                MAX(CASE WHEN etc.weekday = 'TUESDAY' AND etc.timeslot_index = 5 THEN etc.constraint_value END) AS tuesday_slot_5,
                MAX(CASE WHEN etc.weekday = 'TUESDAY' AND etc.timeslot_index = 6 THEN etc.constraint_value END) AS tuesday_slot_6,
                -- Mittwoch: Timeslots 0 bis 6
                MAX(CASE WHEN etc.weekday = 'WEDNESDAY' AND etc.timeslot_index = 0 THEN etc.constraint_value END) AS wednesday_slot_0,
                MAX(CASE WHEN etc.weekday = 'WEDNESDAY' AND etc.timeslot_index = 1 THEN etc.constraint_value END) AS wednesday_slot_1,
                MAX(CASE WHEN etc.weekday = 'WEDNESDAY' AND etc.timeslot_index = 2 THEN etc.constraint_value END) AS wednesday_slot_2,
                MAX(CASE WHEN etc.weekday = 'WEDNESDAY' AND etc.timeslot_index = 3 THEN etc.constraint_value END) AS wednesday_slot_3,
                MAX(CASE WHEN etc.weekday = 'WEDNESDAY' AND etc.timeslot_index = 4 THEN etc.constraint_value END) AS wednesday_slot_4,
                MAX(CASE WHEN etc.weekday = 'WEDNESDAY' AND etc.timeslot_index = 5 THEN etc.constraint_value END) AS wednesday_slot_5,
                MAX(CASE WHEN etc.weekday = 'WEDNESDAY' AND etc.timeslot_index = 6 THEN etc.constraint_value END) AS wednesday_slot_6,
                -- Donnerstag: Timeslots 0 bis 6
                MAX(CASE WHEN etc.weekday = 'THURSDAY' AND etc.timeslot_index = 0 THEN etc.constraint_value END) AS thursday_slot_0,
                MAX(CASE WHEN etc.weekday = 'THURSDAY' AND etc.timeslot_index = 1 THEN etc.constraint_value END) AS thursday_slot_1,
                MAX(CASE WHEN etc.weekday = 'THURSDAY' AND etc.timeslot_index = 2 THEN etc.constraint_value END) AS thursday_slot_2,
                MAX(CASE WHEN etc.weekday = 'THURSDAY' AND etc.timeslot_index = 3 THEN etc.constraint_value END) AS thursday_slot_3,
                MAX(CASE WHEN etc.weekday = 'THURSDAY' AND etc.timeslot_index = 4 THEN etc.constraint_value END) AS thursday_slot_4,
                MAX(CASE WHEN etc.weekday = 'THURSDAY' AND etc.timeslot_index = 5 THEN etc.constraint_value END) AS thursday_slot_5,
                MAX(CASE WHEN etc.weekday = 'THURSDAY' AND etc.timeslot_index = 6 THEN etc.constraint_value END) AS thursday_slot_6,
                -- Freitag: Timeslots 0 bis 6
                MAX(CASE WHEN etc.weekday = 'FRIDAY' AND etc.timeslot_index = 0 THEN etc.constraint_value END) AS friday_slot_0,
                MAX(CASE WHEN etc.weekday = 'FRIDAY' AND etc.timeslot_index = 1 THEN etc.constraint_value END) AS friday_slot_1,
                MAX(CASE WHEN etc.weekday = 'FRIDAY' AND etc.timeslot_index = 2 THEN etc.constraint_value END) AS friday_slot_2,
                MAX(CASE WHEN etc.weekday = 'FRIDAY' AND etc.timeslot_index = 3 THEN etc.constraint_value END) AS friday_slot_3,
                MAX(CASE WHEN etc.weekday = 'FRIDAY' AND etc.timeslot_index = 4 THEN etc.constraint_value END) AS friday_slot_4,
                MAX(CASE WHEN etc.weekday = 'FRIDAY' AND etc.timeslot_index = 5 THEN etc.constraint_value END) AS friday_slot_5,
                MAX(CASE WHEN etc.weekday = 'FRIDAY' AND etc.timeslot_index = 6 THEN etc.constraint_value END) AS friday_slot_6
            FROM employees e
            LEFT JOIN lunch_break_constraints lbc
                ON e.abbreviation = lbc.employee_abbreviation
            LEFT JOIN free_day_constraints fdc
                ON e.abbreviation = fdc.employee_abbreviation
            LEFT JOIN breaks_between_constraints bbc
                ON e.abbreviation = bbc.employee_abbreviation
            LEFT JOIN course_distribution_constraints cdc
                ON e.abbreviation = cdc.employee_abbreviation
            LEFT JOIN subsequent_timeslots_constraints stc
                ON e.abbreviation = stc.employee_abbreviation
            LEFT JOIN employee_timeslot_constraints etc
                ON e.abbreviation = etc.employee_abbreviation
            GROUP BY 
                e.abbreviation, e.firstname, e.lastname, lbc.constraint_value, fdc.constraint_value, 
                bbc.constraint_value, cdc.constraint_value, stc.constraint_value, stc.timeslot_amount;
        """
        return self.fetch_data(query)


    def collect_preferences_without_timeslots(self):
        """
        Sammelt alle Mitarbeiterpräferenzen, die keine Timeslots enthalten.
        """
        query = """
        SELECT 
            e.abbreviation,
            e.firstname,
            e.lastname,
            lbc.constraint_value lunch_break,
            fdc.constraint_value free_day,
            bbc.constraint_value breaks_between,
            cdc.constraint_value course_distribution,
            stc.constraint_value subsequent_timeslots,
            stc.timeslot_amount
        FROM employees e
        LEFT JOIN lunch_break_constraints lbc
        ON e.abbreviation = lbc.employee_abbreviation
        LEFT JOIN free_day_constraints fdc
        ON e.abbreviation = fdc.employee_abbreviation
        LEFT JOIN breaks_between_constraints bbc
        ON e.abbreviation = bbc.employee_abbreviation
        LEFT JOIN course_distribution_constraints cdc
        On e.abbreviation = cdc.employee_abbreviation
        LEFT JOIN subsequent_timeslots_constraints stc
        ON e.abbreviation = stc.employee_abbreviation;
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

    def barplot_timeslots(self, data, count_column, titel):
        """
        Erstelle ein interaktives Balkendiagramm mit Plotly und zeige es in Streamlit an.
        """
        # Daten vorbereiten
        data['Zeitslot'] = data.apply(lambda row: f"{row['start_time']} - {row['end_time']}", axis=1)

        # Plotly-Balkendiagramm erstellen
        fig = px.bar(
            data,
            x='Zeitslot',
            y=count_column,
            title=titel,
            labels={'Zeitslot': 'Zeitslot', count_column: 'Anzahl Belegungen'},
            text=count_column,  # Werte über den Balken anzeigen
            color='Zeitslot',  # Optionale Farbgebung nach Zeitslot
        )

        # Layout anpassen
        fig.update_traces(texttemplate='%{text}', textposition='outside')  # Text außerhalb der Balken
        fig.update_layout(
            xaxis=dict(title='Zeitslot', tickangle=45),  # Achsentitel und Winkel
            yaxis=dict(title='Anzahl Belegungen'),
            title=dict(font=dict(size=20), x=0.5),  # Titel zentrieren
            margin=dict(t=50, b=50),  # Abstände oben/unten
            showlegend=False  # Legende ausblenden
        )

        # Diagramm in Streamlit anzeigen
        st.plotly_chart(fig, use_container_width=True)

    def plot_unpopular_timeslots(self, data):
        """
        Erstellt ein Balkendiagramm der unbeliebtesten Zeitslots.
        """
        # Seaborn-Stil aktivieren (optional für ein modernes Design)
        sns.set_theme(style="whitegrid")

        # Daten vorbereiten
        labels = [f"{row['start_time']} - {row['end_time']}" for _, row in data.iterrows()]
        belegung = data['belegung']

        # Balkendiagramm erstellen
        plt.figure(figsize=(10, 6))  # Diagrammgröße
        plt.bar(labels, belegung, color="#001145")  # Balkenfarbe auf #001145 setzen

        # Achsentitel und Diagrammtitel
        plt.xlabel('Zeitslot', fontsize=12, labelpad=10)
        plt.ylabel('Anzahl Belegungen', fontsize=12, labelpad=10)

        # Achsenticks optimieren
        plt.xticks(rotation=45, ha='right', fontsize=10)
        plt.yticks(fontsize=10)

        # Layout anpassen
        plt.tight_layout()

    # Diagramm in Datei speichern
        plt.savefig("./static/img/graphs/unpopular_timeslots.png", format="png")
        plt.close()

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
        #plt.show()

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
        #plt.show()

    def plot_employee_preferences2(self, data):
        """
       Visualisiert die Häufigkeit der NOT_WANTED-Präferenzen für Mitarbeiter um 08:00 Uhr.

       :param data: Pandas DataFrame mit Spalten 'fullname' und 'count'.
           """
        # Visualisierung erstellen
        plt.figure(figsize=(10, 6))
        # neue Spalte mit dem vollen Namen des Mitarbeiters erstellen
        data['fullname'] = data['firstname'] + ' ' + data['lastname']
        plt.bar(data['fullname'], data['count'], color='steelblue')

        # Diagramm beschriften
        plt.title('Anzahl der Tage mit NOT-WANTED Präferenz um 08:00 Uhr', fontsize=14)
        plt.xlabel('Mitarbeiter', fontsize=12)
        plt.ylabel('Anzahl der Präferenzen', fontsize=12)
        plt.xticks(rotation=45, fontsize=10)
        plt.tight_layout()

        # Diagramm anzeigen
        #plt.show()
    def employee_clustering(self, data):
        """
        Visualisiert die Cluster der Mitarbeiterpräferenzen.
        """
        import plotly.express as px
        import numpy as np

        # Zufälliges Rauschen hinzufügen, nur für Cluster 0
        noise_scale = 0.8  # Skala des Rauschens (anpassbar)
        data.loc[data['cluster'] == 0, 'PCA1'] += np.random.normal(0, noise_scale, size=data[data['cluster'] == 0].shape[0])
        data.loc[data['cluster'] == 0, 'PCA2'] += np.random.normal(0, noise_scale, size=data[data['cluster'] == 0].shape[0])


        # Plotly-Visualisierung
        fig = px.scatter(
            data,
            x='PCA1',
            y='PCA2',
            color='cluster',
            hover_data={'PCA1': False, 'PCA2': False, 'firstname': True, 'lastname': True},  # Nur Vorname und Nachname anzeigen
            title='Cluster basierend auf Mitarbeiterpräferenzen',
            color_continuous_scale=['blue', 'green', 'red']
        )

        # Layout-Anpassungen
        fig.update_layout(
            width=1000,  # Breite der Grafik (X-Achse länger machen)
            height=600,  # Höhe der Grafik
            xaxis_title=None,  # X-Achsentitel entfernen
            yaxis_title=None,  # Y-Achsentitel entfernen
            showlegend=True  # Legende anzeigen lassen
        )

        # Tooltip weiter anpassen (falls notwendig)
        fig.update_traces(
            hovertemplate="<b>%{customdata[0]} %{customdata[1]}</b><extra></extra>"  # Tooltip auf firstname und lastname
        )


        return fig


    # TODO DONE: Wie viele Dozenten mögen/mögen nicht um 8:00 aufstehen? -> Was soll das heißte/WIe sollen wir das beantworten?
    # DONE: Anzahl der Tage an denen Dozenten nicht um 8:00 Uhr arbeiten möchten in der employee_timeslot_constraints Tabelle
    # TODO Clustering der Dozentenwünsche
    # TODO: Durchschnittlicher Dozentenwunsch (???)
