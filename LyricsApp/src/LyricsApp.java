import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;

public class LyricsApp extends JFrame {

    static JPanel mainPanel = new JPanel();
    static MenuPanel menuPanel = new MenuPanel();
    static LyricsPanel lyricsPanel = new LyricsPanel();
    static CardLayout layout = new CardLayout();

    public static void main(String[] args) throws URISyntaxException {

        JFrame frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        mainPanel.setLayout( layout );

        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(lyricsPanel, "LYRICS");

        frame.add(mainPanel);
        frame.setTitle("LyricsApp");
        ImageIcon logo = new ImageIcon("images/vinyl.jpg");
        frame.setIconImage(logo.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}
