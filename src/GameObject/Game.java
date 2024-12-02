package GameObject;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.InputStream;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game extends JPanel implements ActionListener, KeyListener, MouseListener {

    // kích thước của JPanel
    int boardWidth = 500;
    int boardHeight = 500;

    // các ảnh đồ họa cần thiết trong game
    BufferedImage birdImage[] = new BufferedImage[3];
    BufferedImage bottomPipeImage;
    BufferedImage topPipeImage;
    BufferedImage foregroundImage;
    BufferedImage backgroundImage;
    BufferedImage gameOverLabel;
    BufferedImage restartButton;
    BufferedImage addToLeaderBoardButton;
    BufferedImage scoreBoard;
    BufferedImage [] scoreNum = new BufferedImage[10];

    // vòng lặp
    Timer gameLoop;
    Timer placePipeTimer;
    private int score = 0;
    private int bestScore = 0;
    private String medal;
    private static Audio audio = new Audio();

    //Bird
    Bird bird;
    
    private int distanceScore = 0;
    //Pipe
    ArrayList<Pipe> pipes;
    Random random = new Random();

    //font
    private Font flappyFontBase, flappyFontReal, flappyScoreFont, flappyMiniFont = null;
    private Point clickedPoint = new Point(-1, -1);
    
    //Game State
    final static int MENU = 0;
    final static int GAME = 1;
    private int gameState = MENU;

    private GameOverScreen gameOverScreen = new GameOverScreen(this.score, this.bestScore);
    public Game() throws IOException {
        setFocusable(true);
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        addKeyListener(this);
        
        try {
            InputStream is = new BufferedInputStream(new FileInputStream("D:\\sourceCode\\Flappy-bird-main\\res\\flappy-font.ttf"));
            flappyFontBase = Font.createFont(Font.TRUETYPE_FONT, is);

            // Header and sub-header fonts
            flappyScoreFont = flappyFontBase.deriveFont(Font.PLAIN, 50);
            flappyFontReal = flappyFontBase.deriveFont(Font.PLAIN, 20);
            flappyMiniFont = flappyFontBase.deriveFont(Font.PLAIN, 15);

        } catch (Exception ex) {
            // ex.printStackTrace();
        }

        birdImage[0] = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\yellowBird1.png"));
        birdImage[1] = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\yellowBird2.png"));
        birdImage[2] = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\yellowBird3.png"));
        topPipeImage = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\pipe-south.png"));
        bottomPipeImage = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\pipe-north.png"));
        foregroundImage = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\foreground.png"));
        backgroundImage = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\background.png"));
        restartButton = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\restart.png"));
        gameOverLabel = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\gameOverText.png"));
        addToLeaderBoardButton = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\addtoleaderboard.png"));
        scoreBoard = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\scoreCard.png"));
        for(int i = 0; i < 10; i++){
            scoreNum[i] = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\" + i + ".png"));
        }
        
        
        bird = new Bird(200, 150, birdImage);
        pipes = new ArrayList<>();

        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to be executed
                placePipes();
            }
        });
        placePipeTimer.start();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        if(bird.isAlive()){
            drawScore(g, this.score);
        }else{
            this.gameOverScreen.setScore(score);
            this.gameOverScreen.setBestScore(bestScore);
            this.gameOverScreen.setVisible(true);
            revalidate();
            repaint();
            add(gameOverScreen);
        }
    }

    public void draw(Graphics g) {

        // Vẽ nền và nền đất
        g.drawImage(backgroundImage, 0, 0, this);
        g.drawImage(foregroundImage, 0, 0, this); // Đặt vị trí cho foreground nếu cần

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.widthPipe, pipe.heightPipe, this);
        }
        g.drawImage(foregroundImage, 0, 0, this); // Đặt vị trí cho foreground nếu cần

        bird.renderBird(g);
    }

    
    private boolean isTouching (Rectangle r) {
		return r.contains(clickedPoint);
    }
    
    public void drawScore(Graphics g, int score) {
    String scoreString = String.valueOf(score);
    int xPos = 240; // Vị trí bắt đầu
    for (int i = 0; i < scoreString.length(); i++) {
        String digit = String.valueOf(scoreString.charAt(i));
        BufferedImage digitImage = getDigitImage(digit);  // Hàm lấy ảnh tương ứng với từng chữ số
        g.drawImage(digitImage, xPos, 40, this);
        xPos += 15; // Khoảng cách giữa các chữ số
    }
}

    private BufferedImage getDigitImage(String digit) {
        return scoreNum[Integer.parseInt(digit)];
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    public void move() {
        if (bird.isAlive()) {
            bird.velocityY += bird.gravity;
            bird.yBird += bird.velocityY;

            if (bird.yBird >= 385) { // Kiểm tra nếu chim chạm đáy
                bird.yBird = 385;
                bird.kill();// Cố định vị trí khi chạm nền
                audio.hit();
                gameLoop.stop();
                placePipeTimer.stop();
                

            }

            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                pipe.movePipe();

                if (!pipe.passed && bird.xBird > pipe.x + pipe.widthPipe && pipe.img == topPipeImage) {
                    if(!pipe.passed){
                    score += 1;
                    pipe.passed = true;
                    audio.point();
                    }
                }

                if (collision(bird, pipe)) {
                    bird.kill();
                    audio.hit();

                    if (bird.yBird >= 385) {
                        gameLoop.stop();
                        placePipeTimer.stop();
                    }
                }
            }
        }
    }

    public void placePipes() {
        int randomPipeY = (int) (0 - Pipe.heightPipe / 4 - Math.random() * (Pipe.heightPipe / 2));
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(500, 0, topPipeImage);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(500, 0, bottomPipeImage);
        bottomPipe.y = topPipe.y + Pipe.heightPipe + openingSpace;
        pipes.add(bottomPipe);
    }

    public void resetGame() {
        bird.xBird = 200;           // Đặt lại vị trí X của con chim
        bird.yBird = 150;           // Đặt lại vị trí Y của con chim
        bird.velocityY = 0;
        bird.reHealth();
        pipes.clear();// Đặt lại vận tốc Y
        bird.gravity = 1;
        bestScore = Math.max(score, bestScore);
        score = 0;// Đặt lại hình ảnh của chim
        //bo panel gameoverscreen
        this.gameOverScreen.setVisible(false);
        repaint();             // Vẽ lại màn hình để làm mới giao diện
        gameLoop.start();
        placePipeTimer.start();
    }

    boolean collision(Bird a, Pipe b) {
        if (a.xBird > b.x && a.yBird < 0) {
            return true;
        }
        return a.xBird < b.x + b.widthPipe
                && //a's top left corner doesn't reach b's top right corner
                a.xBird + a.widthBird > b.x
                && //a's top right corner passes b's top left corner
                a.yBird < b.y + b.heightPipe
                && //a's top left corner doesn't reach b's bottom left corner
                a.yBird + a.heightBird > b.y;    //a's bottom left corner passes b's top left corner
    }

    //Key Action
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (bird.isAlive()) {
                audio.jump();
                bird.velocityY = bird.jumpStrength;  // Tạo hiệu ứng nhảy khi game chưa kết thúc
            } else {
                resetGame();  // Khởi động lại trò chơi khi game đã kết thúc
            }
        }
    }

    // Mosuse Action
    public void mouseClicked(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        clickedPoint = e.getPoint();
        if(bird.isAlive()){
            audio.jump();
            bird.velocityY = bird.jumpStrength;
        }else{
            resetGame();
        }
    }

    public int getScore() {
        return score;
    }
    public int getBestScore() {
        return bestScore;
    }

}