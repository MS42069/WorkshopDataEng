# Basis-Image
FROM python:3.10-slim

# Arbeitsverzeichnis setzen
WORKDIR /app

# Anforderungen kopieren und installieren
COPY requirements.txt requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# App-Dateien kopieren
COPY . .

# Exponiere den Port
EXPOSE 5000

# Befehl zum Starten der Anwendung
CMD ["python", "run.py"]
