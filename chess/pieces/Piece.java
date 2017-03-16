package pieces;
import java.awt.Point;

public abstract class Piece {

	private char color;
	Point location;
	
	Piece(char color){
		if(color != 'w' || color != 'b'){
			// throw exception
			// error
		}
		this.color = color;
	}
	
	/**
	 * Returns a int array that representing the mobility of the piece
	 * Copy 0 1 2 notes from doc
	 *
	 * @return mobility of game piece.
	 */
	public abstract int[][] getMobility();
	
	/**
	 * Returns the string representation of the chess game piece to 
	 * be used in the ascii board representation.
	 *
	 * @return the ascii representation of the game piece
	 */
	public String toString(){
		return color + "";
	}
	
	public char getColor(){
		return this.color;
	}
}
