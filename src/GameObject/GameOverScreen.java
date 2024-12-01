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

    private LeaderBoard leaderBoard;

    private JPanel nameTextPanel;
    private TextField nameText;

    // Medal images
    Image gold;
    Image silver;
    Image bronze;

    public GameOverScreen(int score, int bestScore) {
        this.score = score;
        this.bestScore = bestScore;
        setPreferredSize(new Dimension(500, 500)); // kích thước khung chung
        initComponents();
        setOpaque(false); // Để bo qua hiển thị ảnh nền
    }

    private void initComponents() {
        setLayout(null);

        // Tải ảnh bảng điểm
        scoreboardImage = new ImageIcon(getClass().getResource("/res/scoreCard.png")).getImage();

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
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(restartButton); // Lấy frame hiện tại
            if (currentFrame != null) {
                currentFrame.dispose(); // Đóng frame hiện tại
            }
            // Tạo và hiển thị StartScreen mới
            JFrame newFrame = new JFrame("Flappy Bird");
            try {
                Game startScreen = new Game(); // Khởi tạo màn hình mới
                newFrame.add(startScreen);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            newFrame.pack();
            newFrame.setLocationRelativeTo(null);
            newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newFrame.setVisible(true);
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
            this.leaderBoard = new LeaderBoard();
            // lay frame hien tai
            JFrame leaderBoardFrame = new JFrame();
            //tao size
            leaderBoardFrame.setPreferredSize(new Dimension(400, 450));
            //add leaderBoard vao frame va cai dat 1 so thuoc tinh
            leaderBoardFrame.add(leaderBoard);
            leaderBoardFrame.pack();
            leaderBoardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            leaderBoardFrame.setVisible(true);
        });

        // Thêm nút vào giao diện
        add(leaderboardButton);

        JButton addLeaderBoardButton = new JButton(new ImageIcon(getClass().getResource("/res/addtoleaderboard.png")));
        addLeaderBoardButton.setBounds(startX + buttonWidth + buttonGap, 400, buttonWidth, buttonHeight);
        addLeaderBoardButton.setContentAreaFilled(false);
        addLeaderBoardButton.setBorderPainted(false);

        addLeaderBoardButton.addActionListener(e -> {
            // Thêm người chơi vào bảng xếp hạng)
            this.nameTextPanel = new JPanel();
            setTextField();
            JFrame frame = new JFrame();
            frame.setPreferredSize(new Dimension(300, 500));
            frame.add(nameTextPanel);
            frame.pack();
            frame.setVisible(true);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ nền (như các class trên chỉ thêm mỗi gameOver và scoreboardImage)
        gameover = new ImageIcon(getClass().getResource("/res/gameOverText.png")).getImage();

        // Kích thước panel
        int panelWidth = getWidth();

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

        // Vẽ điểm số (bạn có thể thêm phần này nếu cần)
    }

    public void setTextField() {
        this.nameText = new TextField();
        // Thêm textField vào panel
        this.nameTextPanel.setLayout(new GridLayout(2, 1));
        this.nameTextPanel.add(new JLabel("Enter your name:"));
        this.nameTextPanel.add(this.nameText);
        add(nameTextPanel);
    }

    // public static void main(String[] args) {
    //     JFrame frame = new JFrame("Flappy Bird Game Over");
    //     GameOverScreen gameOverScreen = new GameOverScreen(10, 12);
    //     frame.add(gameOverScreen);
    //     frame.pack();
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setVisible(true);
    // }
}