package tankgame.game.movable;

import tankgame.GameConstants;
import tankgame.game.GameObject;
import tankgame.game.Tank;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {
    private Rectangle hitBox;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;
    private Tank myTank;

    private final float R = 5;
    private  BufferedImage img;

     Bullet(float x, float y, float vx, float xy, float angle, BufferedImage img, Tank t) {
         this.x = x;
         this.y = y;
         this.vx = vx;
         this.vy = vy;
         this.img = img;
         this.angle = angle;
         this.hitBox.setLocation((int) this.x, (int) this.y);
         this.myTank = t;
     }

    void update() {
        moveForwards();
    }

    public void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        moveBound();
    }

    private void moveBound() {
        this.hitBox.setLocation((int) this.x, (int) this.y);
    }

    public void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_WORLD_WIDTH - 88) {
            x = GameConstants.GAME_WORLD_WIDTH - 88;
        }
        if (y < 30) {
            y = 30;
        }
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 80) {
            y = GameConstants.GAME_WORLD_HEIGHT - 80;
        }
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

//    @Override
//    public String toString() {
//        return "x=" + x + ", y=" + y + ", angle=" + angle;
//    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth(null) / 2.0, this.img.getHeight(null) / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

        g2d.setColor(Color.RED);
        g2d.drawRect((int) x, (int) y, this.img.getWidth(null), this.img.getHeight(null));
    }
}
