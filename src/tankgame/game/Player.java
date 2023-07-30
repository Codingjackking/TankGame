package tankgame.game;

public class Player {
    private int score;
    private int lives;
    private Tank tank;

    public Player(Tank tank) {
        this.tank = tank;
        this.score = 0;
        this.lives = 3;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    public int getLives() {
        return lives;
    }

    public void decrementLives() {
        this.lives--;
    }

    public Tank getTank() {
        return tank;
    }
}
