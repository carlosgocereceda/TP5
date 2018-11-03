package main.java.es.ucm.fdi.tp.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WolfAndSheepState;

public class Main {

	
	public static void main(String[] args){
		if(validArguments(args)){
			List<GamePlayer> players = new ArrayList<GamePlayer>();
			GameState<?, ?> game = createInitialState(args);
			players.add(createPlayer(args[0], args[1], "Player 1"));
			players.add(createPlayer(args[0], args[2], "Player 2"));
			playGame(game, players);
		 // <-- closes the scanner when the try-block ends
		}
		else{
			System.exit(-1);
			//exit(-1);
		}
	}
	public static boolean validArguments(String[] args){
		boolean ok = true;
		if(args.length !=3){
			System.err.println("Se han introducido más de tres elementos");
			return false;
		}
		if(!args[0].equalsIgnoreCase("was")|| !args[0].equalsIgnoreCase("was")){
			System.err.println("El juego introducido no es correcto");
			return false;
		}
		else return true;
	}
	public static GameState<?,?> createInitialState(String[] gameName){
		if(gameName[0].equalsIgnoreCase("was")){
			return new WolfAndSheepState();
		}
		else{
			
				return new TttState(3);
		}
		
	}
	public static GamePlayer createPlayer(String gameName,
			String playerType, String playerName){
		GamePlayer gameplayer = null;
		Scanner s = new Scanner(System.in);
		switch(playerType){
		case "console":
			gameplayer = new ConsolePlayer(playerName, s );
		break;
		case "smart":
			gameplayer = new SmartPlayer(playerName, 3);
			break;
		case "random":
			gameplayer = new RandomPlayer(playerName);
		break;
		}
		if(gameplayer == null){
			System.out.println("No se reconoce el tipo de jugador.");
		}
		return gameplayer;
	}
	public static <S extends GameState<S, A>, A extends GameAction<S, A>> int playGame(GameState<S, A> initialState,
			List<GamePlayer> players) {
		int playerCount = 0;
		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
		@SuppressWarnings("unchecked")
		S currentState = (S) initialState;

		while (!currentState.isFinished()) {
			// request move
			A action = players.get(currentState.getTurn()).requestAction(currentState);
			// apply move
			currentState = action.applyTo(currentState);
			System.out.println("After action:\n" + currentState);

			if (currentState.isFinished()) {
				// game over
				String endText = "The game ended: ";
				int winner = currentState.getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
			}
		}
		return currentState.getWinner();
	}
	public static void match(GameState<?, ?> initialState, GamePlayer a, GamePlayer b, int times) {
		int va = 0, vb = 0;

		List<GamePlayer> players = new ArrayList<GamePlayer>();
		players.add(a);
		players.add(b);

		for (int i = 0; i < times; i++) {
			switch (playGame(initialState, players)) {
			case 0:
				va++;
				break;
			case 1:
				vb++;
				break;
			}
		}
		System.out.println("Result: " + va + " for " + a.getName() + " vs " + vb + " for " + b.getName());
	}

}
