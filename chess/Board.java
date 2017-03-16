import java.awt.Point;

import pieces.*;

public class Board {
	
	Piece[][] board = new Piece[8][8];
	private char turn = 'w';
	
	Board(){
		
	}
	
	public boolean movePiece(Point piecePos, Point targetPos){
		
		Piece selected = board[piecePos.x][piecePos.y];

		if(selected == null){ // Not a valid piece
			return false;
		}
		
		if(selected.getColor() != this.turn){ // Current Player can't move that piece
			return false;
		}
		
		Piece targetPiece = board[targetPos.x][targetPos.y];
		
		// Determines Required mobility to access position
		int requiredMobility = 1;
		
		if(targetPiece != null){ // is the target space empty?
			if(targetPiece.getColor() == selected.getColor()){ // is team mate?
				return false; // can't move to where a team mate is
			} else {
				requiredMobility = 2; // must capture that piece
			}
		} 
		
		
		// Gets pieces mobility
		int selectedMobility[][] = selected.getMobility();
		
		if(selectedMobility[targetPos.x][targetPos.y] != requiredMobility){ // might be able to do this in a better way
			return false; // Mobilities don't match up
		}
		
		// Target is in mobility, no must check path to reach it
		if((selected instanceof King) || (selected instanceof Knight)){ // No path to check
			
		} else if(! isPathClear(piecePos, targetPos)){ // otherwise check path
			return false; // pieces in the way
		}
		
		// Creates Temp Board with move executed.
		Piece tmpBoard[][] = this.board;
		tmpBoard[piecePos.x][piecePos.y] = null;
		tmpBoard[targetPos.x][targetPos.y] = selected;
		
		// Turns out you don't have to check for check, just make sure the piece is valid
		
//		if(selected.getColor() == inCheck(tmpBoard)){ // can't put yourself in check
//			return false;
//		}
		
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

	private boolean isPathClear(Point origin, Point target){
		if(origin.equals(target)){
			return true; // Maybe make this false...this should never happen
		}
		
		if(origin.x == target.x){ // check vertical path
			
			int orientation = ((origin.y > target.y) ? -1 : 1 );
			int currY = origin.y + orientation;
			while(currY != target.y){
				if(this.board[origin.x][currY] != null){ // every piece between must be clear
					return false;
				}
				currY += orientation;
			}
			
		} else if (origin.y == target.y) { // check horizontal path
			
			int orientation = ((origin.x > target.x) ? -1 : 1 );
			int currX = origin.x + orientation;
			while(currX != target.x){
				if(this.board[currX][origin.y] != null){ // every piece between must be clear
					return false;
				}
				currX += orientation;
			}
			
		} else if ((origin.x - target.x) == (origin.y - target.y)){ // check diagonal of slope 1
			
			int orientation = ((origin.x > target.x) ? -1 : 1 );
			int curr = origin.x + orientation;
			while(curr != target.x){
				if(this.board[curr][curr] != null){ // every piece between must be clear
					return false;
				}
				curr += orientation;
			}
			
			
		} else {
			return false; // Not a valid move, perhaps a knight snuck in here
		}
		
		return true;
	}
	
	private void toggleTurn(){
		turn = (turn == 'w' ? 'b' : 'w');
	}
	
	public static char inCheck(Piece[][] board){
		return ' ';
	}
	
	public static boolean inStalemate(Piece[][] board){
		return false;
	}
	
}
