package es.ucm.fdi.tp.ttt;

import es.ucm.fdi.tp.base.model.GameAction;

/**
 * An action for TickTackToe.
 */
public class TttAction implements GameAction<TttState, TttAction> {

	private static final long serialVersionUID = -8491198872908329925L;
	/*
	 * JUGADOR QUE VA A HACER EL MOVIMIENTO
	 * FILA Y COLUMNA QUE SE VA A HACER
	 * 
	 */
	
	private int player;
    private int row;
    private int col;

    public TttAction(int player, int row, int col) {
        this.player = player;
        this.row = row;
        this.col = col;
    }

    public int getPlayerNumber() {
        return player;
    }

    public TttState applyTo(TttState state) {
    	/*
    	 * COGE EL TABLERO Y CONSTRUYE EL SIGUIENTE ESTADO
    	 */
        if (player != state.getTurn()) {
            throw new IllegalArgumentException("Not the turn of this player");
        }

        // make move
        /*
         * GETBOARD SACA LA MATRIZ
         * METE AL JUGADOR
         * VEMOS SI HAY UN GANADOR, SI SE HAN PRODUCIDO TABLAS...
         */
        int[][] board = state.getBoard();
        board[row][col] = player;

        // update state
        /*
         * AQUÍ SE COMPRUEBA SI HAY GANADOR...
         */
        /*
         * EN EL WAS NECESITAMOS MAS INFORMACION QUE LA FILA Y LA COLUMNA
         * NECESITARÉ LA FILA Y LA COLUMNA ORIGEN Y A DONDE ME VOY A MOVER
         * EN LAS OVEJAS DA IGUAL LA OVEJA QUE SE MUEVA, SÓLO IMPORTA A 
         * DÓNDE SE MUEVE.
         */
        TttState next;
        if (TttState.isWinner(board, state.getTurn())) {
            next = new TttState(state, board, true, state.getTurn());
        } else if (TttState.isDraw(board)) {
            next = new TttState(state, board, true, -1);
        } else {
            next = new TttState(state, board, false, -1);
        }
        return next;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String toString() {
        return "place " + player + " at (" + row + ", " + col + ")";
    }

}
