 /**
 * @author      David Parsons
 * @author      Phil Plucinski
 */
package pieces;

import java.awt.Point;

public abstract class Piece {

	protected char color;
	public Point location;

	Piece(char color, Point location) {
		if (color != 'w' || color != 'b') {
			// throw exception
			// error
		}
		if (location.getX() > 7 && location.getX() < 0 && location.getY() > 7 && location.getY() < 0) {
			// throw exception
			// error
		}
		this.color = color;
		this.location = location;
	}

	/**
	 * Returns a int array that representing the mobility of the piece Copy 0 1
	 * 2 notes from doc
	 *
	 * @return mobility of game piece.
	 */
	public abstract int[][] getMobility();

	public void move(Point p) {
		location = p;
	}

	public abstract Piece copy();

	/**
	 * Returns the string representation of the chess game piece to be used in
	 * the ascii board representation.
	 *
	 * @return the ascii representation of the game piece
	 */
	public String toString() {
		return color + "";
	}

	public char getColor() {
		return this.color;
	}
}
