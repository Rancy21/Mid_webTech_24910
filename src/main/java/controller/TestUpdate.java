package controller;


import model.Location;
import model.User;

public class TestUpdate {
	public static void main(String[] args) {
		UserDao us = new UserDao();
		LocationDao d = new LocationDao();
		Location location = d.getLocationByCode("code2");
		User user = us.getUserByName("larr");
		user.setVillage(location);
		
		User uS = us.updateUser(user);
		if(uS != null) {
			System.out.println("successfull");
		}else {
			System.out.println("failed");
		}
        
	}
}
