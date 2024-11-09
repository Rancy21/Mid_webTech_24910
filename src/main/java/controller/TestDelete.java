package controller;


import model.User;

public class TestDelete {
public static void main(String[] args) {
	UserDao us = new UserDao();
	User user = us.getUserByName("jon");
	if(us.deleteUser(user)) {
		System.out.println("successfull");
	}else {
		System.out.println("failed");
	}
}
}
