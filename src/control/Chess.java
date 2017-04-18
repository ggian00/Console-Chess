package control;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * Main Class
 * 
 * @author David Parsons
 * @author Phil Plucinski
 */
public class Chess {

	/**
	 * Buffer read for obtaining input from user
	 */
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Board for chess match
	 */
	public static Board b = null;

	/**
	 * Keeps track of any pending draws
	 */
	public static boolean pendingDraw = true;

	/**
	 * origin point of move
	 */
	public static Point origin = null;

	/**
	 * target point of move
	 */
	public static Point target = null;

	/**
	 * end of parsed move (holds "why?")
	 */
	public static String end = "";

	/**
	 * promotion if needed for current move
	 */
	public static char promotion = 'Q';

	/**
	 * Main
	 */
	public static void main(String args[]) {

		System.out.println("Welcome to Chess.");
		b = new Board();

		do {

			if (b.inCheck(b.getTurn(), b.board, b.whitePieces, b.blackPieces)) {
				System.out.println("Check");
			}

			System.out.println(b);

			readMove();

			while (!b.executeMove(b.board, b.whitePieces, b.blackPieces, origin, target, promotion)) {
				System.out.println("Illegal move, try again");
				readMove();
			}

			b.toggleTurn();

		} while (b.matchCanContinue());

	}

	/**
	 * Consumes input from console and updates static data fields accordingly in
	 * an attempt to prepare for move execution
	 *
	 */
	private static void readMove() {
		String input = "";

		System.out.print((b.getTurn() == 'w' ? "White's move: " : "Black's move: ")); // change
																						// tempTurn
																						// to
																						// b.getTurn()

		while (true) {
			try {
				input = reader.readLine();
				break;
			} catch (IOException e) {
			}
		}

		if (input.equals("resign")) {
			System.out.println((b.getTurn() == 'w' ? "Black wins" : "White wins")); // change
																					// tempTurn
																					// to
																					// b.getTurn()
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

		if (parts.length > 2) {
			end = parts[parts.length - 1];
			promotion = parts[2].charAt(0);
		}

		pendingDraw = end.equals("draw?");
	}
}
