import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class LyricsPanel extends JPanel {
    private JTextArea moodDescriptionTextArea;
    private JTextArea aiResponseTextArea;
    private MyButton generateButton;
    private MyButton menuButton;
    private BufferedImage backgroundImage;
    public LyricsPanel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);

        // set background
        try {
            backgroundImage = ImageIO.read(new File("images/lyrics.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);  // Make leftPanel transparent

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.insets = new Insets(10, 10, 10, 10);

        // Label for Mood Description
        JLabel moodLabel = new JLabel("Your Mood:");
        leftPanel.add(moodLabel, gbcLeft);

        // Text area for Mood Description
        moodDescriptionTextArea = new JTextArea(3, 40);
        JScrollPane moodScrollPane = new JScrollPane(moodDescriptionTextArea);
        gbcLeft.gridy++;
        leftPanel.add(moodScrollPane, gbcLeft);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);  // Make rightPanel transparent

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.insets = new Insets(10, 10, 10, 10);

        // Label for AI Response
        JLabel aiResponseLabel = new JLabel("Lyrics for you:");
        rightPanel.add(aiResponseLabel, gbcRight);

        // Text area for AI Response
        aiResponseTextArea = new JTextArea(30, 30);
        aiResponseTextArea.setEditable(false);
        aiResponseTextArea.setLineWrap(true);  // Enable line wrapping
        aiResponseTextArea.setWrapStyleWord(true);  // Wrap at word boundaries
        JScrollPane responseScrollPane = new JScrollPane(aiResponseTextArea);
        gbcRight.gridy++;
        rightPanel.add(responseScrollPane, gbcRight);

        setButtons();

        gbcLeft.gridy++;
        leftPanel.add(generateButton, gbcLeft);

        gbcRight.gridy++;
        rightPanel.add(menuButton, gbcRight);

        add(leftPanel);
        add(rightPanel);
    }

    public void setButtons(){

        generateButton = new MyButton("Generate!");
        generateButton.addActionListener(e -> {

            // Disable the button to prevent multiple clicks
            generateButton.setEnabled(false);

            // Use SwingWorker to perform the task in a separate thread
            SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                @Override
                protected String doInBackground() throws Exception {
                    // Call your AI generation method here
                    String userInput = moodDescriptionTextArea.getText();

                    // Call your AI method with userInput and get the response
                    String aiResponse = null;
                    try {
                        aiResponse = ChatGPTClient.chatGPT(userInput);
                        aiResponse = aiResponse.replace("\\n", "\n");
                    } catch (URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }

                    return aiResponse;
                }

                @Override
                protected void done() {
                    try {
                        // Update the UI with the response
                        aiResponseTextArea.setText(get());
                    } catch (Exception ex) {
                        // Handle any exceptions that occurred during the background task
                        ex.printStackTrace();
                    } finally {
                        // Re-enable the button
                        generateButton.setEnabled(true);
                    }
                }
            };

            // Execute the SwingWorker
            worker.execute();
        });


        menuButton = new MyButton("Menu");
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LyricsApp.layout.show( LyricsApp.mainPanel, "MENU");
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
