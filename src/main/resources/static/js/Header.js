document.getElementById('sidebarToggle').addEventListener('click', function() {
    const layout = document.querySelector('.layout');
    layout.classList.add('transition-sidebar');
    layout.classList.toggle('show-sidebar');
});

document.addEventListener('click', function(event) {
    const layout = document.querySelector('.layout');
    const sidebarToggle = document.getElementById('sidebarToggle');

    // Check if the click is outside of the layout element and not on the toggle button
    if (!layout.contains(event.target) && event.target !== sidebarToggle) {
        // If the click is outside, remove the 'show-sidebar' class
        layout.classList.remove('show-sidebar');
    }
});