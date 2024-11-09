document.addEventListener("DOMContentLoaded", function () {
  const pages = document.querySelectorAll(".page");
  const steps = document.querySelectorAll(".step");
  const progressBar = document.querySelector(".progress-bar");
  const form = document.getElementById("signupForm");
  let currentPage = 0;

  // Initialize first page and progress
  updateDisplay();

  // Add event listeners to all next buttons
  document.querySelectorAll(".next-btn").forEach((button) => {
    button.addEventListener("click", () => {
      if (validateCurrentPage()) {
        currentPage++;
        updateDisplay();
      }
    });
  });

  // Add event listeners to all previous buttons
  document.querySelectorAll(".prev-btn").forEach((button) => {
    button.addEventListener("click", () => {
      currentPage--;
      updateDisplay();
    });
  });

  // Handle form submission
  form.addEventListener("submit", (e) => {
    e.preventDefault();
    if (validateCurrentPage()) {
      // Here you would typically send the form data to your server
      console.log("Form submitted successfully!");
      alert("Sign up successful!");
    }
  });

  function updateDisplay() {
    // Update pages
    pages.forEach((page, index) => {
      page.classList.remove("active");
      if (index === currentPage) {
        page.classList.add("active");
      }
    });

    // Update steps
    steps.forEach((step, index) => {
      step.classList.remove("active", "completed");
      if (index === currentPage) {
        step.classList.add("active");
      } else if (index < currentPage) {
        step.classList.add("completed");
      }
    });

    // Update progress bar
    const progress = (currentPage / (pages.length - 1)) * 100;
    progressBar.style.background = `linear-gradient(to right, #0077b6 ${progress}%, #caf0f8 ${progress}%)`;

    // Show/hide buttons based on current page
    updateNavigationButtons();
  }

  function updateNavigationButtons() {
    const prevButtons = document.querySelectorAll(".prev-btn");
    const nextButtons = document.querySelectorAll(".next-btn");
    const submitButton = document.querySelector(".submit-btn");

    prevButtons.forEach((button) => {
      button.style.display = currentPage === 0 ? "none" : "block";
    });

    nextButtons.forEach((button) => {
      button.style.display =
        currentPage === pages.length - 1 ? "none" : "block";
    });

    if (submitButton) {
      submitButton.style.display =
        currentPage === pages.length - 1 ? "block" : "none";
    }
  }

  function validateCurrentPage() {
    const currentPageElement = pages[currentPage];
    const inputs = currentPageElement.querySelectorAll("input, select");
    let isValid = true;

    inputs.forEach((input) => {
      if (input.hasAttribute("required") && !input.value) {
        isValid = false;
        input.classList.add("error");
        if (!input.nextElementSibling?.classList.contains("error-message")) {
          const errorMessage = document.createElement("div");
          errorMessage.className = "error-message";
          errorMessage.textContent = "This field is required";
          input.parentNode.appendChild(errorMessage);
        }
      } else {
        input.classList.remove("error");
        const errorMessage = input.parentNode.querySelector(".error-message");
        if (errorMessage) {
          errorMessage.remove();
        }
      }
    });

    return isValid;
  }
});
