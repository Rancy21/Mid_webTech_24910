<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.User"%>
<%@page import="model.Membership"%>
<%@page import="model.MembershipStatus"%>
<%@page import="controller.UserDao"%>
<!DOCTYPE html>
<%UserDao dao = new UserDao();
List<User> userList = dao.getAllUsers();%>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Pending Membership Requests</title>
    <style>
      /* Basic styling similar to the provided example */
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

      body {
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: var(--bg-color);
        color: var(--primary-color);
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

      .container {
        max-width: 800px;
        margin: 0 auto;
        padding: 20px;
        background: white;
        border-radius: var(--border-radius);
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
      }

      h2 {
        text-align: center;
        color: var(--primary-color);
      }

      table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
      }

      table th,
      table td {
        padding: 12px;
        text-align: left;
        border-bottom: 1px solid #ddd;
      }

      table th {
        background-color: var(--secondary-color);
        color: white;
      }

      button {
        background-color: var(--secondary-color);
        color: white;
        padding: 8px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
      }

      button:hover {
        background-color: #2980b9;
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
        <a href="libPro.jsp">Get Province</a>
        <a href="login.html" style="margin-right: 0;">Log Out</a>
      </nav>
    </header>

    <div class="container">
      <h2>Pending Membership Requests</h2>
 <h4 class="message">${message}</h4>
      <h4 class="error">${error}</h4>
      <table>
        <thead>
          <tr>
            <th>User Name</th>
            <th>Membership Type</th>
            <th>Details</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody id="requestsTableBody">
          <!-- Table rows will be dynamically populated -->
        </tbody>
      </table>
    </div>

    <script>
      // Map of membership details based on type
      const detailsMap = {
        Gold: "50 Rwf per day, can borrow up to 5 books",
        Silver: "30 Rwf per day, can borrow up to 3 books",
        Striver: "10 Rwf per day, can borrow up to 2 books",
      };

      // Fetch requests from server-side data
      const requests = [
        <% 
          // Assuming 'userList' is available in the request or session scope
          for (User user : userList) { 
            Membership mostRecentMembership = user.findMembershipWithNullRegisterDate().orElse(null);
            if (mostRecentMembership != null) {
        %>
        {
          firstName: "<%= user.getFirstName() %>",
          lastName: "<%= user.getLastName() %>",
          userName: "<%= user.getUserName() %>",
          membershipType: "<%= mostRecentMembership.getType().getName()%>",
        },
        <%   } 
          } 
        %>
      ];

      // Render the requests table
      function renderRequests() {
        const tableBody = document.getElementById("requestsTableBody");
        requests.forEach((request) => {
          const row = document.createElement("tr");

          row.innerHTML = `
            <td><%out.println("${request.firstName}");%> <%out.println("${request.lastName}");%></td>
            <td><%out.println("${request.membershipType}");%></td>
            <td><%out.println("${detailsMap[request.membershipType]}");%></td>
            <td>
              <form method="POST" action="processMembership">
                <input type="hidden" name="userName" value="<%out.println("${request.userName}");%>">
                <input type="hidden" name="membershipType" value="<%out.println("${request.membershipType}");%>">
                <button type="submit" formaction="processMembership?approval=approve">Approve</button>
                <button type="submit" formaction="processMembership?approval=reject">Reject</button>
              </form>
            </td>
          `;
          tableBody.appendChild(row);
        });
      }

      // Initial render of requests
      renderRequests();
    </script>
  </body>
</html>
