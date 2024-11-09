<%@page import="model.BookStatus"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Room" %>
<%@ page import="model.Shelf" %>
<%@ page import="model.Book" %>
<%@ page import="org.hibernate.Session" %>

<%List<Room> rooms = (List<Room>)session.getAttribute("rooms");
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

      .modal button:hover {
        background: #38a169;
      }
    </style>
</head>
<body>
    <header class="navbar">
        <h1>Library</h1>
        <nav class="nav-links">
            <a href="readonly-book.jsp">Home</a>
        <a href="readonly-bor.jsp">Borrows</a>
        <a href="readonly-pro.jsp">Get Province</a>
        <a href="readonly-bkno.jsp">Book Per Room</a>
        <a href="login.html" style="margin-right: 0;">Log Out</a>
        
		</nav>
    </header>
    
    <div class="background-container">
    <div class ="background-overlay">
    <div class = "content">
     <!-- Search Bar -->
        <section class="search-bar">
            <label for="room">Room:</label>
            <select id="room" name="room" onchange="displayShelfOptions()">
                <option value="">Select Room</option>
                <% for (Room room : rooms) { %>
                    <option value="<%= room.getRoomCode()%>"><%= room.getRoomCode() %></option>
                <% } %>
            </select>

            <label for="shelf" style="display: none" id="shelf-label">Shelf:</label>
            <select id="shelf" name="shelf" style="display: none"></select>

            <button id="search" onclick="displayBooks()" style="display: none">Search</button>
        </section>
        
        <!-- Book Cards -->
        <section class="book-container" id="bookContainer">        
        </section>

				<div id="borrowModal" class="modal">
					<div class="modal-content">
						<span class="close-btn" onclick="closeModal()">&times;</span>
						<h3>Borrow Book</h3>
						<form action="borrow" method="POST">
							<input type="hidden" name="title" id="modalTitle" /> <input
								type="hidden" name="author" id="modalAuthor" /> <input
								type="hidden" name="room" id="modalRoom" /> <input
								type="hidden" name="shelf" id="modalShelf" /> <label
								for="pickupDate">Pick-up Date:</label> <input type="date"
								id="pickupDate" name="pickupDate" required />
							<button type="submit">Borrow</button>
						</form>
					</div>
				</div>

			</div>
    </div> 
    </div>

    <script>
        // Room and Shelf data for JavaScript
        const roomData = {
            <% for (Room room : rooms) { %>
                "<%= room.getRoomCode()%>": {
                    code: "<%= room.getRoomCode() %>",
                    shelves: {
                        <% for (Shelf shelf : room.getShelves()) { %>
                            "<%= shelf.getId() %>": {
                                category: "<%= shelf.getBookCategory() %>",
                                books: [
                                    <% for (Book book : shelf.getBooks()) { %>
                                        {
                                            title: "<%= book.getTitle()%>",
                                            author: "<%= book.getPublisherName() %>",
                                            edition: <%= book.getEdition() %>,
                                            isbn: "<%= book.getISBNCode() %>"
                                     
                                        }<%= (shelf.getBooks().indexOf(book) == shelf.getBooks().size() - 1) ? "" : "," %>
                                     
                                    <% } %>
                                ]
                            }<%= (room.getShelves().indexOf(shelf) == room.getShelves().size() - 1) ? "" : "," %>
                        <% } %>
                    }
                }<%= (rooms.indexOf(room) == rooms.size() - 1) ? "" : "," %>
            <% } %>
        };

        function displayShelfOptions() {
            const roomId = document.getElementById("room").value;
            const shelfSelect = document.getElementById("shelf");
            const shelfLabel = document.getElementById("shelf-label");
            const searchButton = document.getElementById("search");

            if (roomId && roomData[roomId]) {
                shelfSelect.innerHTML = `<option value="">Select Shelf</option>`;
                for (const [shelfId, shelf] of Object.entries(roomData[roomId].shelves)) {
                    const option = document.createElement("option");
                    option.value = shelfId;
                    option.textContent = shelf.category;
                    shelfSelect.appendChild(option);
                }
                shelfLabel.style.display = "inline-block";
                shelfSelect.style.display = "inline-block";
                searchButton.style.display = "inline-block";
            } else {
                shelfSelect.style.display = "none";
                shelfLabel.style.display = "none";
                searchButton.style.display = "none";
            }
        }

        function displayBooks() {
            const roomId = document.getElementById("room").value;
            const shelfId = document.getElementById("shelf").value;
            const bookContainer = document.getElementById("bookContainer");

            if (roomId && shelfId && roomData[roomId].shelves[shelfId]) {
                bookContainer.innerHTML = "";
                roomData[roomId].shelves[shelfId].books.forEach(book => {
                    const bookCard = document.createElement("div");
                    bookCard.classList.add("book-card");

                    bookCard.innerHTML = `
                        <h3><%out.println("${book.title}");%></h3>
                        <p>Publisher: <%out.println("${book.author}");%></p>
                        <p>Edition: <%out.println("${book.edition}");%></p>
                        <p>Room: <%out.println("${roomData[roomId].code}");%>, Shelf: <%out.println("${roomData[roomId].shelves[shelfId].category}");%></p>
                        
                    `;
                    
                    bookContainer.appendChild(bookCard);
                });
            }
        }
        
        function borrowBook(bookTitle, author, room, shelf) {
            console.log("Borrow Book clicked");
            // Set modal form values based on the selected book
            document.getElementById("modalTitle").value = bookTitle;
            document.getElementById("modalAuthor").value = author;
            document.getElementById("modalRoom").value = room;
            document.getElementById("modalShelf").value = shelf;

            // Display the modal
            document.getElementById("borrowModal").style.display = "flex";
        }


          function closeModal() {
            document.getElementById("borrowModal").style.display = "none";
          }

          // Close modal when clicking outside of the modal content
          window.onclick = function (event) {
            const modal = document.getElementById("borrowModal");
            if (event.target == modal) {
              modal.style.display = "none";
            }
          };
        
     // Display books from the first room on page load
        function loadFirstRoomBooks() {
            const roomSelect = document.getElementById("room");
            const shelfSelect = document.getElementById("shelf");

            // Select the first room if available
            const firstRoomId = roomSelect.options[1]?.value; // Assuming the first option is the default placeholder
            if (firstRoomId) {
                roomSelect.value = firstRoomId;
                displayShelfOptions(); // Populate shelves

                // Select the first shelf in the selected room if available
                const firstShelfId = shelfSelect.options[1]?.value; // Assuming the first option is the default placeholder
                if (firstShelfId) {
                    shelfSelect.value = firstShelfId;
                    displayBooks(); // Display books from the first shelf
                }
            }
        }
        // Call loadFirstRoomBooks on page load
        document.addEventListener("DOMContentLoaded", loadFirstRoomBooks);
    </script>
</body>
</html>
