package pieces;

public class Bishop extends Piece {

	Bishop(char color) {
		super(color);
	}

	public String toString() {
		return super.toString() + 'B';
	}
}
