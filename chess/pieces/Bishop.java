package pieces;

public class Bishop extends Piece {

	public Bishop(char color) {
		super(color);
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
}
