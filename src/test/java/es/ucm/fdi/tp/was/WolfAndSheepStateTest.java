package es.ucm.fdi.tp.was;

import static org.junit.Assert.*;

import org.junit.Test;

public class WolfAndSheepStateTest {

	@Test
	public void WolfWinner() {

		WolfAndSheepState state = new WolfAndSheepState();
		int[][] board;

		for (int i = 1; i < state.getBoard().length; i = i + 2) {

			board = state.getBoard();
			board[0][i] = 0;
			WolfAndSheepState newstate = new WolfAndSheepState(state, board);
			assertEquals("The winner has to be the wolf", true, newstate.isWinner(0));

		}

	}
	
	@Test
	public void initialWolf(){
		
		WolfAndSheepState state = new WolfAndSheepState();
		int[][] board = state.getBoard();
		assertEquals("The wolf only has one action", 1, state.validActions(0).size());
		
		board[state.getBoard().length - 1][0] = -1;
		board[state.getBoard().length - 2][1] = 0;
		state = new WolfAndSheepState(state, board);
		assertEquals("The wolf only have four action", 4, state.validActions(0).size());
		
	}


	@Test
	public void loboacorralado(){
		WolfAndSheepState state = new WolfAndSheepState();
		int[][] board = state.getBoard();
		/*
		WolfAndSheepState state = new WolfAndSheepState();
		int[][] board = state.getBoard();
		board[7][0] = 0;
		board[6][1] = 1;
		WolfAndSheepState newstate = new WolfAndSheepState(state, board);
		assertEquals("El lobo no tiene movimientos porque ha siido acorralado", 0, newstate.validActions(0).size());
	*/
		for(int i=0;i< state.getBoard().length;i++){
			for(int j=0;j< state.getBoard().length;j++ ){
				if ((i + j) % 2 == 1){
					board[i][j] = 0;
					if(i == 0 || j == 0 || i ==state.getBoard().length -1 || j == state.getBoard().length-1){
						if(state.at(i-1,j-1) == -1){
							board[i-1][j-1] = 1;
						}
						else if(state.at(i+1,j-1) == -1){
							board[i+1][j-1] = 1;
						}
						else if(state.at(i-1,j+1) == -1){
							board[i-1][j+1] = 1;
						}
						else{
							if(state.at(i+1,j+1) == -1){
								board[i+1][j+1] = 1;
							}
						}
					}
					else{
						board[i-1][j-1] = 1;
						board[i+1][j-1] = 1;
						board[i-1][j+1] = 1;
						board[i+1][j+1] = 1;
					}
					
					WolfAndSheepState newstate = new WolfAndSheepState(state, board);
					assertEquals("El lobo no tiene movimientos porque ha siido acorralado", 0, newstate.validActions(0).size());
				}
			}
		
		}
		
	}
	@Test
	public void ovejas(){
		WolfAndSheepState state= new WolfAndSheepState();
		int[][] board = state.getBoard();
		
		for(int j =1;j<state.getBoard().length;j = j+2){
			board[0][j] = -1;
		}
		for (int i = 1; i < state.getBoard().length; i = i + 2) {
			board[0][i] = 1;
			state = new WolfAndSheepState(state, board);
			if(i == state.getBoard().length-1){
				assertEquals("Unicamente tiene un movimiento posible porque está al final",
						1, state.validActions(1).size());
			}
			else{
				assertEquals("Tiene dos movimientos posibles",
						2, state.validActions(1).size());
			}
			board[0][i] = -1;
		}
	}

}
