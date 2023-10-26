import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
 class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
    }

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

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }

    public void addCard(Card card){
        cards.add(card);
    }
}