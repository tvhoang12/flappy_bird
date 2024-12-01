/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameObject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import GameObject.Player;

class LeaderBoard {
    private ArrayList<Player> highScore;
    private BufferedImage backGround;
    private JPanel leaderBoardPanel = new JPanel();

    public LeaderBoard() {
        highScore = new ArrayList<>();
    }
    // Display the leaderboard
    public void UpdateLeaderBoard(Player newHighScore) throws IOException {
        // Logic for showing the leaderboard
        Scanner sc = new Scanner(new File("D:\\sourceCode\\Flappy-bird-main\\src\\GameObject\\LeaderBoard.txt"));

        while(sc.hasNextLine()) {
            Player temp = new Player(sc.nextLine());
            this.highScore.add(temp);
        }
        sc.close();

        if(newHighScore.isBigger(this.highScore.get(9))) {
            if(newHighScore.isBigger(this.highScore.get(0))) {
                this.highScore.add(0, newHighScore);
            }
            else {
                int i = 1;
                while (highScore.get(i).isBigger(newHighScore) && i < 10) {
                    i ++;
                }
                this.highScore.add(i, newHighScore);
            }   
            this.highScore.remove(10);
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\sourceCode\\Flappy-bird-main\\src\\GameObject\\LeaderBoard.txt"))) {
            for(Player p : highScore) {
                bw.write(p.toString());
                bw.newLine();
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void setScreen(Font customFont) throws IOException {
        this.leaderBoardPanel.setLayout(new GridLayout(10, 3, 10, 10));

        try {
            Scanner sc = new Scanner(new File("D:\\sourceCode\\Flappy-bird-main\\src\\GameObject\\LeaderBoard.txt"));
            customFont = customFont.deriveFont(Font.PLAIN, 40f);
            for (int i = 0; i < 10; i++) {
                JLabel rank = new JLabel(String.format("%d", i + 1));
                rank.setFont(customFont);
                rank.setForeground(Color.WHITE);
                this.leaderBoardPanel.add(rank);

                JLabel username = new JLabel(sc.next());
                username.setFont(customFont);
                username.setForeground(Color.WHITE);
                this.leaderBoardPanel.add(username);

                JLabel score = new JLabel(sc.next()); // Điểm số
                score.setFont(customFont);
                score.setForeground(Color.WHITE);
                this.leaderBoardPanel.add(score);
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JPanel showScreen() {
        return this.leaderBoardPanel;
    }

    public void setScreenBackground() throws IOException {
        this.backGround = ImageIO.read(new File("D:\\sourceCode\\Flappy-bird-main\\res\\LeaderBoard_Board_resize.png"));
        JLabel picLabel = new JLabel(new ImageIcon(this.backGround));
        this.leaderBoardPanel.add(picLabel);
    }
}

public class Leader {
    public static void main(String [] args) throws IOException {
        JFrame frame = new JFrame("Leader Board");
        frame.setSize(450, 600);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tải font tùy chỉnh
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("D:\\sourceCode\\Flappy-bird-main\\Botsmatic_font\\FlappyBirdRegular-9Pq0.ttf"));
            customFont = customFont.deriveFont(80f); // Thiết lập kích thước font
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.out.println("Font không thể được tải. Sử dụng font mặc định.");
        }

        // Tạo tiêu đề "Leader Board" căn giữa với font tùy chỉnh
        JLabel titleLabel = new JLabel("Leader Board", JLabel.CENTER);
        if (customFont != null) {
            titleLabel.setFont(customFont);
        } else {
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        }
        frame.add(titleLabel, BorderLayout.NORTH);

        LeaderBoard newLeaderBoard = new LeaderBoard();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 to add a player:");
        int t = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (t == 1) {
            System.out.println("Enter player's name:");
            String name = sc.nextLine();
            System.out.println("Enter player's score:");
            int score = sc.nextInt();
            Player newp = new Player(name + " " + score);
            newLeaderBoard.UpdateLeaderBoard(newp);
        }
        newLeaderBoard.setScreen(customFont);
        frame.add(newLeaderBoard.showScreen(), BorderLayout.CENTER);
        frame.setVisible(true);
    }
}