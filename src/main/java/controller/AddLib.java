package controller;

import model.Gender;
import model.Location;
import model.Role;
import model.User;

public class AddLib {
	public static void main(String[] args) {
//		 User user = new User();
//		 LocationDao dao = new LocationDao();
//		Location location = dao.getLocationByCode("11123");
//       user.setFirstName("the librarian");
//       user.setLastName("librarian");
//       user.setGender(Gender.MALE);
//       user.setPassword("123");
//       user.setRole(Role.LIBRARIAN);
//       user.setPhoneNumber("07855");
//       user.setUserName("librarian");
//       user.setVillage(location);

       UserDao d = new UserDao();
       User u = d.getUserByName("rancy");
       System.out.println(u.getLastName());
       System.out.println(u.getPhoneNumber());
//       if(d.createUser(user)) {
//    	   System.out.println("ok");
//       }else {
//    	   System.out.println("no");
//       }
	}
}
