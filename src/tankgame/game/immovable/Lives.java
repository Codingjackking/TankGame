package tankgame.game.immovable;

import tankgame.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lives extends GameObject {
    float x,y;
    BufferedImage img;
    public Lives(float x, float y, BufferedImage lives) {
        this.x = x;
        this.y = y;
        this.img = lives;
    }

    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int) x, (int) y, null);
    }
}
