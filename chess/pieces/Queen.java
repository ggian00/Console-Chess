package pieces;

public class Queen extends Piece {

	Queen(char color) {
		super(color);
	}

	public String toString() {
		return super.toString() + 'Q';
	}

}
