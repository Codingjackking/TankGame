package tankgame.game.immovable.PowerUps;

import tankgame.Resources.ResourceManager;
import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.MapLoader;
import tankgame.game.movable.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lives extends PowerUp {
    private boolean isCollidable;
    float x,y;
    BufferedImage img;
    private Rectangle hitBox;
    private int lives;
//    private final int respawnDelay = 15000; // 15 seconds respawn delay
//    private long respawnTime = 0;

    public Lives(float x, float y, BufferedImage img) {
        super(x,y, img);
        this.x = x;
        this.y = y;
        this.img = img;
        this.isCollidable = true;
        this.hitBox = new Rectangle((int) x, (int) y,this.img.getWidth(), this.img.getHeight());
        this.lives = 1;
    }

    @Override
    public void update(MapLoader ml) {

    }

    @Override
    public void apply(Tank tank) {
        tank.addLife();
        this.img = ResourceManager.getSprite("empty");

    }
//    public void respawn() {
//        respawnTime = System.currentTimeMillis() + respawnDelay;
//    }

    @Override
    public boolean isCollidable() {
        return isCollidable;
    }

    @Override
    public void destroy() {
        this.destroyed = true;
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

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, (int) x, (int) y, null);
//        buffer.drawImage(this.img, (int) x, (int) y, null);
    }

    @Override
    public void reset() {
        this.destroyed = false;
        this.isCollidable = true;
        this.img = ResourceManager.getSprite("lives");
    }


}
