<%@page import="java.util.List"%>
<%@page import="model.Room"%>
<%@page import="model.Shelf"%>
<%@page import="model.Book"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<%List<Room> rooms = (List<Room>)session.getAttribute("rooms");
%>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Library Management System</title>
    <style>
      /* Navbar styling */
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
      :root {
        --primary-color: #2c3e50;
        --secondary-color: #3498db;
        --bg-color: #f5f6fa;
        --border-radius: 8px;
      }

      body {
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: var(--bg-color);
        color: var(--primary-color);
      }

      .container {
        max-width: 700px;
        margin: 0 auto;
        padding: 20px;
      }

      h2 {
        color: var(--primary-color);
        margin-bottom: 30px;
        font-size: 2em;
        text-align: center;
      }

      .section {
        background: white;
        padding: 25px;
        border-radius: var(--border-radius);
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        margin-bottom: 30px;
      }

      .form-section h3 {
        color: var(--secondary-color);
        margin-top: 0;
        margin-bottom: 20px;
        padding-bottom: 10px;
        border-bottom: 2px solid var(--bg-color);
      }

      .form-section-grid {
        min-width: 200px;
      }

      input,
      select {
        width: 100%;
        padding: 12px;
        margin: 8px 0 20px;
        border: 1px solid #ddd;
        border-radius: 4px;
        font-size: 16px;
        box-sizing: border-box;
        transition: border-color 0.3s ease;
      }

      input:focus,
      select:focus {
        outline: none;
        border-color: var(--secondary-color);
        box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.2);
      }

      button {
        background-color: var(--secondary-color);
        color: white;
        padding: 12px 24px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
        width: 100%;
        transition: background-color 0.3s ease;
      }

      button:hover {
        background-color: #2980b9;
      }

      label {
        display: block;
        margin-bottom: 8px;
        font-weight: 500;
        color: var(--primary-color);
      }
      
      .message{
      	color: green;
      	text-align: center;
      }
      .error{
      	color: red;
      	text-align: center;
      }

      /* Style for select dropdowns */
      select {
        appearance: none;
        -webkit-appearance: none;
        -moz-appearance: none;
        background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
        background-repeat: no-repeat;
        background-position: right 1rem center;
        background-size: 1em;
      }

      /* Responsive adjustments */
      /* @media (min-width: 768px) {
        .form-sections-grid {
          display: grid;
          grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
          gap: 20px;
        }

        .section {
          margin-bottom: 0;
        }
      } */
    </style>
  </head>
  <body>
    <header class="navbar">
      <h1>Library</h1>
      <nav class="nav-links">
        <a href="libHome.jsp">Home</a>
        <a href="libBor.jsp">Book Borrowing</a>
        <a href="libMem.jsp">Memberships</a>
        <a href="libPro.jsp">Get Province</a>
        <a href="bkno.jsp">Books Per Room</a><a href="login.html" style="margin-right: 0;">Log Out</a>
      </nav>
    </header>
    <div class="container">
      <h2>Library Management</h2>

      <!-- Register a New Room -->
      <div class="section form-section">
        <h3>Register a New Room</h3>
        <h4 class="message">${roomMessage}</h4>
        <h4 class="error">${roomError}</h4>
        <form action="addRoom" method="post">
          <label for="roomCode">Room Code:</label>
          <input type="text" id="roomCode" name="roomCode" required />

          <button type="submit">Add Room</button>
        </form>
      </div>

      <!-- Register a New Shelf -->
      <div class="section form-section">
        <h3>Register a New Shelf</h3>
        <h4 class="message">${shelfMessage}</h4>
        <h4 class="error">${shelfError}</h4>
        <form action="addShelf" method="post">

          <label for="bookCategory">Book Category:</label>
          <input type="text" id="bookCategory" name="bookCategory" required />

          <label for="shelfRoom">Assign to Room:</label>
          <select id="shelfRoom" name="room" required>
            <option value="">Select Room</option>
          </select>

          <button type="submit">Add Shelf</button>
        </form>
      </div>

      <!-- Register a New Book -->
      <div class="section form-section">
        <h3>Register a New Book</h3>
        <h4 class="message">${bookMessage}</h4>
        <h4 class="error">${bookError}</h4>
        <form action="addBook" method="post">
          <label for="title">Title:</label>
          <input type="text" id="title" name="title" required />

          <label for="edition">Edition:</label>
          <input type="number" id="edition" name="edition" required min="1"/>

          <label for="ISBNCode">ISBN Code:</label>
          <input type="text" id="ISBNCode" name="ISBNCode" required />

          <label for="publicationYear">Publication Year:</label>
          <input
            type="date"
            id="publicationYear"
            name="publicationYear"
            required
          />

          <label for="publisherName">Publisher Name:</label>
          <input type="text" id="publisherName" name="publisherName" required />

          <label for="bookRoom">Select Room:</label>
          <select
            id="bookRoom"
            name="room"
            onchange="populateShelves()"
            required
          >
            <option value="">Select Room</option>
          </select>

          <label for="bookShelf">Assign to Shelf:</label>
          <select id="bookShelf" name="shelf" required>
            <option value="">Select Shelf</option>
          </select>

          <button type="submit">Add Book</button>
        </form>
      </div>
    </div>

    <script>
 // Initialize the rooms variable in JavaScript
    const rooms = {
        <% 
        for (Room room : rooms) { 
            if (room != null && room.getRoomCode() != null) { %>
                "<%= room.getRoomCode() %>": [
                    <% 
                    // Check if shelves list is not null before iterating
                    if (room.getShelves() != null) { 
                        for (Shelf shelf : room.getShelves()) { 
                            if (shelf != null && shelf.getBookCategory() != null) { %>
                                "<%= shelf.getBookCategory() %>"<%= (room.getShelves().indexOf(shelf) == room.getShelves().size() - 1) ? "" : "," %>
                            <% } 
                        } 
                    } %>
                ]<%= (rooms.indexOf(room) == rooms.size() - 1) ? "" : "," %>
            <% } 
        } %>
    };

      // Populate Room dropdowns
      function populateRoomDropdowns() {
        const roomDropdowns = document.querySelectorAll(
          "#shelfRoom, #bookRoom"
        );

        roomDropdowns.forEach((dropdown) => {
          dropdown.innerHTML = '<option value="">Select Room</option>';
          for (const room in rooms) {
            const option = document.createElement("option");
            option.value = room;
            option.textContent = room;
            dropdown.appendChild(option);
          }
        });
      }

      // Populate shelves based on the selected room
      function populateShelves() {
        const roomName = document.getElementById("bookRoom").value;
        const shelfDropdown = document.getElementById("bookShelf");
        shelfDropdown.innerHTML = '<option value="">Select Shelf</option>';

        if (roomName && rooms[roomName]) {
          rooms[roomName].forEach((shelf) => {
            const option = document.createElement("option");
            option.value = shelf;
            option.textContent = shelf;
            shelfDropdown.appendChild(option);
          });
        }
      }


      // Initialize dropdowns on page load
      document.addEventListener("DOMContentLoaded", populateRoomDropdowns);
    </script>
  </body>
</html>
