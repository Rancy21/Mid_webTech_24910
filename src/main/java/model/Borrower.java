package model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "borrower")
public class Borrower {
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	@Temporal(TemporalType.DATE)
	@Column(name = "due_date")
	private Date dueDate;
	@Column(name = "fine")
	private int fine;
	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	private UUID id;
	@Column(name = "late_charge_fees")
	private int lateChargeFees;
	@Column(name = "pickup_date")
	private Date pickUpDate;
	@ManyToOne
	@JoinColumn(name = "reader_id")
	private User reader;
	@Temporal(TemporalType.DATE)
	@Column(name = "return_date")
	private Date returnDate;
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public int getLateChargeFees() {
		return lateChargeFees;
	}
	public void setLateChargeFees(int lateChargeFees) {
		this.lateChargeFees = lateChargeFees;
	}
	public Date getPickUpDate() {
		return pickUpDate;
	}
	public void setPickUpDate(Date pickUpDate) {
		this.pickUpDate = pickUpDate;
	}
	public User getReader() {
		return reader;
	}
	public void setReader(User reader) {
		this.reader = reader;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public int getFine() {
		return fine;
	}
	public void setFine(int fine) {
		this.fine = fine;
	}
	
	@Override
	public String toString() {
	    return String.format("Borrower[id=%s, pickUpDate=%s, dueDate=%s]",
	        id,
	        pickUpDate != null ? pickUpDate.toString() : "null",
	        dueDate != null ? dueDate.toString() : "null");
	}
	
}
