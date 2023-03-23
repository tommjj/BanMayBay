package utiliz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DrawMethods {

    public static void drawAmmo(Graphics g, int maxAmmo, int cuAmmo, int x, int y, int width, int height, int offset, Color color) {
        int aLength = (int) (((float) width / maxAmmo) * cuAmmo);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(x, y);
        g2d.setColor(color);
        int[] px = {0 + offset, aLength + offset, aLength, 0};
        int[] py = {0, 0, height, height};
        g2d.fillPolygon(px, py, 4);
    }
    
    public static void drawHealth(Graphics g, int max, int current, int x, int y, int width, int height, int offset, Color color) {
        int length = width / max;

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.translate(x, y);
        for (int i = 0; i < current; i++) {
            int[] px = {(length * i) + offset, (length * (i + 1)) + offset - 2, (length * (i + 1)) - 2, (length * i)};
            int[] py = {0, 0, height, height};
            g2d.fillPolygon(px, py, 4);
        }
    }
}
