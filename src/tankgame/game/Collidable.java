package tankgame.game;

import java.awt.*;

public interface Collidable {
    boolean isCollidable();
    void destroy();
    void collide(Collidable obj);
    Rectangle getHitBox();

    boolean checkCollision(Collidable with);
}
