package tankgame.game.immovable.PowerUps;

import tankgame.resources.ResourceManager;
import tankgame.game.Collidable;
import tankgame.game.MapLoader;
import tankgame.game.movable.Tank;
import java.awt.*;
import java.awt.image.BufferedImage;

public class  DamageUP extends PowerUp {
    float x,y;
    BufferedImage img;
    private Rectangle hitBox;
    private boolean isCollidable;
    private int damage;

    public DamageUP(float x, float y, BufferedImage img) {
        super(x,y,img);
        this.x = x;
        this.y = y;
        this.img = img;
        this.isCollidable = true;
        this.hitBox = new Rectangle((int) x, (int) y,this.img.getWidth(), this.img.getHeight());
        this.damage = 1;
    }

    @Override
    public void update(MapLoader ml) {
    }

    @Override
    public void apply(Tank tank) {
        tank.addDamage();
        this.img = ResourceManager.getSprite("empty");
    }

    @Override
    public boolean isCollidable() {
        return isCollidable;
    }

    @Override
    public void destroy() {
        this.destroyed = true;
        this.isCollidable = false;
    }

    @Override
    public void collide(Collidable obj) {
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public boolean checkCollision(Collidable obj) {
        return false;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, (int) x, (int) y, null);
    }

    @Override
    public void reset() {
        this.destroyed = false;
        this.isCollidable = true;
        this.img = ResourceManager.getSprite("dmgup");
    }
}
