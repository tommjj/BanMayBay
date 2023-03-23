package main;

import inputs.KeyInputs;
import inputs.MouseInputs;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import utiliz.Constants.WindowConstants;


public class GamePanel extends JPanel{
    private Game game;
    private MouseInputs mouseInputs;
    private KeyInputs keyInputs;
    
    public GamePanel(Game game) {
        this.game = game;
        
        mouseInputs = new MouseInputs(this);
        keyInputs = new KeyInputs(this);
        
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        addMouseWheelListener(mouseInputs);
        addKeyListener(keyInputs);
       
        setPanelSize();
  
    }
    
    public Game getGame() {
        return game;
    }

    private void setPanelSize() {
        Dimension size = new Dimension(WindowConstants.WIDTH_SIZE, WindowConstants.HEIGHT_SIZE);
        setPreferredSize(size);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.rander(g);
        game.setFrames(game.getFrames()+1);
    }
}
