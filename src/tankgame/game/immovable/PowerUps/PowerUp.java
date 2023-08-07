package tankgame.game.immovable.PowerUps;

import tankgame.game.Collidable;
import tankgame.game.immovable.ImmovableObjects;
import tankgame.game.movable.Tank;
import java.awt.image.BufferedImage;

public abstract class PowerUp extends ImmovableObjects implements Collidable {
    protected PowerUp(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    public abstract void apply(Tank tank);
}
