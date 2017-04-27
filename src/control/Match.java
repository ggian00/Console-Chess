package control;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import pieces.Piece;

/**
 * This class is the representation of a chess match. A caller can execute
 * moves, make AI moves, undo moves, and get a String[][] representation of the
 * board at any moment. After the match is concluded, the user can iterate
 * through match states to be able to display the match, move by move.
 * 
 * @author Phil Plucinski
 *
 */
public class Match {
	Board engineBoard;
	private String[][] displayBoard = new String[8][8];
	private List<Move> moves = new ArrayList<Move>();
	private String title;
	private Boolean ongoing = true;
	private int currentMoveIndex = -1;

	/**
	 * No-arg constructor that simply initializes various variables in the
	 * class.
	 */
	public Match() {
		engineBoard = new Board();
		populateDisplayBoard();
	}

	/**
	 * Calls the executeMove within Board and updates the display board within
	 * Match.
	 * 
	 * @param origin
	 *            Point containing the piece to be moved.
	 * @param target
	 *            Destination point
	 * @param promotion
	 *            char indicating the desired promotion, if applicable
	 * @return
	 */
	public Move executeMove(Point origin, Point target, char promotion) {
		if (!ongoing) {
			return null;
		}
		Move move = engineBoard.executeMove(engineBoard.board, engineBoard.whitePieces, engineBoard.blackPieces, origin,
				target, promotion);

		if (move == null) {
			return null;
		}

		engineBoard.toggleTurn();

		if (engineBoard.inCheck(engineBoard.getTurn(), engineBoard.board, engineBoard.whitePieces,
				engineBoard.blackPieces)) {
			move.check = engineBoard.getTurn();
		}

		if (!engineBoard.matchCanContinue()) {
			ongoing = false;
		}

		move.updateDisplayBoard(displayBoard);
		displayBoard = move.displayBoard;
		moves.add(move);
		currentMoveIndex++;

		System.out.println(move.toString());

		return move;
	}

	/**
	 * Reverses the last move in both the engine and display boards.
	 * 
	 * @return true if successful, false otherwise
	 */
	public Boolean undo() {
		if (moves.isEmpty()) {
			return false;
		}
		engineBoard.undo();
		if (currentMoveIndex == 0) {
			populateDisplayBoard();
		} else {
			displayBoard = moves.get(--currentMoveIndex).displayBoard;
		}
		moves.remove(moves.size() - 1);
		return true;
	}

	/**
	 * Performs a move randomly selected from the set of valid moves for the
	 * current player.
	 * 
	 * @return Move representation of the performed move
	 */
	public Move makeAIMove() {
		if (!ongoing) {
			return null;
		}
		char turn = engineBoard.getTurn();
		Move move = null;
		do {
			ArrayList<Piece> pieces = (turn == 'w') ? engineBoard.whitePieces : engineBoard.blackPieces;
			int pieceNo = new Random().nextInt(pieces.size());
			ArrayList<Point> moves = engineBoard.getValidMoves(pieces.get(pieceNo));
			Collections.shuffle(moves);
			for (Point point : moves) {
				move = executeMove(pieces.get(pieceNo).location, point, 'Q');
				if (move != null) {
					break;
				}
			}
		} while (move == null);

		return move;

	}

	/**
	 * Returns the display board based on its current state. While ongoing, the
	 * board represents the currennt turn. When the match is not ongoing, the
	 * display board corresponds to that of the move that is currently being
	 * examined.
	 * 
	 * @return String[][] representation of the board in the form of
	 *         board[col][row]. ie. board [a][4] == "wq"
	 */
	public String[][] getCurrentDisplayBoard() {
		return displayBoard;
	}

	/**
	 * Sets the display board to the initial setup, to reflect move 'zero'.
	 */
	public void setToZerothMove() {
		if (ongoing) {
			return;
		}
		populateDisplayBoard();
		currentMoveIndex = -1;
	}

	/**
	 * Returns the next Move in the match and sets the displayBoard to the
	 * displayBoard of that move.
	 * 
	 * @return Next Move
	 */
	public Move getNextMove() {
		if (ongoing) {
			return null;
		}
		if (currentMoveIndex >= moves.size() - 1) {
			return null;
		}
		displayBoard = moves.get(++currentMoveIndex).displayBoard;
		return moves.get(currentMoveIndex);
	}

	/**
	 * Returns the previous Move in the match and sets the displayBoard to the
	 * displayBoard of that move.
	 * 
	 * @return Previous Move
	 */
	public Move getPrevMove() {
		if (ongoing) {
			return null;
		}
		if (currentMoveIndex < 0) {
			return null;
		}
		if (currentMoveIndex == 0) {
			setToZerothMove();
			return null;
		}
		displayBoard = moves.get(--currentMoveIndex).displayBoard;
		return moves.get(currentMoveIndex);
	}

	/**
	 * Returns the last Move of the match and sets the displayBoard to that of
	 * the move.
	 * 
	 * @return Last Move
	 */
	public Move getLastMove() {
		if (ongoing) {
			return null;
		}
		if (moves.isEmpty()) {
			return null;
		}
		displayBoard = moves.get(moves.size() - 1).displayBoard;
		currentMoveIndex = moves.size() - 1;
		return moves.get(moves.size() - 1);
	}

	/**
	 * @return Title of the Match
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Title of the Match
	 * 
	 * @param title
	 *            title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return true if match is ongoing. false otherwise.
	 */
	public Boolean isOngoing() {
		return ongoing;
	}

	/**
	 * Sets the displayBoard to the initial setup of a chess board.
	 */
	private void populateDisplayBoard() {

		displayBoard = new String[8][8];

		displayBoard[0][7] = "br";
		displayBoard[1][7] = "bn";
		displayBoard[2][7] = "bb";
		displayBoard[3][7] = "bq";
		displayBoard[4][7] = "bk";
		displayBoard[5][7] = "bb";
		displayBoard[6][7] = "bn";
		displayBoard[7][7] = "br";

		for (int i = 0; i < 8; i++) {
			displayBoard[i][6] = "bp";
			displayBoard[i][1] = "wp";
		}

		displayBoard[0][0] = "wr";
		displayBoard[1][0] = "wn";
		displayBoard[2][0] = "wb";
		displayBoard[3][0] = "wq";
		displayBoard[4][0] = "wk";
		displayBoard[5][0] = "wb";
		displayBoard[6][0] = "wn";
		displayBoard[7][0] = "wr";

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (displayBoard[i][j] == null) {
					displayBoard[i][j] = "";
				}
			}
		}
	}
}
