package tankgame.game.movable;

import tankgame.GameConstants;
import tankgame.Resources.ResourceManager;

import tankgame.game.*;
import tankgame.game.immovable.PowerUps.PowerUp;
import tankgame.game.immovable.Walls.BreakableWall;
import tankgame.game.immovable.Walls.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 *
 * @author anthony-pc
 */
public class Tank extends MovableObjects implements Collidable {
    private MapLoader ml;
    private float x;
    private float y;
    private float originalX;
    private float originalY;
    private float vx;
    private float vy;
    private float angle;

    private Rectangle hitBox;
    private float R = 2;
    private final float ROTATIONSPEED = 3.0f;

    private int health = 5;
    private int life = 3;
    private int shieldHealth;
    private int speedBoostDuration = 0;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private final List<Bullet> ammo;
    private Bullet b;
    private long timeSinceLastShot = 0L;
    private long cooldown = 2000;
    private int damage;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public BufferedImage getImg() {
        return img;
    }

    public Tank(MapLoader ml, float x, float y, float vx, float vy, float angle, BufferedImage img) {
        super(ml, x, y, angle, img);
        this.x = x;
        this.y = y;
        this.originalX = x;
        this.originalY = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.ml = ml;
        this.angle = angle;
        this.hitBox = new Rectangle((int) x, (int) y, this.img.getWidth(), this.img.getHeight());
        this.ammo = new ArrayList<>();
        this.damage = 1;
        this.shieldHealth = 0;
    }

    public List<Bullet> getAmmo() {
        return ammo;
    }


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

