import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class testPlayground {

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	public static Board b = null;

	/**
	 * Description...
	 *
	 * @param ...
	 * @return .....
	 */
	public static void main(String args[]) {
		System.out.println("Welcome to Chess.");
		b = new Board();
		b.board = new Piece[8][8];
		b.board[5][0] = new Rook('w', new Point(5, 0));
		b.board[7][0] = new King('w', new Point(7, 0));
		b.board[5][1] = new Pawn('w', new Point(5, 1));
		b.board[6][1] = new Pawn('w', new Point(6, 1));
		b.board[5][2] = new Knight('w', new Point(5, 2));
		b.board[6][2] = new Knight('b', new Point(6, 2));
		b.board[7][1] = new Pawn('w', new Point(7, 1));

		b.whitePieces = new ArrayList<Piece>();
		b.blackPieces = new ArrayList<Piece>();

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (b.board[x][y] != null) {
					(b.board[x][y].getColor() == 'w' ? b.whitePieces : b.blackPieces).add(b.board[x][y]);

				}
			}
		}

		// (tempTurn == 'w' ? "White's move: " : "Blacks's move: ");
		// System.out.println(b);
		// System.out.println("\n" + (tempTurn == 'w' ? "White's move: " :
		// "Blacks's move: ")); // change tempTurn to b.getTurn()
		do {
			System.out.println(b);

			Object[] parts = readMove();
			Point origin = (Point) parts[0];
			Point target = (Point) parts[1];
			char promotion = (char) parts[2];

			// System.out.println(origin + "\n" + target + "\n" + promotion +
			// "\n" );

			while (!b.executeMove(origin, target, promotion)) {
				System.out.println("Illegal move, try again");
				parts = readMove();
				origin = (Point) parts[0];
				target = (Point) parts[1];
				promotion = (char) parts[2];
			}

			b.toggleTurn();

			// Move will be made by this point
		} while (b.matchCanContinue()); // && not stalemate
		// mobilityTestSuite();

		b.printEndState();

	}

	/**
	 * Description...
	 *
	 * @param ...
	 * @return .....
	 */
	private static Object[] readMove() {
		String input = "";

		Point op = null;
		Point tp = null;

		System.out.print((b.getTurn() == 'w' ? "White's move: " : "Blacks's move: ")); // change
																						// tempTurn
																						// to
																						// b.getTurn()

		while (true) {
			try {
				input = reader.readLine();
				System.out.println("");
				break;
			} catch (IOException e) {
			}
		}
		// System.out.println("MOVE: " + input);

		if (input.contains("resign")) {

		}

		String origin = input.split(" ")[0];
		String target = input.split(" ")[1];
		char pro = 'Q';
		if (input.split(" ").length > 2) {
			pro = input.split(" ")[1].charAt(0);
		}

		// System.out.println("Origin: " + origin + "\nTarget: " + target);

		// (x,y)
		op = new Point(origin.charAt(0) - 'a', origin.charAt(1) - '1');
		tp = new Point(target.charAt(0) - 'a', target.charAt(1) - '1');

		// (y,x)
		// op = new Point(origin.charAt(1) - '1', origin.charAt(0) - 'a');
		// tp = new Point(target.charAt(1) - '1', target.charAt(0) - 'a');

		// System.out.println("Origin: " + op + "\nTarget: " + tp +
		// "\nPromotion: " + pro );

		Object output[] = { op, tp, pro };

		return output;
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
