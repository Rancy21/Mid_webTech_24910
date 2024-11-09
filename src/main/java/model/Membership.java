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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "membership")
public class Membership {
	@Override
	public String toString() {
		return "Membership [id=" + id + ", registerDate=" + registerDate + ", expiringDate=" + expiringDate + ", code="
				+ code + ", type=" + type + ", status=" + status + ", reader=" + reader + "]";
	}
	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	private UUID id;
	@Temporal(TemporalType.DATE)
	@Column(name = "register_date")
	private Date registerDate;
	@Temporal(TemporalType.DATE)
	@Column(name = "expiring_date")
	private Date expiringDate;
	@Column(name = "membership_code")
	private String code;
	@ManyToOne
	@JoinColumn(name = "membership_type")
	private MembershipType type;
	@Enumerated(EnumType.STRING)
	@Column(name = "membership_status")
	private MembershipStatus status;
	@ManyToOne
	@JoinColumn(name = "reader_id")
	private User reader;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Date getExpiringDate() {
		return expiringDate;
	}
	public void setExpiringDate(Date expiringDate) {
		this.expiringDate = expiringDate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public MembershipType getType() {
		return type;
	}
	public void setType(MembershipType type) {
		this.type = type;
	}
	public MembershipStatus getStatus() {
		return status;
	}
	public void setStatus(MembershipStatus status) {
		this.status = status;
	}
	public User getReader() {
		return reader;
	}
	public void setReader(User reader) {
		this.reader = reader;
	}
}
