document.addEventListener('DOMContentLoaded', function () {
    var togglePassword = document.getElementById("toggle-passwordlogin");
    var passwordInput = document.getElementById("password");

    togglePassword.addEventListener("click", function() {
        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            this.classList.remove("fa-eye-slash");
            this.classList.add("fa-eye");
        } else {
            passwordInput.type = "password";
            this.classList.remove("fa-eye");
            this.classList.add("fa-eye-slash");
        }
    });
});


document.addEventListener('DOMContentLoaded', function () {
    var togglePassword = document.getElementById("toggle-password");
    var passwordInput = document.getElementById("password");
    var confirmPasswordInput = document.getElementById("confirm_password");
    var toggleConfirmPassword = document.getElementById("toggle-confirm-password");

    togglePassword.addEventListener("click", function() {
        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            this.classList.remove("fa-eye-slash");
            this.classList.add("fa-eye");
        } else {
            passwordInput.type = "password";
            this.classList.remove("fa-eye");
            this.classList.add("fa-eye-slash");
        }
    });

    toggleConfirmPassword.addEventListener("click", function() {
        if (confirmPasswordInput.type === "password") {
            confirmPasswordInput.type = "text";
            this.classList.remove("fa-eye-slash");
            this.classList.add("fa-eye");
        } else {
            confirmPasswordInput.type = "password";
            this.classList.remove("fa-eye");
            this.classList.add("fa-eye-slash");
        }
    });
});
    
       function checkPasswordStrength() {

        var password = document.getElementById("password").value;
        var strengthText = document.getElementById("password-strength");
        
        if (/^\d+$/.test(password) && password.length < 12) {
            strengthText.textContent = "Weak password: Add letters and symbols.";
            strengthText.style.setProperty('color', 'red', 'important');

        }
        else if (/^[a-zA-Z]+$/.test(password)) {
            strengthText.textContent = "Weak password: Add numbers and symbols.";
            strengthText.style.setProperty('color', 'red', 'important');
        } else if (/^[a-zA-Z0-9]+$/.test(password)) {
            strengthText.textContent = "Okay password: Add symbols.";
            strengthText.style.setProperty('color', 'orange', 'important');
        } else if (/^[a-zA-Z0-9!@#$%^&*()_+=\-\[\]{};':"\\|,.<>\/?]+$/.test(password)) {
            strengthText.textContent = "Strong password";
            strengthText.style.setProperty('color', 'green', 'important');
        } else if (password.length === 0) {
            strengthText.textContent = "";
        } else {
            strengthText.textContent = "Password format is invalid.";
            strengthText.style.setProperty('color', 'green', 'important');
        }
    }

    function validatePasswords() {
        var password = document.getElementById("password").value;
        var confirmPassword = document.getElementById("confirm_password").value;

        if (password !== confirmPassword) {
            alert("The passwords do not match.");
            return false;
        }

        // You might want to check password strength again or other conditions here
        return true;
    }
