package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.*;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "shelf")
public class Shelf {
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	private UUID id;
	@Column(name = "available_stock")
	private int availableStock;
	@Column(name = "book_category")
	private String bookCategory;
	@Column(name = "borrowed_number")
	private int borrowedNumber;
	@Column(name = "initial_stock")
	private int initialStock;
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;
	@OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Book> books = new ArrayList<>();
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public int getAvailableStock() {
		return availableStock;
	}
	public void setAvailableStock(int availableStock) {
		this.availableStock = availableStock;
	}
	public String getBookCategory() {
		return bookCategory;
	}
	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}
	public int getBorrowedNumber() {
		return borrowedNumber;
	}
	public void setBorrowedNumber(int borrowedNumber) {
		this.borrowedNumber = borrowedNumber;
	}
	public int getInitialStock() {
		return initialStock;
	}
	public void setInitialStock(int initialStock) {
		this.initialStock = initialStock;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
}
