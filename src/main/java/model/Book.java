package model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "book")
public class Book {
	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	private UUID id;
	@Enumerated(EnumType.STRING)
	@Column(name = "book_status")
	private BookStatus status;
	@Column(name = "edition")
	private int edition;
	@Column(name = "ISBNCode")
	private String ISBNCode;
	@Column(name = "publication_year")
	private Date publicationYear;
	@Column(name = "publisher_name")
	private String publisherName;
	@ManyToOne
	@JoinColumn(name = "shelf_id")
	private Shelf shelf;
	@Column(name = "title")
	private String title;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public BookStatus getStatus() {
		return status;
	}
	public void setStatus(BookStatus status) {
		this.status = status;
	}
	public int getEdition() {
		return edition;
	}
	public void setEdition(int edition) {
		this.edition = edition;
	}
	public String getISBNCode() {
		return ISBNCode;
	}
	public void setISBNCode(String iSBNCode) {
		ISBNCode = iSBNCode;
	}
	public Date getPublicationYear() {
		return publicationYear;
	}
	public void setPublicationYear(Date publicationYear) {
		this.publicationYear = publicationYear;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public Shelf getShelf() {
		return shelf;
	}
	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
	    return String.format("Book[id=%s, title=%s, ISBNCode=%s]",
	        id, title, ISBNCode);
	}
}
