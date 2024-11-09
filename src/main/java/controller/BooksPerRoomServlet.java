package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Room;
import model.Shelf;
@WebServlet(urlPatterns = "/booksPerRoom")
public class BooksPerRoomServlet extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		RequestDispatcher rd;
		HttpSession session = req.getSession();
		session.setAttribute("noMessage", null);
		session.setAttribute("noError", null);
		RoomDao dao = new RoomDao();
		String roomCode = req.getParameter("roomCode");
		Room room = dao.getRoomByCode(roomCode);
		if (room != null) {
			int books = 0;
			List<Shelf> shelves = room.getShelves();
			if (!shelves.isEmpty()) {
				for (Shelf shelf : shelves) {
					books += shelf.getBooks().size();
				}
				session.setAttribute("noMessage", "There are " + books + " books in " + roomCode + ".");
				rd = req.getRequestDispatcher("bkno.jsp");
				rd.forward(req, res);
			} else {
				session.setAttribute("noError", "There are no shelves in " + roomCode + ".");
				rd = req.getRequestDispatcher("bkno.jsp");
				rd.forward(req, res);
			}

		}else {
			session.setAttribute("noError", "Error Fetching the book name from the database");
			rd = req.getRequestDispatcher("bkno.jsp");
			rd.forward(req, res);
		}
	}

}
