package pieces;

import java.awt.Point;

public class Queen extends Piece {

	public Queen(char color, Point location) {
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

	public String toString() {
		return super.toString() + 'Q';
	}

}
