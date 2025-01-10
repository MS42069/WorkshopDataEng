# Basis-Image
FROM python:3.9-slim

# Arbeitsverzeichnis setzen
WORKDIR /app

# Systempakete installieren (falls nötig)
RUN apt-get update && apt-get install -y \
    build-essential \
    libfreetype6-dev \
    python3-dev \
    && apt-get clean

# Abhängigkeiten kopieren und installieren
COPY requirements.txt /app/requirements.txt
RUN pip install --no-cache-dir -r /app/requirements.txt

# Alle Dateien ins Arbeitsverzeichnis kopieren
COPY . /app

# Port für Streamlit freigeben
EXPOSE 8501

# Streamlit starten
CMD ["streamlit", "run", "app.py", "--server.port=8501", "--server.address=0.0.0.0"]
