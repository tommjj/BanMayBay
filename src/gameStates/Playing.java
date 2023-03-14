package gameStates;

import entities.Enemy;
import entities.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import main.Game;
import ui.GameoverOverlay;
import utiliz.Constants;
import utiliz.Constants.WindowConstants;

public class Playing extends State implements Statemethods {
    
    private Enemy enemy = new Enemy(0, 0);
    
    private Player player;
    
    private int score = 0;
    
    private boolean gameover = false;
    private GameoverOverlay gameoverOverlay;
    
    public Playing(Game game) {
        super(game);
        
        player = new Player(this);
        
        gameoverOverlay = new GameoverOverlay(this);
        
        initbuttons();
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

        enemy.draw(g);
        player.draw(g);
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
        player.mouseMoved(e);
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
        player.mouseDragged(e);
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
    
}
