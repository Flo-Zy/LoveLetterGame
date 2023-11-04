import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class Card {
    private String name;
    private String effect;
    private int score;

    public Card(String name, String effect, int score) {
        this.name = name;
        this.effect = effect;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    public int getScore(){
        return score;
    }

    public abstract void performEffect(Player currentPlayer, List<Player> players);
}

class GuardCard extends Card {
    public GuardCard() {
        super("Wächterin", "Errätst du die Handkarte eines Mitspielers, " +
                "scheidet dieser aus. Gilt nicht für \"Wächterin\"!", 1);
    }
    @Override
    public void performEffect(Player currentPlayer, List<Player> players) {
        Scanner scanner = new Scanner(System.in);

        int guessedCard = 0; // Initialisiere guessedCard

        List<Player> selectablePlayers = new ArrayList<>();

        for (Player player : players) {
            if (!player.isEliminated() && !player.isProtected()) {
                selectablePlayers.add(player);
            }
        }

        if (selectablePlayers.isEmpty()) {
            System.out.println("Es gibt keine wählbaren Spieler.");
            return;
        }

        int targetPlayerNumber;
        Player targetPlayer = null;
        boolean validSelection = false;

        while (!validSelection) {
            // Gib die wählbaren Spieler mit Nummern aus
            System.out.println("Wähle einen anderen Spieler (1-" + selectablePlayers.size() + "):");
            for (int i = 0; i < selectablePlayers.size(); i++) {
                Player player = selectablePlayers.get(i);
                System.out.println((i + 1) + ". " + player.getName());
            }

            // Eingabe der gewählten Nummer
            targetPlayerNumber = scanner.nextInt();

            if (targetPlayerNumber >= 1 && targetPlayerNumber <= selectablePlayers.size()) {
                targetPlayer = selectablePlayers.get(targetPlayerNumber - 1);

                // Eingabe, um die Handkarte zu erraten

                System.out.print("Errate die Handkarte des ausgewählten Spielers (2-8): ");
                guessedCard = scanner.nextInt();

                if (guessedCard == 1) {
                    System.out.println("Du darfst keine Wächterin wählen.");
                } else if (guessedCard >= 2 && guessedCard <= 8) {
                    validSelection = true;
                } else {
                    System.out.println("Ungültige Auswahl. Bitte wähle eine Karte zwischen 2 und 8.");
                }
            } else {
                System.out.println("Ungültige Auswahl. Bitte wähle einen gültigen Spieler.");
            }
        }

        // Überprüfe, ob die erratene Karte mit der tatsächlichen Karte des Spielers übereinstimmt
        if (targetPlayer.getHand().get(0).getScore() == guessedCard) {
            System.out.println("Richtig erraten! " + targetPlayer.getName() + " scheidet aus.");
            targetPlayer.eliminate();
        } else {
            System.out.println("Falsch geraten. " + targetPlayer.getName() + " scheidet nicht aus.");
        }
    }
}

class PriestCard extends Card {
    public PriestCard() {
        super("Priester", "Schaue dir die Handkarte eines Mitspielers an.", 2);
    }

    @Override
    public void performEffect(Player currentPlayer, List<Player> players) {
        Scanner scanner = new Scanner(System.in);

        // Lasse den Spieler einen Mitspieler auswählen, dessen Handkarte er sehen möchte.
        System.out.println("Wähle einen Mitspieler, dessen Handkarte du sehen möchtest:");
        List<Player> selectablePlayers = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (!player.isProtected() && !player.equals(currentPlayer) && !player.isEliminated()) {
                selectablePlayers.add(player);
                System.out.println((selectablePlayers.size()) + ". " + player.getName());
            }
        }

        if (selectablePlayers.isEmpty()) {
            System.out.println("Es gibt keine wählbaren Spieler, die nicht geschützt sind oder bereits ausgeschieden sind.");
            return; // Beende die Methode, da keine wählbaren Spieler verfügbar sind.
        }

        int chosenPlayerIndex = scanner.nextInt();

        if (chosenPlayerIndex >= 1 && chosenPlayerIndex <= selectablePlayers.size()) {
            Player chosenPlayer = selectablePlayers.get(chosenPlayerIndex - 1);
            Card chosenCard = chosenPlayer.getHand().get(0); // Annahme: Jeder Spieler hat nur eine Karte

            // Zeige die Handkarte des gewählten Spielers
            System.out.println(currentPlayer.getName() + " schaut sich die Handkarte von " + chosenPlayer.getName() + " an.");
            System.out.println(chosenPlayer.getName() + " hat die Karte: " + chosenCard.getName());
        } else {
            System.out.println("Ungültige Auswahl. Bitte wähle einen Mitspieler.");
        }
    }
}

