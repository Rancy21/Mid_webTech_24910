<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="model.Borrower"%>
<%@page import="model.BookStatus"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Room" %>
<%@ page import="model.Shelf" %>
<%@ page import="model.Book" %>
<%@ page import="controller.MyHelper"%>


<%List<Borrower> bors = (List<Borrower>)session.getAttribute("borrows");
MyHelper helper = new MyHelper();
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Library Home</title>
    <style>
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
      
      .message{
      color:green;
      text-align: center;
      margin-top: 10px;
      }
      
      .error{
      margin-top: 10px;
      color:red;
      text-align: center;
      }
      

      .background-overlay {
        background: rgba(255, 255, 255, 0.95);
        min-height: calc(100vh - 80px);
        padding: 2rem;
      }

      .search-bar {
        background: white;
        padding: 1.5rem;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
        margin-bottom: 2rem;
        display: flex;
        gap: 1rem;
        align-items: center;
        flex-wrap: wrap;
      }

      .search-bar select {
        padding: 0.5rem 1rem;
        border: 1px solid #e2e8f0;
        border-radius: 4px;
        background: white;
        min-width: 150px;
      }

      .search-bar button {
        background: #2b6cb0;
        color: white;
        padding: 0.5rem 2rem;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s ease;
      }

      .search-bar button:hover {
        background: #2c5282;
      }

      .book-container {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
        gap: 2rem;
        padding: 1rem;
      }

      .book-card {
        background: white;
        border-radius: 8px;
        padding: 1.5rem;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        display: flex;
        flex-direction: column;
      }

      .book-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 12px rgba(0, 0, 0, 0.1);
      }

      .book-card h3 {
        color: #2d3748;
        font-size: 1.25rem;
        margin-bottom: 1rem;
      }

      .book-card p {
        color: #4a5568;
        margin-bottom: 0.5rem;
        font-size: 0.95rem;
      }

      .book-card button {
        margin-top: auto;
        background: #c89855;
        color: white;
        padding: 0.5rem 1rem;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s ease;
      }

      .book-card button:hover {
        background: #38a169;
      }

      @media (max-width: 768px) {
        .navbar {
          padding: 1rem;
        }

        .search-bar {
          flex-direction: column;
          align-items: stretch;
        }

        .book-container {
          grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
          gap: 1rem;
        }
      }
      
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
        border-radius: 8px;
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
        background: #c89855;
        color: white;
        padding: 0.5rem 1rem;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s ease;
      }
      
      h2 {
        color: #2c3e50;
        margin-bottom: 30px;
        font-size: 2em;
        text-align: center;
      }

      .modal button:hover {
        background: #38a169;
      }
    </style>
</head>
<body>
    <header class="navbar">
        <h1>Library</h1>
        <nav class="nav-links">
            <a href="books.jsp">Home</a>
        <a href="borrowing">Borrowing</a>
        <a href="mem.jsp">My Membership</a>
        <a href="pro.jsp">Get Province</a><a href="login.html" style="margin-right: 0;">Log Out</a>
		</nav>
    </header>
    
    <div class="background-container">
    <div class ="background-overlay">
    <div class = "content">
    <h2>User Borrowing</h2>
    <h3 class="error">${brrError}</h3>
        
        <!-- Book Cards -->
        <section class="book-container" id="bookContainer">        
        </section>

			</div>
    </div> 
    </div>

    <script>
        // Book data for JavaScript
        
        
        const bkData = [
        	<%for (Borrower bor : bors) {
	if (bor != null && bor.getBook() != null) {%>
        			{
        				title:"<%=bor.getBook().getTitle()%>",
        				<%if (bor.getPickUpDate() == null) {%>
        				pickup: "",
        				<%} else {%>
        				pickup:"<%=formatter.format(bor.getPickUpDate())%>",
        				<%}%>
        				
        				<%if (bor.getDueDate() == null) {%>
        				due: "",
        				<%} else {%>
        				due: "<%=bor.getDueDate()%>",
        				<%}%>
        				
        				<%if (bor.getReturnDate() == null || bor.getReturnDate().equals(formatter.parseObject("1999-01-01")) ) {%>
        				ret: "",
        				<%} else {%>
        				ret: "<%=bor.getReturnDate()%>",
        				<%}%>
        				status: "<%=helper.getBorrowingStatus(bor)%>",
        				late: "<%=bor.getLateChargeFees()%>",
        			}<%=(bors.indexOf(bor) == bors.size() - 1) ? "" : ","%>
        	<%}
}%>
        ];
        
        function display(){
            const bookContainer = document.getElementById("bookContainer");
            bkData.forEach((book) => {
            const bookCard = document.createElement("div");
            bookCard.classList.add("book-card");
            	bookCard.innerHTML = `
                    <h3><%="${book.title}"%></h3>
                    <p>Pick UP Date: <strong><%="${book.pickup}"%></strong></p>
                    <p>Due Date: <strong><%="${book.due}"%></strong></p>
                    <p>Return Date: <strong><%="${book.ret}"%></strong></p>
                    <p>Borrowing Status: <strong><%="${book.status}"%></strong></p>
                    <p>Late Fee: <strong><%="${book.late}"%></strong></p>
                `;
            	bookContainer.appendChild(bookCard);
            });
            
        }
        
        
        // Call display on page load
        document.addEventListener("DOMContentLoaded", display);
    </script>
</body>
</html>
