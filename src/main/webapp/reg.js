document.addEventListener("DOMContentLoaded", function () {
  const pages = document.querySelectorAll(".page");
  const page1 = document.querySelector(".page-1");
  const page2 = document.querySelector(".page-2");
  const page3 = document.querySelector(".page-3");
  const page4 = document.querySelector(".page-4");
  const form = document.getElementById("signupForm");
  let currentPage = 1;

  // Initialize first page and progress

  disablePage4();
  disablePage2();
  disablePage3();
  activatePage1();
  updateDisplay();

  // Add event listeners to all next buttons
  document.querySelectorAll(".next-btn").forEach((button) => {
    button.addEventListener("click", () => {
      if (validateCurrentPage()) {
        console.log("Before: " + currentPage);
        if (currentPage == 1) {
          disablePage1();
          activatePage2();
        } else if (currentPage == 2) {
          disablePage2();
          activatePage3();
        } else if (currentPage == 3) {
          disablePage3();
          activatePage4();
        }
        currentPage++;
        console.log("After: " + currentPage);
        updateDisplay();
      }
    });
  });

  // Add event listeners to all previous buttons
  document.querySelectorAll(".prev-btn").forEach((button) => {
    button.addEventListener("click", () => {
      if (currentPage == 2) {
        disablePage2();
        activatePage1();
      } else if (currentPage == 3) {
        disablePage3();
        activatePage2();
      } else if (currentPage == 4) {
        disablePage4();
        activatePage3();
      }
      currentPage--;
      updateDisplay();
    });
  });

  // Handle form submission
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    if (validateCurrentPage()) {
      try {
        response = await sendFormDataToServer();
         const serverResponse = await response.json(); // Parse JSON response
        	console.log("Server response:", serverResponse);
        	if(serverResponse.status == "exists"){
				window.location.href = "regno.jsp";
			}else{
				window.location.href = "regok.jsp";
			}
        	
      } catch (error) {
        console.error("Error submitting form:", error);
        alert("Error signing up. Please try again later.");
      }
    }
  });

  function updateDisplay() {
    updateNavigationButtons();
  }

  function disablePage1() {
    page1.classList.remove("active");
  }

  function activatePage1() {
    page1.classList.add("active");
  }

  function disablePage2() {
    page2.classList.remove("active");
  }

  function activatePage2() {
    page2.classList.add("active");
  }

  function disablePage3() {
    page3.classList.remove("active");
  }

  function activatePage3() {
    page3.classList.add("active");
  }

  function disablePage4() {
    page4.classList.remove("active");
  }

  function activatePage4() {
    page4.classList.add("active");
  }

  function updateNavigationButtons() {
    const prevButtons = document.querySelectorAll(".prev-btn");
    const nextButtons = document.querySelectorAll(".next-btn");
    const submitButton = document.querySelector(".submit-btn");

    prevButtons.forEach((button) => {
      button.style.display = currentPage - 1 === 0 ? "none" : "block";
    });

    nextButtons.forEach((button) => {
      button.style.display =
        currentPage - 1 === pages.length - 1 ? "none" : "block";
    });

    if (submitButton) {
      submitButton.style.display =
        currentPage - 1 === pages.length - 1 ? "block" : "none";
    }
  }
  
  async function sendFormDataToServer() {
  // Create an object with all the form data
  const formData = {
    // User Information
    userName: form.querySelector("#userName").value,
    firstName: form.querySelector("#firstName").value,
    lastName: form.querySelector("#lastName").value,
    phoneNumber: form.querySelector("#phoneNumber").value,
    gender: form.querySelector("#gender").value,
    role: form.querySelector("#role").value,
    password: form.querySelector("#password").value,
    
    // Location Information
    provinceCode: form.querySelector("#province").value,
    district: form.querySelector("#district").value,
    districtCode: form.querySelector("#districtCode").value,
    sector: form.querySelector("#sector").value,
    sectorCode: form.querySelector("#sectorCode").value,
    cell: form.querySelector("#cell").value,
    cellCode: form.querySelector("#cellCode").value,
    village: form.querySelector("#village").value,
    villageCode: form.querySelector("#villageCode").value
  };

  // Convert the form data to URL-encoded format
  const urlEncodedData = Object.keys(formData)
    .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(formData[key])}`)
    .join('&');

  const response = await fetch("register", {
    method: "POST",
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: urlEncodedData
  });

  if (!response.ok) {
    throw new Error(`HTTP error ${response.status}`);
  }
  

  return response;
}

  function validateCurrentPage() {
    const currentPageElement = pages[currentPage - 1];
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
          errorMessage.style.color = "red";
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

    // Check if password and confirm password fields match
    const passwordInput = currentPageElement.querySelector("#password");
    const confirmPasswordInput = currentPageElement.querySelector("#confirmPassword");
    if (passwordInput && confirmPasswordInput) {
      if (passwordInput.value !== confirmPasswordInput.value) {
        isValid = false;
        passwordInput.classList.add("error");
        confirmPasswordInput.classList.add("error");
        if (
          !confirmPasswordInput.nextElementSibling?.classList.contains(
            "error-message"
          )
        ) {
          const errorMessage = document.createElement("div");
          errorMessage.className = "error-message";
          errorMessage.textContent = "Passwords do not match";
          errorMessage.style.color = "red";
          confirmPasswordInput.parentNode.appendChild(errorMessage);
        }
      } else {
        passwordInput.classList.remove("error");
        confirmPasswordInput.classList.remove("error");
        const errorMessage =
          confirmPasswordInput.parentNode.querySelector(".error-message");
        if (errorMessage) {
          errorMessage.remove();
        }
      }
    }

    return isValid;
  }
});
