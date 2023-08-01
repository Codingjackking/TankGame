package tankgame.game.movable;

import tankgame.GameConstants;
import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.Tank;
import tankgame.game.immovable.BreakableWall;
import tankgame.game.immovable.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Timer;

public class Bullet extends GameObject implements Collidable {
    private Rectangle hitBox;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;
    private Tank myTank;
    private Bullet b;
    private final float R = 3;
    private  BufferedImage img;
    private boolean isActive = true;
    private boolean hidden = true;

    public Bullet(float x, float y, float angle, BufferedImage img, Tank myTank) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.img = img;
        this.angle = angle;
        this.hitBox = new Rectangle((int) x, (int) y,this.img.getWidth(), this.img.getHeight());
        this.myTank = myTank;
    }
    public void update() {
        moveForwards();
    }

    public void hidden() {
        hidden = false;
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
            isActive = false;
        }
        if (x >= GameConstants.GAME_WORLD_WIDTH - 88) {
            x = GameConstants.GAME_WORLD_WIDTH - 88;
            isActive = false;
        }
        if (y < 30) {
            y = 30;
            isActive = false;
        }
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 80) {
            y = GameConstants.GAME_WORLD_HEIGHT - 80;
            isActive = false;
        }
    }

    public boolean myTankCheck(Tank check) {
        if (this.myTank.equals(check)) {
            return true;
        } else {
            return false;
        }
    }
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }
    @Override
    public void handleCollision(Collidable obj) {
        if (isActive) {
            if (obj instanceof Tank) {
                if (!myTankCheck((Tank) obj)) {
                    ((Tank) obj).hitTank();
                    isActive = false;
                }
            } else if (obj instanceof BreakableWall) {
                ((BreakableWall) obj).hitWall();
                isActive = false;
            } else if (obj instanceof Wall) {
                isActive = false;
            }
        }
    }
    @Override
    public void drawImage(Graphics g) {
        if ((isDrawable() && hidden == false)) {
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth(null) / 2.0, this.img.getHeight(null) / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);
            if (b != null) {
                this.b.drawImage(g);
            }
        }
    }

    @Override
    public boolean isDrawable() {
        return isActive;
    }
}
