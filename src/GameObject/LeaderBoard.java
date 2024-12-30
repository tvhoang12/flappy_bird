
package GameObject;

import java.awt.*;
import java.io.*;
// import java.util.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeaderBoard extends JPanel {
    private ArrayList<Player> highScore = new ArrayList<>();

    public LeaderBoard() {
        // tao panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //khoi tao leader board cho game - leader board duoc luu trong file LeaderBoard.txt
        //tao label va cau hinh cho no
        JLabel label = new JLabel("Leaderboard");
        panel.setPreferredSize(new Dimension(350, 400));
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.DARK_GRAY);
        panel.add(label);

        setHighScore();
        //cau hinh cho panel
        panel.setOpaque(false);
        //show 10 nguoi choi cao nhat
        panel.add(showLeaderBoard(), BorderLayout.CENTER);
        panel.setVisible(true);

        add(panel);
    }

    
    public void setHighScore() {
        //doc du lieu tu file LeaderBoard.txt
        try {
        Scanner sc = new Scanner(new File("D:\\sourceCode\\Flappy-bird-main\\src\\GameObject\\LeaderBoard.txt"));
        while (sc.hasNextLine()) {
            highScore.add(new Player(sc.nextLine()));
        }
        sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void UpdateLeaderBoard(Player newHighScore) {
        if(highScore.size() < 10 || highScore == null) {
            
            for(int i = 0; i < highScore.size(); i++) {
                if(newHighScore.isBigger(highScore.get(i))) {
                    highScore.add(i, newHighScore);
                    break;
                }
            }
            
        }
        else {
            // cap nhat leader board khi co nguoi choi moi co diem cao hon nguoi choi o vi tri cuoi cung
            if(newHighScore.isBigger(highScore.get(9))) {
                //neu nguoi choi co diem cao hon nguoi dung dau thi them vao dau tien
                if(newHighScore.isBigger(highScore.get(0))) {
                    highScore.add(0, newHighScore);
                }
                else {
                    //tim vi tri nguoi choi co diem cao nhat trong bang it diem hon nguoi choi moi
                    int i = 1;
                    while (highScore.get(i).isBigger(newHighScore) && i < highScore.size()) {
                        i ++;
                    }
                    //them vao vi tri cua nguoi choi vua tim thay
                    highScore.add(i, newHighScore);
                }   
                //loai bo nguoi choi o vi tri cuoi cung(thu 11 do da them vao 1 nguoi choi moi)
                if(highScore.size() > 10) highScore.remove(10);
            }
        }   
        //ghi lai vao file LeaderBoard.txt
        try {
        saveHighScore("D:\\sourceCode\\Flappy-bird-main\\src\\GameObject\\LeaderBoard.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveHighScore(String path) throws IOException {
        // D:\sourceCode\Flappy-bird-main\src\GameObject\LeaderBoard.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Player player : highScore) {
                writer.write(player.toString());
                writer.newLine();
            }
        }
    }

    public JPanel showLeaderBoard() {
        // Tạo 1 panel mới chứa leaderboard
        JPanel highScorePanel = new JPanel(); // Đặt layout mặc định
        
        if (highScore == null || highScore.size() == 0) {
            // Nếu không có dữ liệu
            highScorePanel.setLayout(new BoxLayout(highScorePanel, BoxLayout.Y_AXIS));
            JLabel temp = new JLabel("No data");
            temp.setAlignmentX(Component.CENTER_ALIGNMENT);
            temp.setFont(new Font("Arial", Font.BOLD, 50));
            temp.setForeground(Color.DARK_GRAY);
            highScorePanel.add(temp);
        } else {
            int numPlayers = Math.min(highScore.size(), 10); // Hiển thị tối đa 10 người chơi
            highScorePanel.setLayout(new GridLayout(numPlayers, 3, 10, 10)); // Đặt layout theo số lượng người chơi
            
            for (int i = 0; i < numPlayers; i++) {
                // Hiển thị từng người chơi
                JLabel rankLabel = new JLabel((i + 1) + ".");
                setLabel(rankLabel);
                highScorePanel.add(rankLabel);
    
                JLabel nameLabel = new JLabel(highScore.get(i).getNickName());
                setLabel(nameLabel);
                highScorePanel.add(nameLabel);
    
                JLabel scoreLabel = new JLabel(Integer.toString(highScore.get(i).getScore()));
                setLabel(scoreLabel);
                highScorePanel.add(scoreLabel);
            }
        }
        return highScorePanel;
    }

    public void setLabel(JLabel label) {
        // cau hinh cho label
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.DARK_GRAY);
    }
    //kiem tra xem nguoi choi co phai la nguoi choi co diem cao trong top 10 hay khong
    public boolean isHighScore(Player newHighScore) {
        return newHighScore.isBigger(highScore.get(9));
    }

}