package pieces;

import java.awt.Point;

public class Bishop extends Piece {

	public Bishop(char color, Point location) {
		super(color, location);
	}

	public String toString() {
		return super.toString() + 'B';
	}

	@Override
	public int[][] getMobility() {
		int mobility[][] = new int[8][8];

		int currX = this.location.x;
		int currY = this.location.y;

		for (int x = 0; x < 8; x++) {
			if (currY - currX + x >= 0 && currY - currX + x < 8) {
				mobility[x][currY - currX + x] = 2;
			}
			if (currY + currX - x >= 0 && currY + currX - x < 8) {
				mobility[x][currY + currX - x] = 2;
			}
		}

		mobility[currX][currY] = 0;

		return mobility;
	}

	public void move(Point p) {
		super.move(p);
	}

	public Piece copy() {
		return new Bishop(color, new Point(location.x, location.y));
	}
}
