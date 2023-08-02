package tankgame.game.immovable.Walls;

import tankgame.Resources.ResourceManager;
import tankgame.game.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall implements Collidable {
    float x,y;
    BufferedImage img;
    private Rectangle hitBox;
    private boolean isCollidable;
    private int health = 2;

    public BreakableWall(float x, float y, BufferedImage img) {
        super(x, y, img);
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitBox = new Rectangle((int) x, (int) y, this.img.getWidth(), this.img.getHeight());
        isCollidable = true;
    }

    @Override
    public void update() {
        if (this.health <= 0) {
            this.destroy();
        }
    }

    @Override
    public boolean isCollidable() {
        return this.isCollidable;
    }

    @Override
    public void destroy() {
        this.isCollidable = false;
    }

    @Override
    public void collide(Collidable obj) {
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public boolean checkCollision(Collidable with) {
        return false;
    }

    public void hitWall() {
        if (this.health > 1) {
            this.health -=1;
        } else {
            setHealth(0);
            this.img = ResourceManager.getSprite("break2");
        }
    }
//    public void removeHealth () {
//        if(this.health > 0) {
//            this.health -= 1;
//        }
//        if(this.health <= 0) {
//            this.img = ResourceManager.getSprite("empty");
//        } else if (this.health <= 5) {
//            this.img = ResourceManager.getSprite("break1");
//        }
//    }
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, (int) x, (int) y, null);
    }

    @Override
    public void reset() {
        this.img = ResourceManager.getSprite("break2");
        health = 2;
        this.isCollidable = true;
        this.destroyed = false;
    }
}
