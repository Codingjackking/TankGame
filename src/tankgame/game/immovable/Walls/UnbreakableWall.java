package tankgame.game.immovable.Walls;

import tankgame.game.Collidable;
import tankgame.game.MapLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall implements Collidable  {
    BufferedImage img;
    boolean isCollidable;
    private Rectangle hitBox;

    public UnbreakableWall(float x, float y, BufferedImage img) {
        super(x,y,img);
        this.img = img;
        this.hitBox = new Rectangle((int) x, (int) y,this.img.getWidth(), this.img.getHeight());
        this.isCollidable = true;
    }

    @Override
    public void update(MapLoader ml) {

    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public boolean checkCollision(Collidable with) {
        return false;
    }
    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void collide(Collidable with) {
    }
    @Override
    public void drawImage(Graphics buffer) {
        Graphics2D g2d = (Graphics2D) buffer;
        g2d.drawImage(this.img, (int) x, (int) y, null);
    }

    @Override
    public void reset() {
        this.destroyed = false;
    }

}
