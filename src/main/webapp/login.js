document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("loginForm");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    if (validateForm()) {
      try {
        await sendLoginDataToServer();
        console.log("Login successful!");
        // Redirect the user or show a success message
      } catch (error) {
        console.error("Error logging in:", error);
        alert("Error logging in. Please try again.");
      }
    }
  });

  function validateForm() {
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");
    let isValid = true;

    if (!emailInput.value) {
      isValid = false;
      emailInput.classList.add("error");
      showErrorMessage(emailInput, "Email is required");
    } else {
      emailInput.classList.remove("error");
      hideErrorMessage(emailInput);
    }

    if (!passwordInput.value) {
      isValid = false;
      passwordInput.classList.add("error");
      showErrorMessage(passwordInput, "Password is required");
    } else {
      passwordInput.classList.remove("error");
      hideErrorMessage(passwordInput);
    }

    return isValid;
  }

  function showErrorMessage(input, message) {
    if (!input.nextElementSibling?.classList.contains("error-message")) {
      const errorMessage = document.createElement("div");
      errorMessage.className = "error-message";
      errorMessage.textContent = message;
      errorMessage.style.color = "red";
      input.parentNode.appendChild(errorMessage);
    }
  }

  function hideErrorMessage(input) {
    const errorMessage = input.parentNode.querySelector(".error-message");
    if (errorMessage) {
      errorMessage.remove();
    }
  }

  async function sendLoginDataToServer() {
    const formData = new FormData(form);

    const response = await fetch("/login", {
      method: "POST",
      body: formData,
    });

    if (!response.ok) {
      throw new Error(`HTTP error ${response.status}`);
    }

    const serverResponse = await response.json();
    console.log("Server response:", serverResponse);
  }
});
