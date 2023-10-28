import java.util.ArrayList;
import java.util.List;

class Player {
    private String name;
    private int daysUntilLastDate;
    private int score;
    private List<Card> hand;
    private boolean isProtected;

    public Player(String name, int daysUntilLastDate) {
        this.name = name;
        this.daysUntilLastDate = daysUntilLastDate;
        this.score = 0;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getDaysUntilLastDate(){
        return daysUntilLastDate;
    }

    public int getScore() {
        return score;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addToHand(Card card){
        hand.add(card);
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean protectedStatus) {
        isProtected = protectedStatus;
    }
}
