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
import model.User;

@WebServlet("/processBorrow")
public class ProcessBorrowServlet extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		HttpSession session = req.getSession();
		session.setAttribute("borMessage", null);
		session.setAttribute("borError", null);
		RequestDispatcher rd;
		UserDao uDao = new UserDao();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		BorrowerDao dao = new BorrowerDao();
		BookDao bDao = new BookDao();
		if ("approve".equalsIgnoreCase(req.getParameter("action"))) {
			User user = uDao.getUserByName(req.getParameter("userName").trim());
			if (user != null) {
				String title = req.getParameter("bookTitle");
				String pick = req.getParameter("pickUpDate");
				String isbn = req.getParameter("isbn");
				List<Borrower> bors = user.getBorrowed();
				System.out.println(bors);
				Book bk = new Book();
				Borrower borrow = findBorrower(user, title, pick, isbn);

				if (borrow != null) {
					bk = borrow.getBook();
					bk.setStatus(BookStatus.BORROWED);
					borrow.setBook(bk);
					Date due = new Date();
					try {
						due = formatter.parse(req.getParameter("returnDate"));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					borrow.setDueDate(due);
					borrow.setReader(user);
					System.out.println(borrow.getReader());
					System.out.println(borrow.getBook());
					System.out.println(borrow);
					if (dao.updateBorrower(borrow) && bDao.updateBook(bk)) {
						session.setAttribute("borMessage",
								"Approved borrowing request for " + user.getFirstName() + " to borrow " + title + ".");
						rd = req.getRequestDispatcher("libBor.jsp");
						rd.forward(req, res);
					} else {
						session.setAttribute("borError", "Failed to approve borrowing request for "
								+ user.getFirstName() + " to borrow " + title + ".");
						rd = req.getRequestDispatcher("libBor.jsp");
						rd.forward(req, res);
					}
				} else {
					session.setAttribute("borError", "Error while fetching the borrow request.");
					rd = req.getRequestDispatcher("libBor.jsp");
					rd.forward(req, res);
				}

			} else {
				session.setAttribute("borError", "Error while fetching user information.");
				rd = req.getRequestDispatcher("libBor.jsp");
				rd.forward(req, res);
			}
		} else if ("reject".equalsIgnoreCase(req.getParameter("action"))) {
			User user = uDao.getUserByName(req.getParameter("userName").trim());
			if (user != null) {
				String title = req.getParameter("bookTitle");
				String pick = req.getParameter("pickUpDate");
				String isbn = req.getParameter("isbn");
				List<Borrower> bors = user.getBorrowed();
				System.out.println(bors);
				Book bk = new Book();
				Borrower borrow = findBorrower(user, title, pick, isbn);

				if (borrow != null) {
					bk = borrow.getBook();
					bk.setStatus(BookStatus.AVAILABLE);
					borrow.setBook(bk);
					String ret = "1999-01-01";
					try {
						borrow.setReturnDate(formatter.parse(ret));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(borrow.getReader());
					System.out.println(borrow.getBook());
					System.out.println(borrow);
					if (dao.updateBorrower(borrow) && bDao.updateBook(bk)) {
						session.setAttribute("borMessage",
								"Rejected borrowing request for " + user.getFirstName() + " to borrow " + title + ".");
						rd = req.getRequestDispatcher("libBor.jsp");
						rd.forward(req, res);
					} else {
						session.setAttribute("borError", "Failed to Reject borrowing request for " + user.getFirstName()
								+ " to borrow " + title + ".");
						rd = req.getRequestDispatcher("libBor.jsp");
						rd.forward(req, res);
					}
				} else {
					session.setAttribute("borError", "Error while fetching the borrow request.");
					rd = req.getRequestDispatcher("libBor.jsp");
					rd.forward(req, res);
				}

			} else {
				session.setAttribute("borError", "Error while fetching user information.");
				rd = req.getRequestDispatcher("libBor.jsp");
				rd.forward(req, res);
			}
		}else if ("setAvailable".equalsIgnoreCase(req.getParameter("action"))) {
			System.out.println("Processing set to available....");
			User user = uDao.getUserByName(req.getParameter("userName").trim());
			System.out.println(user);
			if (user != null) {
				String title = req.getParameter("bookTitle");
				String pick = req.getParameter("returnDate");
				String isbn = req.getParameter("isbn");
				List<Borrower> bors = user.getBorrowed();
				System.out.println(bors);
				Book bk = new Book();
				Borrower borrow = findBorrowerByReturnDate(user, title, pick, isbn);
				System.out.println(borrow);
				if (borrow != null) {
					bk = borrow.getBook();
					bk.setStatus(BookStatus.AVAILABLE);
					borrow.setBook(bk);
					Date today = new Date();
					try {
						borrow.setReturnDate(formatter.parse(formatter.format(today)));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(borrow.getReader());
					System.out.println(borrow.getBook());
					System.out.println(borrow);
					if (dao.updateBorrower(borrow) && bDao.updateBook(bk)) {
						session.setAttribute("borMessage",
								 "Book" + title + "has been set to available.");
						rd = req.getRequestDispatcher("libBor.jsp");
						rd.forward(req, res);
					} else {
						session.setAttribute("borError", "Failed to set Book" + title + "to available.");
						rd = req.getRequestDispatcher("libBor.jsp");
						rd.forward(req, res);
					}
				} else {
					session.setAttribute("borError", "Error while fetching the borrow request.");
					rd = req.getRequestDispatcher("libBor.jsp");
					rd.forward(req, res);
				}

			} else {
				session.setAttribute("borError", "Error while fetching user information.");
				rd = req.getRequestDispatcher("libBor.jsp");
				rd.forward(req, res);
			}
		}

	}

	private Borrower findBorrower(User user, String title, String pickUpDate, String isbn) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return user.getBorrowed().stream()
				.filter(bor -> bor.getBook().getTitle().equals(title)
						&& formatter.format(bor.getPickUpDate()).equals(pickUpDate)
						&& bor.getBook().getISBNCode().equals(isbn))
				.findFirst().orElse(null);
	}
	
	private Borrower findBorrowerByReturnDate(User user, String title, String returnDate, String isbn) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return user.getBorrowed().stream()
				.filter(bor -> bor.getBook().getTitle().equals(title)
						&& formatter.format(bor.getDueDate()).equals(returnDate)
						&& bor.getBook().getISBNCode().equals(isbn))
				.findFirst().orElse(null);
	}

}
