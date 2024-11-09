<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="model.MembershipStatus"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.Optional"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.Membership"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Library Membership Application</title>
    <style>
    <%User user = (User)session.getAttribute("user");
    Date today = new Date();
    Membership lastMem = user.getMostRecentMembership().orElse(null);
    boolean isvalid = false;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String date;
    if(lastMem !=null && lastMem.getStatus().equals(MembershipStatus.APPROVED)){
		 date = formatter.format(lastMem.getRegisterDate());
		 String type = lastMem.getType().getName();
		isvalid = true;
    }
    %>
      /* Using similar styles as the original design */
      :root {
        --primary-color: #2c3e50;
        --secondary-color: #3498db;
        --bg-color: #f5f6fa;
        --border-radius: 8px;
      }
      
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
        margin-bottom: 20px;
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

      body {
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        background-color: var(--bg-color);
        color: var(--primary-color);
        margin: 0;
        padding: 20px;
      }

      .container {
        max-width: 600px;
        margin: 0 auto;
        padding: 20px;
        background: white;
        border-radius: var(--border-radius);
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      }

      h2 {
        color: var(--primary-color);
        font-size: 2em;
        text-align: center;
      }

      p {
      margin-top: 10px;
        font-size: 1rem;
        margin-bottom: 1.5rem;
      }
      ul{
      padding: 0 0 10px 20px;}
      

      select,
      button {
        width: 100%;
        padding: 12px;
        margin: 12px 0;
        font-size: 1rem;
        border-radius: var(--border-radius);
        border: 1px solid #ddd;
      }

      button {
        background-color: var(--secondary-color);
        color: white;
        border: none;
        cursor: pointer;
        transition: background-color 0.3s;
      }

      button:hover {
        background-color: #2980b9;
      }

      .membership-info {
        font-weight: bold;
        color: var(--secondary-color);
        margin-top: 20px;
      }
      
      .message{
      color:green;
      text-align: center;
      }
      
      .error{
      color:red;
      tex-align: center;
      }
    </style>
  </head>
  <body>
  <header class="navbar">
        <h1>Library</h1>
        <nav class="nav-links">
            <a href="books.jsp">Home</a>
        <a href="borrowing">Borrowed books</a>
        <a href="mem.jsp">My Membership</a>
        <a href="pro.jsp">Get Province</a>
        <a href="login.html" style="margin-right: 0;">Log Out</a>
		</nav>
    </header>
    <div class="container">
      <h2>Apply for Membership</h2>
      <h3 class = "error">${memError}</h3>
    <h3 class = "message">${memMessage}</h3>

      <!-- Membership Type Description -->
      <p>Select your desired membership type:</p>
      <ul>
        <li>Gold: 50 Rwf per day, can borrow up to 5 books.</li>
        <li>Silver: 30 Rwf per day, can borrow up to 3 books.</li>
        <li>Striver: 10 Rwf per day, can borrow up to 2 books.</li>
      </ul>

      <!-- Membership Selection Form -->
      <form action="applyMembership" method="POST">
        <label for="membershipType">Choose Membership Type:</label>
        <select id="membershipType" name="membershipType" required>
          <option value="">Select Membership</option>
          <option value="Gold">Gold</option>
          <option value="Silver">Silver</option>
          <option value="Striver">Striver</option>
        </select>
        <button type="submit">Apply for Membership</button>
      </form>
      
      <%if(lastMem !=null && lastMem.getStatus().equals(MembershipStatus.APPROVED)){ %>
      <!-- Last Membership Info -->
      <div class="membership-info">
        <!-- Assuming server-side script populates these values -->
        <p>Last Membership Type: <strong><%=lastMem.getType().getName()%></strong></p>
        <p>Approval Date: <strong><%=formatter.format(lastMem.getRegisterDate())%></strong></p>
      </div>
      <%} %>
    </div>
  </body>
</html>
