package controller;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Borrower;
import model.User;

@WebServlet(urlPatterns = "/borrowing")
public class BeforeBorrow extends HttpServlet{
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher rd;
		UserDao dao = new UserDao();
		HttpSession session = req.getSession();
		session.setAttribute("brrError", null);
		User user = (User)session.getAttribute("user");
		System.out.println(user);
		if(user != null) {
			User nuser = dao.getUserByName(user.getUserName());
			MyHelper helper = new MyHelper();
			try {
				helper.UpdateBorrows();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Borrower> bors = nuser.getBorrowed();
			if(!bors.isEmpty()) {
				session.setAttribute("borrows", bors);
				rd = req.getRequestDispatcher("bor.jsp");
				rd.forward(req, res);
			}else {
				
				session.setAttribute("borrows", bors);
				session.setAttribute("brrError", "No Borrowing made");
				rd = req.getRequestDispatcher("bor.jsp");
				rd.forward(req, res);
			}
			
		}else{
			rd = req.getRequestDispatcher("login.html");
			rd.forward(req, res);
		}
	}
}
