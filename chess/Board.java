import java.awt.Point;
import java.util.ArrayList;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class Board {

	Piece[][] board = new Piece[8][8];
	ArrayList<Piece> whitePieces = new ArrayList<Piece>(16);
	ArrayList<Piece> blackPieces = new ArrayList<Piece>(16);
	private char turn = 'w';

	Board() {
		initializeBoard();
	}

	public boolean movePiece(Point piecePos, Point targetPos) {

		///////////////////////////////////////////////////
		// Won't this cause an ArrayOutOfBoundsExe ? //
		// I think we should either manually check bounds//
		// Or put this in a try-catch //
		///////////////////////////////////////////////////
		Piece selected = board[piecePos.x][piecePos.y];

		if (selected == null) { // Not a valid piece
			return false;
		}

		if (selected.getColor() != this.turn) { // Current Player can't move
												// that piece
			return false;
		}

		///////////////////////////////////////////////////
		// Needs bounds checking //
		///////////////////////////////////////////////////
		Piece targetPiece = board[targetPos.x][targetPos.y];

		// Determines Required mobility to access position
		// 1 = blank space. 2 = capture
		int requiredMobility = 1;

		if (targetPiece != null) { // is the target space empty?
			if (targetPiece.getColor() == selected.getColor()) { // is team
																	// mate?
				return false; // can't move to where a team mate is
			} else {
				requiredMobility = 2; // must capture that piece
			}
		}

		// Gets piece's mobility
		int selectedMobility[][] = selected.getMobility();

		// might be able to do this a better way
		if (selectedMobility[targetPos.x][targetPos.y] != requiredMobility) {
			return false; // Mobilities don't match up
		}

		// Target is in mobility, no must check path to reach it
		if ((selected instanceof King) || (selected instanceof Knight)) { // No
																			// path
																			// to
																			// check

		} else if (!isPathClear(piecePos, targetPos)) { // otherwise check path
			return false; // pieces in the way
		}

		// Creates Temp Board with move executed.
		Piece tmpBoard[][] = this.board;
		tmpBoard[piecePos.x][piecePos.y] = null;
		tmpBoard[targetPos.x][targetPos.y] = selected;

		// Turns out you don't have to check for check, just make sure the piece
		// is valid

		// if(selected.getColor() == inCheck(tmpBoard)){ // can't put yourself
		// in check
		// return false;
		// }

		// if nothing else prevents this from being a valid move, make it and
		// return success

		this.board = tmpBoard; // should be ok, assuming the creation of
								// tmpBoard is ok

		return true;
	}

	/**
	 * Prints out the chess board. Empty spaces of the same parity are black
	 * (##), white ( ) otherwise. Non-empty space are represented by aA, where a
	 * is the color (w/b) and A is the piece (p = pawn, K = king, Q = queen, R =
	 * rook, B = bishop, N = knight).
	 *
	 * @return String representation of the chess board.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('\n');
		for (int row = 7; row >= 0; row--) {
			for (int col = 0; col < 8; col++) {
				// No Piece
				if (board[row][col] == null) {
					if ((row % 2) == 0 ^ (col % 2) == 0) { // Different parities
						sb.append("  ");
					} else { // Same parity
						sb.append("##");
					}
					// Piece
				} else {
					sb.append(board[row][col]);
				}
				sb.append(' ');
			}
			sb.append(row + 1);
			sb.append('\n');
		}
		for (int col = 0; col < 8; col++) {
			sb.append(" " + (char) ('a' + col) + " ");
		}
		sb.append('\n');
		return sb.toString();
	}

	
	/**
	 * Returns true if the piece at the origin location has the proper
	 * mobility and is no blocked by anything else in reaching the target location.
	 * Returns false if the piece can not legally reach the target
	 *
	 * @param origin location of the piece whose path is being checked
	 * @param target the ending location of the path the piece wants to follow
	 * @return boolean representing whether or not the path is clear for the given piece
	 */
	private boolean isPathClear(Point origin, Point target) {
		if (origin.equals(target)) {
			return false; // Maybe make this false...this should never happen
		}

		if (origin.x == target.x) { // check vertical path

			int orientation = ((origin.y > target.y) ? -1 : 1);
			int currY = origin.y + orientation;
			while (currY != target.y) {
				if (this.board[origin.x][currY] != null) { // every piece
															// between must be
															// clear
					return false;
				}
				currY += orientation;
			}

		} else if (origin.y == target.y) { // check horizontal path

			int orientation = ((origin.x > target.x) ? -1 : 1);
			int currX = origin.x + orientation;
			while (currX != target.x) {
				if (this.board[currX][origin.y] != null) { // every piece
															// between must be
															// clear
					return false;
				}
				currX += orientation;
			}

		} else if ((origin.x - target.x) == (origin.y - target.y)) { // check
																		// diagonal
																		// of
																		// slope
																		// 1

			int orientation = ((origin.x > target.x) ? -1 : 1);
			int curr = origin.x + orientation;
			while (curr != target.x) {
				if (this.board[curr][curr] != null) { // every piece between
														// must be clear
					return false;
				}
				curr += orientation;
			}

		} else {
			return false; // Not a valid move, perhaps a knight snuck in here
		}

		return true;
	}

	/**
	 * Toggles the who is allowed to perform valid moves.
	 * Sets turn to white if turn is black and vice versa
	 * 
	 */
	private void toggleTurn() {
		turn = (turn == 'w' ? 'b' : 'w');
	}

	public char inCheck() {
		
		// we need to determine how this is used.
		
		Piece whiteKing = getKing('w');
		int whiteKingX = (int) whiteKing.location.getX();
		int whiteKingY = (int) whiteKing.location.getY();
		for (Piece p : blackPieces) {
			if (!(p instanceof King) && (p.getMobility()[whiteKingX][whiteKingY] == 2)
					&& (isPathClear(p.location, whiteKing.location))) {

				return 'w';
			}
		}

		Piece blackKing = getKing('b');
		int blackKingX = (int) blackKing.location.getX();
		int blackKingY = (int) blackKing.location.getY();
		for (Piece p : whitePieces) {
			if (!(p instanceof King) && (p.getMobility()[blackKingX][blackKingY] == 2)
					&& (isPathClear(p.location, blackKing.location))) {

				return 'b';
			}
		}
		return ' ';
	}

	private Piece getKing(char color) {
		ArrayList<Piece> searchList = (color == 'b' ? blackPieces : whitePieces);
		for (Piece p : searchList) {
			if (p instanceof King) {
				return p;
			}
		}
		return null;
	}

	private boolean existValidMoves() {
		for (Piece p : whitePieces) {
			int[][] mobility = p.getMobility();
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if (mobility[x][y] != 0) {
						return true;
					}
				}
			}
		}
		for (Piece p : blackPieces) {
			int[][] mobility = p.getMobility();
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if (mobility[x][y] != 0) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean inStalemate(Piece[][] board) {
		if ((inCheck() == ' ') && !existValidMoves()) {
			return true;
		}
		return false;
	}

	private void initializeBoard() {
		board[7][0] = new Rook('b', new Point(7, 0));
		board[7][1] = new Knight('b', new Point(7, 1));
		board[7][2] = new Bishop('b', new Point(7, 2));
		board[7][3] = new Queen('b', new Point(7, 3));
		board[7][4] = new King('b', new Point(7, 4));
		board[7][5] = new Bishop('b', new Point(7, 5));
		board[7][6] = new Knight('b', new Point(7, 6));
		board[7][7] = new Rook('b', new Point(7, 7));

		for (int i = 0; i < 8; i++) {
			board[6][i] = new Pawn('b', new Point(6, i));
		}

		for (int x = 7; x >= 6; x--) {
			for (int y = 0; y < 8; y++) {
				blackPieces.add(board[x][y]);
			}
		}

		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn('w', new Point(1, i));
		}

		board[0][0] = new Rook('w', new Point(0, 0));
		board[0][1] = new Knight('w', new Point(0, 1));
		board[0][2] = new Bishop('w', new Point(0, 2));
		board[0][3] = new Queen('w', new Point(0, 3));
		board[0][4] = new King('w', new Point(0, 4));
		board[0][5] = new Bishop('w', new Point(0, 5));
		board[0][6] = new Knight('w', new Point(0, 6));
		board[0][7] = new Rook('w', new Point(0, 7));

		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 8; y++) {
				whitePieces.add(board[x][y]);
			}
		}
	}

}
