package tankgame.game;

import tankgame.Resources.ResourceManager;


public class GameObject {
    public static GameObject newInstance(String type, float x, float y) throws UnsupportedOperationException {
        System.out.println(type);
        return switch (type) {
            case "9", "2" -> new Wall(x, y, ResourceManager.getSprite("unbreak"));
            case "3" -> new BreakableWall(x, y, ResourceManager.getSprite("break1"));
            case "4" -> new Health(x, y, ResourceManager.getSprite("health"));
            case "5" -> new Shield(x, y, ResourceManager.getSprite("shield"));
            case "6" -> new Speed(x, y, ResourceManager.getSprite("speed"));
            case "7" -> new DamageUP(x, y, ResourceManager.getSprite("dmgup"));
            case "8" -> new Lives(x, y, ResourceManager.getSprite("lives"));
            default -> throw new UnsupportedOperationException();
        };
    }
}
