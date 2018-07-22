package test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import test.GamePiece.Type;
import test.Location.Position;

public class BoardState {

	private List<GamePiece> allGamePieces = new ArrayList<>();
	
	private Location playerLocation;
	
	private GamePiece goat, monkey, cabbage, wolf, washingMachine, gun, hunter;
	
	private String lastMove;
	
	private BoardState parentBoardState;
	
	public List<GamePiece> getAllGamePieces() {
		return allGamePieces;
	}
	
	public boolean isBoatEmpty(){
		for (GamePiece gp : allGamePieces){
			if (gp.getLocation().getPosition() == Position.BOAT){
				return false;
			}
		}
		return true;
	}
	
	public void addToGamePieces(GamePiece gp){
		allGamePieces.add(gp);
		gp.setCurrentBoardState(this);
	}

	public void setAllGamePieces(List<GamePiece> allGamePieces) {
		this.allGamePieces = allGamePieces;
	}
	
	public BoardState getParentBoard(){
		return parentBoardState;
	}
	
	public void setParentBoard(BoardState ps){
		this.parentBoardState = ps;
	}
	
	public boolean isBoardStateAcceptable(){

		if (arePiecesTogetherOnLand(goat, cabbage) && isPlayerOnOppositeLand(goat.getLocation())){
			return false;
		}
			
		if (arePiecesTogetherOnLand(goat, gun) && isPlayerOnOppositeLand(goat.getLocation())){
			if (!arePiecesTogetherOnLand(gun, hunter)){
				return false;
			}
		}
			
		if (arePiecesTogetherOnLand(wolf, goat) && isPlayerOnOppositeLand(goat.getLocation())){
			return false;
		}
			
		if (arePiecesTogetherOnLand(wolf, monkey) && isPlayerOnOppositeLand(monkey.getLocation())){
			if (!arePiecesTogetherOnLand(monkey, washingMachine)){
				return false;
			}
		}
		
		if (arePiecesTogetherOnLand(gun, wolf) && arePiecesTogetherOnLand(gun, hunter) && isPlayerOnOppositeLand(wolf.getLocation())){
			return false;
		}
		
		if (arePiecesTogetherOnLand(cabbage, monkey)&& Location.getOppositeLocation(monkey.getLocation()).equals((wolf.getLocation()))){
			return false;
		}
		
		Location washingMachineLoc = washingMachine.getLocation();
		
		if (!monkey.getLocation().equals(cabbage.getLocation()) && monkey.getLocation().equals(washingMachineLoc) && isPlayerOnOppositeLand(monkey.getLocation()) ){
			if (!this.washingMachineEmpty()){
				return false;
			}
		}
		
		int boatCount = 0;
		int machineCount = 0;
		for (GamePiece ps : allGamePieces){
			if (ps.getLocation().getPosition() == Position.WASHINGMACHINE ){
				machineCount++;
			}
			if (ps.getLocation().getPosition() == Position.BOAT ){
				boatCount++;
			}
		}
		
		if (boatCount > 1 || machineCount > 1){
			return false;
		}
		
		return true;
	}
	
	public boolean isPlayerOnOppositeLand(Location location){
		if (location.isLand() && Location.getOppositeLocation(location).equals(playerLocation)){
			return true;
		}
		return false;
	}
	
	public boolean arePiecesTogetherOnLand(GamePiece piece1, GamePiece piece2){
		if (!piece1.getLocation().isLand() || !piece2.getLocation().isLand() ){
			return false;
		}
		if (!piece1.getLocation().equals(piece2.getLocation())){
			return false;
		}
		
		return true;
	}
	
	public GamePiece getGoat() {
		return goat;
	}

	public void setGoat(GamePiece goat) {
		addToGamePieces(goat);
		this.goat = goat;
	}

	public GamePiece getMonkey() {
		return monkey;
	}

	public void setMonkey(GamePiece monkey) {
		addToGamePieces(monkey);
		this.monkey = monkey;
	}

	public GamePiece getCabbage() {
		return cabbage;
	}

	public void setCabbage(GamePiece cabbage) {
		addToGamePieces(cabbage);
		this.cabbage = cabbage;
	}

	public GamePiece getWolf() {
		return wolf;
	}

	public void setWolf(GamePiece wolf) {
		addToGamePieces(wolf);
		this.wolf = wolf;
	}

