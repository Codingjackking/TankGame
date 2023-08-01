package tankgame.game.immovable;

import tankgame.game.Collidable;
import tankgame.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject implements Collidable  {
    float x,y;
    BufferedImage img;
    private Rectangle hitBox;

    public Wall(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitBox = new Rectangle((int) x, (int) y,this.img.getWidth(), this.img.getHeight());
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
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
        return true;
    }
}
