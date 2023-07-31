package tankgame.game;

import tankgame.Resources.ResourceManager;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public interface Sounds {
    default void bullSounds() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Clip bullet;
        AudioInputStream music = AudioSystem.getAudioInputStream((InputStream) ResourceManager.getSound("shot"));
        bullet = AudioSystem.getClip();
        bullet.open(music);
        bullet.start();
    }

    default void bullFire() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Clip bullet;
        AudioInputStream music = AudioSystem.getAudioInputStream((InputStream) ResourceManager.getSound("shotfire"));
        bullet = AudioSystem.getClip();
        bullet.open(music);
        bullet.start();
    }

    default void bullBoom() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        Clip bullet;
        AudioInputStream music = AudioSystem.getAudioInputStream((InputStream) ResourceManager.getSound("shotboom"));
        bullet = AudioSystem.getClip();
        bullet.open(music);
        bullet.start();
    }
}
