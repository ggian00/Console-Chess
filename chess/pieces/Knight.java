package pieces;

import java.awt.Point;

public class Knight extends Piece {

	public Knight(char color, Point location) {
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

		if (currX + 2 <= 7) {
			if (currY + 1 <= 7) {
				mobility[currX + 2][currY + 1] = 2;
			}
			if (currY - 1 >= 0) {
				mobility[currX + 2][currY - 1] = 2;
			}
		}

		if (currX + 1 <= 7) {
			if (currY + 2 <= 7) {
				mobility[currX + 1][currY + 2] = 2;
			}
			if (currY - 2 >= 0) {
				mobility[currX + 1][currY - 2] = 2;
			}
		}

		if (currX - 1 >= 0) {
			if (currY + 2 <= 7) {
				mobility[currX - 1][currY + 2] = 2;
			}
			if (currY - 2 >= 0) {
				mobility[currX - 1][currY - 2] = 2;
			}
		}

		if (currX - 2 >= 0) {
			if (currY + 1 <= 7) {
				mobility[currX - 2][currY + 1] = 2;
			}
			if (currY - 1 >= 0) {
				mobility[currX - 2][currY - 1] = 2;
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
		return super.toString() + 'N';
	}

}
