import java.awt.Point;

import pieces.King;
import pieces.Knight;
import pieces.Piece;

public class Board {

	Piece[][] board = new Piece[8][8];
	private char turn = 'w';

	Board() {

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

	private boolean isPathClear(Point origin, Point target) {
		if (origin.equals(target)) {
			return true; // Maybe make this false...this should never happen
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

	private void toggleTurn() {
		turn = (turn == 'w' ? 'b' : 'w');
	}

	public static char inCheck(Piece[][] board) {
		return ' ';
	}

	public static boolean inStalemate(Piece[][] board) {
		return false;
	}

}
