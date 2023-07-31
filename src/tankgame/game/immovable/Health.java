package tankgame.game.immovable;

import tankgame.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Health extends GameObject {
    float x,y;
    BufferedImage img;
    public Health(float x, float y, BufferedImage health) {
        this.x = x;
        this.y = y;
        this.img = health;
    }

    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int) x, (int) y, null);
    }
}
