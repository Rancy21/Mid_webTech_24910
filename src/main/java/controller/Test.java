package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import model.Book;
import model.BookStatus;
import model.Gender;
import model.Location;
import model.LocationType;
import model.Role;
import model.User;

public class Test {
    public static void main(String[] args) throws ParseException {
        // Uncomment and implement User if needed
    	
    	LocationDao dao = new LocationDao();
//        Location location = dao.getLocationByCode("code2");
//        User user = new User();
//        user.setFirstName("Jess");
//        user.setLastName("Mugisha");
//        user.setGender(Gender.MALE);
//        user.setPassword("123");
//        user.setRole(Role.STUDENT);
//        user.setPhoneNumber("07855");
//        user.setVillage(location);
//        user.setUserName("jon");
//
//        UserDao d = new UserDao();
//        
//        User cUser = d.createUser(user);
//        
//        if(cUser != null) {
//        	System.out.println("user created");
//        }else {
//        	System.out.println("failed to create location 1");
//        }
//        Location loc1 = new Location();
//        loc1.setLocationCode("1");
//        loc1.setLocationName("Kigali");
//        loc1.setLocationType(LocationType.PROVINCE);
//
// 
//        if (dao.createLocation(loc1)) {
//            System.out.println("location1 created");
//        } else {
//            System.out.println("failed to create location 1");
//        }
//        
//        Location loc2 = new Location();
//        loc2.setLocationCode("2");
//        loc2.setLocationName("North Province");
//        loc2.setLocationType(LocationType.PROVINCE);
//
// 
//        if (dao.createLocation(loc2)) {
//            System.out.println("North Province created");
//        } else {
//            System.out.println("failed to create location 1");
//        }
//        
//        Location loc3 = new Location();
//        loc3.setLocationCode("3");
//        loc3.setLocationName("South Province");
//        loc3.setLocationType(LocationType.PROVINCE);
//
// 
//        if (dao.createLocation(loc3)) {
//            System.out.println("South Province created");
//        } else {
//            System.out.println("failed to create location 1");
//        }
//        
//        Location loc4 = new Location();
//        loc4.setLocationCode("4");
//        loc4.setLocationName("East Province");
//        loc4.setLocationType(LocationType.PROVINCE);
//
// 
//        if (dao.createLocation(loc4)) {
//            System.out.println("East Province created");
//        } else {
//            System.out.println("failed to create location 1");
//        }
//        
//        Location loc5 = new Location();
//        loc5.setLocationCode("5");
//        loc5.setLocationName("West Province");
//        loc5.setLocationType(LocationType.PROVINCE);
//
// 
//        if (dao.createLocation(loc5)) {
//            System.out.println("West Province created");
//        } else {
//            System.out.println("failed to create location 1");
//        }
        // Example of creating a Book
        Book bk = new Book();
        bk.setEdition(1);
        bk.setISBNCode("code");

//         Current date and future date setup
        Date currentDate = new Date(); // Current date and time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Set desired format

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 30); // Add 30 days

        Date newDate = calendar.getTime();
        String formattedDate = formatter.format(currentDate);
        String formattedNewDate = formatter.format(newDate);

        bk.setPublicationYear(formatter.parse(formattedNewDate));
        bk.setPublisherName("larr");
        bk.setStatus(BookStatus.AVAILABLE);
        bk.setTitle("book");

        BookDao bookDao = new BookDao();
        System.out.println(bookDao.createBook(bk));

        System.out.println("Current Date: " + formattedDate);
        System.out.println("Date after 30 days: " + formattedNewDate);
    }
}
