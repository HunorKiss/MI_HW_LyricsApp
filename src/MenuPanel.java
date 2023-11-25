import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {

    private BufferedImage backgroundImage;
    static MyButton enterLyricsButton;
    static MyButton exitButton;
    public MenuPanel(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true);

        // set background
        try {
            backgroundImage = ImageIO.read(new File("images/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setButtons();
        setLayout(new GridLayout(1, 2));

        ImageIcon singer = new ImageIcon("images/singer.jpg");
        JLabel imageLabel = new JLabel(singer);

        JLabel buttonLabel = new JLabel();
        buttonLabel.setBackground( new Color(0, 0, 0, 0) );

        buttonLabel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20,20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonLabel.add(enterLyricsButton, gbc);
        gbc.gridy = 1;
        buttonLabel.add(exitButton, gbc);

        add(imageLabel);
        add(buttonLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private static void setButtons() {

        enterLyricsButton = new MyButton("Enter Lyrics generator");
        enterLyricsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LyricsApp.layout.show(LyricsApp.mainPanel, "LYRICS");
            }
        });

        exitButton = new MyButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
