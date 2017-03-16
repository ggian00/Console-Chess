import java.awt.Point;

import pieces.*;

public class Board {
	Piece[][] board = new Piece[8][8];
	
	Board(){
		
	}
	
	public boolean movePiece(Point piecePos, Point targetPos){
		
		Piece selected = board[piecePos.x][piecePos.y];

		if(selected == null){ // Not a valid piece
			return false;
		}
		
		Piece targetPiece = board[targetPos.x][targetPos.y];
		
		// Determines Required mobility to access position
		int requiredMobility = 0;
		
		if(targetPiece != null){ // is the target space empty?
			if(targetPiece.getColor() == selected.getColor()){ // is team mate?
				return false; // can't move to where a team mate is
			} else {
				requiredMobility = 1; // must capture that piece
			}
		}
		
		
		// Gets pieces mobility
		int selectedMobility[][] = selected.getMobility();
		
		if(selectedMobility[targetPos.x][targetPos.y] != requiredMobility){ // might be able to do this in a better way
			return false; // Mobilities don't match up
		}
		
		// Target is in mobility, no must check path to reach it
		if((selected instanceof King) || (selected instanceof Knight)){ // No path to check
			
		} else { // otherwise check path
			// TODO....
			// TODO....
			// TODO....
			// TODO....
			// TODO....
		}
		
		// Creates Temp Board with move executed.
		Piece tmpBoard[][] = this.board;
		tmpBoard[piecePos.x][piecePos.y] = null;
		tmpBoard[targetPos.x][targetPos.y] = selected;
		
		if(selected.getColor() == inCheck(tmpBoard)){ // can't put yourself in check
			return false;
		}
		
		// if nothing else prevents this from being a valid move, make it and return success
		
		this.board = tmpBoard; //should be ok, assuming the creation of tmpBoard is ok
		
		return true;
	}
	
	/**
	 * Description...
	 *
	 * @param ...
	 * @return .....
	 */
	//needs to be fixed to print in correct way
	public String toString(){
		String output = "";
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
				if(board[x][y] == null){
					if((x % 2) == 0){ //even row
						if((y % 2) == 0){ // even row, even col
							output += "  ";
						} else {
							output += "##";
						}
					} else {
						if((y % 2) == 0){ // odd row, even col
							output += "##";
						} else {
							output += "  ";
						}
					}
				} else {
					output += board[x][y];
				}
				output += " ";
			}
			output += '\n';
		}
		return output;
	}
	
	public static char inCheck(Piece[][] board){
		return ' ';
	}
	
	public static boolean inStalemate(Piece[][] board){
		return false;
	}
}
