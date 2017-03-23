package control;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * 
 * Main Class
 * 
 * @author      David Parsons
 * @author      Phil Plucinski
 */
public class Chess {

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	public static Board b = null;
	public static boolean pendingDraw = true;
	public static Point origin = null;
	public static Point target = null;
	public static String end = "";
	public static char promotion = 'Q';
	
	static Scanner scan = null;
	static final boolean AUTO = false;

	/**
	 * Main
	 */
	public static void main(String args[]) {
		
		// AUTO
		try {
			scan = new Scanner(new File("ex1.txt"));
		} catch (Exception e) {
			System.out.println("Test File not found Error");
			return;
		}
		// END AUTO
		
		System.out.println("Welcome to Chess.");
		b = new Board();

		do{
			
			if(b.inCheck(b.getTurn(), b.board, b.whitePieces, b.blackPieces)){
				System.out.println("Check");
			}
			
			System.out.println(b);
			
			// AUTO
			if(AUTO){
				readMoveTest();
			} else {
			// END AUTO
				readMove();
			// AUTO
			}
			// END AUTO
			

			while (! b.executeMove(b.board, b.whitePieces, b.blackPieces, origin, target, promotion)){
				System.out.println("Illegal move, try again");
				readMove();
			}

			b.toggleTurn();
			
		} while(b.matchCanContinue());
		
		
	}
	

	/**
	 * Consumes input from console and updates static data fields
	 * accordingly in an attempt to prepare for move execution
	 *
	 */
	private static void readMove() {
		String input = "";

		System.out.print((b.getTurn() == 'w' ? "White's move: " : "Black's move: ")); // change tempTurn to b.getTurn()
		
		while (true) {
			try {
				input = reader.readLine();
				System.out.println("");
				break;
			} catch (IOException e) {
			}
		}
		
		if(input.equals("resign")){
			System.out.println((b.getTurn() == 'w' ? "Black wins" : "White wins")); // change tempTurn to b.getTurn()
			System.exit(0); // end match
		} else if (input.equals("draw") && pendingDraw) {
			System.out.println("Draw");
			System.exit(0); // draw was waiting and is now confirmed
		}
		
		String[] parts = input.split(" ");
		
		// (x,y)
		origin = new Point(parts[0].charAt(0) - 'a', parts[0].charAt(1) - '1');
		target = new Point(parts[1].charAt(0) - 'a', parts[1].charAt(1) - '1');
		
		promotion = 'Q';
		end = "";
		
		if(parts.length > 2){
			end = parts[parts.length - 1];
			promotion = parts[2].charAt(0);
		}
		
		pendingDraw = end.equals("draw?");
	}
	
	private static void readMoveTest() {
		String input = "";
		
		input = scan.nextLine();
		System.out.println((b.getTurn() == 'w' ? "White's move: " : "Black's move: ") + input); // change tempTurn to b.getTurn()
		
//		System.out.println("MOVE: " + input);
		
		if(input.equals("resign")){
			System.out.println((b.getTurn() == 'w' ? "Black wins" : "White wins")); // change tempTurn to b.getTurn()
			System.exit(0); // end match
		} else if (input.equals("draw") && pendingDraw) {
			System.exit(0); // draw was waiting and is now confirmed
		}
		
		String[] parts = input.split(" ");
		
		// (x,y)
		origin = new Point(parts[0].charAt(0) - 'a', parts[0].charAt(1) - '1');
		target = new Point(parts[1].charAt(0) - 'a', parts[1].charAt(1) - '1');
		
		promotion = 'Q';
		end = "";
		
		if(parts.length > 2){
			end = parts[parts.length - 1];
			promotion = parts[2].charAt(0);
		}
		
		pendingDraw = end.equals("draw?");
	}
}
