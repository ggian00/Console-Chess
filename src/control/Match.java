package control;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Match {
	private Board engineBoard;
	private String[][] displayBoard = new String[8][8];
	private String[][] prevBoard = null;
	private List<Move> moves = new ArrayList<Move>();
	private String title;

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

		prevBoard = copyDisplayBoard();
		engineBoard.toggleTurn();

		if (engineBoard.inCheck(engineBoard.getTurn(), engineBoard.board, engineBoard.whitePieces,
				engineBoard.blackPieces)) {
			move.check = engineBoard.getTurn();
		}

		moves.add(move);
		executeMoveOnDisplayBoard(move);

		return move;
	}

	public Boolean undo() {
		if (prevBoard == null) {
			return false;
		}
		engineBoard.undo();
		displayBoard = prevBoard;
		moves.remove(moves.size() - 1);
		return true;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private void executeMoveOnDisplayBoard(Move m) {
		String firstPiece = displayBoard[(int) m.firstPieceOrigin.getX()][(int) m.firstPieceOrigin.getY()];
		if (m.isPromotion()) {
			String color = firstPiece.substring(0, 1);
			displayBoard[(int) m.firstPieceTarget.getX()][(int) m.firstPieceTarget.getY()] = color + m.promotionType;
		} else {
			displayBoard[(int) m.firstPieceTarget.getX()][(int) m.firstPieceTarget.getY()] = firstPiece;
		}
		if (m.secondPieceOrigin != null) {
			displayBoard[(int) m.secondPieceTarget.getX()][(int) m.secondPieceTarget
					.getY()] = displayBoard[(int) m.firstPieceOrigin.getX()][(int) m.firstPieceOrigin.getY()];
			displayBoard[(int) m.secondPieceOrigin.getX()][(int) m.secondPieceOrigin.getY()] = "";
		}
	}

	private String[][] copyDisplayBoard() {

		String[][] copyBoard = new String[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				copyBoard[i][j] = displayBoard[i][j];
			}
		}

		return copyBoard;
	}

	private void populateDisplayBoard() {

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

	}
}
