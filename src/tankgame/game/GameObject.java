package tankgame.game;

import tankgame.Resources.ResourceManager;
import tankgame.game.immovable.PowerUps.*;
import tankgame.game.immovable.Walls.BreakableWall;
import tankgame.game.immovable.Walls.UnbreakableWall;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class GameObject {
    protected float x;
    protected float y;
    protected boolean destroyed;
    protected BufferedImage img;
    public GameObject(float x, float y, BufferedImage img) {
        this.x =  x;
        this.y = y;
        this.img = img;
        this.destroyed = false;
    }

    public static GameObject newInstance(String type, float x, float y) {
        return switch (type) {
            case "9", "2" -> new UnbreakableWall(x, y, ResourceManager.getSprite("unbreak"));
            case "3" -> new BreakableWall(x, y, ResourceManager.getSprite("break1"));
            case "4" -> new Health(x, y, ResourceManager.getSprite("health"));
            case "5" -> new Shield(x, y, ResourceManager.getSprite("shield"));
            case "6" -> new Speed(x, y, ResourceManager.getSprite("speed"));
            case "7" -> new DamageUP(x, y, ResourceManager.getSprite("dmgup"));
            case "8" -> new Lives(x, y, ResourceManager.getSprite("lives"));
            default -> throw new UnsupportedOperationException();
        };
    }
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public abstract void update(MapLoader ml);
    public abstract void drawImage(Graphics g);

    public abstract void reset();
}
