package Proj;

import java.util.Random;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    int x = 750;
    int y = 550;
    int playerId = 0;
    int playerCount = 0;

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

    public Affichage(int playerId, int playerCount) {
        this.playerCount = playerCount;
        this.playerId = playerId;
        setTitle("Monopoly");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLocationRelativeTo(null);

        // Load the image from a file
        image = Toolkit.getDefaultToolkit().getImage("image.jpg");
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);

        
        
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
                    if (playerId == Server.currentPlayer) {
                        System.out.println("It is your turn");
                        Random rand = new Random();
                        int pos = rand.nextInt(5) + 1;
                        Server.positions.set(playerId, (Server.positions.get(playerId) + pos) % 40);
                        System.out.println(Server.positions.get(playerId));
                        ChangePosition();
                        for (Player player : Server.players) {
                            paintPlayers(player.affichage.getGraphics());
                        }
                        Server.currentPlayer = (Server.currentPlayer + 1) % playerCount;
                    }
                    else {
                        System.out.println("It is not your turn");
                    }
                } catch (Exception e1) {
                    System.err.println("Error while sending message to server: " + e1.getMessage());
                }
            }
        });

        // Add the JPanel to the JFrame and display it
        add(panel);
        setVisible(true);
    }

    public void CalculateCoordonate() {

    }

    public void ChangePosition() {
        //clear the previous position
        Graphics g = getGraphics();
        g.setColor(Color.WHITE);
        g.fillOval(x, y, 30, 30);
        int pos = Server.positions.get(playerId);
        if (pos < 10) {
            x = 750 - pos * 50;
            y = 550;
        }
        else if (pos < 20) {
            x = 50;
            y = 550 - (pos - 10) * 50;
        }
        else if (pos < 30) {
            x = 50 + (pos - 20) * 50;
            y = 50;
        }
        else if (pos < 40) {
            x = 750;
            y = 50 + (pos - 30) * 50;
        }
    }

    // Paint all the Players on the board
    public void paintPlayers(Graphics g) {
        for (Player player : Server.players) {
            int x = player.affichage.x;
            int y = player.affichage.y;
            g.setColor(Color.RED);
            g.fillOval(x, y, 30, 30);
        }
    }
}
