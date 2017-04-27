package control;

import java.awt.Point;

public class Move {

	Point firstPieceOrigin;
	Point firstPieceTarget;
	Point secondPieceOrigin = null;
	Point secondPieceTarget = null;
	char promotionType = '\0';
	char check = '\0';

	Move() {
	}

	void setMove(Point firstOrigin, Point firstTarget, Point secondOrigin, Point secondTarget) {

		this.firstPieceOrigin = firstOrigin;
		this.firstPieceTarget = firstTarget;
		this.secondPieceOrigin = secondOrigin;
		this.secondPieceTarget = secondTarget;
		this.promotionType = '\0';
		this.check = '\0';
	}

	public Point getFirstPieceOrigin() {
		return firstPieceOrigin;
	}

	public Point getFirstPieceTarget() {
		return firstPieceTarget;
	}

	public Point getSecondPieceOrigin() {
		return secondPieceOrigin;
	}

	public Point getSecondPieceTarget() {
		return secondPieceTarget;
	}

	public boolean isPromotion() {
		return promotionType == '\0';
	}

	public boolean isCheck() {
		return check == '\0';
	}

	public char getPromotion() {
		return promotionType;
	}

	public char getCheck() {
		return check;
	}

}
