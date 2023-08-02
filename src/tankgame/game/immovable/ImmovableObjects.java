package tankgame.game.immovable;

import tankgame.game.GameObject;

import java.awt.image.BufferedImage;

public abstract class ImmovableObjects extends GameObject {
    protected ImmovableObjects(float x, float y, BufferedImage img) {
        super(x,y,img);
    }
}
