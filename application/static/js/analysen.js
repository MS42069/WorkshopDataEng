function toggleSidebar() {
    const sidebar = document.querySelector(".sidebar");
    const content = document.querySelector(".content");

    // Überprüfen, ob die Sidebar eingeklappt ist
    if (sidebar.classList.contains("closed")) {
        sidebar.classList.remove("closed");
    } else {
        sidebar.classList.add("closed");
    }
}

function loadContentForArea(url, areaId) {
    const graphicsArea = document.getElementById(areaId);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("Fehler beim Laden der Inhalte");
            }
            return response.text();
        })
        .then(data => {
            // Inhalte in den Bereich einfügen
            graphicsArea.innerHTML = data;

            // Flexbox-Stile anwenden
            graphicsArea.style.display = "flex";
            graphicsArea.style.justifyContent = "space-between";
            graphicsArea.style.alignItems = "center";
        })
        .catch(error => {
            console.error("Fehler:", error);
        });
}

function loadContentForBothAreas(url1, url2) {
    // Erste graphics-area
    const graphicsArea1 = document.getElementById("graphics-area-1");
    fetch(url1)
        .then(response => {
            if (!response.ok) {
                throw new Error("Fehler beim Laden der Inhalte");
            }
            return response.text();
        })
        .then(data => {
            graphicsArea1.innerHTML = data;
            graphicsArea1.style.display = "flex";
        })
        .catch(error => console.error("Fehler beim Laden der ersten Area:", error));

    // Zweite graphics-area
    const graphicsArea2 = document.getElementById("graphics-area-2");
    fetch(url2)
        .then(response => {
            if (!response.ok) {
                throw new Error("Fehler beim Laden der Inhalte");
            }
            return response.text();
        })
        .then(data => {
            graphicsArea2.innerHTML = data;
            graphicsArea2.style.display = "flex";
        })
        .catch(error => console.error("Fehler beim Laden der zweiten Area:", error));
}




