package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Gender;
import model.Location;
import model.LocationType;
import model.Role;
import model.User;
@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet{
	public void doPost(HttpServletRequest req,
			HttpServletResponse res) throws IOException, ServletException{
		HttpSession session = req.getSession();
		LocationDao loc = new LocationDao();
		UserDao dao = new UserDao();
		boolean locSaved = false;
		//Check if village already exists
		Location village = loc.getLocationByCode(req.getParameter("villageCode"));
		System.out.println("User Name: " + req.getParameter("userName"));
		System.out.println("First Name: " + req.getParameter("firstName"));
		System.out.println("Last Name: " + req.getParameter("lastName"));
		System.out.println("Gender: " + req.getParameter("gender"));
		System.out.println("Province Code: " + req.getParameter("provinceCode"));
		
		if(village == null) {
			// Initiate location
			String provinceCode = req.getParameter("provinceCode");
			Location province = getProvince(provinceCode);
			String  districtName = req.getParameter("district");
			String  districtCode = req.getParameter("districtCode");
			Location district = checkLocation(districtName, districtCode, province, "district");
			String  sectorName = req.getParameter("sector");
			String  sectorCode = req.getParameter("sectorCode");
			Location sector = checkLocation(sectorName, sectorCode, district, "sector");
			
			String  cellName = req.getParameter("cell");
			String  cellCode = req.getParameter("cellCode");
			Location cell = checkLocation(cellName, cellCode, sector, "cell");
			
			String  villageName = req.getParameter("village");
			String  villageCode = req.getParameter("villageCode");
			village = new Location(villageCode, villageName, LocationType.VILLAGE, cell);
			if(loc.createLocation(village)) {
				locSaved = true;
			}
			System.out.println(village);
		
		}else {
			locSaved = true;
		}
		
		
		//initiate User information
		String userName = req.getParameter("userName");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String phoneNumber = req.getParameter("phoneNumber");
		String gender = req.getParameter("gender");
		Gender gend = getGender(gender);
		String rl = req.getParameter("role");
		Role role = getRole(rl);
		String password = req.getParameter("password");
		String confirm = password;//req.getParameter("confirmPassword");
		User user = new User(firstName, lastName, gend, phoneNumber, hashString(password), role, userName, village);
		System.out.println(user);
		
		PrintWriter out = res.getWriter();
//		if(locSaved) {
//			if(confirm.equals(password)) {
//				if(dao.createUser(user)) {
//					session.setAttribute("answer", "User successfully saved!");
//					RequestDispatcher rd = req.getRequestDispatcher("regok.jsp");
//				    rd.forward(req, res);
//				}
//				else {
//					session.setAttribute("answer", "Failed to save user!");
//					RequestDispatcher rd = req.getRequestDispatcher("regok.jsp");
//				    rd.forward(req, res);
//				}
//			}else {
//				session.setAttribute("answer", "Password doesn't match");
//
//				RequestDispatcher rd = req.getRequestDispatcher("regno.jsp");
//			    rd.forward(req, res);
//			}
//			
//		}else {
//			session.setAttribute("answer", "Error Happened during saving location");
//			RequestDispatcher rd = req.getRequestDispatcher("regok.jsp");
//		    rd.forward(req, res);
//		}
		res.setContentType("application/json");
		
		if(locSaved) {
			if(confirm.equals(password)) {
				User exists = dao.getUserByName(userName);
				if(exists == null) {
					if(dao.createUser(user)) {
						session.setAttribute("answer", "User successfully saved!");
						out.print("{\"status\": \"success\", \"message\": \"User successfully saved!\"}");
					}
					else {
						session.setAttribute("answer", "Failed to save user!");
						out.print("{\"status\": \"error\", \"message\": \"Failed to save user!\"}");
					}
				}else {
					session.setAttribute("answer", "User with userName '"+userName+"' already exists");
					out.print("{\"status\": \"exists\", \"message\": \"Failed to save user!\"}");
				}
			}else {
				session.setAttribute("answer", "Password doesn't match");
				out.print("{\"status\": \"error\", \"message\": \"Failed to save user!\"}");
			}
			
		}else {
			session.setAttribute("answer", "Error Happened during saving location");
			out.print("{\"status\": \"error\", \"message\": \"Error Happened during saving location\"}");
		}
		
//		
//		
//		
//		
//		
	}
	
	
	public Location getProvince(String provinceCode) {
		LocationDao dao = new LocationDao();
		try {
			Location province = dao.getLocationByCode(provinceCode);
			if(province != null) {
				return province;
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public Location checkLocation(String Name, String code, Location parent, String type) {
		LocationDao dao = new LocationDao();
		try {
			Location province = dao.getLocationByCode(code);
			if(province != null) {
				return province;
			}else{
				province = new Location();
				province.setLocationName(Name);
				province.setParentLocation(parent);
				province.setLocationCode(code);
				province.setLocationType(getType(type));
				dao.createLocation(province);
				return province;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public LocationType getType(String type) {
		if(type.equalsIgnoreCase("cell")) {
			return LocationType.CEll;
		}else if(type.equalsIgnoreCase("district")) {
			return LocationType.DISTRICT;
		}else if(type.equalsIgnoreCase("sector")) {
			return LocationType.SECTOR;
		}else if(type.equalsIgnoreCase("village")) {
			return LocationType.VILLAGE;
		}
		
		return null;
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
	
	public Gender getGender(String type) {
		if(type.equalsIgnoreCase("male")) {
			return Gender.MALE;
		}else if(type.equalsIgnoreCase("female")) {
			return Gender.FEMALE;
		}
		
		return null;
	}
	
	public Role getRole(String type) {
		if(type.equalsIgnoreCase("student")) {
			return Role.STUDENT;
		}else if(type.equalsIgnoreCase("teacher")) {
			return Role.TEACHER;
		}else if(type.equalsIgnoreCase("hod")) {
			return Role.HOD;
		}else if(type.equalsIgnoreCase("dean")) {
			return Role.DEAN;
		}else if(type.equalsIgnoreCase("manager")) {
			return Role.MANAGER;
		}else if(type.equalsIgnoreCase("librarian")) {
			return Role.LIBRARIAN;
		}
		
		return null;
	}
}
