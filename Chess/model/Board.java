package model;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author estidlore
 */
public class Board {

    public final static int SIZE = 480, CELLS_NUM = 8;

    public final int cellSize;
    public final ArrayList<Piece> pieces;

    public Board() {
        cellSize = SIZE / CELLS_NUM;
        pieces = new ArrayList<>();
        init();
    }

    public void init() {
        for (int i = 0; i < CELLS_NUM; i++) {
            pieces.add(new Pawn(Piece.WHITE, i, 1));
        }
        for (int i = 0; i < CELLS_NUM; i++) {
            pieces.add(new Pawn(Piece.BLACK, i, 6));
        }
    }

    public Piece pieceIn(int x, int y) {
        if(x == -1 && y == -1) {
            return null;
        }
        for (Piece p : pieces) {
            if (p.x == x && p.y == y) {
                return p;
            }
        }
        return null;
    }

    public Piece pieceIn(Point p) {
        return pieceIn(p.x, p.y);
    }

    public boolean pieceInMiddle(Point[] path) {
        for (int i = 0; i < path.length - 1; i++) {
            if (pieceIn(path[i].getLocation()) != null) {
                return true;
            }
        }
        return false;
    }

    public int moveTo(Piece p, int x, int y) {
        Piece left = pieceIn(x, y);
        if (left != null && left.color == p.color) {
            return -1; // same color
        }
        Point[] path = p.pathTo(x, y);
        if (path == null) {
            return -2; // Unreachable
        }
        if (pieceInMiddle(path)) {
            return -3;
        }
        p.setPosition(x, y);
        if (left != null) {
            pieces.remove(left);
            return 1;
        }
        // ...
        return 0;
    }

}
