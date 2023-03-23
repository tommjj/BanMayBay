package gameStates;

import camera.Camera2D;
import camera.Camera2D.CameraDraw;
import entities.Enemy;
import entities.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import main.Game;
import ui.GameoverOverlay;
import utiliz.Constants.WindowConstants;
import utiliz.LoadSave;

public class Playing extends State implements Statemethods, CameraDraw {

    private Enemy enemy = new Enemy(0, 0);

    private Player player;

    private int score = 0;

    private boolean gameover = false;
    private GameoverOverlay gameoverOverlay;

    private Camera2D camera;
    
    private BufferedImage background;

    public Playing(Game game) {
        super(game);

        player = new Player(this);

        gameoverOverlay = new GameoverOverlay(this);

        camera = new Camera2D(100, 100, WindowConstants.WIDTH_SIZE+100, WindowConstants.HEIGHT_SIZE+100, this);

        initbuttons();
        initImg();
    }
    
    private void initImg() {
        background = LoadSave.getImage(LoadSave.BACK_GROUND_IMG);
    }

    public boolean isGameover() {
        return gameover;
    }

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    private void initbuttons() {

    }

    @Override
    public void update() {
        if (gameover) {

        } else {
            player.update();
            enemy.update(this);

        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();     
        g2d.drawImage(background, 0, 0, WindowConstants.WIDTH_SIZE, WindowConstants.HEIGHT_SIZE, null);
        g2d.dispose();
        
        
        g.setColor(Color.WHITE);
        switch (enemy.getNextSpawn()) {
            case 0:
                g.fillRect(0, 0, WindowConstants.WIDTH_SIZE, 10);
                break;
            case 1:              
                g.fillRect(0, 0, 10, WindowConstants.HEIGHT_SIZE);
                break;
            case 2:              
                g.fillRect(0, WindowConstants.HEIGHT_SIZE-10, WindowConstants.WIDTH_SIZE, 10);
                break;
            case 3:              
                g.fillRect(WindowConstants.WIDTH_SIZE - 10, 0, 10, WindowConstants.HEIGHT_SIZE);
                break;
            default:
                throw new AssertionError();
        }
        
        
        
        camera.draw(g);
        
        //player.draw(g);
        player.drawUI(g);
        //
        if (gameover) {
            gameoverOverlay.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        player.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        player.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        player.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {     
        int x = (int) (camera.getX() + e.getX());
        int y = (int) (camera.getY() + e.getY());
        
        player.setCurrentMouse(x, y);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameover) {
            gameoverOverlay.keyEven(e);
        } else {
            player.keyReleased(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {       
        int x = (int) (camera.getX() + e.getX());
        int y = (int) (camera.getY() + e.getY());
        
        player.setCurrentMouse(x, y);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public void reset() {
        enemy.reset();
        enemy.resetSpeed();
        player.reset();
        score = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void Draw(Graphics2D g) {
        player.draw(g);
        enemy.draw(g);
    }
}
