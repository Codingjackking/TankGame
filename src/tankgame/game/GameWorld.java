package tankgame.game;


import tankgame.GameConstants;
import tankgame.Launcher;
import tankgame.Resources.ResourceManager;
import tankgame.game.movable.Tank;
import tankgame.game.movable.TankControl;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private MapLoader ml;
    private Clip music;
    private final Launcher lf;
    private long tick = 0;
    static long tickCount = 0;
//    List<Animations> anims = new ArrayList<Animations>();

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
            this.resetGame();
            while (true) {
                this.tick++;
                ml.updateMap();
                ml.clearDeadObjects();
                this.repaint();   // redraw game
                this.ml.anims.forEach(Animations::update);
                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our
                 * loop run at a fixed rate per/sec.
                */
                Thread.sleep(1000 / 144);

                if (t1.getLife() == 0) {
                    Thread.sleep(2000);
//                    music.stop();
                    this.lf.setFrame("Tank2Wins");
                    return;
                }
                if (t2.getLife() == 0) {
                    Thread.sleep(2000);
//                    music.stop();
                    this.lf.setFrame("Tank1Wins");
                    return;
                }
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
//        try {
////            AudioInputStream music1 = AudioSystem.getAudioInputStream((InputStream) ResourceManager.getSound("music1"));
////            music = AudioSystem.getClip();
////            music.open(music1);
////            music.start();
////            music.loop(5);
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            throw new RuntimeException(e);
//        }
        ml.resetMap();
        this.resetTanks();
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,
                GameConstants.GAME_WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        ml = new MapLoader();
        this.ml.initMap();
        t1 = new Tank(ml,50, 500, 0, 0, (short) 0, ResourceManager.getSprite("tank1"));
        t2 = new Tank(ml,1830, 500, 0, 0, (short) 0, ResourceManager.getSprite("tank2"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        ml.addGameObject(t1);
        ml.addGameObject(t2);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);

    }

    private void resetTanks() {
        t1.reset(GameConstants.TANK1_START_X, GameConstants.TANK1_START_Y, GameConstants.TANK1_START_ANGLE);
        t2.reset(GameConstants.TANK2_START_X, GameConstants.TANK2_START_Y, GameConstants.TANK2_START_ANGLE);

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
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0, GameConstants.GAME_WORLD_WIDTH, GameConstants.GAME_WORLD_HEIGHT);
        ml.drawFloors(buffer);
        ml.drawGameObjects(buffer);
        ml.anims.forEach(animations -> animations.drawImage(buffer));
        renderSplitScreens(g2, world);
        renderMiniMap(g2, world);
    }
}
