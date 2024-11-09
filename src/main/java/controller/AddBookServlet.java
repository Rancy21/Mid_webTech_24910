package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Book;
import model.BookStatus;
import model.Room;
import model.Shelf;
@WebServlet(urlPatterns = "/addBook")
public class AddBookServlet extends HttpServlet{
	public void service(HttpServletRequest req,
			HttpServletResponse res ) throws IOException, ServletException{
		ShelfDao dao = new ShelfDao();
		HttpSession session = req.getSession();
		RequestDispatcher rd;
		session.setAttribute("shelfMessage", null);
		session.setAttribute("shelfError", null);
		session.setAttribute("bookMessage", null);
		session.setAttribute("bookError", null);
		session.setAttribute("roomMessage", null);
		session.setAttribute("roomError", null);
		Shelf shelf = dao.getShelfByCode(req.getParameter("shelf"));
		BookDao bookDao = new BookDao();
		RoomDao roomDao = new RoomDao();
		Book book = new Book();
		Date year = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			year = formatter.parse(req.getParameter("publicationYear"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		book.setTitle(req.getParameter("title"));
		book.setEdition(Integer.parseInt(req.getParameter("edition")));
		book.setISBNCode(req.getParameter("ISBNCode"));
		book.setPublisherName(req.getParameter("publisherName"));
		book.setPublicationYear(year);
		book.setStatus(BookStatus.AVAILABLE);
		book.setShelf(shelf);
		if(bookDao.createBook(book)) {
			shelf.setAvailableStock(shelf.getAvailableStock() + 1);
			shelf.setInitialStock(shelf.getInitialStock() + 1);
			if(dao.updateShelf(shelf)){
				List<Room> rooms = roomDao.getAllRooms();
				session.setAttribute("rooms", rooms);
				session.setAttribute("bookMessage", "Book Registered successfully");
				rd = req.getRequestDispatcher("libHome.jsp");
				rd.forward(req, res);
			}else {
				session.setAttribute("bookError", "Book saved but ");
				rd = req.getRequestDispatcher("libHome.jsp");
				rd.forward(req, res);
			}
			
		}else {
			session.setAttribute("bookError", "Error during Book Registration");
			rd = req.getRequestDispatcher("libHome.jsp");
			rd.forward(req, res);
		}
	}

}