	public GamePiece getWashingMachine() {
		return washingMachine;
	}

	public void setWashingMachine(GamePiece washingMachine) {
		addToGamePieces(washingMachine);
		this.washingMachine = washingMachine;
	}

	public GamePiece getGun() {
		return gun;
	}

	public void setGun(GamePiece gun) {
		addToGamePieces(gun);
		this.gun = gun;
	}

	public GamePiece getHunter() {
		return hunter;
	}

	public void setHunter(GamePiece hunter) {
		addToGamePieces(hunter);
		this.hunter = hunter;
	}

	public Location getPlayerLocation() {
		return playerLocation;
	}

	public void setPlayerLocation(Location playeLocation) {
		this.playerLocation = playeLocation;
	}
	
	public boolean washingMachineEmpty(){
		return !(cabbage.getLocation().equals(new Location(Position.WASHINGMACHINE)) || gun.getLocation().equals(new Location(Position.WASHINGMACHINE)));
	}
	
	@Override
	public boolean equals(Object obj){
		 if (obj == null){ 
			 return false;
		 }
		 if (!(obj instanceof BoardState)){
		       return false;
		 }
		 
		 if (obj == this){
		       return true;
		 }
		 
		 
		 BoardState comp = (BoardState) obj;
		 if (!getPlayerLocation().equals(comp.getPlayerLocation())){
			 return false;
		 }
		for (GamePiece gp : allGamePieces){
			GamePiece otherBoardGp = comp.getPieceByType(gp.getType());
			if (!otherBoardGp.equals(gp)){
				return false;
			}
		}
		 
		    
		    return true;
	}
	
	@Override
	public int hashCode() {
		int encodedPrimes = 1;
		//System.out.println(allGamePieces.size());
		for (GamePiece gp : allGamePieces){
			encodedPrimes = encodedPrimes * (int) Math.pow(gp.getType().getId(), gp.getLocation().getPosition().getId());
			//System.out.println(gp.getType().getName() + " : " + gp.getType().getId() + " : " + gp.getLocation().getPosition().getId());
		}
		
		if (this.getPlayerLocation().getPosition()== Position.BOAT){
			encodedPrimes = encodedPrimes * -1;
		}
		
		if (this.getPlayerLocation().getPosition()== Position.RIGHT){
			encodedPrimes = encodedPrimes * 19;
		}
		
		if (this.getPlayerLocation().getPosition()== Position.LEFT){
			encodedPrimes = encodedPrimes * 23;
		}
		
	    return encodedPrimes;
	}

	public String getLastMove() {
		return lastMove;
	}

	public void setLastMove(String lastMove) {
		this.lastMove = lastMove;
	}
	
	public GamePiece getPieceByType(GamePiece.Type type){
		switch(type)
	     {
	        case MONKEY:
	        	return this.getMonkey();
	        case CABBAGE:
	        	return this.getCabbage();
	        case GOAT:
	        	return this.getGoat();
	        case WOLF:
	        	return this.getWolf();
	        case GUN:
	        	return this.getGun();
	        case HUNTER:
	        	return this.getHunter();
	        case WASHINGMACHINE:
	        	return this.getWashingMachine();
	     }
		return null;

	}
	
	public void setPieceByType(GamePiece piece){
		switch(piece.getType())
	     {
	        case MONKEY:
	        	setMonkey(piece);
	        	break;
	        case CABBAGE:
	        	setCabbage(piece);
	        	break;
	        case GOAT:
	        	setGoat(piece);
	        	break;
	        case WOLF:
	        	setWolf(piece);
	        	break;
	        case GUN:
	        	setGun(piece);
	        	break;
	        case HUNTER:
	        	setHunter(piece);
	        	break;
	        case WASHINGMACHINE:
	        	setWashingMachine(piece);
	        	break;
	     }
	}
	
