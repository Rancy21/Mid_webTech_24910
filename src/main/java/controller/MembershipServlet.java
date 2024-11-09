package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Membership;
import model.MembershipStatus;
import model.MembershipType;
import model.User;

@WebServlet(urlPatterns = "/applyMembership")
public class MembershipServlet extends HttpServlet{
	public void service(HttpServletRequest req,
			HttpServletResponse res ) throws IOException, ServletException{
		HttpSession session = req.getSession();
		RequestDispatcher rd;
		session.setAttribute("memError", null);
		session.setAttribute("memMessage", null);
		MembershipTypeDao typeDao = new MembershipTypeDao();
		User user = (User)session.getAttribute("user");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(user != null) {
			Optional<Membership> lastMembership= user.getMostRecentMembership();
			Membership lastMem = lastMembership.orElse(null);
			MembershipDao dao = new MembershipDao();
			if(lastMem != null) {
				if(!hasValidMembership(user, lastMem)) {
					Membership mem = new Membership();
					MembershipType type = typeDao.getMembershipTypeByName(req.getParameter("membershipType"));
					
			        mem.setType(type);
			        mem.setStatus(MembershipStatus.PENDING);
			        System.out.println(user);
			        mem.setReader(user);
			        if(dao.createMembership(mem)) {
			        	session.setAttribute("memMessage", "You Apply successfully");
			        	rd = req.getRequestDispatcher("mem.jsp");
			        	rd.forward(req, res);
			        }
			        else {
			        	session.setAttribute("memError", "Error Applying for the membership");
			        	rd = req.getRequestDispatcher("mem.jsp");
			        	rd.forward(req, res);
			        }
				}else {
					session.setAttribute("memError", "You already have a valid membership");
		        	rd = req.getRequestDispatcher("mem.jsp");
		        	rd.forward(req, res);
				}
			}else {
				Membership mem = new Membership();
				MembershipType type = typeDao.getMembershipTypeByName(req.getParameter("membershipType"));
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        calendar.add(Calendar.DAY_OF_MONTH, 30); // Add 30 days

		        Date newDate = calendar.getTime();
		        
		        mem.setType(type);
		        mem.setStatus(MembershipStatus.PENDING);
		        mem.setReader(user);
		        if(dao.createMembership(mem)) {
		        	session.setAttribute("memMessage", "You Apply successfully");
		        	rd = req.getRequestDispatcher("mem.jsp");
		        	rd.forward(req, res);
		        }
		        else {
		        	session.setAttribute("memError", "Error Applying for the membership");
		        	rd = req.getRequestDispatcher("mem.jsp");
		        	rd.forward(req, res);
		        }
			}
			
		}
	}
	
	public static boolean checkDate(Date oldDate, Date currentDate) {
        LocalDate date1 = oldDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate date2 = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return oldDate.before(currentDate);
    }
	
	public boolean hasValidMembership(User user, Membership mem) {
		Date date = new Date();
		Date memDate = mem.getExpiringDate();
		return checkDate(memDate, date);
	}
	
		
}
