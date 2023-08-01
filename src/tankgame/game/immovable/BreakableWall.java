package tankgame.game.immovable;

import tankgame.Resources.ResourceManager;
import tankgame.game.Collidable;
import tankgame.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameObject implements Collidable {
    float x,y;
    BufferedImage img;
    private Rectangle hitBox;
    private int health = 2;

    public BreakableWall(float x, float y, BufferedImage img) {
       this.x = x;
       this.y = y;
       this.img = img;
       this.hitBox = new Rectangle((int) x, (int) y,this.img.getWidth(), this.img.getHeight());
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    public int getHealth() {return this.health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void hitWall() {
        if (this.getHealth() > 1) {
            this.health -=1;
        } else {
            setHealth(0);
            this.img = ResourceManager.getSprite("break2");
        }
    }

    @Override
    public void drawImage(Graphics buffer) {
        if (isDrawable()) {
            Graphics2D g2d = (Graphics2D) buffer;
            g2d.drawImage(this.img, (int) x, (int) y, null);
        }
    }
    @Override
    public boolean isDrawable() {
        if (this.health != 0) {
            return true;
        } else {
            return false;
        }
    }
}
