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

/**
 * Grabs the element with password id from html page and checks it's strength.
 * Returns a boolean value based on whether password is seen as strong or not
 */
function checkPasswordStrength() {

    var password = document.getElementById("password").value;
    var strengthText = document.getElementById("password-strength");
    var errorMsg = "";

    if (password.length > 32) {
        strengthText.textContent = "Too Long: Must be Less than 32 characters";
        strengthText.style.setProperty('color', 'red', 'important');
        return false;
    }

    if (password.length <= 8) {
        errorMsg += "- Too Short: Must be atleast 8 Characters\n";
        let lengthText = document.getElementById("password-length")
        lengthText.textContent = "- Too Short: Must be atleast 8 Characters";
        // Code uses Regex (Regular Expression) and property escape sequences to help parse info
        if (!/^[A-Z]+$/.test(password)) {
            errorMsg += "- Password must have atleast 1 Capital letter\n";
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
        if (errorMsg.length > 0) {
            strengthText.textContent = errorMsg;
            strengthText.style.setProperty('color', 'red', 'important');
            return false;
        }
        strengthText.textContent = "Strong password";
        strengthText.style.setProperty('color', 'green', 'important');
        return true;
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
}
