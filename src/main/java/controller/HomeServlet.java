package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Role;
import model.User;
@WebServlet(urlPatterns = "/home")
public class HomeServlet extends HttpServlet{
	public void service(HttpServletRequest req,
			HttpServletResponse res ) throws IOException, ServletException {
//		HttpSession session = req.getSession();
		User user = (User)req.getAttribute("user");
		if(user.getRole().equals(Role.LIBRARIAN)) {
			RequestDispatcher rd;
			rd = req.getRequestDispatcher("libHome.jsp");
			rd.forward(req, res);
		}else if (user.getRole().equals(Role.STUDENT) || user.getRole().equals(Role.TEACHER)){
			RequestDispatcher rd;
			rd = req.getRequestDispatcher("books.jsp");
			rd.forward(req, res);
		}else {
			RequestDispatcher rd;
			rd = req.getRequestDispatcher("readonly-book.jsp");
			rd.forward(req, res);
		}
		
	}
}
