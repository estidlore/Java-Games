package model;

import java.awt.Point;

/**
 *
 * @author estidlore
 */
public class Pawn extends Piece {

    public Pawn(int color, int x, int y) {
        super(PAWN, 1, color, x, y);
    }

    @Override
    public Point[] pathTo(int x, int y) {
        if (this.y + color == y && Math.abs(this.x - x) <= 1) {
            return new Point[]{
                new Point(x, y)
            };
        } else if (this.x == x && this.y + color * 2 == y
                && y == (this.color == Piece.WHITE ? 1 : 6)) {
            return new Point[]{
                new Point(x, y - color),
                new Point(x, y)
            };
        }
        return null;
    }

    @Override
    public boolean move(int x, int y) {
        if (this.y + color == y && Math.abs(this.x - x) <= 1) {

        }
        return false;
    }

}
