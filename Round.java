import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Round {
    private static Deck deck;
    private static List<Player> players;
    private int currentPlayerIndex;
    private Scanner scanner;

    public Round(Deck deck, List<Player> players) {
        this.deck = deck;
        this.players = players;
        currentPlayerIndex = 0;
        scanner = new Scanner(System.in);
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
        if (currentPlayer.isProtected()) {
            // Wenn der Spieler geschützt ist, gib eine Nachricht aus und beende seinen Zug
            System.out.println(currentPlayer.getName() + " ist geschützt und überspringt diesen Zug.");
            currentPlayer.setProtected(false); // Setze den Schutz-Status zurück
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            return;
        }

        Card drawnCard = deck.drawCard();
        if (drawnCard != null) {
            currentPlayer.addToHand(drawnCard);
        }

        while (true) {

            // Ziehe eine Karte vom Nachziehstapel und füge sie der Hand des Spielers hinzu

            // Gib die Karten in der Hand des aktuellen Spielers aus
            System.out.println(currentPlayer.getName() + "'s Hand:");
            List<Card> hand = currentPlayer.getHand();
            for (int i = 0; i < hand.size(); i++) {
                System.out.println((i + 1) + ". " + hand.get(i).getName());
            }

            // Lasse den Spieler eine Karte aus seiner Hand auswählen
            System.out.print("Wähle die Nummer der Karte, die du spielen möchtest: ");
            int chosenCardIndex;

            try {
                chosenCardIndex = scanner.nextInt();
            } catch (InputMismatchException e) {
                // Ungültige Eingabe (nicht numerisch)
                System.out.println("Ungültige Eingabe. Bitte gib eine gültige Kartennummer an.");
                scanner.nextLine(); // Leere den Scanner-Puffer
                continue;
                // Beende die Methode
            }

            if (chosenCardIndex < 1 || chosenCardIndex > hand.size()) {
                System.out.println("Ungültige Auswahl. Bitte wähle eine gültige Kartennummer.");
                continue;
                // Beende die Methode
            }

            // Hol die ausgewählte Karte
            Card selectedCard = hand.get(chosenCardIndex - 1);

            // Führe die Aktion der ausgewählten Karte aus (performEffect)
            selectedCard.performEffect(currentPlayer, players);

            // Entferne die ausgespielte Karte aus der Hand des Spielers
            hand.remove(chosenCardIndex - 1);

            // Wechsle zum nächsten Spieler
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            break;
        }
    }
}
