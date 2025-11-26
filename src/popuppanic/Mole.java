package popuppanic;

import java.awt.*;
import javax.swing.*;

public class Mole extends GameObject {

    private Image img;
    private boolean visible = false;
    private long visibleTimer = 0;

    private final long MAX_VISIBLE_DURATION = 1500;
    private final String IMAGE_PATH = "assets/mole.png";

    public Mole(int x, int y, int w, int h) {
        super(x, y, w, h);
        
        try {
            this.img = Toolkit.getDefaultToolkit().getImage(IMAGE_PATH);
        } catch (Exception e) {
            System.err.println("error Loading Mole Image: " + e.getMessage());
        }
    }

    @Override
    public void update(long dt) {
        if (visible) {
            visibleTimer += dt;

            if (visibleTimer >= MAX_VISIBLE_DURATION) {
                visible = false;
                visibleTimer = 0;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (visible) {
            if (img != null) {
                g.drawImage(img, x, y, width, height, null);
            } else {
                g.setColor(Color.ORANGE);
                g.fillRect(x, y, width, height);
                g.setColor(Color.BLACK);
                g.drawString("MOLE", x + width/4, y + height/2);
            }
        }
    }

    @Override
    public boolean contains(Point p) {
        if (!visible) return false;

        return getBounds().contains(p);
    }

    @Override
    public void onClick() {
        if (visible) {
            System.out.println("Mole clicked! +1 point!");

            visible = false;
            visibleTimer = 0;
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void popUp(long duration) {
        this.visible = true;
        this.visibleTimer = 0; // reset timer
    }
}
