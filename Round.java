import java.util.List;

class Round {
    private static Deck deck;
    private static List<Player> players;
    private int currentPlayerIndex;

    public Round(Deck deck, List<Player> players) {
        this.deck = deck;
        this.players = players;
        currentPlayerIndex = 0;
    }

    public static void startRound() {
        deck.shuffle();
        for (Player player : players) {
            Card card = deck.drawCard();
            if (card != null) {
                player.addToHand(card);
            }
        }
    }

    public void playTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);

        // Gib die Karten in der Hand des aktuellen Spielers aus
        System.out.println(currentPlayer.getName() + "'s Hand:");
        List<Card> hand = currentPlayer.getHand();
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i).getName());
        }

        // Hier folgt die Implementierung der Karten-Effekte, die spezifisch für jede Karte sind.
        // Du kannst eine Schleife verwenden, um den Spieler auszuwählen, welche Karte er spielen möchte, und dann den entsprechenden Effekt ausführen.

        // Beispiel: Spieler wählt die erste Karte (Index 0) zum Spielen
        int chosenCardIndex = 0;
        Card chosenCard = hand.get(chosenCardIndex);

        if (chosenCard instanceof GuardCard) {
            // Implementiere den Effekt der Guard-Karte
        } else if (chosenCard instanceof PriestCard) {
            // Implementiere den Effekt der Priest-Karte
        } else if (chosenCard instanceof BaronCard) {
            // Implementiere den Effekt der Baron-Karte
        }
        // Führe für jede Karte den entsprechenden Effekt aus

        // Entferne die gespielte Karte aus der Hand des Spielers
        hand.remove(chosenCardIndex);

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}