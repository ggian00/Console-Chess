package pieces;

import java.awt.Point;

public class Rook extends Piece {

	public boolean hasMoved = false;

	public Rook(char color, Point location) {
		super(color, location);
	}

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
