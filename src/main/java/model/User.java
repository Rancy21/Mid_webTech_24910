package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "users")
public class User extends Person{
	@Column(name = "password")
	private String password;
	@Column(name = "role")
	private Role role;
	@Column(name = "user_name")
	private String userName;
	@ManyToOne
	@JoinColumn(name = "village_id")
	private Location village;
	@OneToMany(mappedBy = "reader", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Membership> memberships = new ArrayList<>();
	@OneToMany(mappedBy = "reader", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
    private List<Borrower> borrowed = new ArrayList<>(); // Initialize the list
	public String getPassword() {
		return password;
	}
	public List<Borrower> getBorrowed() {
		return borrowed;
	}
	public void setBorrowed(List<Borrower> borrowed) {
		this.borrowed = borrowed;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Location getVillage() {
		return village;
	}
	public void setVillage(Location village) {
		this.village = village;
	}
	public List<Membership> getMemberships() {
		return memberships;
	}
	public void setMemberships(List<Membership> memberships) {
		this.memberships = memberships;
	}
	public User(String firstName, String lastName, Gender gender, String phoneNumber, String password,
			Role role, String userName, Location village) {
		super(firstName, lastName, gender, phoneNumber);
		this.password = password;
		this.role = role;
		this.userName = userName;
		this.village = village;
	}
	
	public User() {
		super();
	}
	
	
	@Override
	public String toString() {
	    return String.format("User[id=%s, userName=%s, role=%s]", 
	        getPersonId(), userName, role);
	}
	public Optional<Membership> getMostRecentMembership() {
		if(memberships.isEmpty()) {
			System.out.println("Memberships is Empty");
		}
        if (memberships == null || memberships.isEmpty()) {
            return Optional.empty();
        }

        Date today = new Date();
        Membership closestMembership = null;
        long minDifference = Long.MAX_VALUE;

        for (Membership membership : memberships) {
            if (membership.getRegisterDate() != null) {
                long difference = Math.abs(membership.getRegisterDate().getTime() - today.getTime());
                if (difference < minDifference) {
                    minDifference = difference;
                    closestMembership = membership;
                }
            }
        }

        return Optional.ofNullable(closestMembership);
    }
	
	public Optional<Membership> findMembershipWithNullRegisterDate() {
	    return memberships.stream()
	        .filter(membership -> membership.getRegisterDate() == null)
	        .findFirst(); // Returns an Optional containing the first match or empty if none found
	}
	
	public Optional<Membership> getMostRecentActiveMembership() {
        Date currentDate = new Date();
        
        return memberships.stream()
            .filter(membership -> 
                membership.getRegisterDate() != null &&
                membership.getStatus() == MembershipStatus.APPROVED &&
                (membership.getExpiringDate() == null || membership.getExpiringDate().after(currentDate)))
            .max(Comparator.comparing(Membership::getRegisterDate));
    }
	
	public List<Borrower> getBorrowedDuringMembership() {
		List<Borrower> bors = new ArrayList<Borrower>();
		Membership mem = getMostRecentActiveMembership().orElse(null);
		for(Borrower borrow : borrowed) {
			if(borrow.getPickUpDate().after(mem.getRegisterDate())) {
				bors.add(borrow);
			}
		}
		return bors;
	}
}
