import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Die Deck-Klasse repräsentiert ein virtuelles Kartendeck.
 */
 public class Deck {
    private List<Card> cards;

    /**
     * Erstellt das Kartendeck als Array-Liste.
     */
    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
    }
    /**
     * Kartendeck wird initialisiert und mit den Karten befüllt.
     */
    private void initializeDeck(){
        cards.add(new PrincessCard());
        cards.add(new CountessCard());
        cards.add(new KingCard());
        cards.add(new PrinceCard());
        cards.add(new PrinceCard());
        cards.add(new HandmaidCard());
        cards.add(new HandmaidCard());
        cards.add(new BaronCard());
        cards.add(new BaronCard());
        cards.add(new PriestCard());
        cards.add(new PriestCard());
        cards.add(new GuardCard());
        cards.add(new GuardCard());
        cards.add(new GuardCard());
        cards.add(new GuardCard());
        cards.add(new GuardCard());
    }

    /**
     * Mischt die Karten im Deck
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Zieht eine Karte aus dem Deck.
     *
     * @return Die Karte die gezogen wird.
     */
    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }

    /**
     * Überprüft, ob das Deck leer ist.
     *
     * @return true, falls das Deck leer ist, sonst false.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Fügt eine Karte in das Deck
     *
     * @param card Hinzugefügte Karte.
     */
    public void addCard(Card card){
        cards.add(card);
    }

    /**
     * Fügt dem Deck die Liste der gespielten Karten hinzu.
     *
     * @param playerPlayedCards Die gespielten Karten.
     */
    public void addPlayedCards(List<Card> playerPlayedCards) {
    }

    /**
     * Fügt dem Deck die beiseite-gelegten Karten hinzu.
     *
     * @param asideCards Die Liste der beiseite-gelegten Karten.
     */
    public void addAsideCards(List<Card> asideCards) {
        cards.addAll(asideCards);
    }

    /**
     * Zieht eine Karte aus der Liste der beiseite-gelegten Karten.
     *
     * @param asideCards Die Liste der beiseite-gelegten Karten.
     * @return Die gezogene Karte.
     */
    public Card drawFromAsideCards(List<Card> asideCards) {
        if (asideCards.size() == 1) {
            return asideCards.remove(0); // erste Karte aus asideCards wird rausgenommen.
        }
        return null; // Gib null zurück, wenn asideCards leer ist.
    }
 }

