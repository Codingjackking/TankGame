package tankgame.game;

import tankgame.GameConstants;
import tankgame.Resources.ResourceManager;

import tankgame.game.movable.Bullet;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject implements Collidable {

    private float originalX;
    private float originalY;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;

    private Rectangle hitBox;
    private float R = 2;
    private final float ROTATIONSPEED = 3.0f;

    private int health = 5;
    private int life = 3;
    private int shieldHealth = 0;
    private int speedBoostDuration = 0;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private final List<Bullet> ammo;
    private List<Bullet> toRemove = new ArrayList<>();
    private Bullet b;

    private long timeSinceLastShot = 0L;
    private long cooldown = 4000;
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public BufferedImage getImg() {
        return img;
    }

    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.originalX = x;
        this.originalY = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.hitBox = new Rectangle((int) x, (int) y,this.img.getWidth(), this.img.getHeight());
        this.ammo = new ArrayList<>();
    }

    List<Bullet> getAmmo() {
        return ammo;
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
        if (this.ShootPressed  && (this.timeSinceLastShot + this.cooldown) < System.currentTimeMillis()) {
            this.timeSinceLastShot = System.currentTimeMillis();
            fireBullet();
        }
        this.ammo.forEach(Bullet::update);
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet b : ammo) {
            if (b.getHitBox().intersects(this.getHitBox())) {
                b.update();
            } else {
                b.hidden();
                b.update();
            }
            if (!b.isDrawable()) {
                toRemove.add(b);
            }
        }
        ammo.removeAll(toRemove);
        // Handle speed boost duration
        if (speedBoostDuration > 0) {
            speedBoostDuration -= 1000 / 144;
            if (speedBoostDuration <= 0) {
                R = 2; // Reset the speed back to the default value after the speed boost duration is over
            }
        }

        // Handle shield health
        if (shieldHealth > 0) {
            shieldHealth--;
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

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        moveBound();
    }
    // New method to calculate the bullet starting position
    private Point getBulletStartPos() {
        int bx = (int) (x + img.getWidth() / 2.0 - 5 + 20 * Math.cos(Math.toRadians(angle)));
        int by = (int) (y + img.getHeight() / 2.0 - 5 + 20 * Math.sin(Math.toRadians(angle)));
        return new Point(bx, by);
    }

    // Method to fire the bullet
    private void fireBullet() {
        // Get the starting position for the bullet
        Point bulletStartPos = getBulletStartPos();

        // Create the bullet at the calculated starting position
        Bullet b = new Bullet(bulletStartPos.x, bulletStartPos.y, angle, ResourceManager.getSprite("bullet"), this);
        this.ammo.add(b);
    }

    @Override
    public void handleCollision(Collidable obj) {
        if (obj instanceof Bullet) {
            this.hitTank();
            obj.handleCollision(this);
        } else {
            int newX = (int) this.x;
            int newY = (int) this.y;
            if(this.DownPressed) {
                newX += this.vx;
                newY += this.vy;
            }
            if(this.UpPressed) {
                newX -= this.vx;
                newY -= this.vy;
            }
            this.setX(newX);
            this.setY(newY);
        }

    }

    // Method to add health when the tank collides with the health power-up
    public void addHealth() {
        if (health < 5) {
            health++;
        }
    }

    // Method to add shield when the tank collides with the shield power-up
    public void addShield() {
        shieldHealth = 2;
    }

    // Method to add a life when the tank collides with the lives power-up
    public void addLife() {
        life++;
    }

    // Method to add a temporary speed boost when the tank collides with the speed power-up
    public void addSpeedBoost() {
        speedBoostDuration = 500; // Set the duration of the speed boost in milliseconds (adjust as needed)
        R = 5; // Set the speed of the tank during the speed boost (adjust as needed)
    }

    public int getLife() {
        return this.life;
    }

    public void hitTank() {
        if (this.health > 1) {
            this.health--;
        } else if (this.life > 1) {
            setX(originalX);
            setY(originalY);
            this.R = 2;
            this.health = 5;
            this.life--;
            moveBound();
        } else {
            this.life = 0;
        }
    }

    private void moveBound() {
        this.hitBox.setLocation((int) this.x, (int) this.y);
    }

    private void checkBorder() {
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

    public void setShieldHealth(int shieldHealth) {
        this.shieldHealth = shieldHealth;
    }

    public int getShieldHealth() {
        return shieldHealth;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    public void drawImage(Graphics g) {
        if (isDrawable()) {
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth(null) / 2.0, this.img.getHeight(null) / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);

            g2d.setColor(Color.RED);
            g2d.drawRect((int) x, (int) y, this.img.getWidth(null), this.img.getHeight(null));
            this.ammo.forEach(b -> b.drawImage(g2d));
//            g2d.setColor(Color.GREEN);
//            g2d.drawRect((int) x, (int) y - 20, 100, 15);
//            //Shooting Cooldown Timer;
//            long currentWidth = 100 - ((this.timeSinceLastShot + this.cooldown) - System.currentTimeMillis()) / 40;
//            if (currentWidth > 100) {
//                currentWidth = 100;
//            }
//            g2d.fillRect((int) x, (int) y - 20, (int) currentWidth, 15);

        }
        // Draw the white background of the health bar
        g.setColor(Color.WHITE);
        g.fillRect((int) x, (int) (y - 20), 50, 8);

        // Draw the green part representing the health
        g.setColor(Color.GREEN);
        g.fillRect((int) x, (int) (y - 20), 10 * health, 8);

        //Drawing the green part representing lifes
        g.setColor(Color.GREEN);
        for (int i = 0; i < this.life; i++) {
            g.fillOval(((int) x + this.img.getWidth(null) / 2 - (3 * 10 + 2 * 5) / 2) + (10 + 5) * i,
                    (int) y + this.img.getHeight(null) + 5,
                    10,
                    10);
        }
    }

    @Override
    public boolean isDrawable() {
        return life != 0;
    }
}
