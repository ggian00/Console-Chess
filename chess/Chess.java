import java.awt.Point;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class Chess {

	/**
	 * Description...
	 *
	 * @param ...
	 * @return .....
	 */
	public static void main(String args[]) {
		System.out.println("Welcome to Chess.");
		Board b = new Board();
		System.out.println(b);

		mobilityTestSuite();

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
