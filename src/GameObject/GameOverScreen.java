package GameObject;

import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JPanel {
    private int score;
    private int bestScore;
    Image foregroundImage;
    Image backgroundImage;
    Image gameover;
    Image scoreboardImage;

    // Medal images
    Image gold;
    Image silver;
    Image bronze;

    public GameOverScreen(int score, int bestScore) {
        this.score = score;
        this.bestScore = bestScore;
        setPreferredSize(new Dimension(500, 500)); // kích thước khung chung
        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        // Tải ảnh bảng điểm
        scoreboardImage = new ImageIcon(getClass().getResource("/res/scoreboard.png")).getImage();

        // Tải ảnh huân chương
        gold = new ImageIcon(getClass().getResource("/res/gold.png")).getImage();
        silver = new ImageIcon(getClass().getResource("/res/silver.png")).getImage();
        bronze = new ImageIcon(getClass().getResource("/res/bronze.png")).getImage();

        // Kích thước panel
        int panelWidth = 500;

        // Kích thước nút
        int buttonWidth = 100;
        int buttonHeight = 80;
        int buttonGap = 40;

        // Tổng chiều rộng của hai nút và khoảng cách
        int totalButtonsWidth = buttonWidth * 2 + buttonGap;

        // Vị trí X bắt đầu để căn giữa
        int startX = (panelWidth - totalButtonsWidth) / 2;

        // Nút chơi lại
        ImageIcon restartIcon = new ImageIcon(getClass().getResource("/res/restart.png"));
        Image restartImg = restartIcon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        JButton restartButton = new JButton(new ImageIcon(restartImg));
        restartButton.setBounds(startX, 400, buttonWidth, buttonHeight);
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);

        // Thêm ActionListener để xử lý sự kiện click
        restartButton.addActionListener(e -> {
        // Chuyển sang class StartScreen
        // Ví dụ này giả định rằng StartScreen là một JFrame hoặc JPanel
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(restartButton); // Lấy frame hiện tại
        if (currentFrame != null) {
        currentFrame.dispose(); // Đóng frame hiện tại
        }
        StartScreen startScreen = new StartScreen(); // Khởi tạo màn hình mới
        startScreen.setVisible(true); // Hiển thị màn hình mới
    });

        // Thêm nút vào giao diện
        add(restartButton);

        // Thêm ActionListener để xử lý sự kiện click
        restartButton.addActionListener(e -> {
        // Chuyển sang class StartScreen
        // Ví dụ này giả định rằng StartScreen là một JFrame hoặc JPanel
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(restartButton); // Lấy frame hiện tại
        if (currentFrame != null) {
        currentFrame.dispose(); // Đóng frame hiện tại
        }
        StartScreen startScreen = new StartScreen(); // Khởi tạo màn hình mới
        startScreen.setVisible(true); // Hiển thị màn hình mới
    });

        // Thêm nút vào giao diện
        add(restartButton); 

        // Nút bảng xếp hạng
        ImageIcon leaderboardIcon = new ImageIcon(getClass().getResource("/res/leaderboardbutton.png"));
        Image leaderboardImg = leaderboardIcon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        JButton leaderboardButton = new JButton(new ImageIcon(leaderboardImg));
        leaderboardButton.setBounds(startX + buttonWidth + buttonGap, 400, buttonWidth, buttonHeight);
        leaderboardButton.setContentAreaFilled(false);
        leaderboardButton.setBorderPainted(false);

        // Thêm ActionListener để xử lý sự kiện click
        leaderboardButton.addActionListener(e -> {
        // Chuyển sang class LeaderboardScreen
        // Giả định rằng LeaderboardScreen là một JFrame hoặc JPanel
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(leaderboardButton); // Lấy frame hiện tại
        if (currentFrame != null) {
        currentFrame.dispose(); // Đóng frame hiện tại
        }
        LeaderBoard leaderboardScreen = new LeaderBoard(); // Khởi tạo màn hình mới
        leaderboardScreen.setVisible(true); // Hiển thị màn hình mới
    });

        // Thêm nút vào giao diện
        add(leaderboardButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ nền (như các class trên chỉ thêm mỗi gameOver và scoreboardImage)
        backgroundImage = new ImageIcon(getClass().getResource("/res/Background.png")).getImage();
        foregroundImage = new ImageIcon(getClass().getResource("/res/Foreground.png")).getImage();
        gameover = new ImageIcon(getClass().getResource("/res/gameOverText.png")).getImage();
        scoreboardImage = new ImageIcon(getClass().getResource("/res/scoreboard.png")).getImage();

        // Kích thước panel
        int panelWidth = getWidth();

        // Vẽ hình nền
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        g.drawImage(foregroundImage, 0, 0, getWidth(), getHeight(), null);

        // Vẽ chữ "Game Over" 
        int gameOverWidth = (int)(panelWidth * 0.7); // 70% chiều rộng panel
        int gameOverHeight = (int)((double)gameover.getHeight(null) / gameover.getWidth(null) * gameOverWidth);
        int gameOverX = (panelWidth - gameOverWidth) / 2;
        g.drawImage(gameover, gameOverX, 20, gameOverWidth, gameOverHeight, null);

        // Vẽ bảng điểm căn giữa và tăng kích thước
        int scoreboardWidth = (int)(panelWidth * 0.8); // 80% chiều rộng panel
        int scoreboardHeight = (int)((double)scoreboardImage.getHeight(null) / scoreboardImage.getWidth(null) * scoreboardWidth);
        int scoreboardX = (panelWidth - scoreboardWidth) / 2;
        int scoreboardY = 150;
        g.drawImage(scoreboardImage, scoreboardX, scoreboardY, scoreboardWidth, scoreboardHeight, null);
        
        
        // Chọn ảnh huân chương dựa trên điểm số
        Image medalImage = null;
        if (score >= bestScore) {
            medalImage = gold;
        } else if (score >= bestScore - 1) {
            medalImage = silver;
        } else if (score >= bestScore - 2) {
            medalImage = bronze;
        }

        // Vẽ ảnh huân chương lên trên bảng điểm
        if (medalImage != null) {
            int medalWidth = scoreboardWidth / 4; // Kích thước huân chương tương đối với bảng điểm
            int medalHeight = medalWidth;
            int medalX = scoreboardX + scoreboardWidth / 8; // Đặt bên trái của bảng điểm
            int medalY = scoreboardY + scoreboardHeight / 3;
            g.drawImage(medalImage, medalX, medalY, medalWidth, medalHeight, null);
        }

        // Vẽ điểm số
        
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird Game Over");
        GameOverScreen gameOverScreen = new GameOverScreen(10, 12);
        frame.add(gameOverScreen);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}