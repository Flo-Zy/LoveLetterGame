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
        System.out.println("Du kannst nun mit \\playcard deinen Spielzug ausführen");
    }

    public void playTurn() {
        try {
            // Überprüfe, ob der Nachziehstapel leer ist
            if (deck.isEmpty()) {
                endRound();
                return;
            }
            Player currentPlayer = players.get(currentPlayerIndex);

            if (currentPlayer.isEliminated()) {
                System.out.println(currentPlayer.getName() + " ist bereits ausgeschieden und überspringt diesen Zug.");
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                return;
            }

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
                Player nextPlayer = players.get(currentPlayerIndex);

                System.out.println("Dein Zug wurde jetzt beendet.");
                Thread.sleep(2500);
                System.out.println(nextPlayer.getName() + " ist jetzt dran und kann mit \\playcard seinen Spielzug beginnen");
                break;
            }
        } catch (InterruptedException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    private void endRound() {
        // Überprüfe, wer die aktuelle Runde gewonnen hat
        Player roundWinner = determineRoundWinner();
        if (roundWinner != null) {
            roundWinner.increaseScore(); // Der Gewinner erhält einen Punkt
        }

        // Zeige den Gewinner und den aktuellen Punktestand an
        System.out.println("Runde beendet! " + roundWinner.getName() + " gewinnt diese Runde.");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore() + " Punkt(e)");
        }

        // Bereite die Spieler für die nächste Runde vor
        prepareForNextRound();

        // Mische die Karten und starte die nächste Runde
        startRound();
    }

    private Player determineRoundWinner() {
        // Hier kannst du die Logik implementieren, um den Gewinner der Runde zu bestimmen.
        // Dazu könntest du die Kartenwerte der verbleibenden Spieler vergleichen.

        // Zum Beispiel:
        Player roundWinner = null;
        int maxScore = 0;
        int remainingPlayers = 0;

        for (Player player : players) {
            // Überprüfe, ob der Spieler noch im Spiel ist (nicht ausgeschieden)
            if (!player.isEliminated()) {
                remainingPlayers++;
                int playerScore = calculatePlayerScore(player);
                if (playerScore > maxScore) {
                    maxScore = playerScore;
                    roundWinner = player;
                }
            }
        }

        // Wenn nur noch ein Spieler übrig ist, wird dieser als Gewinner der Runde betrachtet
        if (remainingPlayers == 1) {
            roundWinner = players.stream().filter(player -> !player.isEliminated()).findFirst().orElse(null);
        }

        return roundWinner;
    }

    private int calculatePlayerScore(Player player) {
        // Hier kannst du die Logik implementieren, um den Punktestand eines Spielers basierend auf seinen Karten zu berechnen.
        // Du kannst die Karten in seiner Hand durchgehen und ihre Werte addieren.

        int score = 0;
        for (Card card : player.getHand()) {
            score += card.getScore();
        }

        return score;
    }

    private void prepareForNextRound() {
        // Hier kannst du die Spieler für die nächste Runde vorbereiten, z.B. ihre Hände leeren.
        for (Player player : players) {
            player.clearHand();
        }
    }
}
