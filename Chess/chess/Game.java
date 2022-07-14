package chess;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;
import viu.Jutil;
import model.Board;
import model.Piece;
import model.Theme;
import viu.View;

/**
 *
 * @author estidlore
 */
public class Game extends View {

    private final Board board;
    private final JLabel[][] cells;
    private final Theme theme;
    private final Point selection;

    public int playerTurn;

    public Game() {
        super(Board.SIZE, Board.SIZE + 30);
        board = new Board();
        cells = new JLabel[Board.CELLS_NUM][Board.CELLS_NUM];
        theme = Theme.WOOD;
        selection = new Point(-1, -1);
    }

    @Override
    public void init() {
        addMouseListener(this);
        for (int j = cells.length - 1; j >= 0; j--) {
            for (int i = cells.length - 1; i >= 0; i--) {
                Color c = (i + j) % 2 == 0 ? theme.white : theme.black;
                cells[i][j] = Jutil.label(c, i * board.cellSize,
                        j * board.cellSize, board.cellSize, board.cellSize);
                add(cells[i][j]);
            }
        }
        start();
    }

    private void start() {
        selection.setLocation(-1, -1);
        playerTurn = 0;
        board.pieces.forEach((p) -> {
            cells[p.x][p.y].setText(p.color + " : " + p.type);
        });
    }

    private void select(int x, int y) {
        if (selection.x != -1 && selection.y != -1) {
            cells[selection.x][selection.y].setBorder(null);
        }
        if (selection.x == x && selection.y == y) {
            return; // unselect
        }
        Piece p = board.pieceIn(selection);
        System.out.println("a");
        if (p != null) {
            int moved = board.moveTo(p, x, y);
            System.out.println("moved: " + moved);
            if (moved == 0) {
                cells[selection.x][selection.y].setText("");
                cells[x][y].setText(p.color + " : " + p.type);
            }
            if (moved == -1) {
                selection.setLocation(x, y);
            } else {
                selection.setLocation(-1, -1);
            }
        } else if (board.pieceIn(x, y) != null) {
            cells[x][y].setBorder(theme.border);
            selection.setLocation(x, y);
            System.out.println("c");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX(), mY = e.getY();
        JLabel c;
        for (int j = 0; j < Board.CELLS_NUM; j++) {
            for (int i = 0; i < Board.CELLS_NUM; i++) {
                c = cells[i][j];
                if (mX >= c.getX() && mX <= c.getX() + board.cellSize
                        && mY >= c.getY() && mY <= c.getY() + board.cellSize) {
                    select(i, j);
                }
            }
        }
    }

}
