package tankgame.game;


import javax.sound.sampled.*;

public class Sound {
    private Clip sound;

    public Sound (Clip sound) {
        this.sound = sound;
    }

    public void playSound() {
        if (this.sound.isRunning()) {
            this.sound.stop();
        }
        this.sound.setFramePosition(0);
        this.sound.start();
    }

    public void setLooping() {
        this.sound.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (this.sound.isRunning()) {
            this.sound.stop();
        }
    }

    public void setVolume(float level) {
        FloatControl volume = (FloatControl) this.sound.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(20.0f * (float) Math.log10(level));
    }
}
