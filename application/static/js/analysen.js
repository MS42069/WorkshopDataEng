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
    const content = document.getElementById(areaId);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("Fehler beim Laden der Inhalte");
            }
            return response.text();
        })
        .then(data => {
            // Inhalte in den Bereich einfügen
            content.innerHTML = data;

            // Flexbox-Stile anwenden
            content.style.display = "flex";
            content.style.justifyContent = "center";
            content.style.alignItems = "center";
        })
        .catch(error => {
            console.error("Fehler:", error);
        });
}

function loadContentForArea_Employees(url, areaId) {
    const content = document.getElementById(areaId);
    console.log("loadContentForArea_Employees");
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("Fehler beim Laden der Inhalte");
            }
            return response.text();
        })
        .then(data => {
            // Inhalte in den Bereich einfügen
            content.innerHTML = data;

            // Plotly-Grafik neu laden, falls vorhanden
            const graphJSONScript = content.querySelector("#cluster-plot-script");
            if (graphJSONScript) {
                const graphJSON = JSON.parse(graphJSONScript.textContent);
                Plotly.newPlot('cluster-plot', graphJSON.data, graphJSON.layout);
            }

            // Flexbox-Stile anwenden
            content.style.display = "flex";
            content.style.justifyContent = "center";
            content.style.alignItems = "center";
        })
        .catch(error => {
            console.error("Fehler:", error);
        });
}


