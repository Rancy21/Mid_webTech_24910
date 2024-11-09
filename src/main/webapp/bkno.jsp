<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="model.Room"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Library Membership Application</title>
    <%List<Room> rooms = (List<Room>)session.getAttribute("rooms");%>
    <style>
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
        font-size: 1rem;
        margin-bottom: 1.5rem;
      }

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
      color: green;
      text-align: center;
      }
      
      .error{
      color: red;
      text-align: center;
      }
    </style>
  </head>
  <body>
    <header class="navbar">
      <h1>Library</h1>
      <nav class="nav-links">
        <a href="libHome.jsp">Home</a>
        <a href="libBor.jsp">Book Borrowing</a>
        <a href="libMem.jsp">Memberships</a>
        <a href="bkno.jsp">Books Per Room</a>
        <a href="pro.jsp">Get Province</a><a href="login.html" style="margin-right: 0;">Log Out</a>
      </nav>
    </header>
    <div class="container">
      <h2>Books Per Room</h2>

      <!-- Membership Type Description -->

      <!-- Membership Selection Form -->
      <h4 class="error">${noError}</h4>
      <h4 class="message">${noMessage}</h4>
      <form action="booksPerRoom" method="POST">
        <label for="membershipType">Select a Room:</label>
        <select id="membershipType" name="roomCode" required>
          <option value="">Select Room</option>
          <%for(Room room : rooms){ %>
          <option value="<%=room.getRoomCode()%>"><%=room.getRoomCode()%></option>
          <%} %>
        </select>
        <button type="submit">Get Amount of Books</button>
      </form>
    </div>
  </body>
</html>
