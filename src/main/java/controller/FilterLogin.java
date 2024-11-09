package controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Borrower;
import model.Role;
import model.Room;
import model.User;
@WebFilter(urlPatterns = "/home")
public class FilterLogin implements Filter{
	 @Override
	    public void init(FilterConfig filterConfig) throws ServletException {
	        // Initialization code, if needed
	    }
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
		session.setAttribute("borMessage", null);
		session.setAttribute("borError", null);
        session.setAttribute("shelfMessage", null);
		session.setAttribute("shelfError", null);
		session.setAttribute("bookMessage", null);
		session.setAttribute("bookError", null);
		session.setAttribute("roomMessage", null);
		session.setAttribute("roomError", null);
		session.setAttribute("memError", null);
		session.setAttribute("memMessage", null);
		session.setAttribute("brwMessage", null);
		session.setAttribute("brwError", null);
		session.setAttribute("message", null);
		session.setAttribute("error", null);
        UserDao dao = new UserDao();
        RoomDao roomDao = new RoomDao();
        User user = dao.getUserByName(userName);
        String hashPassword = hashString(password);
//        System.out.println(user);
        RequestDispatcher rd;
        if(user != null) {
	        if(user.getPassword().equals(hashPassword)) {
	        	List<Room>rooms = roomDao.getAllRooms();
	        	if(rooms!= null) {
	        		session.setAttribute("rooms", rooms);
	        	}
	        	if(user.getRole().equals(Role.LIBRARIAN)) {
	        		session.setAttribute("libuser", user);
	        	}else {
	        		session.setAttribute("user", user);
	        	}
	        	req.setAttribute("user", user);
	        	
	        	chain.doFilter(req, res);
	        }else {
	        	session.setAttribute("error", "Wrong password");
	        	rd = req.getRequestDispatcher("log.jsp");
	        	rd.forward(req, res);
	        }
        }else {
        	session.setAttribute("error", "User doesn't exist");
        	rd = req.getRequestDispatcher("log.jsp");
        	rd.forward(req, res);
        }
		
	}
	
	public static String hashString(String input) {
        try {
            // Use SHA-256 hashing algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // This should never happen with "SHA-256"
            throw new RuntimeException(e);
        }
    }
}
