import java.util.ArrayList;
import java.util.List;

/**
 * Player Klasse die alle wichtigen Attribute und Methoden für einen einzelnen Spieler enthält.
 */
public class Player {
    private String name;
    private int daysUntilLastDate;
    private int score;
    private List<Card> hand;
    private List<Card> playedCards;
    private boolean isProtected;
    private boolean eliminated;

    /**
     * Konstruktor.
     *
     * @param name Name des Spielers.
     * @param daysUntilLastDate Tage seit dem letzten Date. Wichtig für die Spieler-Reihenfolge.
     */
    public Player(String name, int daysUntilLastDate) {
        this.name = name;
        this.daysUntilLastDate = daysUntilLastDate;
        this.score = 0;
        this.hand = new ArrayList<>();
        playedCards = new ArrayList<>();
        this.eliminated = false;
    }

    /**
     * Gibt den Namen des Spielers.
     *
     * @return Name des Spielers.
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Anzahl der Tage seit dem letzten Date.
     *
     * @return Tage seit dem letzten Date.
     */
    public int getDaysUntilLastDate() {
        return daysUntilLastDate;
    }

    /**
     * Gibt den Punktestand des Spielers zurück.
     *
     * @return Punktestand des Spielers.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gibt die Karten des Spielers zurück.
     *
     * @return Liste der Karten des Spielers.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Fügt eine Karte dem Spieler hinzu.
     *
     * @param card Hinzugefügte Karte.
     */
    public void addToHand(Card card) {
        hand.add(card);
    }

    /**
     * Löscht alle Karten des Spielers.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Liste die die gespielten Karten des Spielers als Ablagestapel darstellt
     *
     * @return Liste der abgelegten Karten.
     */
    public List<Card> getPlayedCards() {
        return playedCards;
    }

    /**
     * Legt die gerade gespielte Karte in den Ablagestapel.
     *
     * @param selectedCard Die Karte die gespielt wird.
     */
    public void addPlayedCard(Card selectedCard) {
        playedCards.add(selectedCard);
    }

    /**
     * Überprüft ob der Spieler durch die Zofe-Karte geschützt ist
     *
     * @return Falls geschützt, dann true, sonst false.
     */
    public boolean isProtected() {
        return isProtected;
    }

    /**
     * Setzt durch das Spielen der Zofe-Karte den Spieler auf geschützt.
     *
     * @param protectedStatus Falls geschützt, dann true, sonst false.
     */
    public void setProtected(boolean protectedStatus) {
        isProtected = protectedStatus;
    }

    /**
     * Sollte der Spieler eine Runde gewinnen, wird sein Score um 1 Punkt inkrementiert.
     */
    public void increaseScore() {
        score++;
    }

    /**
     * Nimmt die Werte der Karten in der Hand des Spielers und berechnet diese.
     *
     * @return Der gerechnete Wert
     */
    public int calculatePlayerScore() {

        int score = 0;
        for (Card card : hand) {
            score += card.getScore();
        }

        return score;
    }

    /**
     * Setzt den Status des Spielers auf Eliminiert.
     *
     * @param eliminated true, falls der Spieler ausgeschieden ist, sonst false
     */
    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    /**
     * Überprüft, ob ein Spieler eliminiert ist oder nicht.
     *
     * @return Falls eliminiert, dann true, sonst false.
     */
    public boolean isEliminated() {
        return eliminated;
    }

    /**
     * Setzt den Status des Spielers auf eliminiert.
     */
    public void eliminate() {
        this.eliminated = true;
    }
}
