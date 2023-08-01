package tankgame.game.immovable;

import tankgame.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lives extends GameObject {
    float x,y;
    BufferedImage img;
    private Rectangle hitBox;
    private final int respawnDelay = 15000; // 10 seconds respawn delay
    private long respawnTime = 0;

    public Lives(float x, float y, BufferedImage lives) {
        this.x = x;
        this.y = y;
        this.img = lives;
        this.hitBox = new Rectangle((int) x, (int) y,this.img.getWidth(), this.img.getHeight());

    }
    public void respawn() {
        respawnTime = System.currentTimeMillis() + respawnDelay;
    }
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override

    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int) x, (int) y, null);
    }

    @Override
    public boolean isDrawable() {
        return System.currentTimeMillis() > respawnTime;
    }
}
