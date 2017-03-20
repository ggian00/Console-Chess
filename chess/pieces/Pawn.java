package pieces;

import java.awt.Point;

public class Pawn extends Piece {

	public Pawn(char color, Point location) {
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

		// Pawn can move two down/up if not moved yet
		// One down/up all the time
		// And can capture in any down/up diagonal
		// down/up is dependent upon color
		int orientation = (this.getColor() == 'w' ? 1 : -1); // sets up down
																// mobility 1
																// for up, -1
																// for down

		int currX = this.location.x;
		int currY = this.location.y;

		// sets two possible caputures
		mobility[currX + 1][currY + orientation] = 2;
		mobility[currX - 1][currY + orientation] = 2;

		// Two possible forward moves (1 fwd or 2 fwd)

		mobility[currX][currY + orientation] = 1;
		// is piece still in starting row
		if (getColor() == 'w') {
			if (currY < 6) {
				mobility[currX][currY + orientation * 2] = (currY == 1) ? 1 : 0;
			}
		} else {
			if (currY > 1) {
				mobility[currX][currY + orientation * 2] = (currY == 6) ? 1 : 0;
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
		return super.toString() + 'p';
	}
}
