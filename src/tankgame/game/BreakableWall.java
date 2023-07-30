package tankgame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameObject{
    float x,y;
    BufferedImage img;
    public BreakableWall(float x, float y, BufferedImage break1) {
       this.x = x;
       this.y = y;
       this.img = break1;
    }

    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int) x, (int) y, null);
    }
}
