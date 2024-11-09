package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MembershipType;
@WebServlet(urlPatterns = "/saveType")
public class SaveType extends HttpServlet{
	public void service(HttpServletRequest req,
			HttpServletResponse res ) throws IOException, ServletException{
		MembershipTypeDao dao = new MembershipTypeDao();
		MembershipType type = new MembershipType();
		
		type.setName("Gold");
		type.setMaxBooks(5);
		type.setPrice(50);
		
		if(dao.createMembershipType(type)){
			System.out.println("Gold saved");
		}else {
			System.out.println("Gold not saved");
		}
		
		type = new MembershipType();
		
		type.setName("Silver");
		type.setMaxBooks(3);
		type.setPrice(30);
		
		if(dao.createMembershipType(type)){
			System.out.println("silver saved");
		}else {
			System.out.println("silver not saved");
		}
		
		type = new MembershipType();
		
		type.setName("Striver");
		type.setMaxBooks(2);
		type.setPrice(10);
		
		if(dao.createMembershipType(type)){
			System.out.println("striver saved");
		}else {
			System.out.println("striver not saved");
		}
		
		RequestDispatcher rd = req.getRequestDispatcher("save.jsp");
		req.setAttribute("ok", "Done");
		rd.forward(req, res);
		
	}
}
