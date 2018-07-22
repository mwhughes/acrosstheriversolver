package test;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import test.Location.Position;

public class GamePiece {
	enum Type
	{
	    MONKEY(11, false, null, "Monkey"), CABBAGE(3, true, null, "Cabbage"), GOAT(5, false, null, "Goat"), WOLF(7, false, null, "Wolf"), GUN(2, true, null, "Gun"),
	    HUNTER(13, false, GUN, "Hunter"), WASHINGMACHINE(17, false, HUNTER, "Washing Machine");
	    
	   private int id;
	    private boolean canGoInWashingMachine;

	    private Type requiredForMovement;
	    private String name;
	    private Type(int id, boolean canGoInWashingMachine, Type requiredForMovement, String name){
	    	this.setId(id);
	    	this.setCanGoInWashingMachine(canGoInWashingMachine);
	    	this.requiredForMovement = requiredForMovement;
	    
	    	this.name = name;
	    }
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public boolean isCanGoInWashingMachine() {
			return canGoInWashingMachine;
		}
		public void setCanGoInWashingMachine(boolean canGoInWashingMachine) {
			this.canGoInWashingMachine = canGoInWashingMachine;
		}

		public Type getRequiredForMovement() {
			return requiredForMovement;
		}
		public void setRequiredForMovement(Type requiredForMovement) {
			this.requiredForMovement = requiredForMovement;
		}
		
		public String getName(){
			return name;
		}
	    
	}
	
	
	
	public GamePiece(Type type, Location location){
		this.location = new Location(location.getPosition());
		this.type = type;
	}

	
	private BoardState currentBoardState;
	private BoardState parrentBoardState;
	private Location location;
	private Type type;
	public Location getLocation() {
		return location;
	}
	
	public Type getType(){
		return this.type;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public BoardState getCurrentBoardState() {
		return currentBoardState;
	}
	public void setCurrentBoardState(BoardState currentBoardState) {
		this.currentBoardState = currentBoardState;
	}
	public BoardState getParrentBoardState() {
		return parrentBoardState;
	}
	public void setParrentBoardState(BoardState parrentBoardState) {
		this.parrentBoardState = parrentBoardState;
	}
	
	public Set<Location> getAdmissibleLocations(){
		System.out.println("wtf");
		Set<Position> result = new HashSet<>();
		for (Position position : Location.Position.values()){
			result.add(position);
		}
		
		if (this.type != Type.GUN && this.type != Type.CABBAGE){
			result.remove(Location.Position.WASHINGMACHINE);
		}
		
		Set<Location> result2 = new HashSet<Location>();
		for (Position position : result){
			result2.add(new Location(position));
		}
		return result2;
	}
	
	@Override
	public boolean equals(Object obj){
		 if (obj == null){ 
			 return false;
		 }
		 if (!(obj instanceof GamePiece)){
		       return false;
		 }
		    if (obj == this){
		        return true;
		    }
		    
		 GamePiece comp = (GamePiece) obj;
		 
		 if (!comp.getLocation().equals(this.getLocation())){
			 return false;
		 }
		 
		 if (comp.type != this.type){
			 return false;
		 }
		 
		    return true;
	}
	
	
	private boolean canGoInWashingMachineNow(){
		if (!this.getType().canGoInWashingMachine){
			return false;
		}
		
		if (!currentBoardState.washingMachineEmpty()){
			return false;
		}
		
		if (this.getLocation().getPosition() == Position.BOAT){
			return true;
		}
		
		if (this.getLocation().equals(currentBoardState.getWashingMachine().getLocation())){
			return true;
		}
		
		return false;
	}
	
	public Location getCurrentBaseLocationOfGun(){
		if (currentBoardState.getGun().getLocation().getPosition() == Position.WASHINGMACHINE){
			return currentBoardState.getWashingMachine().getLocation();
		}
		
		return currentBoardState.getGun().getLocation();
	}
	
	public Set<Location> getAccessiblePositions(){
	//	System.out.println(currentBoardState.getPlayerLocation().getPosition().getName());
		Location washingMachineLocation = currentBoardState.getWashingMachine().getLocation();
		if (!currentBoardState.getPlayerLocation().equals(location) && location.getPosition() != Position.WASHINGMACHINE ){
			return new HashSet<>();
		} else if (this.getLocation().getPosition() == Position.WASHINGMACHINE && !currentBoardState.getPlayerLocation().equals(washingMachineLocation) ) {
			return new HashSet<>();
		}
		Type requirement = this.type.requiredForMovement;
		boolean canMove = true;
		
		if (requirement != null){
			if (requirement == Type.GUN){
				canMove = getCurrentBaseLocationOfGun().equals(this.getLocation())||this.getLocation().getPosition() == Position.BOAT;
				
			} else {
				canMove = currentBoardState.getHunter().getLocation().equals(this.getLocation()) ||this.getLocation().getPosition() == Position.BOAT;
			}
		}
		
		if (getType() == Type.WASHINGMACHINE){
			canMove = canMove && currentBoardState.washingMachineEmpty();
		}
		
		if (!canMove){
			return new HashSet<>();
		}
		
		Set<Location> locations = new HashSet<>();
		// Washing Machine?
		
		if (canGoInWashingMachineNow()){
			locations.add(new Location(Position.WASHINGMACHINE));
		}

		if (currentBoardState.isBoatEmpty()){
			locations.add(new Location(Position.BOAT));
		}
		
		
		for (Position p : this.getLocation().getNeighboringPositions()){
			locations.add(new Location(p));
		}
		
		if (this.location.getPosition() == Position.WASHINGMACHINE){
			locations.add(washingMachineLocation);
		}
		
		return locations;
	}




}
