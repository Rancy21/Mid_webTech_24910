<%@page import="controller.MyHelper"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="model.Borrower"%>
<%@page import="controller.BorrowerDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Library Borrow Requests</title>
    <%MyHelper helper = new MyHelper();
    helper.UpdateBorrows();
    BorrowerDao dao = new BorrowerDao();
    List<Borrower> bors = dao.getAllBorrowers();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    %>
    <!-- <%out.println(bors);%> -->
    <style>
      /* Styles from libHome.html for consistency */
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
        max-width: 1200px;
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

      .section h3 {
        color: var(--secondary-color);
        margin-top: 0;
        margin-bottom: 20px;
        padding-bottom: 10px;
        border-bottom: 2px solid var(--bg-color);
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
        padding: 10px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
        transition: background-color 0.3s ease;
      }

      button:hover {
        background-color: #2980b9;
      }

      .action-buttons {
        display: flex;
        gap: 10px;
      }

      /* Modal Styles */
      .modal {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        justify-content: center;
        align-items: center;
      }

      .modal-content {
        background: white;
        padding: 2rem;
        border-radius: var(--border-radius);
        width: 300px;
        text-align: center;
      }

      .close-btn {
        color: #aaa;
        float: right;
        font-size: 1.5rem;
        font-weight: bold;
        cursor: pointer;
      }

      .close-btn:hover {
        color: #000;
      }

      .modal input {
        margin-top: 1rem;
        width: 100%;
        padding: 0.5rem;
        border-radius: 4px;
      }

      .modal button {
        margin-top: 1rem;
        width: 100%;
        background: #3498db;
        color: white;
        padding: 0.5rem 1rem;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s ease;
      }

      .modal button:hover {
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
        <a href="libPro.jsp">Get Province</a>
        <a href="bkno.jsp">Books Per Room</a><a href="login.html" style="margin-right: 0;">Log Out</a>
      </nav>
    </header>
    <div class="container">
      <h2>Library Borrow Management</h2>
      
      <h3 class = "error">${borError}</h3>
    <h3 class = "message">${borMessage}</h3>

      <!-- Pending Borrow Requests Section -->
      <div class="section">
        <h3>Pending Borrow Requests</h3>
        <table>
          <thead>
            <tr>
              <th>User Name</th>
              <th>Book Title</th>
              <th>Pick Up Date</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody id="pendingRequestsTable">
            <!-- Rows will be dynamically added here -->
          </tbody>
        </table>
      </div>

      <!-- Currently Borrowed Books Section -->
      <div class="section">
        <h3>Currently Borrowed Books</h3>
        <table>
          <thead>
            <tr>
              <th>User Name</th>
              <th>Book Title</th>
              <th>Pick Up Date</th>
              <th>Due Date</th>
              <th>Late Fee</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody id="borrowedBooksTable">
            <!-- Rows will be dynamically added here -->
          </tbody>
        </table>
      </div>

      <!-- Approve Request Modal -->
      <div id="approveModal" class="modal">
        <div class="modal-content">
          <span class="close-btn" onclick="closeModal()">&times;</span>
          <h3>Approve Borrow Request</h3>
          <form action="processBorrow?action=approve" method="POST">
            <input type="hidden" name="bookTitle" id="approveBookTitle" />
            <input type="hidden" name="isbn" id="approveIsbn" />
            <input type="hidden" name="userName" id="approveUserName" />
            <input type="hidden" name="pickUpDate" id="approvepickUpDate"/>
            <label for="returnDate">Due Date:</label>
            <input type="date" id="returnDate" name="returnDate" required />
            <button type="submit">Approve</button>
          </form>
        </div>
      </div>
    </div>

    <script>
      // Sample data
      
      
      const pendingRequests = [
    <%for (Borrower bor : bors) {%>
        <%if (bor != null && bor.getDueDate() == null && bor.getPickUpDate() != null && bor.getReturnDate()==null) {%>
            {
                userName: "<%=bor.getReader().getFirstName()%> <%=bor.getReader().getLastName()%>",
                bookTitle: "<%=bor.getBook().getTitle()%>",
                isbn: "<%=bor.getBook().getISBNCode()%>",
                borrowDate: "<%=bor.getPickUpDate() != null ? formatter.format(bor.getPickUpDate()) : ""%>",
                name: "<%=bor.getReader().getUserName()%>",
            },
        <%}%>
    <%}%>
];

const borrowedBooks = [
    <%for (Borrower bor : bors) {%>
        <%if (bor != null && bor.getDueDate() != null && bor.getReturnDate() == null) {%>
            {
                userName: "<%=bor.getReader().getFirstName()%> <%=bor.getReader().getLastName()%>",
                bookTitle: "<%=bor.getBook().getTitle()%>",
                isbn: "<%=bor.getBook().getISBNCode()%>",
                borrowDate: "<%=bor.getPickUpDate() != null ? formatter.format(bor.getPickUpDate()) : ""%>",
                returnDate: "<%=bor.getDueDate() != null ? formatter.format(bor.getDueDate()) : ""%>",
                name: "<%=bor.getReader().getUserName()%>",
                late: "<%= bor.getLateChargeFees()%>"
            },
        <%}%>
    <%}%>
];

      const orrowedBooks = [
        {
          userName: "Charlie Brown",
          bookTitle: "Clean Code",
          borrowDate: "2024-09-25",
          returnDate: "2024-10-25",
        },
        {
          userName: "Dana White",
          bookTitle: "Design Patterns",
          borrowDate: "2024-10-01",
          returnDate: "2024-10-31",
        },
      ];

      // Populate Pending Requests Table
      const pendingTable = document.getElementById("pendingRequestsTable");
      pendingRequests.forEach((request) => {
        const row = document.createElement("tr");
        row.innerHTML = `
                <td><%="${request.userName}"%></td>
                <td><%="${request.bookTitle}"%></td>
                <td><%="${request.borrowDate}"%></td>
                <td class="action-buttons">
                    <button onclick="openApproveModal('<%="${request.bookTitle}"%>', '<%="${request.name}"%>','<%="${request.borrowDate}"%>', '<%="${request.isbn}"%>')">Approve</button>
                    <form action="processBorrow?action=reject" method="POST" style="display: inline;">
                        <input type="hidden" name="bookTitle" value="<%="${request.bookTitle}"%>">
                        <input type="hidden" name="pickUpDate" value="<%="${request.borrowDate}"%>">
                        <input type="hidden" name="isbn" value="<%="${request.isbn}"%>">
                        <input type="hidden" name="userName" value="<%="${request.name}"%>">
                        <button type="submit">Reject</button>
                    </form>
                </td>
            `;
        pendingTable.appendChild(row);
      });

      // Populate Borrowed Books Table
      const borrowedTable = document.getElementById("borrowedBooksTable");
      borrowedBooks.forEach((book) => {
        const row = document.createElement("tr");
        row.innerHTML = `
        	<td><%="${book.userName}"%></td>
            <td><%="${book.bookTitle}"%></td>
            <td><%="${book.borrowDate}"%></td>
            <td><%="${book.returnDate}"%></td>
            <td><%="${book.late}"%></td>
            <td>
                <form action="processBorrow?action=setAvailable" method="POST" style="display: inline;">
                    <input type="hidden" name="bookTitle" value="<%="${book.bookTitle}"%>">
                    <input type="hidden" name="returnDate" value="<%="${book.returnDate}"%>">
                    <input type="hidden" name="isbn" value="<%="${book.isbn}"%>">
                    <input type="hidden" name="userName" value="<%="${book.name}"%>">
                        <button type="submit">Set Book to Available</button>
                    </form>
                </td>
            `;
        borrowedTable.appendChild(row);
      });

      // Modal functions
      function openApproveModal(bookTitle, userName, pickup, isbn) {
        document.getElementById("approveBookTitle").value = bookTitle;
        document.getElementById("approveIsbn").value = isbn;
        document.getElementById("approveUserName").value = userName;
        document.getElementById("approvepickUpDate").value = pickup;
        document.getElementById("approveModal").style.display = "flex";
      }

      function closeModal() {
        document.getElementById("approveModal").style.display = "none";
      }

      // Close modal when clicking outside of the modal content
      window.onclick = function (event) {
        const modal = document.getElementById("approveModal");
        if (event.target == modal) {
          modal.style.display = "none";
        }
      };
    </script>
  </body>
</html>
