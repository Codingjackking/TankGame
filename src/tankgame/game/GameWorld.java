package tankgame.game;


import tankgame.GameConstants;
import tankgame.Launcher;
import tankgame.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private Player p1;
    private Player p2;

    private final Launcher lf;
    private long tick = 0;

    List<GameObject> gobjs = new ArrayList<>(1000);
    /**
     *
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
        ResourceManager.loadResources();
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update();
                this.repaint();   // redraw game
                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our
                 * loop run at a fixed rate per/sec.
                */
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
        this.t2.setX(600);
        this.t2.setY(300);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResourceAsStream("maps/map1.csv")));
        try (BufferedReader mapReader = new BufferedReader (isr)) {
            int row = 0;
            String[] gameItems;
            while (mapReader.ready()) {
                gameItems = mapReader.readLine().strip().split(",");
                for (int col = 0; col < gameItems.length; col++) {
                    //System.out.printf("%d ::: %d\n",row,col);
                    String gameObject = gameItems[col];
                    if ("0".equals(gameObject)) continue;
                    this.gobjs.add(GameObject.newInstance(gameObject,col*30, row*30));
                    }
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        t1 = new Tank(300, 300, 0, 0, (short) 0, ResourceManager.getSprite("tank1"));
        p1 = new Player(t1);
        t2 = new Tank(900, 300, 0, 0, (short) 0, ResourceManager.getSprite("tank2"));
        p2 = new Player(t2);

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.lf.getJf().addKeyListener(tc2);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.drawImage(ResourceManager.getSprite("bg"), 0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, null);
        this.gobjs.forEach(gameObject -> gameObject.drawImage(buffer));
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        g2.drawImage(world, 0, 0, null);
    }
}
