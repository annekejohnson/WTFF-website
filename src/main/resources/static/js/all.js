
        window.onpageshow = function(event) {
        // If the page is loaded from the cache (e.g., back button was clicked)
        if (event.persisted) {
            // Reload the page to ensure updated content or session status
            window.location.reload();
        }
        };
