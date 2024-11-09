<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Sign Up</title>
    <link rel="stylesheet" href="regStyle.css" />
  </head>
  <body>
    <div class="wrapper">
      <div class="side background-container">
        <div class="sign background-overlay">
          <div class="content">
            <p>Sign UP</p>
          </div>
        </div>
      </div>

      <div class="page-container">
        <form id="signupForm" action = "register" method = "POST">
          <div class="page page-1">
            <h2>Personal Information</h2>
            <h2 style="color: red;">${answer}</h2>
            
    
  <div class="input-box">
    <input type="text" id="firstName" name="firstName" placeholder="First Name" required>
  </div>
  <div class="input-box">
    <input type="text" id="lastName" name="lastName" placeholder="Last Name" required>
  </div>
  <div class="input-box">
    <input type="tel" id="phoneNumber" name="phoneNumber" placeholder="Phone Number" required>
  </div>
  <div class="input-box">
    <select id="gender" name="gender" required>
      <option value="">Select Gender</option>
      <option value="MALE">Male</option>
      <option value="FEMALE">Female</option>
      <option value="OTHER">Other</option>
    </select>
  </div>
            <div class="button-group">
              <button type="button" class="next-btn">Next</button>
            </div>
          </div>

          <div class="page page-2">
            <h2>Location Details</h2>
            <div class="input-box">
    <select id="province" name="provinceCode" required>
      <option value="">Select Province</option>
      <option value="1">KIGALI</option>
      <option value="2">NORTH PROVINCE</option>
      <option value="3">SOUTH PROVINCE</option>
      <option value="4">EAST PROVINCE</option>
      <option value="5">WEST PROVINCE</option>
    </select>
  </div>
  <div class="input-box">
    <input type="text" id="district" name="district" placeholder="District" required>
  </div>
  <div class="input-box">
    <input type="text" id="districtCode" name="districtCode" placeholder="District Code" required>
  </div>
  <div class="input-box">
    <input type="text" id="sector" name="sector" placeholder="Sector" required>
  </div>
            <div class="button-group">
              <button type="button" class="prev-btn">Previous</button>
              <button type="button" class="next-btn">Next</button>
            </div>
          </div>

          <div class="page page-3">
            <h2>Location Details</h2>
            <div class="input-box">
    <input type="text" id="sectorCode" name="sectorCode" placeholder="Sector Code" required>
  </div>
  <div class="input-box">
    <input type="text" id="cell" name="cell" placeholder="Cell" required>
  </div>
  <div class="input-box">
    <input type="text" id="cellCode" name="cellCode" placeholder="Cell Code" required>
  </div>
  <div class="input-box">
    <input type="text" id="village" name="village" placeholder="Village" required>
  </div>
  <div class="input-box">
    <input type="text" id="villageCode" name="villageCode" placeholder="Village Code" required>
  </div>
            <div class="button-group">
              <button type="button" class="prev-btn">Previous</button>
              <button type="button" class="next-btn">Next</button>
            </div>
          </div>

          <div class="page page-4">
            <h2>Account Setup</h2>
            <!-- Role Selection -->
  <div class="input-box">
    <select id="role" name="role" required>
      <option value="">Select Role</option>
      <option value="STUDENT">Student</option>
      <option value="TEACHER">Teacher</option>
      <option value="HOD">HOD</option>
      <option value="DEAN">Dean</option>
      <option value="MANAGER">Manager</option>
    </select>
  </div>
  
  <div class="input-box">
    <input type="text" id="userName" name="userName" placeholder="Username" required>
  </div>

  <!-- Password Fields -->
  <div class="input-box">
    <input type="password" id="password" name="password" placeholder="Password" required>
  </div>
  <div class="input-box">
    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" required>
  </div>
            <div class="button-group">
              <button type="button" class="prev-btn">Previous</button>
              <button type="submit" class="submit-btn">Sign Up</button>
            </div>
          </div>
        </form>

        <div class="register-link">
          Already have an account? <a href="login.html">Login</a>
        </div>
      </div>
    </div>
    <script src="reg.js"></script>
  </body>
</html>
    