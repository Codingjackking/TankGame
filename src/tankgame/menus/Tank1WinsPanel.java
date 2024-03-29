package tankgame.menus;

import tankgame.Launcher;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Tank1WinsPanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton exit;
    private Launcher lf;

    public Tank1WinsPanel(Launcher lf) {                        // lf is a launcher
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("menu/Tank1Wins.png")));       // starts the game
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
            e.printStackTrace();
            System.exit(-3);
        }
        this.setBackground(Color.BLACK);                                        // sets background black
        this.setLayout(null);

        exit = new JButton("Congrats!!");                                             // exits game
        exit.setSize(new Dimension(200,100));
        exit.setFont(new Font("Courier New", Font.BOLD ,24));
        exit.setBounds(150,400,150,50);
        exit.addActionListener((actionEvent -> {
            this.lf.setFrame("end");
        }));
        this.add(exit);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground,0,0,null);
    }
}

