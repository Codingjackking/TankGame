package tankgame.game;

import tankgame.GameConstants;
import tankgame.Resources.ResourceManager;
import tankgame.game.immovable.PowerUps.*;
import tankgame.game.immovable.Walls.BreakableWall;
import tankgame.game.immovable.Walls.UnbreakableWall;
import tankgame.game.movable.Bullet;
import tankgame.game.movable.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MapLoader {
    private List<GameObject> gobjs;
    private GameWorld gw;
    public List<Animations> anims = new ArrayList<Animations>();
    public List<GameObject> getGameObjects() {
        return gobjs;
    }

    public void addGameObject(GameObject obj) {
        this.gobjs.add(obj);
    }
    public void removeGameObject(GameObject obj) {
        this.gobjs.remove(obj);
    }

    void resetMap() {
        gobjs.forEach(GameObject::reset);
    }

    void clearDeadObjects() {
        this.gobjs.removeIf(gameObject -> gameObject.isDestroyed());
    }

    void updateMap() {
        for (int i = 0; i < gobjs.size(); i++) {
            gobjs.get(i).update(this);
        }
    }

    void initMap() {
        this.gobjs = new ArrayList<>();

            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(GameWorld.class.getClassLoader().getResourceAsStream("maps/map1.csv")));
            this.anims.add(new Animations(300,300,ResourceManager.getAnimation("bulletshoot")));
            try (BufferedReader mapReader = new BufferedReader(isr)) {
                int row = 0;
                String[] gameItems;
                while (mapReader.ready()) {
                    gameItems = mapReader.readLine().strip().split(",");
                    for (int col = 0; col < gameItems.length; col++) {
                        //System.out.printf("%d ::: %d\n",row,col);
                        String gameObject = gameItems[col];
                        if ("0".equals(gameObject)) continue;
                        this.gobjs.add(GameObject.newInstance(gameObject, col * 30, row * 30));
                    }
                    row++;
                }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    void drawFloors(Graphics g) {
        BufferedImage floor = ResourceManager.getSprite("bg");
        for (int i = 0; i < GameConstants.GAME_WORLD_WIDTH; i +=320) {
            for (int j = 0; j < GameConstants.GAME_WORLD_HEIGHT; j += 240) {
                g.drawImage(floor, i, j, null);
            }
        }
    }

    void drawGameObjects(Graphics g) {
        for(int i = 0; i<gobjs.size(); i++) {
            gobjs.get(i).drawImage(g);
        }
    }


}