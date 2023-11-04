import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

class Round {
    private static Deck deck;
    private static List<Player> players;
    private int currentPlayerIndex;
    private Scanner scanner;
    private static List<Card> asideCards;

    public Round(Deck deck, List<Player> players) {
        this.deck = deck;
        this.players = players;
        currentPlayerIndex = 0;
        scanner = new Scanner(System.in);
        asideCards = new ArrayList<>();
    }

    public static void startRound() {
        deck.shuffle();
        int asideCardCount = players.size() == 2 ? 3 : 1;

        for (int i = 0; i < asideCardCount; i++) {
            Card asideCard = deck.drawCard();
            asideCards.add(asideCard);
        }

        for (Player player : players) {
            Card card = deck.drawCard();
            if (card != null) {
                player.addToHand(card);
            }
        }

        if (asideCardCount == 3) {
            System.out.println("Beiseite gelegte Karten:");
            for (int i = 0; i < asideCardCount; i++) {
                System.out.println((i + 1) + ". " + asideCards.get(i).getName());
            }
            System.out.println("Du kannst diese Karten jederzeit mit \\showAsideCards anzeigen.");
        }

        Player firstPlayer = players.get(0);
        System.out.println(firstPlayer.getName() +",du kannst nun mit \\playcard deinen Spielzug ausführen");
    }

    public void playTurn() {
        try {
            while (true) {
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

                /*if (currentPlayer.isProtected()) {
                    // Wenn der Spieler geschützt ist, gib eine Nachricht aus und beende seinen Zug
                    System.out.println(currentPlayer.getName() + " ist geschützt und überspringt diesen Zug.");
                    currentPlayer.setProtected(false); // Setze den Schutz-Status zurück
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    return;
                }*/

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

                    // Füge die ausgespielte Karte zu den gespielten Karten des Spielers hinzu
                    currentPlayer.addPlayedCard(selectedCard);

                    if (deck.isEmpty() || allPlayersButOneEliminated()) {
                        endRound(); // Runde beenden und Gewinner ermitteln
                    }
                    // Wechsle zum nächsten Spieler
                    currentPlayerIndex = (currentPlayerIndex) % players.size();
                    Player nextPlayer = players.get(currentPlayerIndex + 1);

                    System.out.println("Dein Zug wurde jetzt beendet.");
                    System.out.println(currentPlayer.getName() + " hat folgende Karten bis jetzt schon gespielt:");
                    for (Card playedCard : currentPlayer.getPlayedCards()) {
                        System.out.println(playedCard.getName());
                    }

                    Thread.sleep(2500);
                    System.out.println(nextPlayer.getName() + " ist jetzt dran und kann mit \\playcard seinen Spielzug beginnen");
                    break;
                }
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                return;
            }
        } catch (InterruptedException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    private void endRound() {
        if (players.stream().filter(player -> !player.isEliminated()).count() == 1) {
            // Es gibt nur einen nicht eliminierten Spieler, dieser ist der Gewinner
            Player roundWinner = players.stream().filter(player -> !player.isEliminated()).findFirst().orElse(null);
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
        } else if (deck.isEmpty()) {
            // Das Deck ist leer, vergleiche die Karten der Spieler
            Player roundWinner = determineRoundWinnerByCards();
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
        }
    }

    private Player determineRoundWinnerByCards() {
        // Implementiere die Logik zur Gewinnerermittlung basierend auf den Kartenwerten der Spieler
        // Zum Beispiel: Vergleiche die Summe der Kartenwerte in den Händen der Spieler

        Player roundWinner = null;
        int maxScore = 0;

        for (Player player : players) {
            // Überprüfe, ob der Spieler noch im Spiel ist (nicht ausgeschieden)
            if (!player.isEliminated()) {
                int playerScore = player.calculatePlayerScore();
                if (playerScore > maxScore) {
                    maxScore = playerScore;
                    roundWinner = player;
                }
            }
        }

        return roundWinner;
    }

    /*private Player determineRoundWinner() {
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
    }*/

    private void prepareForNextRound() {
        Player lastRoundWinner = determineRoundWinnerByCards();

        // Speichere die ursprüngliche Reihenfolge der Spieler
        List<Player> originalOrder = new ArrayList<>(players);

        // Hier kannst du die Spieler für die nächste Runde vorbereiten, z.B. ihre Hände leeren.
        for (Player player : players) {
            player.clearHand();
            List<Card> playerPlayedCards = player.getPlayedCards();
            deck.addPlayedCards(playerPlayedCards);
            playerPlayedCards.clear();
            player.setProtected(false);
        }

        // Erstelle eine neue Liste für die Spieler in der Reihenfolge der nächsten Runde
        List<Player> newOrder = new ArrayList<>();

        // Füge den Gewinner der letzten Runde an erste Stelle
        newOrder.add(lastRoundWinner);

        // Füge die anderen Spieler in der ursprünglichen Reihenfolge hinzu
        for (Player player : originalOrder) {
            if (player != lastRoundWinner) {
                newOrder.add(player);
            }
        }

        // Setze die Spielerliste auf die neue Reihenfolge
        players = newOrder;

        deck.addAsideCards(asideCards);
        asideCards.clear();

        startRound();
    }

    public void showAsideCards() {
        if (asideCards.size() > 0) {
            System.out.println("Beiseite gelegte Karten:");
            for (int i = 0; i < asideCards.size(); i++) {
                System.out.println((i + 1) + ". " + asideCards.get(i).getName());
            }
        } else {
            System.out.println("Es wurden keine Karten beiseite gelegt.");
        }
    }

    private boolean allPlayersButOneEliminated() {
        int activePlayersCount = 0;
        for (Player player : players) {
            if (!player.isEliminated()) {
                activePlayersCount++;
            }
        }
        return activePlayersCount == 1;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
}
