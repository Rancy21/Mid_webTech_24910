package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import model.Borrower;

public class MyHelper {
	public String getBorrowingStatus(Borrower borrow) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date ="1999-01-01";
		if(borrow != null && borrow.getPickUpDate() != null && borrow.getDueDate() == null && borrow.getReturnDate() == null) {
			return "Pending";
		}else if (borrow != null && borrow.getPickUpDate() != null && borrow.getDueDate() != null && borrow.getReturnDate() == null) {
			return "Borrowed";
		}else if(borrow != null && borrow.getPickUpDate() != null && borrow.getDueDate() != null && borrow.getReturnDate() != null) {
			return "Returned";
		}else if(borrow != null && borrow.getPickUpDate() != null && borrow.getDueDate() == null && borrow.getReturnDate() != null
				&& borrow.getReturnDate().equals(formatter.parseObject(date))) {
			return "Request Rejected";
		}
		return "";
	}
	
	public long daysBetween(Date startDate, Date endDate) {
        // Convert both dates to LocalDate without calling toInstant()
        LocalDate startLocalDate = convertToLocalDate(startDate);
        LocalDate endLocalDate = convertToLocalDate(endDate);

        // Calculate the number of days between
        long days = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
        System.out.println("Days: "+ days);
        return Math.max(days*(-1), 0);  // Ensure non-negative result
    }

    private LocalDate convertToLocalDate(Date date) {
        // Convert java.util.Date or java.sql.Date to LocalDate
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        } else {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }
	
	public void UpdateBorrows() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		BorrowerDao dao = new BorrowerDao();
		List<Borrower> borrows = dao.getAllBorrowers();
		if(!borrows.isEmpty()) {
			Date today = new Date();
			for (Borrower borrower : borrows) {

				if (borrower.getDueDate() != null) {
					Date due = formatter.parse(formatter.format(borrower.getDueDate()));
					if (today.after(due)) {
						int days = Integer.parseInt(String.valueOf(daysBetween(today, due)));
						System.out.println("Fee of "+borrower.getReader().getUserName()+" for "+borrower.getBook().getTitle()+" is: "+days*50);
						System.out.println("Fee of "+borrower.getReader().getUserName()+" for "+borrower.getBook().getTitle()+" is: "+daysBetween(today, due)*50);
						borrower.setLateChargeFees(days * 50);
						dao = new BorrowerDao();
						dao.updateBorrower(borrower);
					}
				}
			}
		}
		
	}
}
