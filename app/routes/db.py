from flask import Blueprint, jsonify
import psycopg2

db_bp = Blueprint("db", __name__)

@db_bp.route('/test-db', methods=['GET'])
def test_db_connection():
    # Importiere DATABASE lokal innerhalb der Funktion
    from app.config import DATABASE

    try:
        conn = psycopg2.connect(
            host=DATABASE['host'],
            port=DATABASE['port'],
            dbname=DATABASE['dbname'],
            user=DATABASE['user'],
            password=DATABASE['password']
        )
        cur = conn.cursor()

        # Beispiel-Query
        cur.execute("SELECT * FROM courses LIMIT 1")
        result = cur.fetchone()

        if result:
            data = {
                "id": result[0],
                "name": result[1]
            }
        else:
            data = {"message": "No data found in the 'courses' table."}

        cur.close()
        conn.close()

        return jsonify(data)

    except Exception as e:
        return jsonify({"error": str(e)}), 500
