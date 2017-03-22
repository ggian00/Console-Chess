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

	ArrayList<Pawn> enpassantPawns = new ArrayList<Pawn>();

	private char turn = 'w';

	Board() {
		initializeBoard();
	}

	/***
	 * public boolean movePiece(Point piecePos, Point targetPos) {
	 * 
	 * /////////////////////////////////////////////////// // Won't this cause
	 * an ArrayOutOfBoundsExe ? // // I think we should either manually check
	 * bounds// // Or put this in a try-catch //
	 * /////////////////////////////////////////////////// Piece selected =
	 * board[piecePos.x][piecePos.y];
	 * 
	 * if (selected == null) { // Not a valid piece return false; }
	 * 
	 * if (selected.getColor() != this.turn) { // Current Player can't move //
	 * that piece return false; }
	 * 
	 * /////////////////////////////////////////////////// // Needs bounds
	 * checking // /////////////////////////////////////////////////// Piece
	 * targetPiece = board[targetPos.x][targetPos.y];
	 * 
	 * // Determines Required mobility to access position // 1 = blank space. 2
	 * = capture int requiredMobility = 1;
	 * 
	 * if (targetPiece != null) { // is the target space empty? if
	 * (targetPiece.getColor() == selected.getColor()) { // is team // mate?
	 * return false; // can't move to where a team mate is } else {
	 * requiredMobility = 2; // must capture that piece } }
	 * 
	 * // Gets piece's mobility int selectedMobility[][] =
	 * selected.getMobility();
	 * 
	 * // might be able to do this a better way if
	 * (selectedMobility[targetPos.x][targetPos.y] != requiredMobility) { return
	 * false; // Mobilities don't match up }
	 * 
	 * // Target is in mobility, no must check path to reach it if ((selected
	 * instanceof King) || (selected instanceof Knight)) { // No // path // to
	 * // check
	 * 
	 * } else if (!isPathClear(board, piecePos, targetPos)) { // otherwise //
	 * check path return false; // pieces in the way }
	 * 
	 * // Creates Temp Board with move executed. Piece tmpBoard[][] =
	 * this.board; tmpBoard[piecePos.x][piecePos.y] = null;
	 * tmpBoard[targetPos.x][targetPos.y] = selected;
	 * 
	 * // Turns out you don't have to check for check, just make sure the piece
	 * // is valid
	 * 
	 * // if(selected.getColor() == inCheck(tmpBoard)){ // can't put yourself //
	 * in check // return false; // }
	 * 
	 * // if nothing else prevents this from being a valid move, make it and //
	 * return success
	 * 
	 * this.board = tmpBoard; // should be ok, assuming the creation of //
	 * tmpBoard is ok
	 * 
	 * return true; }
	 ***/

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
				if (board[col][row] == null) {
					if ((row % 2) == 0 ^ (col % 2) == 0) { // Different parities
						sb.append("  ");
					} else { // Same parity
						sb.append("##");
					}
					// Piece
				} else {
					sb.append(board[col][row]);
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

	private boolean isPathClear(Piece[][] board, Point origin, Point target) {
		if (origin.equals(target)) {
			return false;
		}

		if (origin.x == target.x) { // check vertical path

			int orientation = ((origin.y > target.y) ? -1 : 1);
			int currY = origin.y + orientation;
			while (currY != target.y) {
				if (board[origin.x][currY] != null) { // every piece
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
				if (board[currX][origin.y] != null) { // every piece
														// between must be
														// clear
					return false;
				}
				currX += orientation;
			}

		} else if (Math.abs((origin.x - target.x)) == Math.abs((origin.y - target.y))) { // check
			// diagonal
			// of
			// slope
			// 1

			int orientationX = ((origin.x > target.x) ? -1 : 1);
			int currX = origin.x + orientationX;
			int orientationY = ((origin.y > target.y) ? -1 : 1);
			int currY = origin.y + orientationY;
			while (currX != target.x && currY != target.y) {
				if (this.board[currX][currY] != null) { // every piece between
														// must be clear
					return false;
				}
				currX += orientationX;
				currY += orientationY;
			}

		} else {
			if (!(board[origin.x][origin.y] instanceof Knight)) {
				return false;
			}
		}

		if (board[target.x][target.y] != null
				&& board[origin.x][origin.y].getColor() == board[target.x][target.y].getColor()) {
			return false;
		}

		return true;
	}

	public char getTurn() {
		return turn;
	}

	public void toggleTurn() {
		turn = (turn == 'w' ? 'b' : 'w');
		ArrayList<Pawn> pawnsToRemove = new ArrayList<Pawn>();
		for (Pawn p : enpassantPawns) {
			p.turnsSinceSpecialMove++;
			if (p.turnsSinceSpecialMove >= 2) {
				pawnsToRemove.add(p);
			}
		}
		for (Pawn p : pawnsToRemove) {
			enpassantPawns.remove(p);
		}
	}

	/**
	 * Is the specified color in check?
	 * 
	 * @param color
	 * @param board
	 * @param whitePieces
	 * @param blackPieces
	 * @return
	 */
	public boolean inCheck(char color, Piece[][] board, ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces) {

		Piece king = getKing((color == 'w' ? whitePieces : blackPieces));
		int kingX = (int) king.location.getX();
		int kingY = (int) king.location.getY();
		// Want to see if opponent pieces can attack king
		ArrayList<Piece> searchList = (color == 'w' ? blackPieces : whitePieces);

		for (Piece p : searchList) {
			if ((p.getMobility()[kingX][kingY] == 2) && (isPathClear(board, p.location, king.location))) {

				return true;
			}
		}

		return false;
	}

	private Piece getKing(ArrayList<Piece> searchList) {
		for (Piece p : searchList) {
			if (p instanceof King) {
				return p;
			}
		}
		return null;
	}

	private boolean existValidMoves(char color) {
		ArrayList<Piece> searchList = (color == 'b' ? blackPieces : whitePieces);

		for (Piece p : searchList) {
			int[][] mobility = p.getMobility();
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if (mobility[x][y] != 0 && isPathClear(board, p.location, new Point(x, y))) {
						// Check if said move will cause check.
						Piece[][] vBoard = new Piece[8][8];
						ArrayList<Piece> vWhitePieces = new ArrayList<Piece>(16);
						ArrayList<Piece> vBlackPieces = new ArrayList<Piece>(16);

						saveStateToVirtualStorage(vBoard, vWhitePieces, vBlackPieces);
						executeMove(vBoard, vWhitePieces, vBlackPieces, p.location, new Point(x, y), ' ');
						if (inCheck(color, vBoard, vWhitePieces, vBlackPieces)) {
							continue;
						}

						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean pawnIsEnpassant(Pawn p) {
		for (Pawn q : enpassantPawns) {
			if (p.equals(q)) {
				return true;
			}
		}
		return false;
	}

	private boolean verifyBounds(Point origin, Point target) {
		if (origin.x < 0 || origin.y < 0 || origin.x > 7 || origin.y > 7) {
			return false;
		}
		if (target.x < 0 || target.y < 0 || target.x > 7 || target.y > 7) {
			return false;
		}
		return true;
	}

	public boolean executeMove(Piece[][] board, ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces,
			Point origin, Point target, char promotion) {

		Piece[][] vBoard = new Piece[8][8];
		ArrayList<Piece> vWhitePieces = new ArrayList<Piece>(16);
		ArrayList<Piece> vBlackPieces = new ArrayList<Piece>(16);

		// Check bounds of origin and target
		if (!verifyBounds(origin, target)) {
			return false;
		}

		// Check that piece exists and that it belongs to the current player
		if (board[origin.x][origin.y] == null || board[origin.x][origin.y].getColor() != turn) {
			return false;
		}

		// Check that piece is not trying to move onto a friendly piece.
		if (board[target.x][target.y] != null
				&& board[origin.x][origin.y].getColor() == board[target.x][target.y].getColor()) {
			return false;
		}

		saveStateToVirtualStorage(vBoard, vWhitePieces, vBlackPieces);

		Piece p = vBoard[origin.x][origin.y];
		char color = p.getColor();

		// Check that piece is not trying to move onto another piece. (Ignore
		// special moves)
		if (p.getMobility()[target.x][target.y] == 1 && board[target.x][target.y] != null) {
			return false;
		}

		// Checking for/performing special moves first

		// Castling
		if (p instanceof King) {
			if (color == 'w' && origin.x == 4 && origin.y == 0 && target.x == 2 && target.y == 0) {
				// White Queen-side Castle attempt detected
				Piece rook = vBoard[0][0];
				if (!((King) p).hasMoved && !inCheck(color, board, whitePieces, blackPieces) && rook instanceof Rook
						&& !((Rook) rook).hasMoved && board[1][0] == null && board[2][0] == null
						&& board[3][0] == null) {
					// Check if king is to pass through attacked squares.
					for (Piece q : blackPieces) {
						for (int i = 1; i <= 3; i++) {
							if (q.getMobility()[i][0] == 2 && isPathClear(board, q.location, new Point(i, 0))) {
								return false;
							}
						}
					}
					// Castle eligible
					vBoard[2][0] = p;
					vBoard[3][0] = rook;
					vBoard[4][0] = null;
					vBoard[0][0] = null;
					p.move(new Point(2, 0));
					rook.move(new Point(3, 0));

					if (inCheck(color, vBoard, vWhitePieces, vBlackPieces)) {
						return false;
					}

					whitePieces.remove(board[origin.x][origin.y]);
					whitePieces.remove(board[0][0]);
					board[2][0] = p;
					board[3][0] = rook;
					board[4][0] = null;
					board[0][0] = null;
					whitePieces.add(p);
					whitePieces.add(rook);

					return true;

				} else {
					return false;
				}
			} else if (color == 'w' && origin.x == 4 && origin.y == 0 && target.x == 6 && target.y == 0) {
				// White King-side Castle attempt detected
				Piece rook = board[7][0];
				if (!((King) p).hasMoved && !inCheck(color, board, whitePieces, blackPieces) && rook instanceof Rook
						&& !((Rook) rook).hasMoved && board[5][0] == null && board[6][0] == null) {
					// Check if king is to pass through attacked squares.
					for (Piece q : blackPieces) {
						for (int i = 5; i <= 6; i++) {
							if (q.getMobility()[i][0] == 2 && isPathClear(board, q.location, new Point(i, 0))) {
								return false;
							}
						}
					}
					// Castle eligible
					vBoard[6][0] = p;
					vBoard[5][0] = rook;
					vBoard[4][0] = null;
					vBoard[7][0] = null;
					p.move(new Point(6, 0));
					rook.move(new Point(5, 0));

					if (inCheck(color, vBoard, vWhitePieces, vBlackPieces)) {
						return false;
					}

					whitePieces.remove(board[origin.x][origin.y]);
					whitePieces.remove(board[7][0]);
					board[6][0] = p;
					board[5][0] = rook;
					board[4][0] = null;
					board[7][0] = null;
					whitePieces.add(p);
					whitePieces.add(rook);

					return true;
				} else {
					return false;
				}
			} else if (color == 'b' && origin.x == 4 && origin.y == 7 && target.x == 2 && target.y == 7) {
				// Black Queen-side Castle attempt detected
				Piece rook = board[0][7];
				if (!((King) p).hasMoved && !inCheck(color, board, whitePieces, blackPieces) && rook instanceof Rook
						&& !((Rook) rook).hasMoved && board[1][7] == null && board[2][7] == null
						&& board[3][7] == null) {
					// Check if king is to pass through attacked squares.
					for (Piece q : whitePieces) {
						for (int i = 1; i <= 3; i++) {
							if (q.getMobility()[i][7] == 2 && isPathClear(board, q.location, new Point(i, 7))) {
								return false;
							}
						}
					}
					// Castle eligible
					vBoard[2][7] = p;
					vBoard[3][7] = rook;
					vBoard[4][7] = null;
					vBoard[0][7] = null;
					p.move(new Point(2, 7));
					rook.move(new Point(3, 7));

					if (inCheck(color, vBoard, vWhitePieces, vBlackPieces)) {
						return false;
					}

					blackPieces.remove(board[origin.x][origin.y]);
					blackPieces.remove(board[0][7]);
					board[2][7] = p;
					board[3][7] = rook;
					board[4][7] = null;
					board[0][7] = null;
					blackPieces.add(p);
					blackPieces.add(rook);

					return true;
				} else {
					return false;
				}
			} else if (color == 'b' && origin.x == 4 && origin.y == 7 && target.x == 6 && target.y == 7) {
				// Black King-side Castle attempt detected
				Piece rook = board[7][7];
				if (!((King) p).hasMoved && !inCheck(color, board, whitePieces, blackPieces) && rook instanceof Rook
						&& !((Rook) rook).hasMoved && board[5][7] == null && board[6][7] == null) {
					// Check if king is to pass through attacked squares.
					for (Piece q : whitePieces) {
						for (int i = 5; i <= 6; i++) {
							if (q.getMobility()[i][7] == 2 && isPathClear(board, q.location, new Point(i, 7))) {
								return false;
							}
						}
					}
					// Castle eligible
					vBoard[6][7] = p;
					vBoard[5][7] = rook;
					vBoard[4][7] = null;
					vBoard[7][7] = null;
					p.move(new Point(6, 7));
					rook.move(new Point(5, 7));

					if (inCheck(color, vBoard, vWhitePieces, vBlackPieces)) {
						return false;
					}

					blackPieces.remove(board[origin.x][origin.y]);
					blackPieces.remove(board[7][7]);
					board[6][7] = p;
					board[5][7] = rook;
					board[4][7] = null;
					board[7][7] = null;
					blackPieces.add(p);
					blackPieces.add(rook);

					return true;
				} else {
					return false;
				}
			}
		}

		// MAY NEED TO CONFIRM PROMOTION CHAR
		// HAVE TO CONFIRM PERSISTENCE OF PAWNS WITH EN PASSANT
		// Enpassant/Pawn Special Move
		if (p instanceof Pawn) {
			// EP
			if (color == 'w' && p.location.y == 4 && board[target.x][target.y] == null) {
				if (board[target.x][4] instanceof Pawn && pawnIsEnpassant(new Pawn('b', new Point(target.x, 4)))) {

					vBoard[origin.x][origin.y] = null;
					vBoard[target.x][target.y] = p;
					p.move(new Point(target.x, target.y));

					if (inCheck(color, vBoard, vWhitePieces, vBlackPieces)) {
						return false;
					}

					blackPieces.remove(board[target.x][4]);
					whitePieces.remove(board[origin.x][origin.y]);
					board[origin.x][origin.y] = null;
					board[target.x][4] = null;
					board[target.x][target.y] = p;
					whitePieces.add(p);

					return true;
				} else {
					return false;
				}
			} else if (color == 'b' && p.location.y == 3 && board[target.x][target.y] == null) {
				if (board[target.x][3] instanceof Pawn && pawnIsEnpassant(new Pawn('w', new Point(target.x, 3)))) {
					vBoard[origin.x][origin.y] = null;
					vBoard[target.x][target.y] = p;
					p.move(new Point(target.x, target.y));

					if (inCheck(color, vBoard, vWhitePieces, vBlackPieces)) {
						return false;
					}

					whitePieces.remove(board[target.x][3]);
					blackPieces.remove(board[origin.x][origin.y]);
					board[origin.x][origin.y] = null;
					board[target.x][3] = null;
					board[target.x][target.y] = p;
					blackPieces.add(p);

					return true;
				} else {
					return false;
				}
				// Special Move
			} else if (Math.abs(origin.y - target.y) == 2) {
				if ((color == 'w' && origin.y == 1)
						|| (color == 'b' && origin.y == 6) && isPathClear(board, origin, target)) {
					enpassantPawns.add((Pawn) p);
				} else {
					return false;
				}
				// Promotion
			} else if (color == 'w' && target.y == 7) {
				Piece temp = p;
				vWhitePieces.remove(p);
				p = promote(promotion, temp);
				vWhitePieces.add(p);

				vBoard[origin.x][origin.y] = null;
				vBoard[target.x][target.y] = p;
				p.move(new Point(target.x, target.y));

				if (inCheck(color, vBoard, vWhitePieces, vBlackPieces)) {
					return false;
				}

				whitePieces.remove(board[origin.x][origin.y]);
				board[origin.x][origin.y] = null;
				board[target.x][target.y] = p;
				whitePieces.add(p);

				return true;

			} else if (color == 'b' && target.y == 0) {
				Piece temp = p;
				vBlackPieces.remove(p);
				p = promote(promotion, temp);
				vBlackPieces.add(p);

				vBoard[origin.x][origin.y] = null;
				vBoard[target.x][target.y] = p;
				p.move(new Point(target.x, target.y));

				if (inCheck(color, vBoard, vWhitePieces, vBlackPieces)) {
					return false;
				}

				blackPieces.remove(board[origin.x][origin.y]);
				board[origin.x][origin.y] = null;
				board[target.x][target.y] = p;
				blackPieces.add(p);

				return true;
			}
		}

		// All Special Cases handled.

		// Piece must be allowed to move to target.
		if (p.getMobility()[target.x][target.y] == 0) {
			return false;
		}

		// Path must be clear.
		if (!isPathClear(board, origin, target)) {
			return false;
		}

		vBoard[origin.x][origin.y] = null;
		vBoard[target.x][target.y] = p;
		p.move(new Point(target.x, target.y));

		if (inCheck(color, vBoard, vWhitePieces, vBlackPieces)) {
			if (p instanceof Pawn && enpassantPawns.contains((Pawn) p)) {
				enpassantPawns.remove((Pawn) p);
			}
			return false;
		}

		if (board[target.x][target.y] != null) {
			(color == 'b' ? whitePieces : blackPieces).remove(board[target.x][target.y]);
		}

		(color == 'w' ? whitePieces : blackPieces).remove(board[origin.x][origin.y]);
		(color == 'w' ? whitePieces : blackPieces).add(p);
		board[origin.x][origin.y] = null;
		board[target.x][target.y] = p;

		return true;
	}

	private Piece promote(char promotion, Piece pawn) {
		Piece promotedPiece = null;
		switch (promotion) {
		case 'Q':
			promotedPiece = new Queen(pawn.getColor(), pawn.location);
			break;
		case 'N':
			promotedPiece = new Knight(pawn.getColor(), pawn.location);
			break;
		case 'R':
			promotedPiece = new Rook(pawn.getColor(), pawn.location);
			break;
		case 'B':
			promotedPiece = new Bishop(pawn.getColor(), pawn.location);
			break;
		}
		return promotedPiece;
	}

	private void saveStateToVirtualStorage(Piece[][] vBoard, ArrayList<Piece> vWhitePieces,
			ArrayList<Piece> vBlackPieces) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (board[x][y] != null) {
					vBoard[x][y] = board[x][y].copy();
					if (vBoard[x][y].getColor() == 'w') {
						vWhitePieces.add(vBoard[x][y]);
					} else {
						vBlackPieces.add(vBoard[x][y]);
					}
				}
			}
		}
	}

	public boolean checkMate() {
		if (inCheck('w', board, whitePieces, blackPieces) && !inCheck('b', board, whitePieces, blackPieces)
				&& !existValidMoves('w') && !existValidMoves('b')) {
			return true;
		}
		return false;
	}

	public boolean inStalemate() {
		if (!inCheck('w', board, whitePieces, blackPieces) && !inCheck('b', board, whitePieces, blackPieces)
				&& !existValidMoves('w') && !existValidMoves('b')) {
			return true;
		}
		return false;
	}

	// Interface with match logic that allows the implementer to determine if
	// the match can continue
	public boolean matchCanContinue() {
		return true;
	}

	// Prints final state of the match
	public void printEndState() {
		System.out.println("The Match is over");
	}

	public void initializeBoard() {
		board[0][7] = new Rook('b', new Point(0, 7));
		board[1][7] = new Knight('b', new Point(1, 7));
		board[2][7] = new Bishop('b', new Point(2, 7));
		board[3][7] = new Queen('b', new Point(3, 7));
		board[4][7] = new King('b', new Point(4, 7));
		board[5][7] = new Bishop('b', new Point(5, 7));
		board[6][7] = new Knight('b', new Point(6, 7));
		board[7][7] = new Rook('b', new Point(7, 7));

		for (int i = 0; i < 8; i++) {
			board[i][6] = new Pawn('b', new Point(i, 6));
		}

		for (int y = 7; y >= 6; y--) {
			for (int x = 0; x < 8; x++) {
				blackPieces.add(board[x][y]);
			}
		}

		for (int i = 0; i < 8; i++) {
			board[i][1] = new Pawn('w', new Point(i, 1));
		}

		board[0][0] = new Rook('w', new Point(0, 0));
		board[1][0] = new Knight('w', new Point(1, 0));
		board[2][0] = new Bishop('w', new Point(2, 0));
		board[3][0] = new Queen('w', new Point(3, 0));
		board[4][0] = new King('w', new Point(4, 0));
		board[5][0] = new Bishop('w', new Point(5, 0));
		board[6][0] = new Knight('w', new Point(6, 0));
		board[7][0] = new Rook('w', new Point(7, 0));

		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 8; x++) {
				whitePieces.add(board[x][y]);
			}
		}
	}

}
