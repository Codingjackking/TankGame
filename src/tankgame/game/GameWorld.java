package tankgame.game;


import tankgame.GameConstants;
import tankgame.Launcher;
import tankgame.Resources.ResourceManager;
import tankgame.game.movable.Bullet;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
    private Clip music;
    private final Launcher lf;
    private long tick = 0;
    static long tickCount = 0;

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
//                tickCount++;
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
        try {
            AudioInputStream music1 = AudioSystem.getAudioInputStream((InputStream) ResourceManager.getSound("music1"));
            music = AudioSystem.getClip();
            music.open(music1);
            music.start();
            music.loop(5);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,
                GameConstants.GAME_WORLD_HEIGHT,
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

        t1 = new Tank(50, 500, 0, 0, (short) 0, ResourceManager.getSprite("tank1"));
        t2 = new Tank(1830, 500, 0, 0, (short) 0, ResourceManager.getSprite("tank2"));

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.lf.getJf().addKeyListener(tc2);
    }

    private void drawFloor(Graphics2D buffer) {
        BufferedImage floor = ResourceManager.getSprite("bg");
        for (int i = 0; i < GameConstants.GAME_WORLD_WIDTH; i +=320) {
            for (int j = 0; j < GameConstants.GAME_WORLD_HEIGHT; j += 240) {
                buffer.drawImage(floor, i, j, null);
            }
        }
    }

    private BufferedImage checkDimension(Tank tank) {
        BufferedImage checked;
        int x, y;
        y = Math.max(0, Math.min((int) (tank.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2), GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT));
        x = Math.max(0, Math.min((int) (tank.getX() - GameConstants.GAME_SCREEN_WIDTH / 4), GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2));
        checked = world.getSubimage(x, y, GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        return checked;
    }
    public void renderSplitScreens(Graphics2D g2, BufferedImage world) {
        // Split Screen for both tanks
        BufferedImage leftHalf = checkDimension(t1);
        BufferedImage rightHalf = checkDimension(t2);
        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2 + 5, 0, null);
    }
    public void renderMiniMap(Graphics2D g2, BufferedImage world) {
        // Creates the mini map
        BufferedImage miniMap = world.getSubimage(0,0,GameConstants.GAME_WORLD_WIDTH, GameConstants.GAME_WORLD_HEIGHT);
        g2.scale(.12, .12);
        // Draws mini map
        g2.drawImage(miniMap, (int) ((double)GameConstants.GAME_SCREEN_WIDTH / .24 - miniMap.getWidth() / 2), (int) ((double)GameConstants.GAME_SCREEN_HEIGHT / .24 - miniMap.getWidth() / 2),null);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        this.drawFloor(buffer);
        this.gobjs.forEach(gameObject -> gameObject.drawImage(buffer));
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        renderSplitScreens(g2, world);
        renderMiniMap(g2, world);
    }

}
