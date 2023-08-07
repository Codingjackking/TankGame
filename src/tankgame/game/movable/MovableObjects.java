package tankgame.game.movable;

import tankgame.game.GameObject;
import tankgame.game.MapLoader;
import java.awt.image.BufferedImage;

public abstract class MovableObjects extends GameObject {
    MapLoader ml;
    float angle;

    MovableObjects(MapLoader ml, float x, float y, float angle, BufferedImage img) {
        super(x,y ,img);
        this.angle = angle;
        this.img = img;
        this.ml = ml;
    }
}