	public BoardState getBoardStateFromMove(GamePiece gp, Location location){
		BoardState newBs = new BoardState();
		newBs.setParentBoard(this);
		
		for (GamePiece gp2 : allGamePieces){
			if (!gp2.equals(gp)){
				GamePiece newGp = new GamePiece(gp2.getType(), new Location(gp2.getLocation().getPosition()));
				newBs.setPieceByType(newGp);
			}
		}
		
		if (location.getPosition() != Position.WASHINGMACHINE){
			newBs.setPlayerLocation(new Location(location.getPosition()));
		} else {
			newBs.setPlayerLocation(new Location(this.getWashingMachine().getLocation().getPosition()));
		}
		
		if (gp != null){
			GamePiece newGp = new GamePiece(gp.getType(), new Location(location.getPosition()));
			newBs.setPieceByType(newGp);
			newBs.setLastMove("Moved " + gp.getType().getName() + " to the " + location.getPosition().getName());
			
		} else {
			newBs.setLastMove("Moved Player to " + newBs.getPlayerLocation().getPosition().getName());
		}
		
		return newBs;
	}
	
	public Set<BoardState> getAdmissibleNeighboringBoardStates(){
		Set<BoardState> boardStates = new HashSet<>();
		for (GamePiece gp2 : allGamePieces){
			for (Location p : gp2.getAccessiblePositions()){
				BoardState neighbor = getBoardStateFromMove(gp2, p);
				if (neighbor.isBoardStateAcceptable()){
					boardStates.add(neighbor);
				}
			}
		}
		
		for (Position neighborPositions : this.getPlayerLocation().getNeighboringPositions()){
			BoardState neighbor = getBoardStateFromMove(null, new Location(neighborPositions));
			if (neighbor.isBoardStateAcceptable()){
				boardStates.add(neighbor);
			}
		}
		
		return boardStates;
	}
	
	public void printState(){
		String state = this.getLastMove() + ": \n ";
		HashMap<Position, Set<GamePiece>> situation = new HashMap<>();
		for (GamePiece gp2 : allGamePieces){
			
			if (situation.containsKey(gp2.getLocation().getPosition())){
				situation.get(gp2.getLocation().getPosition()).add(gp2);
			} else {
				Set<GamePiece> set = new HashSet<>();
				set.add(gp2);
				situation.put(gp2.getLocation().getPosition(), set);
			}
			
			//state += gp2.getType().getName() + " is at the " + gp2.getLocation().getPosition().getName() + " : ";
		}
		
//		state += " \n and the player is at : " + this.getPlayerLocation().getPosition().getName();
//		state += " " + this.hashCode();
		state += "LEFT: ";
		if (situation.containsKey(Position.LEFT)){
			for (GamePiece gp : situation.get(Position.LEFT)){
				state += gp.getType().getName() + ", ";
			}
		}
		state += "---------";
		
		state += "BOAT: ";
		
		if (situation.containsKey(Position.BOAT)){
			for (GamePiece gp : situation.get(Position.BOAT)){
				state += gp.getType().getName() + ", ";
			}
		}
		state += "---------";
		
		state += "RIGHT: ";
		if (situation.containsKey(Position.RIGHT)){
			for (GamePiece gp : situation.get(Position.RIGHT)){
				state += gp.getType().getName() + ", ";
			}
		}
		
		state += "/n WASHING MACHINE: ";
		if (situation.containsKey(Position.WASHINGMACHINE)){
			for (GamePiece gp : situation.get(Position.WASHINGMACHINE)){
				state += gp.getType().getName() + ", ";
			}
		}
		
		System.out.println(state);
		
		
		
	}
	
	public static BoardState getBoardStateFromHashCode(int encoding){
		BoardState newBs = new BoardState();
		Location wash = new Location(Position.WASHINGMACHINE);
		Location left = new Location(Position.LEFT);
		Location right = new Location(Position.RIGHT);
		Location boat = new Location(Position.BOAT);
		for (Type type : Type.values()){
			Location location = new Location(Position.LEFT);

				int prime1 = type.getId();
				int prime2 = (int) Math.pow(type.getId(), 2);
				int prime3 = (int) Math.pow(type.getId(), 3);
				if (encoding % prime3 == 0){
					location = wash;
				} else if (encoding % prime2 == 0){
					location = boat;
				} else if (encoding % prime1 == 0){
					location = right;
				} else {
					location = left;
				}
				
				GamePiece gp = new GamePiece(type, location);
				
				newBs.setPieceByType(gp);
		}
		
		if (encoding < 0){
			newBs.setPlayerLocation(new Location(Position.BOAT));
		} else if (encoding % 19 == 0){
			newBs.setPlayerLocation(new Location(Position.RIGHT));
		} else {
			newBs.setPlayerLocation(new Location(Position.LEFT));
		}
		
	
		
		return newBs;
	}
	
}
