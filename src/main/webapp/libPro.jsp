<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Find Province</title>
    <link rel="stylesheet" href="loginStyle.css" />
  </head>
  <style>
  * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: "Inter", system-ui, -apple-system, sans-serif;
      }

      .navbar {
        background-color: #1a365d;
        padding: 1rem 2rem;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      }

      .navbar h1 {
        color: white;
        font-size: 1.5rem;
        margin-bottom: 1rem;
      }

      .navbar a {
        color: #e2e8f0;
        text-decoration: none;
        margin-right: 2rem;
        font-weight: 500;
        transition: color 0.3s ease;
      }

      .navbar a:hover {
        color: #90cdf4;
      }

      .background-container {
        background-image: url("/api/placeholder/1920/1080");
        min-height: calc(100vh - 80px);
        background-size: cover;
        position: relative;
      }

      .background-overlay {
        background: rgba(255, 255, 255, 0.95);
        min-height: calc(100vh - 80px);
        padding: 2rem;
      }
      
      body {
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: var(--bg-color);
        color: var(--primary-color);
      }
  </style>
  <body>
    <header class="navbar">
      <h1>Library</h1>
      <nav class="nav-links">
        <a href="libHome.jsp">Home</a>
        <a href="libBor.jsp">Book Borrowing</a>
        <a href="libMem.jsp">Memberships</a>
        <a href="bkno.jsp">Books Per Room</a>
        <a href="libPro.jsp">Get Province</a>
        <a href="login.html" style="margin-right: 0;">Log Out</a>
      </nav>
    </header>
  </body>

  <div class="wrapper">
    <div class="side background-container">
      <div class="background-overlay">
        <div class="content">
          <div class="page-container">
            <form id="loginForm" action = "pro" method="post">
            <h4 style="color: green; text-align: center;">${ok}</h4>
            <h4 style="color: red; text-align: center;">${error}</h4>
              <h2>Find Your Province</h2>
              <div class="input-box">
                <input
                  type="text"
                  id="email"
                  placeholder="Phone number"
                  required
                  name="phone"
                />
              </div>
              <div class="button-group">
                <button type="submit" class="submit-btn">Find</button>
              </div>
            </form>
            <div class="register-link"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</html>
