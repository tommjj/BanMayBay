package ui;

import gameStates.Playing;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import utiliz.Constants.WindowConstants;

public class GameoverOverlay {

    private Playing playing;
    private Font font = new Font("Serif", Font.BOLD, 50);

    public GameoverOverlay(Playing playing) {
        this.playing = playing;
    }

    public void update() {

    }

    public void draw(Graphics g) {
        

        Graphics2D g2dt = (Graphics2D) g.create();

        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
        g2dt.setComposite(alcom);
        g2dt.setColor(Color.BLACK);
        g2dt.fillRect(0, 0, WindowConstants.WIDTH_SIZE, WindowConstants.HEIGHT_SIZE);
        g2dt.dispose();
        g.setColor(Color.white);

        g.setFont(font);

        g.drawString("Score: " + playing.getScore(), (int) ((1280 / 2 - 75) * WindowConstants.SCALE), (int) (350 * WindowConstants.SCALE));
        g.drawString("Press R to new game!", (int) ((1280 / 2 - 185) * WindowConstants.SCALE), (int) (600 * WindowConstants.SCALE));
    }

    public void keyEven(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                playing.reset();
                playing.setGameover(!playing.isGameover());
                break;
            default:
                break;
        }
    }
}
