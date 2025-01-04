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