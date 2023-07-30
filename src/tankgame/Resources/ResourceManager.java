package tankgame.Resources;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResourceManager {
    private final static Map<String, BufferedImage> sprites = new HashMap<>();

    private final static Map<String, String> spriteInfo = new HashMap<>() {{
       put("bullet", "bullet/bullet.jpg");
    }};
    private final static Map<String, List<BufferedImage>> animation = new HashMap<>();
    private final static Map<String, Clip> sounds = new HashMap<>();


    private static BufferedImage loadSprites(String path) throws IOException {
        return ImageIO.read(
                Objects.requireNonNull(ResourceManager
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
            ResourceManager.sprites.put("bg", loadSprites("floor/bg.bmp"));
            ResourceManager.sprites.put("menu", loadSprites("menu/title.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadResources() {
        ResourceManager.initSprites();
    }
    public static BufferedImage getSprite(String type) {
        if (!ResourceManager.sprites.containsKey(type)) {
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return ResourceManager.sprites.get(type);
    }
}
