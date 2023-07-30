package tankgame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DamageUP extends GameObject {
    float x,y;
    BufferedImage img;
    public DamageUP(float x, float y, BufferedImage dmgup) {
        this.x = x;
        this.y = y;
        this.img = dmgup;
    }

    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int) x, (int) y, null);
    }

}
