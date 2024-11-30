/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameObject;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

public class Game extends JPanel implements ActionListener, KeyListener {

    int boardWidth = 500;
    int boardHeight = 500;

    Image birdImage;
    Image birdDieImage;
    Image bottomPipeImage;
    Image topPipeImage;
    Image foregroundImage;
    Image backgroundImage;

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    int score = 0;

    //Bird
    int birdWidth = 45;
    int birdHeight = 32;
    int birdX = 200;
    int birdY = 150;
    double rotation = 0.0;
    Bird bird;

    //Pipe
    int pipeWidth = 66;
    int pipeHeight = 300;
    int pipeY = 0;
    int pipeX = boardWidth;
    ArrayList<Pipe> pipes;
    Random random = new Random();

    //Physics
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 8;
    int jumpStrength = -10;

    //current screen
    String currentScreen = "start";
    String playerName = "";

    StartScreen startScreen = new StartScreen();

    public Game() {
        setFocusable(true);
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        addKeyListener(this);

        birdImage = new ImageIcon(getClass().getResource("/res/bird.png")).getImage();
        birdDieImage = new ImageIcon(getClass().getResource("/res/bird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("/res/pipe-south.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("/res/pipe-north.png")).getImage();
        foregroundImage = startScreen.getForegroundImage();
        backgroundImage = startScreen.getBackgroundImage();

        bird = new Bird(birdX, birdY, birdWidth, birdHeight, birdImage);
        pipes = new ArrayList<>();

        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to be executed
                // drawMenu();
                placePipes();
            }
        });
        placePipeTimer.start();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Vẽ nền và nền đất
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawImage(foregroundImage, 0, 0, null); // Đặt vị trí cho foreground nếu cần
        g.drawImage(birdImage, birdX, birdY, birdWidth, birdHeight, null);

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
        g.drawImage(foregroundImage, 0, 0, null); // Đặt vị trí cho foreground nếu cần

        //score
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
    }

    public void drawPlayerNameTexture() {
        JPanel jp = new JPanel();
        jp.setSize(200, 100);
        jp.setLayout(new GridLayout(2, 1));
        jp.setPreferredSize(new Dimension(300, 200));
        jp.add(new JLabel("Enter your name: "));
        jp.add(new TextField());
        playerName = jp.getComponent(1).toString();
        add(jp, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
            placePipeTimer.stop();
        }
        
    }

    public void move() {
        if (!gameOver) {
            velocityY += gravity;
            birdY += velocityY;
            bird.y = birdY;  // Cập nhật bird.y theo birdY
            if (birdY >= 370) { // Kiểm tra nếu chim chạm đáy
                birdY = 370; // Cố định vị trí khi chạm nền
                gameOver = true; // Đặt game over nhưng không thay đổi gravity hay velocity
                bird.img = birdDieImage;
            }
        }
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                score += 1;
                pipe.passed = true;
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }
    }

    public JPanel showLeaderBoard() {
        JPanel jp = new JPanel();
        
        LeaderBoard lb = new LeaderBoard();
        jp = lb.showLeaderBoard();
        
        return jp;
    }

    public void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(pipeX, pipeY, pipeWidth, pipeHeight, topPipeImage);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(pipeX, pipeY, pipeWidth, pipeHeight, bottomPipeImage);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void resetGame() {
        birdX = 200;           // Đặt lại vị trí X của con chim
        birdY = 150;           // Đặt lại vị trí Y của con chim
        velocityY = 0;
        pipes.clear();// Đặt lại vận tốc Y
        gravity = 1;           // Đặt lại trọng lực (nếu có thay đổi)
        gameOver = false;      // Đặt lại trạng thái gameOver
        bird.img = birdImage;
        score=0;// Đặt lại hình ảnh của chim
        repaint();             // Vẽ lại màn hình để làm mới giao diện
        gameLoop.start();
        placePipeTimer.start();
        // Khởi động lại vòng lặp trò chơi
    }

    boolean collision(Bird a, Pipe b) {
        return a.x< b.x + b.width
                && //a's top left corner doesn't reach b's top right corner
                a.x + a.width > b.x
                && //a's top right corner passes b's top left corner
                a.y < b.y + b.height
                && //a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
    }
    
    
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameOver) {
                velocityY = jumpStrength;  // Tạo hiệu ứng nhảy khi game chưa kết thúc
            } else {
                resetGame();  // Khởi động lại trò chơi khi game đã kết thúc
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
