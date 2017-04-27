package control;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Match {
	Board engineBoard;
	private String[][] displayBoard = new String[8][8];
	private List<Move> moves = new ArrayList<Move>();
	private String title;
	private Boolean ongoing = true;
	private int currentMoveIndex = -1;

	public Match() {
		engineBoard = new Board();
		populateDisplayBoard();
	}

	public Move executeMove(Point origin, Point target, char promotion) {
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

	public Boolean undo() {
		if (moves.isEmpty()) {
			return false;
		}
		engineBoard.undo();
		displayBoard = moves.get(--currentMoveIndex).displayBoard;
		moves.remove(moves.size() - 1);
		return true;
	}

	public String[][] getCurrentDisplayBoard() {
		return displayBoard;
	}

	public void setToZerothMove() {
		populateDisplayBoard();
		currentMoveIndex = -1;
	}

	public Move getNextMove() {
		if (currentMoveIndex >= moves.size() - 1) {
			return null;
		}
		displayBoard = moves.get(++currentMoveIndex).displayBoard;
		return moves.get(currentMoveIndex);
	}

	public Move getPrevMove() {
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

	public Move getLastMove() {
		if (moves.isEmpty()) {
			return null;
		}
		displayBoard = moves.get(moves.size() - 1).displayBoard;
		currentMoveIndex = moves.size() - 1;
		return moves.get(moves.size() - 1);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean isOngoing() {
		return ongoing;
	}

	private void populateDisplayBoard() {

		displayBoard = new String[8][8];

		displayBoard[0][7] = "bR";
		displayBoard[1][7] = "bN";
		displayBoard[2][7] = "bB";
		displayBoard[3][7] = "bQ";
		displayBoard[4][7] = "bK";
		displayBoard[5][7] = "bB";
		displayBoard[6][7] = "bN";
		displayBoard[7][7] = "bR";

		for (int i = 0; i < 8; i++) {
			displayBoard[i][6] = "bP";
			displayBoard[i][1] = "wP";
		}

		displayBoard[0][0] = "wR";
		displayBoard[1][0] = "wN";
		displayBoard[2][0] = "wB";
		displayBoard[3][0] = "wQ";
		displayBoard[4][0] = "wK";
		displayBoard[5][0] = "wB";
		displayBoard[6][0] = "wN";
		displayBoard[7][0] = "wR";

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (displayBoard[i][j] == null) {
					displayBoard[i][j] = "";
				}
			}
		}
	}
}
