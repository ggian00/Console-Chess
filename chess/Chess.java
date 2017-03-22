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

public class Chess {

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	public static Board b = null;
	public static boolean pendingDraw = true;
	public static Point origin = null;
	public static Point target = null;
	public static String end = "";
	public static char promotion = 'Q';
	
	static Scanner scan = null;
	static int lineNum = 1;

	/**
	 * Description...
	 *
	 * @param ...
	 * @return .....
	 */
	public static void main(String args[]) {
//		try {
//			scan = new Scanner(new File("ex1.txt"));
//		} catch (Exception e) {
//			System.out.println("Error");
//			return;
//		}
		
		System.out.println("Welcome to Chess.");
		b = new Board();

		do{
			
			if(b.inCheck(b.getTurn(), b.board, b.whitePieces, b.blackPieces)){
				System.out.println("Check");
			}
			
			System.out.println(b);
			
//			readMoveTest();
			readMove();
			
//			System.out.println(origin + "\n"  + target + "\n" + promotion + "\n" );

			while (! b.executeMove(b.board, b.whitePieces, b.blackPieces, origin, target, promotion)){
				System.out.println("Illegal move, try again");
				readMove();
			}

			b.toggleTurn();
			
			// Move will be made by this point
		} while(b.matchCanContinue()); // && not stalemate
		// mobilityTestSuite();
		
		b.printEndState();
		
	}
	

	/**
	 * Description...
	 *
	 * @param ...
	 * @return .....
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
	
	private static void readMoveTest() {
		String input = "";
		
		input = scan.nextLine();
		System.out.println("Line " + lineNum);
		lineNum += 1;

		System.out.print((b.getTurn() == 'w' ? "White's move: " : "Blacks's move: ") + input); // change tempTurn to b.getTurn()
		
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

	private static void printMobility(int[][] mob) {
		for (int y = 7; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				System.out.print(mob[x][y] + " ");
			}
			System.out.println(" " + y);
		}
		System.out.println();
		for (int col = 0; col < 8; col++) {
			System.out.print((char) ('a' + col) + " ");
		}
		System.out.println('\n');
	}

	private static void mobilityTestSuite() {
		// Pawn
		Piece testPawn = new Pawn('b', new Point(4, 6));
		int[][] testmobpawn = testPawn.getMobility();
		testmobpawn[(int) testPawn.location.getX()][(int) testPawn.location.getY()] = 5;
		System.out.println("Testing Pawn mobility at " + testPawn.location.getX() + " " + testPawn.location.getY());
		printMobility(testmobpawn);

		// Rook
		Piece testRook = new Rook('b', new Point(3, 6));
		int[][] testmobrook = testRook.getMobility();
		testmobrook[(int) testRook.location.getX()][(int) testRook.location.getY()] = 5;
		System.out.println("Testing Rook mobility at " + testRook.location.getX() + " " + testRook.location.getY());
		printMobility(testmobrook);

		// Knight
		Piece testKnight = new Knight('b', new Point(3, 7));
		int[][] testmobknight = testKnight.getMobility();
		testmobknight[(int) testKnight.location.getX()][(int) testKnight.location.getY()] = 5;
		System.out
				.println("Testing Knight mobility at " + testKnight.location.getX() + " " + testKnight.location.getY());
		printMobility(testmobknight);

		// Bishop
		Piece testBishop = new Bishop('w', new Point(5, 3));
		int[][] testmobbish = testBishop.getMobility();
		testmobbish[(int) testBishop.location.getX()][(int) testBishop.location.getY()] = 5;
		System.out
				.println("Testing Bishop mobility at " + testBishop.location.getX() + " " + testBishop.location.getY());
		printMobility(testmobbish);

		// Queen
		Piece testQueen = new Queen('b', new Point(3, 6));
		int[][] testmobqueen = testQueen.getMobility();
		testmobqueen[(int) testQueen.location.getX()][(int) testQueen.location.getY()] = 5;
		System.out.println("Testing Queen mobility at " + testQueen.location.getX() + " " + testQueen.location.getY());
		printMobility(testmobqueen);

		// King
		Piece testKing = new King('b', new Point(3, 7));
		int[][] testmobking = testKing.getMobility();
		testmobking[(int) testKing.location.getX()][(int) testKing.location.getY()] = 5;
		System.out.println("Testing King mobility at " + testKing.location.getX() + " " + testKing.location.getY());
		printMobility(testmobking);
	}
}
