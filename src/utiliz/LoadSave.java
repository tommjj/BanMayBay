package utiliz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.GamePanel;

public class LoadSave {
    
    public static final String m = "image/m.png";
    public static final String BULLET = "image/bullet.png";
    public static final String ROCKET = "image/rocket.png";
    public static final String BACK_GROUND_IMG = "image/background.png";
    
    public static BufferedImage getImage(String path) {
        InputStream is = LoadSave.class.getResourceAsStream("/" + path);
        BufferedImage img = null;
        try {
            img = ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return img;
    }
}
