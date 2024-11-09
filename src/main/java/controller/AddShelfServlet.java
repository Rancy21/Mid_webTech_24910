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
@WebServlet(urlPatterns = "/addShelf")
public class AddShelfServlet extends HttpServlet{
	public void service(HttpServletRequest req,
			HttpServletResponse res ) throws IOException, ServletException {
		ShelfDao dao = new ShelfDao();
		HttpSession session = req.getSession();
		RequestDispatcher rd;
		session.setAttribute("shelfMessage", null);
		session.setAttribute("shelfError", null);
		session.setAttribute("bookMessage", null);
		session.setAttribute("bookError", null);
		session.setAttribute("roomMessage", null);
		session.setAttribute("roomError", null);
		String bookCategory = req.getParameter("bookCategory");
		Shelf shelf = new Shelf();
		RoomDao roomDao = new RoomDao();
		Room room = roomDao.getRoomByCode(req.getParameter("room"));
		shelf.setRoom(room);
		shelf.setBookCategory(bookCategory);
		if(dao.createShelf(shelf)) {
			List<Room> rooms = roomDao.getAllRooms();
			session.setAttribute("rooms", rooms);
			session.setAttribute("shelfMessage", "Shelf Registered successfully");
			rd = req.getRequestDispatcher("libHome.jsp");
			rd.forward(req, res);
		}else {
			session.setAttribute("shelfError", "Error during Shelf Registration");
			rd = req.getRequestDispatcher("libHome.jsp");
			rd.forward(req, res);
		}
	}

}
