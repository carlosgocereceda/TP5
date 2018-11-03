package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;


public class WolfAndSheepAction implements GameAction<WolfAndSheepState, WolfAndSheepAction> {

	private static final long serialVersionUID = -8491198872908329925L;
	/*
	 * JUGADOR QUE VA A HACER EL MOVIMIENTO FILA Y COLUMNA QUE SE VA A HACER
	 * 
	 */

	private int player;
	private int old_row;
	private int old_col;
	private int new_row;
	private int new_col;

	public WolfAndSheepAction(int player, int oldrow, int oldcol, int newrow, int newcol) {
		this.player = player;
		this.old_row = oldrow;
		this.old_col = oldcol;
		this.new_col = newcol;
		this.new_row = newrow;

	}

	public int getPlayerNumber() {
		return player;
	}

	public WolfAndSheepState applyTo(WolfAndSheepState state) {
		/*
		 * COGE EL TABLERO Y CONSTRUYE EL SIGUIENTE ESTADO
		 */
		if (player != state.getTurn()) {
			throw new IllegalArgumentException("Not the turn of this player");
		}

		// make move
		/*
		 * GETBOARD SACA LA MATRIZ METE AL JUGADOR VEMOS SI HAY UN GANADOR, SI
		 * SE HAN PRODUCIDO TABLAS...
		 */
		int[][] board = state.getBoard();
		board[new_row][new_col] = player;
		board[old_row][old_col] = -1;
		// update state
		/*
		 * AQUÍ SE COMPRUEBA SI HAY GANADOR...
		 */
		/*
		 * EN EL WAS NECESITAMOS MAS INFORMACION QUE LA FILA Y LA COLUMNA
		 * NECESITARÉ LA FILA Y LA COLUMNA ORIGEN Y A DONDE ME VOY A MOVER EN
		 * LAS OVEJAS DA IGUAL LA OVEJA QUE SE MUEVA, SÓLO IMPORTA A DÓNDE SE
		 * MUEVE.
		 */

		return new WolfAndSheepState(state,board);
	}

	public int getNewRow() {
		return new_row;
	}
	public int getOldRow(){
		return old_row;
	}
	public int getNewCol() {
		return new_col;
	}
	public int getOldCol(){
		return old_col;
	}
	public String toString() {
		return "place " + player + " from (" + old_row + ", " + old_col + ")"
		+ " at (" + new_row + ", " + new_col + ")";
	}


}
