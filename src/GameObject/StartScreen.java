package GameObject;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class StartScreen extends JPanel {
    Image foregroundImage;
    Image backgroundImage;

    public StartScreen() {
        //tao size cho panel
        setFocusable(true);
        setSize(500, 500);
        setPreferredSize(new Dimension(500, 500));

        //tao layout cho panel voi dong dau la tieu de dong 2 gom 2 button
        setLayout(new FlowLayout());

        //add anh flappy bird tittle vao
        ImageIcon icon = new ImageIcon(getClass().getResource("/res/flappybirdtitle.png"));
        JLabel label = new JLabel(icon);
        add(label);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        //tao button start
        JButton startButton = new JButton();
        startButton.setIcon(new ImageIcon(getClass().getResource("/res/playbutton.png")));

        //loai bo vien
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);
        
        //add button vao panel
        buttonPanel.add(startButton);

        //tao button leaderboard
        JButton leaderBoardButton = new JButton();
        leaderBoardButton.setIcon(new ImageIcon(getClass().getResource("/res/leaderboardbutton.png")));

        //loai bo vien
        leaderBoardButton.setBorderPainted(false);
        leaderBoardButton.setContentAreaFilled(false);
        leaderBoardButton.setFocusPainted(false);
        leaderBoardButton.setOpaque(false);
        
        //add button vao panel
        buttonPanel.add(leaderBoardButton);
        buttonPanel.setOpaque(false);
        add(buttonPanel);

        //thuc hien action khi click vao button start
        startButton.addActionListener(e -> {
            Game game = new Game();
            // lay frame hien tai
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().removeAll();
            frame.add(game);
            frame.pack();
            game.requestFocus();
        });

        //thuc hien action khi click vao button leaderboard
        leaderBoardButton.addActionListener(e -> {
            LeaderBoard leaderBoard = new LeaderBoard();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().removeAll();
            frame.add(leaderBoard);
            frame.pack();
        });

        setVisible(true);
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
        // Tạo ImageIcon từ đường dẫn hình ảnh
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        // Tạo JButton với ImageIcon
        JButton button = new JButton(icon);
        // Loại bỏ viền nút
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        return button;
    }
}
