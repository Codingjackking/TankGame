package tankgame.game.immovable;

import tankgame.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Shield extends GameObject {
    float x,y;
    BufferedImage img;
    public Shield(float x, float y, BufferedImage shield) {
        this.x = x;
        this.y = y;
        this.img = shield;
    }

    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int) x, (int) y, null);
    }
}
