package controller;

import model.Location;
import model.LocationType;

public class TestLoc {
	public static void main(String[] args) {
		LocationDao dao = new LocationDao();
		Location province = dao.getLocationByCode("1");
		String  districtName = "district";
		String  districtCode = "disCode";
		Location district = checkLocation(districtName, districtCode, province, "district");
		String  sectorName = "sector";
		String  sectorCode = "sectorCode";
		Location sector = checkLocation(sectorName, sectorCode, district, "sector");
		
		String  cellName = "cell";
		String  cellCode = "sectorCode";
		Location cell = checkLocation(cellName, cellCode, sector, "cell");
		
		String  villageName = "village";
		String  villageCode = "villageCode";
		Location village = checkLocation(villageName, villageCode, cell, "village");
		
		if(dao.createLocation(district)) {
			System.out.println("first ok");
			if(dao.createLocation(sector)) {
				System.out.println("second ok");
				if(dao.createLocation(cell)) {
					System.out.println("third ok");
					if(dao.createLocation(village)) {
						System.out.println("last ok");
					}
				}
			}
			
		}
		
		
		
	}
	
	public static Location getProvince(String provinceCode) {
		LocationDao dao = new LocationDao();
		try {
			Location province = dao.getLocationByCode(provinceCode);
			if(province != null) {
				return province;
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static Location checkLocation(String Name, String code, Location parent, String type) {
		LocationDao dao = new LocationDao();
		try {
			Location province = dao.getLocationByName(Name);
			if(province != null) {
				return province;
			}else{
				province = new Location();
				province.setLocationName(Name);
				province.setParentLocation(parent);
				province.setLocationCode(code);
				province.setLocationType(getType(type));
				return province;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static LocationType getType(String type) {
		if(type.equalsIgnoreCase("cell")) {
			return LocationType.CEll;
		}else if(type.equalsIgnoreCase("district")) {
			return LocationType.DISTRICT;
		}else if(type.equalsIgnoreCase("sector")) {
			return LocationType.SECTOR;
		}else if(type.equalsIgnoreCase("village")) {
			return LocationType.VILLAGE;
		}
		
		return null;
	}
}
