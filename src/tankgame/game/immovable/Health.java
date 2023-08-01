package tankgame.game.immovable;

import tankgame.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Health extends GameObject {
    float x,y;
    BufferedImage img;
    private Rectangle hitBox;

    public Health(float x, float y, BufferedImage health) {
        this.x = x;
        this.y = y;
        this.img = health;
        this.hitBox = new Rectangle((int) x, (int) y,this.img.getWidth(), this.img.getHeight());

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
        return false;
    }
}
