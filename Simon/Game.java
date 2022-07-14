package view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import laz.Timer;
import laz.Update;
import viu.Jutil;
import viu.View;

/**
 *
 * @author estidlore
 */
public class Game extends View {

    private final static int SIZE = 480;

    private final int cell_w;
    private final Color[] colors;
    private final JLabel[] cells;
    private final ArrayList<Integer> pattern;
    private final JButton play_btn;
    private final JLabel speed_txt, score_txt;
    private final Update update;

    private double tps;
    private int mode, index, speed, highscore, score;

    public Game() {
        super(SIZE, SIZE + 30);
        cell_w = SIZE / 2;
        colors = new Color[]{Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN};
        cells = new JLabel[2 * 2];
        pattern = new ArrayList<>();
        play_btn = Jutil.button("New Game", 0, SIZE, 100, 30);
        speed_txt = Jutil.label("Speed: 1", 120, SIZE, 80, 30);
        score_txt = Jutil.label("Score: 0 (Max: 0)", 200, SIZE, 150, 30);
        update = new Update(1) {
            @Override
            public void tick() {
                showPattern();
            }
        };
    }

    @Override
    public void init() {
        for (int i = cells.length - 1; i >= 0; i--) {
            cells[i] = Jutil.label(colors[i], i % 2 * cell_w, i / 2 * cell_w,
                    cell_w, cell_w);
            cells[i].addMouseListener(this);
            add(cells[i]);
        }
        play_btn.addMouseListener(this);
        speed_txt.addMouseListener(this);
        add(play_btn, speed_txt, score_txt);
        mode = -1;
    }

    public void start() {
        pattern.clear();
        pattern.add((int) (Math.random() * 4));
        score = 0;
        mode = 1;
        index = 0;
        score_txt.setText("Score: 0 (Max: " + highscore + ")");
        tps = Math.sqrt(speed + 1);
        update.setTPS(tps);
        update.start();
    }

    public void showPattern() {
        int i = pattern.get(index);
        cells[i].setBackground(Color.WHITE);
        Timer t = new Timer(() -> {
            cells[i].setBackground(colors[i]);
            if (++index == pattern.size()) {
                mode = 0;
                index = 0;
                update.stop();
            }
        }, (int) (500 / tps));
    }

    public void select(int i) {
        cells[i].setBackground(Color.WHITE);
        Timer t = new Timer(() -> {
            cells[i].setBackground(colors[i]);
        }, 100);
        if (pattern.get(index) == i) {
            score_txt.setText("Score: " + ++score
                    + " (Max: " + highscore + ")");
            if (++index == pattern.size()) {
                mode = 1;
                index = 0;
                pattern.add((int) (Math.random() * 4));
                Timer tsp = new Timer(() -> {
                    update.start();
                }, 500);
            }
        } else {
            gameOver();
        }
    }

    public void gameOver() {
        if (score > highscore) {
            highscore = score;
        }
        mode = -1;
        Jutil.msg("Game Over");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (mode == 0) {
            for (int i = cells.length - 1; i >= 0; i--) {
                if (e.getComponent() == cells[i]) {
                    select(i);
                    break;
                }
            }
        } else if (mode == -1) {
            if (e.getComponent() == play_btn) {
                update.stop();
                start();
            } else if (e.getComponent() == speed_txt) {
                speed = (speed + 1) % 5;
                speed_txt.setText("Speed: " + (speed + 1));
            }
        }
    }

}
