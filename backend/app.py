from flask import Flask, jsonify
import psycopg2

app = Flask(__name__)

# Datenbankverbindungsdetails
DATABASE = {
    'host': 'dev-stundenplan-postgres',  # Name des Datenbank-Containers im Docker-Netzwerk
    'port': 5432,                        # Standard-Port für PostgreSQL
    'dbname': 'postgres',                # Name der Datenbank
    'user': 'postgres',                  # Benutzername
    'password': 'password'               # Passwort
}

@app.route('/test-db', methods=['GET'])
def test_db_connection():
    try:
        # Verbindung zur Datenbank herstellen
        conn = psycopg2.connect(
            host=DATABASE['host'],
            port=DATABASE['port'],
            dbname=DATABASE['dbname'],
            user=DATABASE['user'],
            password=DATABASE['password']
        )
        cur = conn.cursor()

        # Test-Query ausführen
        cur.execute("SELECT * FROM courses")
        result = cur.fetchone()
        print(result)

        if result:
                data = {
                    "id": result[0],  # UUID der Reihe
                    "abbreviation": result[1],
                    "block_size": result[2],
                    "casid": result[3],
                    "description": result[4],
                    "name": result[5],
                    "slots_per_week": result[6],
                    "weeks_per_semester": result[7],
                    "fk_room_type_id": result[8],
                    "fk_timetable_id": result[9]
                }
        else:
            data = {"message": "No data found in the 'courses' table."}


        # Verbindung schließen
        cur.close()
        conn.close()

        # Ergebnis zurückgeben
        return jsonify(data)
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
