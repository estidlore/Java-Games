package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;
import viu.Jutil;
import viu.View;

/**
 *
 * @author estidlore
 */
public class Game extends View {

    private final static int SIZE = 360;

    private JLabel[][] cells;
    private final JButton start_btn;
    private int cell_n, cell_w;
    private final Point p;

    public Game() {
        super(SIZE, SIZE + 30);
        start_btn = Jutil.button("New game", 0, SIZE, 100, 30);
        p = new Point();
    }

    @Override
    public void init() {
        start_btn.addMouseListener(this);
        add(start_btn);
        cell_n = 4;
        start();
    }

    private void start() {
        int[] nums = shuffle();
        setCellsNum(cell_n);
        Border b = BorderFactory.createLineBorder(Color.BLACK, 1);
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, cell_w / 4);
        for (int j = cell_n - 1; j >= 0; j--) {
            for (int i = cell_n - 1; i >= 0; i--) {
                int c = nums[j * cell_n + i];
                cells[i][j] = Jutil.label(Color.LIGHT_GRAY,
                        i * cell_w, j * cell_w, cell_w, cell_w);
                cells[i][j].setFont(f);
                cells[i][j].setBorder(b);
                cells[i][j].addMouseListener(this);
                if (c == cell_n * cell_n) {
                    cells[i][j].setText("");
                    cells[i][j].setBackground(Color.GRAY);
                    p.setLocation(i, j);
                } else {
                    cells[i][j].setText(String.valueOf(c));
                }
                add(cells[i][j]);
            }
        }
        slide(cell_n - 1, cell_n - 1);
        repaint();
    }

    private void setCellsNum(int n) {
        if (cells != null) {
            for (int j = cells.length - 1; j >= 0; j--) {
                for (int i = cell_n - 1; i >= 0; i--) {
                    remove(cells[j]);
                }
            }
        }
        cell_n = n;
        cell_w = SIZE / n;
        cells = new JLabel[n][n];
    }

    private int[] shuffle() {
        int n = cell_n * cell_n;
        int[] nums = new int[n];
        for (int i = nums.length - 1; i >= 0; i--) {
            nums[i] = i + 1;
        }
        if (cell_n % 2 == 1) {
            nums[n - 2] = n - 3;
            nums[n - 4] = n - 1;
        }
        for (int i = nums.length - 1; i >= 1; i--) {
            int b = (int) (Math.random() * i);
            nums[b] = nums[i] - nums[b];
            nums[i] = nums[i] - nums[b];
            nums[b] = nums[i] + nums[b];
        }
        return nums;
    }

    private void slide(int i, int j) {
        cells[p.x][p.y].setText(cells[i][j].getText());
        cells[p.x][p.y].setBackground(Color.LIGHT_GRAY);
        cells[i][j].setText("");
        cells[i][j].setBackground(Color.GRAY);
        p.setLocation(i, j);
    }

    private void compare() {
        for (int j = cells.length - 1; j >= 0; j--) {
            for (int i = cell_n - 1; i >= 0; i--) {
                String txt = cells[i][j].getText(),
                        c = String.valueOf(j * cell_n + i + 1);
                if(!txt.equals("") && !txt.equals(c)) {
                    return;
                }
            }
        }
        Jutil.msg("You won");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent() == start_btn) {
            start();
            return;
        }
        for (int j = cell_n - 1; j >= 0; j--) {
            for (int i = cells.length - 1; i >= 0; i--) {
                if (e.getComponent() == cells[i][j]) {
                    if (Math.abs(i - p.x) + Math.abs(j - p.y) == 1) {
                        slide(i, j);
                        compare();
                    }
                    return;
                }
            }
        }
    }

}
