package control;

import java.awt.Point;

public class Move {

	Point firstPieceOrigin;
	Point firstPieceTarget;
	Point secondPieceOrigin = null;
	Point secondPieceTarget = null;
	char promotionType = '\0';
	char check = '\0';
	String[][] displayBoard;

	Move() {
	}

	void setMove(Point firstOrigin, Point firstTarget, Point secondOrigin, Point secondTarget) {

		this.firstPieceOrigin = firstOrigin;
		this.firstPieceTarget = firstTarget;
		this.secondPieceOrigin = secondOrigin;
		this.secondPieceTarget = secondTarget;
		this.promotionType = '\0';
		this.check = '\0';
	}

	public Point getFirstPieceOrigin() {
		return firstPieceOrigin;
	}

	public Point getFirstPieceTarget() {
		return firstPieceTarget;
	}

	public Point getSecondPieceOrigin() {
		return secondPieceOrigin;
	}

	public Point getSecondPieceTarget() {
		return secondPieceTarget;
	}

	public boolean isPromotion() {
		return promotionType != '\0';
	}

	public boolean isCheck() {
		return check == '\0';
	}

	public char getPromotion() {
		return promotionType;
	}

	public char getCheck() {
		return check;
	}

	void updateDisplayBoard(String[][] prevBoard) {
		if (displayBoard != null) {
			return;
		}
		displayBoard = copyDisplayBoard(prevBoard);
		executeMoveOnDisplayBoard();
	}

	public String[][] getDisplayBoard() {
		return displayBoard;
	}

	private String[][] copyDisplayBoard(String[][] board) {

		String[][] copyBoard = new String[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				copyBoard[i][j] = board[i][j];
			}
		}

		return copyBoard;
	}

	private void executeMoveOnDisplayBoard() {
		String firstPiece = displayBoard[(int) firstPieceOrigin.getX()][(int) firstPieceOrigin.getY()];
		if (isPromotion()) {
			String color = firstPiece.substring(0, 1);
			displayBoard[(int) firstPieceTarget.getX()][(int) firstPieceTarget.getY()] = color + promotionType;
		} else {
			displayBoard[(int) firstPieceTarget.getX()][(int) firstPieceTarget.getY()] = firstPiece;
		}
		displayBoard[(int) firstPieceOrigin.getX()][(int) firstPieceOrigin.getY()] = "";
		if (secondPieceOrigin != null) {
			displayBoard[(int) secondPieceTarget.getX()][(int) secondPieceTarget
					.getY()] = displayBoard[(int) firstPieceOrigin.getX()][(int) firstPieceOrigin.getY()];
			displayBoard[(int) secondPieceOrigin.getX()][(int) secondPieceOrigin.getY()] = "";
		}
	}

	public String toString() {
		return "Piece 1: " + firstPieceOrigin.toString() + " to " + firstPieceTarget.toString() + "\nPromotion: "
				+ (promotionType == '\0' ? "N/A" : promotionType) + "\nPiece 2: "
				+ (secondPieceOrigin == null ? "N/A" : secondPieceOrigin.toString() + " to ")
				+ (secondPieceOrigin == null ? "" : secondPieceTarget.toString());
	}

}
