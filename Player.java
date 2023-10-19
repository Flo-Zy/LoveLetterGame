import java.util.ArrayList;
import java.util.List;

class Player {
    private String name;
    private int score;
    private List<String> hand;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public List<String> getHand() {
        return hand;
    }
}
