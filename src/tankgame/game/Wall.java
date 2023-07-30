package tankgame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject{
    float x,y;
    BufferedImage img;
    public Wall(float x, float y, BufferedImage unbreak) {
        this.x = x;
        this.y = y;
        this.img = unbreak;
    }

    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int) x, (int) y, null);
    }
}
