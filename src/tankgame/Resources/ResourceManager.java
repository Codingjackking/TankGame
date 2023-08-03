package tankgame.Resources;

import tankgame.game.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class ResourceManager {
    private final static Map<String, BufferedImage> sprites = new HashMap<>();
    private final static Map<String ,Sound> sounds = new HashMap<>();
    private final static Map<String, List<BufferedImage>> animations = new HashMap<>();
    private static final Map<String, Integer> animationInfo = new HashMap<>() {{
        put("bullethit", 24);
        put("bulletshoot",24);
        put("powerpick",32);
        put("puffsmoke",32);
        put("rocketflame",16);
        put("rockethit",32);
    }};
    private static BufferedImage loadSprites(String path) throws IOException {
//        System.out.println("path = " + path);
        return ImageIO.read(
                Objects.requireNonNull(
                        ResourceManager
                        .class
                        .getClassLoader()
                        .getResource(path)));
    }

    /* Debugging for loading sprites
private static BufferedImage loadSprites(String path) throws IOException {
    URL resourceUrl = ResourceManager.class.getClassLoader().getResource(path);
    if (resourceUrl == null) {
        throw new RuntimeException("Resource not found: " + path);
    }
    return ImageIO.read(resourceUrl);
}
*/

    private static Sound loadSounds(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream ais = AudioSystem.getAudioInputStream(
                Objects.requireNonNull(ResourceManager
                        .class
                        .getClassLoader()
                        .getResource(path)));
        Clip c = AudioSystem.getClip();
        c.open(ais);
        Sound s = new Sound(c);
        s.setVolume(.2f);
        return s;
    }

    private static void initSprites() {
        try {
            ResourceManager.sprites.put("tank1", loadSprites("tank/tank1.png"));
            ResourceManager.sprites.put("tank2", loadSprites("tank/tank2.png"));
            ResourceManager.sprites.put("bullet", loadSprites("bullet/bullet.jpg"));
            ResourceManager.sprites.put("rocket1", loadSprites("bullet/rocket1.png"));
            ResourceManager.sprites.put("rocket2", loadSprites("bullet/rocket2.png"));
            ResourceManager.sprites.put("break1", loadSprites("walls/break1.jpg"));
            ResourceManager.sprites.put("break2", loadSprites("walls/break2.jpg"));
            ResourceManager.sprites.put("unbreak", loadSprites("walls/unbreak.jpg"));
            ResourceManager.sprites.put("health", loadSprites("powerups/health.png"));
            ResourceManager.sprites.put("shield", loadSprites("powerups/shield.png"));
            ResourceManager.sprites.put("speed", loadSprites("powerups/speed.png"));
            ResourceManager.sprites.put("dmgup", loadSprites("powerups/dmgup.png"));
            ResourceManager.sprites.put("lives", loadSprites("powerups/lives.png"));
            ResourceManager.sprites.put("bg", loadSprites("floor/bg.bmp"));
            ResourceManager.sprites.put("menu", loadSprites("menu/title.png"));
            ResourceManager.sprites.put("empty",loadSprites("images/empty.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initAnimations() {
        String baseName = "animations/%s/%s_%04d.png";
        animationInfo.forEach((animationName, frameCount) -> {
            List<BufferedImage> frames = new ArrayList<>();
            try {
                for (int i = 0; i < frameCount; i++) {
                    String spritePath = baseName.formatted(animationName, animationName, i);
                    frames.add(loadSprites(spritePath));
                }
                ResourceManager.animations.put(animationName, frames);
            } catch (IOException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        });
    }

    private static void initSounds() {
        try {
            ResourceManager.sounds.put("bg", loadSounds("sounds/Music.mid"));
            ResourceManager.sounds.put("pickup", loadSounds("sounds/pickup.wav"));
            ResourceManager.sounds.put("shot", loadSounds("sounds/bullet.wav"));
            ResourceManager.sounds.put("shotfire",loadSounds("sounds/shotfiring.wav"));
            ResourceManager.sounds.put("shotboom",loadSounds("sounds/shotexplosion.wav"));

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadResources() {
        ResourceManager.initSprites();
        ResourceManager.initSounds();
        ResourceManager.initAnimations();
    }
    public static BufferedImage getSprite(String type) {
        if (!ResourceManager.sprites.containsKey(type)) {
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return ResourceManager.sprites.get(type);
    }

    public static List<BufferedImage> getAnimation(String type) {
        if (!ResourceManager.animations.containsKey(type)) {
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return ResourceManager.animations.get(type);
    }
    public static Sound getSound(String type) {
        if (!ResourceManager.sounds.containsKey(type)) {
            throw new RuntimeException("%s is missing from sound resources".formatted(type));
        }
        return ResourceManager.sounds.get(type);
    }

    public static void main(String[] args) {
        ResourceManager.loadResources();
    }
}
