package FlappySquarePackage;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ersan on 7/25/2017.
 */
public class Renderer extends JPanel {

    private static final long serialVersionID = 1L;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FlappySquare.flappySquare.repaint(g);
    }
}