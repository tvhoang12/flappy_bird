package GameObject;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class StartScreen extends JPanel {
    //khai bao anh nen
    private Image foregroundImage;
    private Image backgroundImage;
    //khai bao panel chua button
    private JPanel buttonPanel;
    //khai bao cac button
    private JButton startButton;
    private JButton leaderBoardButton;
    //khai bao leaderBoard
    private LeaderBoard leaderBoard;
    private Game game;


    public StartScreen() {
        //tao size cho panel
        setFocusable(true);
        setSize(500, 500);
        setPreferredSize(new Dimension(500, 500));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // tao icon cho frame
        ImageIcon icon = new ImageIcon(getClass().getResource("/res/flappybirdtitle.png"));
        JLabel label = new JLabel(icon);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label, BorderLayout.EAST);

        setButtonPanel();

        add(buttonPanel);

        //thuc hien action khi click vao button start
        startButton.addActionListener(e -> {
            this.game = new Game();
            // lay frame hien tai
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            buttonPanel.setVisible(false);
            frame.add(game);
            frame.pack();
            game.requestFocus();
        });

        //thuc hien action khi click vao button leaderboard
        leaderBoardButton.addActionListener(e -> {
            this.leaderBoard = new LeaderBoard();
            // lay frame hien tai
            JFrame leaderBoardFrame = new JFrame();
            // buttonPanel.setVisible(false);
            leaderBoardFrame.setPreferredSize(new Dimension(400, 450));
            leaderBoardFrame.add(leaderBoard);
            leaderBoardFrame.pack();
            leaderBoardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            leaderBoardFrame.setVisible(true);
        });

        setVisible(true);
    }

    public void setButtonPanel() {

        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new GridLayout(1, 2));

        //tao button start
        this.startButton = createImageButton("/res/playbutton.png");
        
        //add button vao panel
        buttonPanel.add(this.startButton);

        //tao button leaderboard
        this.leaderBoardButton = createImageButton("/res/leaderboardbutton.png");
        
        //add button vao panel
        this.buttonPanel.add(this.leaderBoardButton);
        this.buttonPanel.setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {

        backgroundImage = new ImageIcon(getClass().getResource("/res/Background.png")).getImage();
        foregroundImage = new ImageIcon(getClass().getResource("/res/Foreground.png")).getImage();

        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawImage(foregroundImage, 0, 0, null);
    }

    private JButton createImageButton(String imagePath) {
        // Tạo JButton với ImageIcon
        JButton button = new JButton();
        try {
            // Tạo ImageIcon từ đường dẫn hình ảnh
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            // Set icon cho button
            button.setIcon(icon);
            // Loại bỏ viền nút
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setOpaque(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return button;
    }
}
