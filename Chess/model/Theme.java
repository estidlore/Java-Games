package model;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author estidlore
 */
public class Theme {

    public final Color white, black;
    public final Border border;
    
    private static final Color YELLOW = Color.getHSBColor(0.16f, 0.9f, 0.7f),
            BROWN = Color.getHSBColor(0.01f, 0.7f, 0.5f);
    
    public static final Theme
            GRAY = new Theme(Color.LIGHT_GRAY, Color.DARK_GRAY, Color.YELLOW),
            MONO = new Theme(Color.WHITE, Color.BLACK, Color.YELLOW),
            WOOD = new Theme(YELLOW, BROWN, Color.RED);
    

    public Theme(Color white, Color black, Color border) {
        this.white = white;
        this.black = black;
        this.border = BorderFactory.createLineBorder(border, 2);
        
        
    }

}
