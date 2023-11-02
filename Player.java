import java.util.ArrayList;
import java.util.List;

class Player {
    private String name;
    private int daysUntilLastDate;
    private int score;
    private List<Card> hand;
    private List<Card> playedCards;
    private boolean isProtected;
    private boolean eliminated;

    public Player(String name, int daysUntilLastDate) {
        this.name = name;
        this.daysUntilLastDate = daysUntilLastDate;
        this.score = 0;
        this.hand = new ArrayList<>();
        playedCards = new ArrayList<>();
        eliminated = false;
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

    public void clearHand() {
        hand.clear();
    }

    public List<Card> getPlayedCards() {
        return playedCards;
    }

    public void playCard(Card card) {
        hand.remove(card);
        playedCards.add(card);
    }

    public void clearPlayedCards() {
        playedCards.clear();
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean protectedStatus) {
        isProtected = protectedStatus;
    }

    public void increaseScore() {
        score++;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void eliminate() {
        eliminated = true; // Setze den Spieler als ausgeschieden
    }

}
