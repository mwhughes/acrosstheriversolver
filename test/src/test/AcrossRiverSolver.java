package test;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

import test.GamePiece.Type;
import test.Location.Position;

public class AcrossRiverSolver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AcrossRiverSolver test = new AcrossRiverSolver();
		BoardState initialState = test.initialState(); //BoardState.getBoardStateFromHashCode(10120);//
		BoardState endState = test.endState();
		
		
		HashSet<Integer> queudIds = new HashSet<>();		
	
		LinkedBlockingQueue<BoardState> toExplore = new LinkedBlockingQueue<>();
		
		
		HashSet<BoardState> boardStatesExplored = new HashSet<>();
		toExplore.addAll(initialState.getAdmissibleNeighboringBoardStates());
		
		// Set 1 equals 1 to play game
		if ("1".equals("2")){
			waitForEnter("TEST", initialState);
			return;
		}
		
		BoardState solution = new BoardState();
		boolean solved = false;
		toExplore.addAll(initialState.getAdmissibleNeighboringBoardStates());
		int count = 0;
		while (!toExplore.isEmpty() && !solved && count < 1000000){
			count++;
			BoardState current = toExplore.poll();
		//current.printState();
			queudIds.add(current.hashCode());
			solution = current;
			if (current.hashCode() == endState.hashCode()){
				solved = true;
				solution = current;

			} else {
				for (BoardState neighbor : current.getAdmissibleNeighboringBoardStates()){
					if (!queudIds.contains(neighbor.hashCode())){
						toExplore.add(neighbor);		
						queudIds.add(neighbor.hashCode());
					}
				}
			}
			
		}
		System.out.println("solved? " + solved);
		
		ArrayList<BoardState> solutionList = new ArrayList<>();
		
		BoardState currentNode = solution;
		
		while (currentNode != null){
			solutionList.add(currentNode);
			currentNode = currentNode.getParentBoard();
		}
		
		Collections.reverse(solutionList);
		System.out.println(solutionList.size());
		for (BoardState state : solutionList){
			System.out.println(state.getLastMove());
			state.printState();
		}
	
		
		solution.printState();
	}
	
	private BoardState initialState(){
		BoardState bs = new BoardState();
		bs.setMonkey(new GamePiece(Type.MONKEY, new Location(Position.LEFT)));
		bs.setCabbage(new GamePiece(Type.CABBAGE, new Location(Position.LEFT)));
		bs.setHunter(new GamePiece(Type.HUNTER, new Location(Position.LEFT)));
		bs.setGoat(new GamePiece(Type.GOAT, new Location(Position.RIGHT)));
		bs.setWolf(new GamePiece(Type.WOLF, new Location(Position.LEFT)));
		bs.setGun(new GamePiece(Type.GUN, new Location(Position.WASHINGMACHINE)));
		bs.setWashingMachine(new GamePiece(Type.WASHINGMACHINE, new Location(Position.LEFT)));
		bs.setPlayerLocation(new Location(Position.LEFT));
		return bs;
	}
	
	private BoardState initialStateTest(){
		BoardState bs = new BoardState();
		bs.setMonkey(new GamePiece(Type.MONKEY, new Location(Position.RIGHT)));
		bs.setCabbage(new GamePiece(Type.CABBAGE, new Location(Position.RIGHT)));
		bs.setHunter(new GamePiece(Type.HUNTER, new Location(Position.LEFT)));
		bs.setGoat(new GamePiece(Type.GOAT, new Location(Position.LEFT)));
		bs.setWolf(new GamePiece(Type.WOLF, new Location(Position.RIGHT)));
		bs.setGun(new GamePiece(Type.GUN, new Location(Position.WASHINGMACHINE)));
		bs.setWashingMachine(new GamePiece(Type.WASHINGMACHINE, new Location(Position.RIGHT)));
		bs.setPlayerLocation(new Location(Position.LEFT));
		return bs;
	}
	
	private BoardState endState(){
		BoardState bs = new BoardState();
		bs.setMonkey(new GamePiece(Type.MONKEY, new Location(Position.RIGHT)));
		bs.setCabbage(new GamePiece(Type.CABBAGE, new Location(Position.RIGHT)));
		bs.setHunter(new GamePiece(Type.HUNTER, new Location(Position.RIGHT)));
		bs.setGoat(new GamePiece(Type.GOAT, new Location(Position.LEFT)));
		bs.setWolf(new GamePiece(Type.WOLF, new Location(Position.RIGHT)));
		bs.setGun(new GamePiece(Type.GUN, new Location(Position.WASHINGMACHINE)));
		bs.setWashingMachine(new GamePiece(Type.WASHINGMACHINE, new Location(Position.RIGHT)));
		bs.setPlayerLocation(new Location(Position.BOAT));
		return bs;
	}
	
	private void printNewStateAndOptions(int newStateHash){
		
	}
	
	public static void waitForEnter(String message, BoardState current) {
		   // using InputStreamReader
		
		current.printState();
		for (BoardState test2 : current.getAdmissibleNeighboringBoardStates()){
			System.out.println("Your options are:");
				test2.printState();
			}
		
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));          
            System.out.print("Enter hash of option: ");
 
            String name = reader.readLine();
            Integer inputVal = Integer.parseInt(name);
            System.out.println("Your choice is is: " + inputVal);
            waitForEnter("Ok", BoardState.getBoardStateFromHashCode(inputVal));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
	}
	
	

}
