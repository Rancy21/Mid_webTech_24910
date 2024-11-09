package model;

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
@Table(name = "location")
public class Location {
	@Override
	public String toString() {
		return "Location [locationCode=" + locationCode + ", locationID=" + locationID + ", locationName="
				+ locationName + ", locationType=" + locationType + ", parentLocation=" + parentLocation + "]";
	}
	@Column(name = "location_code")
	private String locationCode;
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID locationID;
	@Column(name = "location_name")
	private String locationName;
	@Enumerated(EnumType.STRING)
	@Column(name = "location_type")
	private LocationType locationType;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Location parentLocation;
	
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public UUID getLocationID() {
		return locationID;
	}
	public void setLocationID(UUID locationID) {
		this.locationID = locationID;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public LocationType getLocationType() {
		return locationType;
	}
	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}
	public Location getParentLocation() {
		return parentLocation;
	}
	public void setParentLocation(Location parentLocation) {
		this.parentLocation = parentLocation;
	}
	public Location(String locationCode,String locationName, LocationType locationType,
			Location parentLocation) {
		super();
		this.locationCode = locationCode;
		this.locationName = locationName;
		this.locationType = locationType;
		this.parentLocation = parentLocation;
	}
	public Location() {
		super();
	}
}
