package tankgame.game;

import java.awt.*;

public interface Collidable {
    default boolean isCollidable() {
        if (this instanceof Collidable) {
            return true;
        }
        return false;
    }

    Rectangle getHitBox();

    default void handleCollision(Collidable obj) {
    }

}
