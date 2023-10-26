public class Card {
    private String name;
    private String effect;

    public Card(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }
}
class GuardCard extends Card {
    public GuardCard() {
        super("Guard", "Guess a player's card.");
    }
}

class PriestCard extends Card {
    public PriestCard() {
        super("Priest", "Look at a player's hand.");
    }
}

class BaronCard extends Card {
    public BaronCard() {
        super("Baron", "Compare hands; lower hand is out.");
    }
}

class HandmaidCard extends Card {
    public HandmaidCard() {
        super("Handmaid", "Protection until your next turn.");
    }
}

class PrinceCard extends Card {
    public PrinceCard() {
        super("Prince", "Choose a player to discard their hand.");
    }
}

class KingCard extends Card {
    public KingCard() {
        super("King", "Trade hands with another player.");
    }
}

class CountessCard extends Card {
    public CountessCard() {
        super("Countess", "Discard if caught with King or Prince.");
    }
}

class PrincessCard extends Card {
    public PrincessCard() {
        super("Princess", "Lose if discarded.");
    }
}