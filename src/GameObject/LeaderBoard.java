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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeaderBoard extends JPanel {
    private static ArrayList<Player> highScore = new ArrayList<>();

    public LeaderBoard() {
        // tao leaderboard
        setLayout(new GridLayout(10, 1));
        setPreferredSize(new Dimension(300, 400));
        // setBackground(new ImageIcon(getClass().getResource("/res/LeaderBoard_Board_resize.png")));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(showLeaderBoard());
        // for (Player p : highScore) {
        //     JLabel label = new JLabel(p.getNickName() + " " + p.getScore());
        //     setFont(new Font("Arial", Font.BOLD, 20));
        //     add(label);
        // }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(getClass().getResource("/res/LeaderBoard_Board_resize.png")).getImage(), 0, 0, null);
    } 
    
    public void UpdateLeaderBoard(Player newHighScore) throws IOException {
        // Logic for showing the leaderboard
        Scanner sc = new Scanner(new File("D:\\sourceCode\\Flappy-bird-main\\src\\GameObject\\LeaderBoard.txt"));

        while(sc.hasNextLine()) {
            Player temp = new Player(sc.nextLine());
            highScore.add(temp);
        }
        sc.close();

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
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1));
        panel.setPreferredSize(new Dimension(300, 400));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        for (Player p : highScore) {
            JLabel label = new JLabel(p.getNickName() + " " + p.getScore());
            label.setFont(new Font("Arial", Font.BOLD, 20));
            panel.add(label);
        }
        
        return panel;
    }

    public boolean isHighScore(Player newHighScore) {
        return newHighScore.isBigger(highScore.get(9));
    }
}