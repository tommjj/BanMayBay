package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public abstract class Button {
    private int x, y;
    private int width, height;
    private Rectangle bounds;
    private boolean mouseOver, mousePressed;

    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initBouds();
    }
    
    public void update() {
    
    }
    
    public void draw(Graphics g) {
        if(mouseOver) {
            g.setColor(Color.yellow);
        } else {
            g.setColor(Color.red);
        }
        
        g.fillRect(x, y, width, height);
        
    }
    
    private void initBouds() {
        bounds = new Rectangle(x, y, width, height);
    }
    
    public void mousePressed(MouseEvent e) {
        if(isIn(e)) {
            mousePressed = true;
        }
    }
    
    public void mouseReleased(MouseEvent e) {
        if(mousePressed) {
            if(isIn(e)) {
                execute();
            }
        }
        mousePressed = false;
    }
    
    public void mouseMoved(MouseEvent e) {
        mouseOver = false;
        
        if(isIn(e)) {
            mouseOver = true;
        }
    }
    
    public boolean isIn(MouseEvent e) {
        return bounds.contains(e.getX(), e.getY());
    }
    
    public abstract void execute();          
}
