package tankgame.game;

import tankgame.Resources.ResourceManager;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

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
        this.sound.setFramePosition(0);
        this.sound.start();
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
//    default void bullSounds() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        Clip bullet;
//        AudioInputStream music = AudioSystem.getAudioInputStream((InputStream) ResourceManager.getSound("shot"));
//        bullet = AudioSystem.getClip();
//        bullet.open(music);
//        bullet.start();
//    }
//
//    default void bullFire() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        Clip bullet;
//        AudioInputStream music = AudioSystem.getAudioInputStream((InputStream) ResourceManager.getSound("shotfire"));
//        bullet = AudioSystem.getClip();
//        bullet.open(music);
//        bullet.start();
//    }
//
//    default void bullBoom() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
//        Clip bullet;
//        AudioInputStream music = AudioSystem.getAudioInputStream((InputStream) ResourceManager.getSound("shotboom"));
//        bullet = AudioSystem.getClip();
//        bullet.open(music);
//        bullet.start();
//    }
}
