package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Location;
import model.User;
@WebServlet(urlPatterns = "/pro")
public class ProvinceServlet extends HttpServlet{
		public void service(HttpServletRequest req,
				HttpServletResponse res ) throws IOException, ServletException {
			HttpSession session = req.getSession();
			session.setAttribute("ok", "");
			session.setAttribute("error", "");
			RequestDispatcher rd;
			UserDao dao = new UserDao();
			User user = dao.getUserByName("rancy");
			String phone = req.getParameter("phone");
			Location village = user.getVillage();
			Location cell = village.getParentLocation();
			Location sector = cell.getParentLocation();
			Location dis = sector.getParentLocation();
			Location pro = dis.getParentLocation();
			if(user.getPhoneNumber().equals(phone)) {
				session.setAttribute("ok", "Your Province is: "+pro.getLocationName());
				rd = req.getRequestDispatcher("pro.jsp");
				rd.forward(req, res);
				
			}else {
				session.setAttribute("error", "Wrong Phone Number");
				rd = req.getRequestDispatcher("pro.jsp");
				rd.forward(req, res);
			}
}
}