class BaronCard extends Card {
    public BaronCard() {
        super("Baron", "Vergleiche deine Handkarte mit der eines Mitspielers." +
                "Der Spieler mit dem niedrigeren Wert scheidet aus.", 3);
    }

    @Override
    public void performEffect(Player currentPlayer, List<Player> players) {
        Scanner scanner = new Scanner(System.in);

        List<Player> selectablePlayers = new ArrayList<>(players); // Kopie der Spielerliste

        while (true) {
            // Wähle einen anderen Spieler, dessen Handkarte du vergleichen möchtest
            System.out.println("Wähle einen anderen Spieler (1-" + selectablePlayers.size() + ") zum Vergleich:");
            for (int i = 0; i < selectablePlayers.size(); i++) {
                Player player = selectablePlayers.get(i);
                if (!player.isProtected()) {
                    System.out.println((i + 1) + ". " + player.getName());
                }
            }

            int selectedPlayerIndex = scanner.nextInt() - 1;
            if (selectedPlayerIndex < 0 || selectedPlayerIndex >= selectablePlayers.size() || selectedPlayerIndex == players.indexOf(currentPlayer)) {
                System.out.println("Ungültige Auswahl. Bitte wähle einen anderen Spieler.");
            } else {
                // Gültige Auswahl
                Player selectedPlayer = selectablePlayers.get(selectedPlayerIndex);
                Card currentPlayerCard = currentPlayer.getHand().get(1); // Die zweite Karte des aktuellen Spielers
                Card selectedPlayerCard = selectedPlayer.getHand().get(0); // Die einzige Karte des ausgewählten Spielers

                System.out.println(currentPlayer.getName() + " hat eine " + currentPlayerCard.getName());
                System.out.println(selectedPlayer.getName() + " hat eine " + selectedPlayerCard.getName());

                if (currentPlayerCard.getScore() > selectedPlayerCard.getScore()) {
                    System.out.println(currentPlayer.getName() + " gewinnt! " + selectedPlayer.getName() + " scheidet aus.");
                    selectedPlayer.eliminate();
                } else if (currentPlayerCard.getScore() < selectedPlayerCard.getScore()) {
                    System.out.println(selectedPlayer.getName() + " gewinnt! " + currentPlayer.getName() + " scheidet aus.");
                    currentPlayer.eliminate();
                } else {
                    System.out.println("Unentschieden! Niemand scheidet aus.");
                }
                break;
            }
        }
    }
}

class HandmaidCard extends Card {
    public HandmaidCard() {
        super("Zofe", "Du bist bis zu deinem nächsten Zug geschützt.",4);
    }
    @Override
    public void performEffect (Player currentPlayer, List<Player> players){
// Setze den Schutz-Status des aktuellen Spielers auf "geschützt"
        currentPlayer.setProtected(true);
        System.out.println(currentPlayer.getName() + " ist bis zu seinem nächsten Zug geschützt.");
    }
}

class PrinceCard extends Card {
    public PrinceCard() {
        super("Prinz", "Wähle einen Spieler, der seine Handkarte ablegt und eine neue Karte zieht.", 5);
    }
    @Override
    public void performEffect(Player currentPlayer, List<Player> players) {
        // Lasse den aktuellen Spieler einen anderen Spieler auswählen
        System.out.println("Wähle einen anderen Spieler (1-" + players.size() + "):");

        List<Player> selectablePlayers = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (!player.isProtected() && !player.isEliminated() && !player.equals(currentPlayer)) {
                selectablePlayers.add(player);
                System.out.println((selectablePlayers.size()) + ". " + player.getName());
            }
        }

        Scanner scanner = new Scanner(System.in);

        int chosenPlayerIndex = scanner.nextInt();

        if (chosenPlayerIndex < 1 || chosenPlayerIndex > selectablePlayers.size()) {
            System.out.println("Ungültige Auswahl. Bitte wähle einen gültigen Spieler.");
            return;
        }

        // Wähle den Ziel-Spieler
        Player targetPlayer = selectablePlayers.get(chosenPlayerIndex - 1);

        // Tausche die Handkarte des aktuellen Spielers mit der des Ziel-Spielers
        List<Card> currentPlayerHand = currentPlayer.getHand();
        List<Card> targetPlayerHand = targetPlayer.getHand();

