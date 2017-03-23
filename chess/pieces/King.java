/**
 * @author      David Parsons
 * @author      Phil Plucinski
 */
package pieces;

import java.awt.Point;

public class King extends Piece {

	public boolean hasMoved = false;

	public King(char color, Point location) {
		super(color, location);
	}

	/**
	 * Returns a int array that representing the mobility of the piece Copy 0 1
	 * 2 notes from doc
	 *
	 * @return mobility of game piece.
	 */
	public int[][] getMobility() {

		/*
		 * note that this currently ignore array out of bounds exceptions
		 */

		int mobility[][] = new int[8][8];

		// King can move/capture one in any direction

		int currX = this.location.x;
		int currY = this.location.y;

		// set three col right
		if (currX + 1 < 8) {
			if (currY + 1 < 8) {
				mobility[currX + 1][currY + 1] = 2;
			}
			mobility[currX + 1][currY] = 2;
			if (currY - 1 >= 0) {
				mobility[currX + 1][currY - 1] = 2;
			}
		}

		// set two in col
		if (currY + 1 < 8) {
			mobility[currX][currY + 1] = 2;
		}
		if (currY - 1 >= 0) {
			mobility[currX][currY - 1] = 2;
		}

		// set three col left
		if (currX - 1 >= 0) {
			if (currY + 1 < 8) {
				mobility[currX - 1][currY + 1] = 2;
			}
			mobility[currX - 1][currY] = 2;
			if (currY - 1 >= 0) {
				mobility[currX - 1][currY - 1] = 2;
			}
		}

		return mobility;
	}

	/**
	 * Returns the string representation of a pawn to be used in the ascii board
	 * representation.
	 *
	 * @return the ascii representation of a pawn.
	 */
	public String toString() {
		return super.toString() + 'K';
	}

	public void move(Point p) {
		super.move(p);
		if (!(hasMoved)) {
			hasMoved = true;
		}
	}

	public Piece copy() {
		return new King(color, new Point(location.x, location.y));
	}

}
