package gameoflife;

import java.awt.Canvas;
import java.awt.Point;
import laz.Update;
import viu.View;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author estidlore
 */
public class Game extends View {

    private final Canvas cvs;
    private final Update update;
    private final static int X_CELLS = 80, Y_CELLS = 30,
            SCREEN_W = 640, SCREEN_H = 480;

    private final static int MODE_SIMPLE = 1, MODE_IMPROVED = 2;

    private boolean[][] map;
    private int population, mode;

    private ArrayList<Point> mapImproved;

    public Game() {
        super(SCREEN_W, SCREEN_H);
        cvs = new Canvas();
        update = new Update(5) {
            @Override
            public void tick() {
                draw();
            }
        };
        map = new boolean[X_CELLS][Y_CELLS];
        mapImproved = new ArrayList<>();
    }

    private boolean calc(int i, int j) {
        int m = X_CELLS - 1, n = Y_CELLS - 1, c = 0;
        if (i > 0) {
            if (map[i - 1][j]) c++; // left
            if (j > 0 && map[i - 1][j - 1]) c++; // up-left
            if (j < n && map[i - 1][j + 1]) c++; // down-left
        }
        if (i < m) {
            if (map[i + 1][j]) c++; // right
            if (j > 0 && map[i + 1][j - 1]) c++; // up-right
            if (j < n && map[i + 1][j + 1]) c++; // down-right
        }
        if (j > 0 && map[i][j - 1]) c++; // up
        if (j < n && map[i][j + 1]) c++; // down
        return (c == 3) || (map[i][j] && c == 2);
    }

    private void draw() {
        if (mode == MODE_SIMPLE) {
            System.out.println("");
            System.out.println("Population: " + population);
            boolean[][] newMap = new boolean[X_CELLS][Y_CELLS];
            population = 0;
            for (int j = 0; j < Y_CELLS; j++) {
                for (int i = 0; i < X_CELLS; i++) {
                    boolean c = calc(i, j);
                    if (c) population++;
                    newMap[i][j] = c;
                    System.out.print(map[i][j] ? " O" : " .");
                }
                System.out.println("");
            }
            map = newMap;
        } else if (mode == MODE_IMPROVED) {
            drawImproved();
        }
    }

    private void calcImproved() {
        short[][] newMap = new short[X_CELLS + 2][Y_CELLS + 2];
        for (int i = mapImproved.size() - 1; i >= 0; i--) {
            int x = mapImproved.get(i).x, y = mapImproved.get(i).y;
            newMap[x - 1][y - 1]++; // up-left
            newMap[x][y - 1]++; // up
            newMap[x + 1][y - 1]++; // up-right
            newMap[x + 1][y]++; // right
            newMap[x + 1][y + 1]++; // down-right
            newMap[x][y + 1]++; // down
            newMap[x - 1][y + 1]++; // down-left
            newMap[x - 1][y]++; // left
        }
        int m = X_CELLS + 1, n = Y_CELLS + 1, index = 0, c;
        ArrayList<Point> newMapImp = new ArrayList<>();
        Point p = mapImproved.get(index);
        for (int j = 1; j < n; j++) {
            for (int i = 1; i < m; i++) {
                c = newMap[i][j];
                if (p.x == i && p.y == j) {
                    if (c == 3 || c == 2) {
                        newMapImp.add(new Point(i, j));
                    }
                    index++;
                    if (index == mapImproved.size()) {
                        i = m;
                        j = n;
                    } else {
                        p = mapImproved.get(index);
                    }
                } else if (c == 3) {
                    newMapImp.add(new Point(i, j));
                }
            }
        }
        mapImproved = newMapImp;
    }

    private void drawImproved() {
        if (mapImproved.isEmpty()) {
            return;
        }
        System.out.println("\nPopulation: " + mapImproved.size());
        int m = X_CELLS + 1, n = Y_CELLS + 1, index = 0;
        Point p = mapImproved.get(index);
        for (int j = 1; j < n; j++) {
            for (int i = 1; i < m; i++) {
                if (p.x == i && p.y == j) {
                    System.out.print(" 0");
                    index++;
                    if (index < mapImproved.size()) {
                        p = mapImproved.get(index);
                    }
                } else {
                    System.out.print(" .");
                }
            }
            System.out.println("");
        }
        calcImproved();
    }

    @Override
    public void init() {
        addMouseListener(this);
        mode = MODE_IMPROVED;
        map[10][5] = true;
        map[10][6] = true;
        map[12][6] = true;
        map[10][7] = true;
        map[11][7] = true;
        // line
        mapImproved.add(new Point(3, 3));
        mapImproved.add(new Point(4, 3));
        mapImproved.add(new Point(5, 3));
        // walker
        mapImproved.add(new Point(11, 6));
        mapImproved.add(new Point(11, 7));
        mapImproved.add(new Point(13, 7));
        mapImproved.add(new Point(11, 8));
        mapImproved.add(new Point(12, 8));

        population = 5;
        update.setWaitMode(Update.WAIT_FULL_TICK);
        update.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX(), y = e.getY();
    }

}
