package GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {
    private int score;
    private int bestScore;
    Image foregroundImage;
    Image backgroundImage;
    Image gameover;
    Image scoreboardImage;

    private LeaderBoard leaderBoard;

    private TextField nameText;
    private JPanel addLeaderBoardPanel;
    private JFrame addLeaderBoardFrame;

    private JPanel scorePanel, buttonPanel;
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

    public void setScore(int score) {
        this.score = score;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    private void initComponents() {
        this.leaderBoard = new LeaderBoard();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Tải ảnh bảng điểm
        scoreboardImage = new ImageIcon(getClass().getResource("/res/scoreboard.png")).getImage();
        //tao panel chua score
        setScorePanel(scoreboardImage);

        add(scorePanel);
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

        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new GridLayout(1, 3));

        // Nút chơi lại
        ImageIcon restartIcon = new ImageIcon(getClass().getResource("/res/playbutton.png"));
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
        this.buttonPanel.add(restartButton);

        // Nút bảng xếp hạng
        ImageIcon leaderboardIcon = new ImageIcon(getClass().getResource("D:\\sourceCode\\Flappy-bird-main\\res\\leaderboardbutton.png"));
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
        this.buttonPanel.add(leaderboardButton);
        //them nut add to leader board
        JButton addLeaderBoardButton = new JButton(new ImageIcon(getClass().getResource("/res/addtoleaderboard.png")));
        addLeaderBoardButton.setBounds(startX + buttonWidth + buttonGap, 450, buttonWidth, buttonHeight);
        addLeaderBoardButton.setContentAreaFilled(false);
        addLeaderBoardButton.setBorderPainted(false);

        // Thiết lập ActionListener cho nút Add to Leaderboard
        addLeaderBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tạo khung JFrame cho việc thêm tên vào Leaderboard
                addLeaderBoardFrame = new JFrame("Add to Leaderboard");
                addLeaderBoardPanel = new JPanel();
                addLeaderBoardPanel.setLayout(new BoxLayout(addLeaderBoardPanel, BoxLayout.Y_AXIS));
                addLeaderBoardPanel.setPreferredSize(new Dimension(300, 200));
                addLeaderBoardPanel.setBackground(new Color(0, 0, 0, 150));

                JLabel promptLabel = new JLabel("Enter your name:");
                promptLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                promptLabel.setForeground(Color.WHITE);
                promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                addLeaderBoardPanel.add(promptLabel);

                addLeaderBoardPanel.add(Box.createVerticalStrut(10));

                nameText = new TextField();
                nameText.setMaximumSize(new Dimension(200, 30));
                addLeaderBoardPanel.add(nameText);

                addLeaderBoardPanel.add(Box.createVerticalStrut(20));

                JButton submitButton = new JButton("Submit");
                submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                addLeaderBoardPanel.add(submitButton);

                addLeaderBoardFrame.add(addLeaderBoardPanel);
                addLeaderBoardFrame.pack();
                addLeaderBoardFrame.setLocationRelativeTo(null);
                addLeaderBoardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addLeaderBoardFrame.setVisible(true);

                // Thiết lập ActionListener cho nút Submit
                
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameText.getText().trim();
                        if (name.length() > 0) {
                            try {
                                // Giả sử Player có constructor Player(String name, int score)
                                leaderBoard.UpdateLeaderBoard(new Player(name + " " + score));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            // Xóa tất cả các thành phần hiện tại trong khung Add Leaderboard
                            addLeaderBoardFrame.getContentPane().removeAll();

                            // Thêm JLabel thông báo thành công
                            JLabel successLabel = new JLabel("Your name has been added to the leaderboard!");
                            successLabel.setFont(new Font("Arial", Font.BOLD, 16));
                            successLabel.setForeground(Color.GREEN);
                            successLabel.setHorizontalAlignment(SwingConstants.CENTER);
                            addLeaderBoardFrame.getContentPane().setLayout(new BorderLayout());
                            addLeaderBoardFrame.getContentPane().add(successLabel, BorderLayout.CENTER);

                            // Làm mới giao diện
                            addLeaderBoardFrame.revalidate();
                            addLeaderBoardFrame.repaint();
                        } else {
                            JOptionPane.showMessageDialog(addLeaderBoardFrame, "Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }
        });

        buttonPanel.add(addLeaderBoardButton);
        buttonPanel.setVisible(true);
        buttonPanel.setOpaque(false);

        add(buttonPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ nền (như các class trên chỉ thêm mỗi gameOver và scoreboardImage)
        gameover = new ImageIcon(getClass().getResource("/res/gameOverText.png")).getImage();
        scoreboardImage = new ImageIcon(getClass().getResource("/res/scoreboard.png")).getImage();
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawImage(foregroundImage, 0, 0, null);

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
        // Thêm textField vào frame moi
            JFrame addLeaderBoardFrame = new JFrame();

            addLeaderBoardFrame.setPreferredSize(new Dimension(400, 200));
            this.addLeaderBoardPanel = new JPanel();
            this.addLeaderBoardPanel.setPreferredSize(new Dimension(300, 200));

            this.nameText = new TextField();
            this.addLeaderBoardPanel.setLayout(new GridLayout(2, 2));
            this.addLeaderBoardPanel.setSize(300, 200);
            this.addLeaderBoardPanel.add(new JLabel("Enter your name:"));
            this.addLeaderBoardPanel.add(this.nameText);
            //them nut submit
            JButton submitButton = new JButton("Submit");
            this.addLeaderBoardPanel.add(submitButton);

            addLeaderBoardFrame.add(this.addLeaderBoardPanel);
        //khi nhan nut submit thi them vao leader board dong thoi mo ra 1 label thong bao
            submitButton.addActionListener(e -> {
                String name = nameText.getText();
                if (name.length() > 0) {
                    try {
                        this.leaderBoard.UpdateLeaderBoard(new Player(name + " " + this.score));
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                this.addLeaderBoardPanel.add(new JLabel("Your name has been added to the leaderboard!"));
                
            });

        this.addLeaderBoardPanel.setOpaque(false);
        addLeaderBoardFrame.pack();
        addLeaderBoardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addLeaderBoardFrame.setVisible(true);
    }

    public void setScorePanel(Image scoreboard) {
        this.scorePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(scoreboard, 0, 0, null);
            }
        };
        //tao size moi cho score panel
        this.scorePanel.setSize(500, 400);
        this.scorePanel.setLayout(null);

        //tao label hien thi diem so phien choi hien tai
        JLabel scoreLabel = new JLabel(String.valueOf(this.score));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setForeground(Color.WHITE);

        //tao label hien thi diem so cao nhat
        JLabel bestScoreLabel = new JLabel(String.valueOf(this.bestScore));
        bestScoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        bestScoreLabel.setForeground(Color.WHITE);

        //vi tri cua label diem so
        scoreLabel.setBounds(300, 150, 100, 50);
        bestScoreLabel.setBounds(300, 200, 100, 50);

        //them label vao panel
        this.scorePanel.setPreferredSize(new Dimension(500, 500));
        this.scorePanel.setOpaque(false);
        this.scorePanel.add(scoreLabel);
        this.scorePanel.add(bestScoreLabel);
        this.scorePanel.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GameOverScreen(10, 10));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}