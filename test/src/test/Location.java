package test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Location {
	 
	enum Position
	{
	   LEFT(0, true, "left"), RIGHT(1, true, "right"), BOAT(2, true, "boat"), WASHINGMACHINE(3, false, "washing machine");
	   
	   private int id;
	   private boolean accessibleByAll;
	   private String name;
	   
	   
	   private Position(int id, boolean accessible, String name){
		   this.setId(id);
		   this.setAccessibleByAll(accessible);
		   this.setName(name);
	   }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public static Collection<Location> getLocations(){
		Collection<Location> set = new HashSet<>();
		for (Position p : Position.values()){
			set.add(new Location(p));
		}
		return set;
	}

	public boolean isAccessibleByAll() {
		return accessibleByAll;
	}

	public void setAccessibleByAll(boolean accessibleByAll) {
		this.accessibleByAll = accessibleByAll;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	}
	
	private boolean isLand;
	private Position position;
	
	public Location(Position position){
		this.position = position;
		if (position == Position.LEFT || position == Position.RIGHT){
			setLand(true);
		} else {
			setLand(false);
		}
	}
	
	public Position getPosition(){
		return this.position;
	}

	public boolean isLand() {
		return isLand;
	}

	public void setLand(boolean isLand) {
		this.isLand = isLand;
	}
	
	@Override
	public boolean equals(Object obj){
		 if (obj == null){ 
			 return false;
		 }
		 if (!(obj instanceof Location)){
		       return false;
		 }
		    if (obj == this){
		        return true;
		    }
		    return this.getPosition().getId() == ((Location) obj).getPosition().getId();
	}
	
	public static Location getOppositeLocation(Location location){
		
		if (location.getPosition() != Position.LEFT && location.getPosition() != Position.RIGHT){
			return location;
		}
		
		if (location.getPosition() == Position.LEFT){
			return new Location(Position.RIGHT);
		}
		
		return new Location(Position.LEFT);
	}
	

	@Override
	public int hashCode() {
	    return this.getPosition().getId();
	}
	
	public Set<Position> getNeighboringPositions(){
		Set<Position> result = new HashSet<>();
		if (this.getPosition() == Position.LEFT || this.getPosition() == Position.RIGHT){
			result.add(Position.BOAT);
		}
		
		if (this.getPosition() == Position.BOAT){
			result.add(Position.RIGHT);
			result.add(Position.LEFT);
		}
		return result;
	}
	

}
