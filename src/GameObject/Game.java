
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

public class Game extends JPanel implements ActionListener, KeyListener,MouseListener {
    int boardWidth = 500;
    int boardHeight = 500;

    BufferedImage birdImage[] = new BufferedImage[3];
    BufferedImage bottomPipeImage;
    BufferedImage topPipeImage;
    BufferedImage foregroundImage;
    BufferedImage backgroundImage;

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    int score = 0;
    
    public static Audio audio = new Audio();
    
    //Bird
    int birdWidth = 45;
    int birdHeight = 32;
    int birdX = 200;
    int birdY = 150;
    Bird bird;

    //Pipe
    int pipeWidth = 70;
    int pipeHeight = 300;
    int pipeY = 0;
    int pipeX = boardWidth;
    ArrayList<Pipe> pipes;
    Random random = new Random();

    
    //Physics
    int velocityX = -4;
    int jumpStrength = -10;
    
    //game state
    final static int MENU = 0;
    final static int GAME = 1;
    private int gameState = MENU;

    public Game() {
        setFocusable(true);
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        addKeyListener(this);
        try {
            birdImage[0] =  ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\yellowBird1.png"));
            birdImage[1] = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\yellowBird2.png"));
            birdImage[2] = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\yellowBird3.png"));
            topPipeImage = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\pipe-south.png"));
            bottomPipeImage = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\pipe-north.png"));
            foregroundImage = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\foreground.png"));
            backgroundImage = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\background.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        bird = new Bird(birdX, birdY, birdWidth, birdHeight, birdImage);
        pipes = new ArrayList<>();

        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to be executed
                placePipes();
            }
        });
        placePipeTimer.start();

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        
    }

    public void draw(Graphics g) {
        // Vẽ nền và nền đất
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawImage(foregroundImage, 0, 0, null); // Đặt vị trí cho foreground nếu cần
    
        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
        g.drawImage(foregroundImage, 0, 0, null); // Đặt vị trí cho foreground nếu cần
        
        bird.renderBird(g);
        //score
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.RED);
            g.drawString("Game Over!", boardWidth / 2 - 80, boardHeight / 2);
        }
    }
    
    public void drawMenu(Graphics g){
     
    }
    
    public void drawScore(){
    
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
//        if (bird.y>=390) {
//            gameLoop.stop();
//            placePipeTimer.stop();
//        }
        move();
        repaint();
    }

    public void move() {
        if (!gameOver) {
            bird.velocity += bird.gravity;
            bird.y += bird.velocity;
//            bird.y = birdY;  // Cập nhật bird.y theo birdY
            if (bird.y >= 390) { // Kiểm tra nếu chim chạm đáy
                bird.y = 390; // Cố định vị trí khi chạm nền
                audio.hit();
                gameLoop.stop();
                placePipeTimer.stop();
                gameOver = true; // Đặt game over nhưng không thay đổi gravity hay velocity
                
            }
        
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width && pipe.img == topPipeImage) {
                score += 1;
                pipe.passed = true;
                audio.point();
            }

            if (collision(bird, pipe)) {
                audio.hit();
                gameOver=Boolean.TRUE;
            }
        }
        }
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
        bird.x = 200;           // Đặt lại vị trí X của con chim
        bird.y = 150;           // Đặt lại vị trí Y của con chim
        bird.velocity = 0;
        pipes.clear();// Đặt lại vận tốc Y
        bird.gravity=1;
        gameOver = false;      // Đặt lại trạng thái gameOver
//        bird.img = birdImage;
        score=0;// Đặt lại hình ảnh của chim
        repaint();             // Vẽ lại màn hình để làm mới giao diện
        gameLoop.start();
        placePipeTimer.start();
        // Khởi động lại vòng lặp trò chơi
    }

    boolean collision(Bird a, Pipe b) {
//        if(a.x>b.x && a.y<0){
//            return true;
//        }
        return a.x< b.x + b.width
                && //a's top left corner doesn't reach b's top right corner
                a.x + a.width > b.x
                && //a's top right corner passes b's top left corner
                a.y < b.y + b.height
                && //a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
    }
    
    
    
    //Key Action
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameOver) {
                audio.jump();
                bird.velocity = jumpStrength;  // Tạo hiệu ứng nhảy khi game chưa kết thúc
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
