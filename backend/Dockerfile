FROM python:3.9-slim

# Installiere Abhängigkeiten
WORKDIR /app
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt

# Kopiere den Code in das Image
COPY . .

# Flask-App starten
CMD ["python", "app.py"]
