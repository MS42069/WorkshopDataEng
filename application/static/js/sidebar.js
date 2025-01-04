function toggleSidebar() {
    const sidebar = document.querySelector(".sidebar");
    const content = document.querySelector(".content");

    // Überprüfen, ob die Sidebar geöffnet ist
    if (sidebar.classList.contains("open")) {
        sidebar.classList.remove("open");
        content.style.marginLeft = "0"; // Zurücksetzen des Inhaltsbereichs
    } else {
        sidebar.classList.add("open");
        content.style.marginLeft = "250px"; // Platz schaffen für die Sidebar
    }
}
