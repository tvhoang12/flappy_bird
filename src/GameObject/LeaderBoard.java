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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeaderBoard extends JPanel {
    private static ArrayList<Player> highScore = new ArrayList<>();

    public LeaderBoard() {

        JPanel panel = new JPanel();
        // tao leaderboard
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Leaderboard");
        panel.setPreferredSize(new Dimension(350, 400));
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.DARK_GRAY);
        panel.add(label);


        // doc file LeaderBoard.txt
        setHighScore("D:\\sourceCode\\Flappy-bird-main\\src\\GameObject\\LeaderBoard.txt");

        panel.setOpaque(false);
        //show 10 nguoi choi cao nhat
        panel.add(showLeaderBoard(), BorderLayout.CENTER);

        panel.setVisible(true);
        add(panel);
    }

    public void setHighScore(String path) {
        // Logic for showing the leaderboard
        try {
        Scanner sc = new Scanner(new File(path));

        while(sc.hasNextLine()) {
            Player temp = new Player(sc.nextLine());
            highScore.add(temp);
        }
        sc.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void UpdateLeaderBoard(Player newHighScore) throws IOException {
        if(newHighScore.isBigger(highScore.get(9))) {
            if(newHighScore.isBigger(highScore.get(0))) {
                highScore.add(0, newHighScore);
            }
            else {
                int i = 1;
                while (highScore.get(i).isBigger(newHighScore) && i < 10) {
                    i ++;
                }
                highScore.add(i, newHighScore);
            }   
            highScore.remove(10);
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

    public JPanel showLeaderBoard() {
        JPanel highScorePanel = new JPanel();
        highScorePanel.setLayout(new GridLayout(10, 3, 3, 10));

        highScorePanel.setSize(300, 400);
        highScorePanel.setOpaque(false);
        highScorePanel.setBorder(null);

        for (int i = 0; i < 10; i++) {
            JLabel temp = new JLabel((i + 1) + ".");
            temp.setFont(new Font("Arial", Font.BOLD, 20));
            temp.setForeground(Color.DARK_GRAY);
            highScorePanel.add(temp);

            temp = new JLabel(highScore.get(i).getNickName());
            temp.setFont(new Font("Arial", Font.BOLD, 20));
            temp.setForeground(Color.DARK_GRAY);
            highScorePanel.add(temp);
            
            temp = new JLabel(Integer.toString(highScore.get(i).getScore()));
            temp.setFont(new Font("Arial", Font.BOLD, 20));
            temp.setForeground(Color.DARK_GRAY);
            highScorePanel.add(temp);

        }

        return highScorePanel;
    }

    public boolean isHighScore(Player newHighScore) {
        return newHighScore.isBigger(highScore.get(9));
    }

}