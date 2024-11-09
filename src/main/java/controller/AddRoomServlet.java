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
@WebServlet(urlPatterns = "/addRoom")
public class AddRoomServlet extends HttpServlet{
	public void service(HttpServletRequest req,
			HttpServletResponse res ) throws IOException, ServletException {
		RoomDao dao = new RoomDao();
		HttpSession session = req.getSession();
		RequestDispatcher rd;
		session.setAttribute("shelfMessage", null);
		session.setAttribute("shelfError", null);
		session.setAttribute("bookMessage", null);
		session.setAttribute("bookError", null);
		session.setAttribute("roomMessage", null);
		session.setAttribute("roomError", null);
		String roomCode = req.getParameter("roomCode");
		Room room = new Room();
		room.setRoomCode(roomCode);
		if(dao.createRoom(room)) {
			List<Room> rooms = dao.getAllRooms();
			session.setAttribute("rooms", rooms);
			session.setAttribute("roomMessage", "Room Registered successfully");
			rd = req.getRequestDispatcher("libHome.jsp");
			rd.forward(req, res);
		}else {
			session.setAttribute("roomError", "Error during Room Registration");
			rd = req.getRequestDispatcher("libHome.jsp");
			rd.forward(req, res);
		}
	}

}
