package tankgame.game;

import tankgame.GameConstants;
import tankgame.Resources.ResourceManager;
import tankgame.game.movable.Bullet;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author anthony-pc
 */
public class Tank  {

    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;

    private final Rectangle hitBox;
    private final float R = 5;
    private final float ROTATIONSPEED = 3.0f;

    private int health = 5;
    private final int life = 3;
    Bullet b;
    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.hitBox = new Rectangle((int) x, (int) y,this.img.getWidth(), this.img.getHeight());

    }

    void setX(float x){ this.x = x; }

    void setY(float y) { this. y = y;}

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }
    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }
    void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed  && GameWorld.tickCount % 40 == 0) {

        }
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        moveBound();
    }

//    @Override
    public void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        moveBound();
    }

    public int getLife() {
        return this.life;
    }

    public void hitTank() {
        if (this.health > 1) {
            --this.health;
        } else if (this.life > 1) {
        }
    }

    private void moveBound() {
//        super.setHitBox();
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

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

//    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth(null) / 2.0, this.img.getHeight(null) / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

        g2d.setColor(Color.RED);
        g2d.drawRect((int) x, (int) y, this.img.getWidth(null), this.img.getHeight(null));

        // Draw the white background of the health bar
        g.setColor(Color.WHITE);
        g.fillRect((int) x, (int) (y - 20), 50, 8);

        // Draw the green part representing the health
        g.setColor(Color.GREEN);
        g.fillRect((int) x, (int) (y - 20), 10 * health, 8);

        //Drawing the green part representing lifes
        g.setColor(Color.GREEN);
        for (int i = 0; i < life; i++) {
            g.fillOval(((int) x + this.img.getWidth(null) / 2 - (3 * 10 + 2 * 5) / 2) + (10 + 5) * i,
                    (int) y + this.img.getHeight(null) + 5,
                    10,
                    10);
        }
        if (b != null) {
            this.b.drawImage(g2d);
        }
    }


}
