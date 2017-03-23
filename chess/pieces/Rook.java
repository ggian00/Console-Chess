package pieces;

import java.awt.Point;

/**
 * @author      David Parsons
 * @author      Phil Plucinski
 */
public class Rook extends Piece {

	/**
	 * Constructor.
	 * 
	 * @param color piece color
	 * @param location intial location
	 */
	public boolean hasMoved = false;

	public Rook(char color, Point location) {
		super(color, location);
	}
	
	
	/**
	 * Returns a int array that representing the mobility of the piece
	 * or positions that the piece could move
	 * 0s, 1s, 2s, in each position
	 * 0 - not accessible
	 * 1 - only for move
	 * 2 - capture (implies move)
	 *
	 * @return mobility of game piece.
	 */
	@Override
	public int[][] getMobility() {
		int mobility[][] = new int[8][8];

		int currX = this.location.x;
		int currY = this.location.y;

		for (int y = 0; y < 8; y++) {
			mobility[currX][y] = 2;
		}
		for (int x = 0; x < 8; x++) {
			mobility[x][currY] = 2;
		}

		mobility[currX][currY] = 0;

		return mobility;
	}

	/**
	 * Returns the string representation of a rook to be used in the ascii board
	 * representation.
	 *
	 * @return the ascii representation of a rook.
	 */
	public String toString() {
		return super.toString() + 'R';
	}

	public void move(Point p) {
		super.move(p);
		if (!(hasMoved)) {
			hasMoved = true;
		}
	}

	public Piece copy() {
		return new Rook(color, new Point(location.x, location.y));
	}

}
