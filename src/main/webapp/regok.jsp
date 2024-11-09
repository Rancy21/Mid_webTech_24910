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
        <form id="signupForm" action="login.html">
            <h2>${answer}</h2>
            <div class="button-group">
            <form action = "login.html">
            <button type="submit" class="prev-btn log-btn">Login</button>
            </form>
            </div>
        </form>
      </div>
      </div>
  </body>
</html>
