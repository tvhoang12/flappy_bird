package GameObject;

public class Player {
    private int score;
    private String nickName;
    public Player() {}
    public Player(String info) {
        String [] in = info.split(" ");
        this.nickName = in[0];
        this.score = Integer.parseInt(in[1]);
    }

    public String getNickName() {
        return nickName;
    }

    public int getScore() {
        return score;
    }

    public boolean isBigger(Player p2) {
        return score > p2.score;
    }

    @Override
    public String toString() {
        return nickName + " " + score;
    }
}
