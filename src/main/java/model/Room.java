package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "room")
public class Room {
	@Column(name = "room_code")
	private String roomCode;
	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	private UUID id;
	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER )
    private List<Shelf> shelves = new ArrayList<>();
	public String getRoomCode() {
		return roomCode;
	}
	public void setRoomCode(String roomCode) {	
		this.roomCode = roomCode;
	}
	public UUID getId() {
		return id;
	}
	public List<Shelf> getShelves() {
		return shelves;
	}
	public void setShelves(List<Shelf> shelves) {
		this.shelves = shelves;
	}
	public void setId(UUID id) {
		this.id = id;
	}
}
