package tankgame.game.immovable.Walls;

import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.immovable.ImmovableObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends ImmovableObjects {
    protected Wall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }
}
