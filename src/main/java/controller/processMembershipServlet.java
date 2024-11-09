package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Membership;
import model.MembershipStatus;
import model.User;

@WebServlet(urlPatterns = "/processMembership")
public class processMembershipServlet extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// Retrieve parameters from the form submission
		HttpSession session = req.getSession();
		session.setAttribute("message", null);
		session.setAttribute("error", null);
		String userName = req.getParameter("userName");
		System.out.println("user Name: " + userName);
		String membershipType = req.getParameter("membershipType");
		String approvalAction = req.getParameter("approval");
		UserDao dao = new UserDao();
		User user = dao.getUserByName(userName.trim());
		RequestDispatcher rd;
		if (user != null) {
			Membership mem = user.findMembershipWithNullRegisterDate().orElse(null);
			MembershipDao mDao = new MembershipDao();
			if ("approve".equals(approvalAction)) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, 30);
				Date newDate = calendar.getTime();
				try {
					mem.setRegisterDate(formatter.parse(formatter.format(date)));
					mem.setExpiringDate(formatter.parse(formatter.format(newDate)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mem.setStatus(MembershipStatus.APPROVED);
				mem.setReader(user);
				if (mDao.updateMembership(mem)) {
					session.setAttribute("message",
							"Membership for " + user.getFirstName() + " " + user.getLastName() + " Approved");
					session.setAttribute("user", user);
					rd = req.getRequestDispatcher("libMem.jsp");
					rd.forward(req, res);
				} else {
					session.setAttribute("error",
							"Failed to approve Membership for " + user.getFirstName() + " " + user.getLastName());
					rd = req.getRequestDispatcher("libMem.jsp");
					rd.forward(req, res);
				}
			} else if ("reject".equals(approvalAction)) {
				mem.setStatus(MembershipStatus.REJECTED);
				mem.setReader(user);
				if (mDao.updateMembership(mem)) {
					session.setAttribute("message",
							"Membership for " + user.getFirstName() + " " + user.getLastName() + " Rejected");
					rd = req.getRequestDispatcher("libMem.jsp");
					rd.forward(req, res);
				} else {
					session.setAttribute("error",
							"Failed to reject Membership for " + user.getFirstName() + " " + user.getLastName());
					rd = req.getRequestDispatcher("libMem.jsp");
					rd.forward(req, res);
				}
			}
		} else {
			session.setAttribute("error", "Error Happened during fetching userName");
			rd = req.getRequestDispatcher("libMem.jsp");
			rd.forward(req, res);

		}
	}
}