    @Override
    public void update(MapLoader ml) {
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
            this.fireBullet();
        }
        this.checkBorder();
        this.moveBound();
        this.runCollisions();
        if (this.health <= 0) {
            this.die();
        }
        if (this.life <= 0) {
            this.destroy();
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

    private void die() {
        this.life--;
        this.health = 5;
    }

    // Method to fire the bullet
    private void fireBullet() {
        int startX = (int) ((this.x + 13) + (37 * (int) Math.round(Math.cos(Math.toRadians(angle)))));
        int startY = (int) ((this.y + 9) + (37 * (int) Math.round(Math.sin(Math.toRadians(angle)))));
        Bullet b = new Bullet(ml, this, startX, startY, angle, ResourceManager.getSprite("bullet"));
        this.ammo.add(b);
        ml.addGameObject(b);
        ml.anims.add(new Animations(startX,startY,ResourceManager.getAnimation("bulletshoot")));
        ResourceManager.getSound("shotfire").playSound();
    }

    // Method to add health when the tank collides with the health power-up
    public void addHealth() {
        if (health < 5) {
            health++;
        }
    }

    public void hitTank(int damage) {
        if (shieldHealth > 0) {
            // If shield exists, reduce shield health
            shieldHealth -= damage;
            if (shieldHealth < 0) {
                // If shield health goes below zero, apply remaining damage to tank health
                removeHealth(Math.abs(shieldHealth));
                shieldHealth = 0;
            }
        }
        else {
            // If no shield, apply damage directly to tank health
            removeHealth(damage);
        }
    }
    public void removeHealth(int damage) {
        if (shieldHealth > 0) {
            // If shield is active, apply damage to shield first
            if (shieldHealth >= damage) {
                shieldHealth -= damage;
            } else {
                // If shield health is not sufficient to absorb all the damage, subtract from shield and the rest from health
                int remainingDamage = damage - shieldHealth;
                shieldHealth = 0;
                health -= remainingDamage;
            }
        } else {
            // If no shield, directly apply damage to health
            health -= damage;
        }
    }

    public void addLife() {
        if (life < 3) {
            life += 1;
        }
    }

    public int getLife() {
        return this.life;
    }

    public void addDamage() {
        if (damage < 5) {
            this.damage++;
        }
    }
    public int getDamage() {
        return damage;
    }

    // Method to add shield when the tank collides with the shield power-up
    public void addShield() {
        shieldHealth += 1;
    }

    public void addSpeedBoost() {
        speedBoostDuration = 1000; // Set the duration of the speed boost in milliseconds (adjust as needed)
        R = 3; // Set the speed of the tank during the speed boost (adjust as needed)
    }
    public void removeBullet(Bullet bullet) {
        ammo.remove(bullet);
    }

    private void runCollisions() {
        for (GameObject o : ml.getGameObjects()) {
            if (!o.equals(this) && o instanceof Collidable && this.checkCollision((Collidable) o)) {
                collide((Collidable) o);
            }
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

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void collide(Collidable obj) {
        if (obj.isCollidable()) {
            if (obj instanceof Tank) {
                this.push((GameObject) obj);
            }
            if (obj instanceof Wall) {
                this.bump();
            }
            if (obj instanceof BreakableWall) {
                this.bump();
            }
            if (obj instanceof PowerUp) {
                ((PowerUp) obj).apply(this);
                ml.anims.add(new Animations(x,y,ResourceManager.getAnimation("powerpick")));
                ResourceManager.getSound("pickup").playSound();
                obj.destroy();
            }
        }
    }

    private void bump() {
        if (this.UpPressed) {
            if (vx != 0) {
                x -= vx;
            }
            if (vy != 0) {
                y -= vy;
            }
        }
        if (this.DownPressed) {
            if (vx != 0) {
                x += vx;
            }
            if (vy != 0) {
                y += vy;
            }
        }
    }

    private void push(GameObject obj) {
        vx = (int) Math.round(R / 2 * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R / 2 * Math.sin(Math.toRadians(angle)));
        if (this.UpPressed) {
            x -= vx * 4;
            y -= vy * 4;
            obj.setX(obj.getX() + vx);
            obj.setY(obj.getY() + vy);
        }
        if (this.DownPressed) {
            x += vx * 4;
            y += vy * 4;
            obj.setX(obj.getX() - vx);
            obj.setY(obj.getY() - vy);
        }
    }

    public void reset(int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        reset();
    }

    @Override
    public void reset() {
        this.destroyed = false;
        this.unToggleDownPressed();
        this.unToggleLeftPressed();
        this.unToggleRightPressed();
        this.unToggleUpPressed();
        this.unToggleShootPressed();
        this.damage = 1;
        this.health = 5;
        this.life = 3;
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public boolean checkCollision(Collidable with) {
        return this.getHitBox().intersects(with.getHitBox());
    }



    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth(null) / 2.0, this.img.getHeight(null) / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

        //Drawing the color of the hitbox
        g2d.setColor(Color.RED);
        g2d.drawRect((int) x, (int) y, this.img.getWidth(null), this.img.getHeight(null));
        g2d.setColor(Color.blue);
        g2d.drawRect((int) x, (int) y - 10, 10 * health, 8);
        //Shooting Cooldown Timer;
        long currentWidth = 50 - ((this.timeSinceLastShot + this.cooldown) - System.currentTimeMillis()) / 40;
        if (currentWidth > 50) {
            currentWidth = 50;
        }
        g2d.fillRect((int) x, (int) y - 10, (int) currentWidth, 8);

        // Draw the cyan shield bar above the health bar
        g2d.setColor(Color.CYAN);
        g2d.fillRect((int) x, (int) y - 22, 10 * shieldHealth, 8);

        // Draw the white background of the health bar
        g.setColor(Color.WHITE);
        g.fillRect((int) x, (int) (y - 16), 50, 8);

        // Draw the red part representing the health
        g.setColor(Color.RED);
        g.fillRect((int) x, (int) (y - 16), 10 * health, 8);

        //Drawing the green part representing life
        g.setColor(Color.GREEN);
        for (int i = 0; i < this.life; i++) {
            g.fillOval(((int) x + this.img.getWidth(null) / 2 - (3 * 10 + 2 * 5) / 2) + (10 + 5) * i,
                    (int) y + this.img.getHeight(null) + 5,
                    10,
                    10);
        }
    }

}
