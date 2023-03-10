package Proj;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;

class Affichage extends JFrame {
    public static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private Image image;
    private int imageWidth;
    private int imageHeight;
    public static int x = 0;
    public static int y = 0;

    public Affichage(int playerpos) {
        setTitle("Monopoly");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLocationRelativeTo(null);

        // Load the image from a file
        image = Toolkit.getDefaultToolkit().getImage("image.jpg");
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);

        // Create a JPanel to display the image
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                g.setColor(Color.RED);
                g.fillOval(x, y, 30, 30);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(imageWidth, imageHeight);
            }
        };
        
        // Create a button and add it to the JPanel
        JButton playButton = new JButton("Play");
        panel.setLayout(null);
        panel.add(playButton);
        
        // Set the position of the button to the center of the JPanel
        playButton.setBounds(panel.getWidth()/2 - 50, panel.getHeight()/2 - 25, 100, 50);
        
        // Add a ComponentListener to the JFrame to listen for componentResized events
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Resize the image to match the new size of the panel
                image = image.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
                imageWidth = image.getWidth(null);
                imageHeight = image.getHeight(null);
                
                // Reposition the button to the center of the panel
                playButton.setBounds(panel.getWidth()/2 - 50, panel.getHeight()/2 - 25, 100, 50);
                panel.repaint();
            }
        });

        // Add an ActionListener to the button to handle click events
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("Play button clicked 1");
                    Player.PlayButton();
                    panel.repaint();

                    
                } catch (IOException e1) {
                    System.err.println("Error while sending message to server: " + e1.getMessage());
                }
            }
        });

        // Add the JPanel to the JFrame and display it
        add(panel);
        setVisible(true);
    }
}