        // Prüfe, welche Karte des aktuellen Spielers gerade nicht gespielt wird (z.B. die erste Karte)
        int currentPlayerCardIndex = (currentPlayerHand.get(0) == this) ? 1 : 0;
        int targetPlayerCardIndex = (targetPlayerHand.get(0) == this) ? 1 : 0;

        // Tausche die Karten
        Card currentPlayerCard = currentPlayerHand.get(currentPlayerCardIndex);
        Card targetPlayerCard = targetPlayerHand.get(targetPlayerCardIndex);

        currentPlayerHand.set(currentPlayerCardIndex, targetPlayerCard);
        targetPlayerHand.set(targetPlayerCardIndex, currentPlayerCard);

        String yourCardType = (currentPlayerCardIndex == 0) ? "erste" : "zweite";
        String targetCardType = (targetPlayerCardIndex == 0) ? "erste" : "zweite";

        System.out.println("Du tauschst deine " + yourCardType + " Karte mit einer " + targetCardType + " Karte von " + targetPlayer.getName() + ".");
    }
}

class KingCard extends Card {
    public KingCard() {
        super("König", "Tausche deine Handkarte mit der eines Mitspielers.",6);
    }
    @Override
    public void performEffect (Player currentPlayer, List<Player> players){
        // Lasse den aktuellen Spieler einen anderen Spieler auswählen
        System.out.println("Wähle einen anderen Spieler (1-" + players.size() + "):");

        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i).getName());
        }

        Scanner scanner = new Scanner(System.in);

        int chosenPlayerIndex = scanner.nextInt() ;

        if (chosenPlayerIndex < 1 || chosenPlayerIndex > players.size()) {
            System.out.println("Ungültige Auswahl. Bitte wähle einen gültigen Spieler.");
            return;
        }

        // Wähle den Ziel-Spieler
        Player targetPlayer = players.get(chosenPlayerIndex - 1);

        // Tausche die Handkarte des aktuellen Spielers mit der des Ziel-Spielers
        List<Card> currentPlayerHand = currentPlayer.getHand();
        List<Card> targetPlayerHand = targetPlayer.getHand();

        // Prüfe, welche Karte des aktuellen Spielers gerade nicht gespielt wird (z.B. die erste Karte)
        int currentPlayerCardIndex = (currentPlayerHand.get(0) == this) ? 1 : 0;
        int targetPlayerCardIndex = (targetPlayerHand.get(0) == this) ? 1 : 0;

        // Tausche die Karten
        Card currentPlayerCard = currentPlayerHand.get(currentPlayerCardIndex);
        Card targetPlayerCard = targetPlayerHand.get(targetPlayerCardIndex);

        currentPlayerHand.set(currentPlayerCardIndex, targetPlayerCard);
        targetPlayerHand.set(targetPlayerCardIndex, currentPlayerCard);

        System.out.println("Du tauschst deine Karte mit " + targetPlayer.getName() + ".");
    }
}

class CountessCard extends Card {
    public CountessCard() {
        super("Gräfin", "Wenn du zusätzlich König oder Prinz auf der Hand hast," +
                "musst du die Gräfin ausspielen.",7);
    }
    @Override
    public void performEffect (Player currentPlayer, List<Player> players){
        List<Card> currentPlayerHand = currentPlayer.getHand();

        // Prüfe, ob der aktuelle Spieler König oder Prinz als Handkarte hat
        boolean hasKingOrPrince = currentPlayerHand.stream()
                .anyMatch(card -> card instanceof KingCard || card instanceof PrinceCard);

        if (hasKingOrPrince) {
            System.out.println("Du musst die Gräfin ausspielen, da du König oder Prinz in deiner Hand hast.");
            currentPlayerHand.remove(this);
        }
    }
}

class PrincessCard extends Card {
    public PrincessCard() {
        super("Prinzessin", "Wenn du die Prinzessin ablegst, scheidest du aus.", 8);
    }

    @Override
    public void performEffect(Player currentPlayer, List<Player> players) {
        // Entferne die Prinzessin aus der Hand des aktuellen Spielers
        List<Card> currentPlayerHand = currentPlayer.getHand();
        //currentPlayerHand.remove(this);

        //players.remove(currentPlayer);
        // Der Spieler scheidet aus dem Spiel aus
        currentPlayer.eliminate();

        System.out.println(currentPlayer.getName() + " hat die Prinzessin abgelegt und scheidet aus dem Spiel aus.");
    }
}