package pieces;

public class King extends Piece {
	
	King(char color){
		super(color);
	}
	

	/**
	 * Returns a int array that representing the mobility of the piece
	 * Copy 0 1 2 notes from doc
	 *
	 * @return mobility of game piece.
	 */
	public int[][] getMobility(){
		
		/*
		 * note that this currently ignore array out of bounds exceptions
		 */
		
		int mobility[][] = new int[8][8];
		
		// King can move/capture one in any direction
		
		int currX = this.location.x;
		int currY = this.location.y;
		
		// set three row above
		mobility[currX + 1][currY + 1] = 2;
		mobility[currX][currY + 1] = 2;
		mobility[currX - 1][currY + 1] = 2;
		
		// set two in same row
		mobility[currX + 1][currY] = 2;
		mobility[currX - 1][currY] = 2;
		
		// set three in row below
		mobility[currX + 1][currY - 1] = 2;
		mobility[currX][currY - 1] = 2;
		mobility[currX - 1][currY - 1] = 2;
		
		return mobility;
	}
	
	
	/**
	 * Returns the string representation of a pawn to 
	 * be used in the ascii board representation.
	 *
	 * @return the ascii representation of a pawn.
	 */
	public String toString(){
		return super.toString() + 'K';
	}
	
}
