package model;

import java.awt.Point;

/**
 *
 * @author estidlore
 */
public abstract class Piece {

    public final static int NONE = 0, WHITE = 1, BLACK = -1,
            PAWN = 1, BISHOP = 2, KNIGHT = 3, ROCK = 4, QUEEN = 5, KING = 6;
    
    public final int type, value, color;
    public int x, y;

    public Piece(int type, int value, int color, int x, int y) {
        this.type = type;
        this.value = value;
        this.color = color;
        this.x = x;
        this.y = y;
    }
    
    public abstract Point[] pathTo(int x, int y);
    
    public abstract boolean move(int x, int y);
    
    protected void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
