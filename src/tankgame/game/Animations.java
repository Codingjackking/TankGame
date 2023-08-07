package tankgame.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Animations {
    private float x,y;
    private List<BufferedImage> frames;
    private long timeSinceUpdate = 0;
    private long delay = 40;

    private int currentFrame = 0;
    private boolean isRunning = false;

    public Animations(float x, float y, List<BufferedImage> frames) {
        this.x = x - frames.get(0).getWidth()/2f;
        this.y = y - frames.get(0).getHeight()/2f;
        this.frames = frames;
        isRunning = true;
        currentFrame = 0;
    }

    public void update() {
        if (timeSinceUpdate + delay < System.currentTimeMillis()) {
            this.timeSinceUpdate = System.currentTimeMillis();
            this.currentFrame++;
//            this.currentFrame = (currentFrame + 1) % this.frames.size();
            if (this.currentFrame == this.frames.size()) {
                isRunning = false;
            }
        }
    }

    public void drawImage(Graphics2D g) {
        if (isRunning) {
            g.drawImage(this.frames.get(currentFrame), (int) x, (int) y, null);
        }
    }
}
