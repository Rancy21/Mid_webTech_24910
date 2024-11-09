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
import model.Borrower;
import model.Membership;
import model.MembershipType;
import model.User;

@WebServlet(urlPatterns = "/borrow")
public class BorrowServlet extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		HttpSession session = req.getSession();
		session.setAttribute("brwMessage", null);
		session.setAttribute("brwError", null);
		String title = req.getParameter("title");
		String due = req.getParameter("pickupDate");
		BookDao bDao = new BookDao();
		RoomDao roomDao = new RoomDao();
		RequestDispatcher rd;
		User user = (User) session.getAttribute("user");
		String name = user.getUserName();
		UserDao uDao = new UserDao();
		user = uDao.getUserByName(name);
		System.out.println(user);
		if (user != null) {
			Membership mem = user.getMostRecentActiveMembership().orElse(null);
			if (mem != null) {
				MembershipType type = mem.getType();
				int maxbooks = type.getMaxBooks();
				List<Borrower> borrowed = user.getBorrowedDuringMembership();
				if(maxbooks > borrowed.size()) {
					Borrower borrow = new Borrower();
					BorrowerDao dao = new BorrowerDao();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Book book = bDao.getBookByTitle(title);
					borrow.setBook(book);
					borrow.setReader(user);
					try {
						borrow.setPickUpDate(formatter.parse(due));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(borrow);
					if(dao.createBorrower(borrow)) {
						book.setStatus(BookStatus.RESERVED);
						if(bDao.updateBook(book)) {
							session.setAttribute("rooms", roomDao.getAllRooms());
							session.setAttribute("brwMessage", "Your borrow request has been registered ");
							rd = req.getRequestDispatcher("books.jsp");
							rd.forward(req, res);
						}else {
							session.setAttribute("brwError", "Failed to upadate Book Status");
							rd = req.getRequestDispatcher("books.jsp");
							rd.forward(req, res);
						}
						
					}else {
						session.setAttribute("brwError", "Failed to register your request");
						rd = req.getRequestDispatcher("books.jsp");
						rd.forward(req, res);
						
					}
				}else {
					session.setAttribute("brwError", "Cannot Borrow more than "+maxbooks+" books.");
					rd = req.getRequestDispatcher("books.jsp");
					rd.forward(req, res);
				}
			}else {
				session.setAttribute("memError", "Please apply for a membership");
				rd = req.getRequestDispatcher("mem.jsp");
				rd.forward(req, res);
			}

		}else {
			rd = req.getRequestDispatcher("login.html");
			rd.forward(req, res);
		}
	}

}
