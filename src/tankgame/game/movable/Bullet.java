package tankgame.game.movable;

import tankgame.GameConstants;
import tankgame.Resources.ResourceManager;
import tankgame.game.Animations;
import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.MapLoader;
import tankgame.game.immovable.Walls.BreakableWall;
import tankgame.game.immovable.Walls.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends MovableObjects implements Collidable { private Rectangle hitBox;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;
    private Tank tank;
    private boolean isCollidable;
    private Bullet b;
    private final float R = 3;
    private  BufferedImage img;
    private boolean isActive = true;
    private boolean hidden = true;
    private int damage;

    public Bullet(MapLoader ml, Tank tank, float x, float y, float angle, BufferedImage img) {
        super(ml, x,y,angle,img);
        this.x = x;
        this.y = y;
        this.img = img;
        this.angle = angle;
        this.hitBox = new Rectangle((int) x, (int) y,this.img.getWidth(), this.img.getHeight());
        this.isCollidable = true;
        this.tank = tank;
        this.damage = tank.getDamage();
    }
    public void update(MapLoader ml) {
        this.moveForwards();
        this.checkBorder();
        this.moveBound();
        this.runCollisions();
    }

    public void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
//        checkBorder();
        moveBound();
    }

    private void moveBound() {
        this.hitBox.setLocation((int) this.x, (int) this.y);

    }

    public void checkBorder() {
        if (x < 30) {
            x = 30;
            this.destroy();
        }
        if (x >= GameConstants.GAME_WORLD_WIDTH - 88) {
            x = GameConstants.GAME_WORLD_WIDTH - 88;
            this.destroy();
        }
        if (y < 30) {
            y = 30;
            this.destroy();
        }
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 80) {
            y = GameConstants.GAME_WORLD_HEIGHT - 80;
            this.destroy();
        }
    }

//    public boolean myTankCheck(Tank check) {
//        if (this.myTank.equals(check)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    private void runCollisions() {
        for(GameObject obj : this.ml.getGameObjects()) {
            if(!obj.equals(this) && obj instanceof Collidable && this.checkCollision((Collidable) obj)) {
                collide((Collidable) obj);
            }
        }
    }

    public boolean myTankCheck(Tank check) {
        if(this.tank.equals(check)) {
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public boolean isCollidable() {
        return isCollidable;
    }

    @Override
    public void destroy() {
        this.destroyed = true;
        tank.removeBullet(this);
    }

    @Override
    public void collide(Collidable obj) {
        if (obj instanceof Tank) {
            if (!myTankCheck((Tank) obj)) {
                ((Tank) obj).hitTank(damage);
//                ((Tank) obj).removeHealth(damage);
                ml.anims.add(new Animations(x, y, ResourceManager.getAnimation("bullethit")));
                this.destroy();
            }
        } else if (obj instanceof Wall && obj.isCollidable()) {
            ml.anims.add(new Animations(x, y, ResourceManager.getAnimation("bullethit")));
            this.destroy();
            if (obj instanceof BreakableWall) {
                ((BreakableWall) obj).removeHealth(this.damage);
                if (((BreakableWall) obj).getHealth() == 0) {
                    this.destroy();
                }
            }
        }
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }
    @Override
    public boolean checkCollision(Collidable with) {
        return this.getHitBox().intersects(with.getHitBox());
    }

    @Override
    public void drawImage(Graphics g) {
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth(null) / 2.0, this.img.getHeight(null) / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);
    }

    @Override
    public void reset() {
        for (Bullet b : tank.getAmmo()) {
            ml.removeGameObject(b);
        }
        tank.getAmmo().clear();
    }
}
