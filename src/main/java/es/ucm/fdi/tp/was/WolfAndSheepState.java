package es.ucm.fdi.tp.was;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;


public class WolfAndSheepState extends GameState<WolfAndSheepState, WolfAndSheepAction> {
	/*
	 * ATRIBUTOS: TURNO SI HAY GANADOR DIMENSIÓN DEL TABLERO TABLERO COMO MATRIZ
	 * EMPTY UN BOOL PARA SABER SI ESTÁ FINALIZADO
	 */
	private static final long serialVersionUID = -2641387354190472377L;

	private final int turn;
	private final boolean finished;
	private final int[][] board;
	private final int winner;

	//private final int dim;

	final static int EMPTY = -1;
	final static int DIM = 8;

	/*
	 * DIM NO LO NECESITAMOS
	 */
	public WolfAndSheepState(WolfAndSheepState prev, int[][] board){
		 super(2);
	        
	        this.board = board;
	        //this.dim = 8;
	        if ( isWinner(prev.turn) ) {
	            finished = true;
	            winner = prev.turn;
	        } else {
	            finished = false;
	            winner = -1;
	        }
	        
	        this.turn = (prev.turn + 1) % 2;
	}
	public WolfAndSheepState() {
		super(2);
		//this.dim = 8;
		board = new int[DIM][];
		for (int i = 0; i < DIM; i++) {
			board[i] = new int[DIM];
			for (int j = 0; j < DIM; j++)
				board[i][j] = EMPTY;
		}
		//Coloco el lobo
		board[DIM-1][0] = 0;
		int i = 0;
		int j = 0;
		while (j < DIM) {
			if (j % 2 == 1) {
				board[i][j] = 1;
			}
			j++;
		}
		this.turn = 0;
		this.winner = -1;
		this.finished = false;
	}

	public WolfAndSheepState(WolfAndSheepState prev, int[][] board, boolean finished, int winner) {
		super(2);
		//this.dim = prev.dim;
		this.board = board;
		this.turn = (prev.turn + 1) % 2;
		this.finished = finished;
		this.winner = winner;
	}

	public boolean isValid(WolfAndSheepAction action) {
		if (isFinished()) {
			return false;
		}
		return at(action.getNewRow(), action.getNewCol()) == EMPTY;
	}

	/*
	 * EN EL TT SE IDENTIFICAN LAS POSICIONES VÁLIDAS VIENDO LAS VACIAS NO HAY
	 * OVEJAS, HAY MOVIMIENTOS! ENUMERADO CON LOS MOVIMIENTOS COMO CLASE Y UN
	 * MÉTODO QUE NOS DIGA SI SE SALE
	 */
	public List<WolfAndSheepAction> validActions(int playerNumber) {
		ArrayList<WolfAndSheepAction> valid = new ArrayList<>();
		if (finished) {
			return valid;
		}

		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				if (playerNumber == 0) {
					if (this.board[i][j] == 0) {
						if (i - 1 < DIM && j - 1 < DIM && i - 1 >= 0 && j - 1 >= 0
								&& at(i - 1, j - 1) == EMPTY) {
							// int player, int oldrow, int oldcol, int newrow,
							// int newcol
							valid.add(new WolfAndSheepAction(playerNumber, i, j, i - 1, j - 1));
						}
						if (i + 1 < DIM && j - 1 < DIM && i + 1 >= 0 && j - 1 >= 0
								&& at(i + 1, j - 1) == EMPTY) {
							valid.add(new WolfAndSheepAction(playerNumber, i, j, i + 1, j - 1));
						}
						if (i - 1 < DIM && j + 1 < DIM && i - 1 >= 0 && j + 1 >= 0
								&& at(i - 1, j + 1) == EMPTY) {
							valid.add(new WolfAndSheepAction(playerNumber, i, j, i - 1, j + 1));
						}
						if (i + 1 < DIM && j + 1 < DIM && i + 1 >= 0 && j + 1 >= 0
								&& at(i + 1, j + 1) == EMPTY) {
							valid.add(new WolfAndSheepAction(playerNumber, i, j, i + 1, j + 1));
						}
					}
				}
				if (playerNumber  == 1) {
					if (this.board[i][j] == 1) {
						if (i + 1 < DIM && j - 1 < DIM && i + 1 >= 0 && j - 1 >= 0
								&& this.board[i + 1][j - 1] == EMPTY) {
							valid.add(new WolfAndSheepAction(playerNumber, i, j, i + 1, j - 1));
						}
						if (i + 1 < DIM && j + 1 < DIM && i + 1 >= 0 && j + 1 >= 0
								&& this.board[i + 1][j + 1] == EMPTY) {
							valid.add(new WolfAndSheepAction(playerNumber, i, j, i + 1, j + 1));
						}
						// valid.add(new TttAction(playerNumber, i, j));
					}
				}
			}
		}

		return valid;
	}

	public static boolean isDraw(int[][] board) {
		return false;
		// no hay empate
	}

	/*
	 * cÓMO SABER SI EL LOBO ES UN JUGADOR SI LA FILA 0 EN ALGUNA DE LAS
	 * COLUMNAS ESTÁ EL LOBO EL LOBO NO TIENE MOVIMIENTOS -> GANAN LAS OVEJAS NO
	 * HAY TABLAS COMPROBAR QUE SE PUEDAN HACER MOVIMINETOS DE MAS DE UNA
	 * CASILLA
	 */
/*	private  boolean isWinner(int[][] board, int playerNumber,
			int x, int y) {
		boolean won = false;
		if(board[x][y] == 0){
			if(y == 0){
				won = true;
			}
		}
		
		if (validActions(0).isEmpty() || validActions(1).isEmpty() ){
			won = true;
		}
		return won;
	}
*/
	public boolean isWinner(int turn) {
		boolean won = false;
		if(turn ==0 && validActions(1).isEmpty()){
			won = true;
		}
		else if(turn == 0 && !validActions(1).isEmpty()){
			for (int i =0;i<DIM;i++){
				for(int j =0;j<DIM;j++){
					if(this.board[i][j] == 0){
						if(i == 0){
							won = true;
						}
					}
					
				}
			}
		}
		if(turn == 1 && validActions(0).isEmpty()){
			won = true;
		}
		
		
		/*
		for (int i = 0; !won && i < board.length; i++) {
			if (isWinner(board, playerNumber, 0, i, 1, 0))
				won = true;
			if (isWinner(board, playerNumber, i, 0, 0, 1))
				won = true;
		}
		return won || isWinner(board, playerNumber, 0, 0, 1, 1) || isWinner(board, playerNumber, 2, 0, -1, 1);
		 */
		return won;
	}

	public int at(int row, int col) {
		return board[row][col];
	}

	public int getTurn() {
		return turn;
	}

	public boolean isFinished() {
		return finished;
	}

	public int getWinner() {
		return winner;
	}

	/**
	 * @return a copy of the board
	 */
	public int[][] getBoard() {
		int[][] copy = new int[board.length][];
		for (int i = 0; i < board.length; i++)
			copy[i] = board[i].clone();
		return copy;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < board.length; i++) {
			sb.append("|");
			for (int j = 0; j < board.length; j++) {
				if ((i + j) % 2 == 1) {
					sb.append(board[i][j] == EMPTY ? "  .  " : board[i][j] == 0 ? "  W  " : "  S  ");
				} else {
					sb.append(board[i][j] == EMPTY ? "     " : board[i][j] == 0 ? "  O  " : "  X  ");
				}

			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
