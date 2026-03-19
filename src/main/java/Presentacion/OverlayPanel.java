package Presentacion;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Usuario
 */
public class OverlayPanel extends JPanel {

    public OverlayPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,getWidth(),getHeight());
    }
}
