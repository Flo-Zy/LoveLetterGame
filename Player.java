import java.util.ArrayList;
import java.util.List;

class Player {
    private String name;
    private int daysUntilDate;
    private int score;
    private List<Card> hand;

    public Player(String name, int daysUntilDate) {
        this.name = name;
        this.daysUntilDate = daysUntilDate;
        this.score = 0;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getDaysUntilDate(){
        return daysUntilDate;
    }

    public int getScore() {
        return score;
    }

    public List<Card> getHand() {
        return hand;
    }
}
