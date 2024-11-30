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
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        //tao label cho tieu de
        JLabel title = new JLabel("Flappy Bird");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 0;
        c.gridy = 0;
        add(title, c);

        //tao button start
        JButton startButton = new JButton();
        startButton.setIcon(new ImageIcon(getClass().getResource("/res/playbutton.png")));
        c.gridx = 0;
        c.gridy = 1;
        add(startButton, c);
        setVisible(true);
        
        //tao button leaderboard
        JButton leaderBoardButton = new JButton();
        leaderBoardButton.setIcon(new ImageIcon(getClass().getResource("/res/leaderboardbutton.png")));
        c.gridx = 1;
        c.gridy = 1;
        add(leaderBoardButton, c);
        //hien thi button ngay khi khoi tao
        setVisible(true);

        //thuc hien action khi click vao button start
        startButton.addActionListener(e -> {
            Game game = new Game();
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

    //getter va setter
    public Image getBackgroundImage() {
        return backgroundImage;
    }
    public Image getForegroundImage() {
        return foregroundImage;
    }

    @Override
    public void paintComponent(Graphics g) {

        backgroundImage = new ImageIcon(getClass().getResource("/res/Background.png")).getImage();
        foregroundImage = new ImageIcon(getClass().getResource("/res/Foreground.png")).getImage();

        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawImage(foregroundImage, 0, 0, null);
        g.drawImage(new ImageIcon(getClass().getResource("/res/StartScreen.png")).getImage(), 0, 0, null);
    }

    public void setBackground(Graphics g) {
        backgroundImage = new ImageIcon(getClass().getResource("/res/Background.png")).getImage();
        foregroundImage = new ImageIcon(getClass().getResource("/res/Foreground.png")).getImage();

        //ve background
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
