<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>
    <link rel="stylesheet" href="loginStyle.css" />
    <style type="text/css">
    h4{
    color: red;
    text-align: center;
    padding: 0 0 20px
    }
    </style>
  </head>
  <body>
    <div class="wrapper">
      <div class="side background-container">
        <div class="background-overlay">
          <div class="content">
            <div class="page-container">
              <form id="loginForm">
                <h2>Login</h2>
                <h4>${error}</h4>
                <div class="input-box">
                  <input
                    type="text"
                    id="email"
                    placeholder="User Name:"
                    required
                    name="userName"
                  />
                </div>
                <div class="input-box">
                  <input
                    type="password"
                    id="password"
                    placeholder="Password:"
                    required
                    name="password"
                  />
                </div>
                <div class="button-group">
                  <button type="submit" class="submit-btn">Login</button>
                </div>
              </form>
              <div class="register-link">
                Don't have an account? <a href="reg.html">Register</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
