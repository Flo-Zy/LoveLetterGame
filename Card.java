import java.util.List;
import java.util.Scanner;

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
                "scheidet dieser aus. Gilt nicht für \"Wächterin\"!",1);
    }
    @Override
    public void performEffect (Player currentPlayer, List<Player> players){
        Scanner scanner = new Scanner(System.in);

        int targetPlayerNumber;
        Player targetPlayer = null;

        int guessedCard = 0; // Initialisiere guessedCard

        boolean validSelection = false;

        while (!validSelection) {
            // Gib die Spieler mit Nummern aus
            System.out.println("Wähle einen anderen Spieler (1-" + players.size() + "):");
            for (int i = 0; i < players.size(); i++) {
                System.out.println((i + 1) + ". " + players.get(i).getName());
            }

            // Eingabe der gewählten Nummer
            targetPlayerNumber = scanner.nextInt();

            // Überprüfe, ob die ausgewählte Nummer gültig ist
            if (targetPlayerNumber >= 1 && targetPlayerNumber <= players.size()) {
                targetPlayer = players.get(targetPlayerNumber - 1);

                // Eingabe, um die Handkarte zu erraten
                System.out.print("Errate die Handkarte des ausgewählten Spielers (1-8): ");
                guessedCard = scanner.nextInt();

                // Überprüfe, ob die ausgewählte Karte gültig ist
                if (guessedCard >= 1 && guessedCard <= 8) {
                    validSelection = true;
                } else {
                    System.out.println("Ungültige Auswahl. Bitte wähle eine Karte zwischen 1 und 8.");
                }
            } else {
                System.out.println("Ungültige Auswahl. Bitte wähle einen gültigen Spieler.");
            }
        }

        // Überprüfe, ob die erratene Karte mit der tatsächlichen Karte des Spielers übereinstimmt
        if (targetPlayer.getHand().get(0).getScore() == guessedCard) {
            System.out.println("Richtig erraten! " + targetPlayer.getName() + " scheidet aus.");
            players.remove(targetPlayer);
        } else {
            System.out.println("Falsch geraten. " + targetPlayer.getName() + " scheidet nicht aus.");
        }
    }
}

class PriestCard extends Card {
    public PriestCard() {
        super("Priester", "Schaue dir die Handkarte eines Mitspielers an.",2);
    }
    @Override
    public void performEffect (Player currentPlayer, List<Player> players){
        Scanner scanner = new Scanner(System.in);

        // Lasse den Spieler einen Mitspieler auswählen
        System.out.println("Wähle einen Mitspieler, dessen Handkarte du sehen möchtest:");

        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i).getName());
        }

        int chosenPlayerIndex = scanner.nextInt();

        if (chosenPlayerIndex >= 1 && chosenPlayerIndex <= players.size()) {
            Player chosenPlayer = players.get(chosenPlayerIndex - 1);
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
                "Der Spieler mit dem niedrigeren Wert scheidet aus.",3);
    }
    @Override
    public void performEffect (Player currentPlayer, List<Player> players){
        Scanner scanner = new Scanner(System.in);

        // Wähle einen anderen Spieler, dessen Handkarte du vergleichen möchtest
        System.out.println("Wähle einen anderen Spieler (1-" + (players.size() - 1) + ") zum Vergleich:");
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) != currentPlayer) {
                System.out.println((i + 1) + ". " + players.get(i).getName());
            }
        }

        int selectedPlayerIndex = scanner.nextInt();
        if (selectedPlayerIndex < 1 || selectedPlayerIndex >= players.size() || players.get(selectedPlayerIndex) == currentPlayer) {
            System.out.println("Ungültige Auswahl. Bitte wähle einen anderen Spieler.");
            return;
        }

        Player selectedPlayer = players.get(selectedPlayerIndex);
        Card currentPlayerCard = currentPlayer.getHand().get(1); // Die zweite Karte des aktuellen Spielers
        Card selectedPlayerCard = selectedPlayer.getHand().get(0); // Die einzige Karte des ausgewählten Spielers

        System.out.println(currentPlayer.getName() + " hat eine " + currentPlayerCard.getName());
        System.out.println(selectedPlayer.getName() + " hat eine " + selectedPlayerCard.getName());

        if (currentPlayerCard.getScore() > selectedPlayerCard.getScore()) {
            System.out.println(currentPlayer.getName() + " gewinnt! " + selectedPlayer.getName() + " scheidet aus.");
            players.remove(selectedPlayer);
        } else if (currentPlayerCard.getScore() < selectedPlayerCard.getScore()) {
            System.out.println(selectedPlayer.getName() + " gewinnt! " + currentPlayer.getName() + " scheidet aus.");
            players.remove(currentPlayer);
        } else {
            System.out.println("Unentschieden! Niemand scheidet aus.");
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
        super("Prinz", "Wähle einen Spieler, der seine Handkarte ablegt und eine neue Karte zieht.",5);
    }
    @Override
    public void performEffect (Player currentPlayer, List<Player> players) {
        // Lasse den aktuellen Spieler einen anderen Spieler auswählen
        System.out.println("Wähle einen anderen Spieler (1-" + players.size() + "):");

        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i).getName());
        }

        Scanner scanner = new Scanner(System.in);

        int chosenPlayerIndex = scanner.nextInt();

        if (chosenPlayerIndex < 1 || chosenPlayerIndex > players.size()) {
            System.out.println("Ungültige Auswahl. Bitte wähle einen gültigen Spieler.");
            return;
        }

        // Wähle den Ziel-Spieler
        Player targetPlayer = players.get(chosenPlayerIndex - 1);

        // Der Ziel-Spieler legt seine Handkarte ab und zieht eine neue Karte
        List<Card> targetHand = targetPlayer.getHand();
        if (!targetHand.isEmpty()) {
            Card discardedCard = targetHand.get(0);
            targetHand.remove(0);

            // Ziehe eine neue Karte vom Deck und füge sie der Hand des Ziel-Spielers hinzu
            Card newCard = currentPlayer.getHand().get(0); // Die erste Karte aus der Hand des aktuellen Spielers
            targetHand.add(newCard);

            // Der aktuelle Spieler verliert seine Handkarte
            currentPlayer.getHand().remove(0);

            System.out.println(targetPlayer.getName() + " legt " + discardedCard.getName() + " ab und zieht eine neue Karte.");
        } else {
            System.out.println(targetPlayer.getName() + " hat keine Handkarte zu verlieren.");
        }
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

        int chosenPlayerIndex = scanner.nextInt();

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
        super("Prinzessin", "Wenn du die Prinzessin ablegst, scheidest du aus.",8);
    }
    @Override
    public void performEffect (Player currentPlayer, List<Player> players){
        // Entferne die Prinzessin aus der Hand des aktuellen Spielers
        List<Card> currentPlayerHand = currentPlayer.getHand();
        currentPlayerHand.remove(this);

        // Der Spieler scheidet aus dem Spiel aus
        players.remove(currentPlayer);

        System.out.println(currentPlayer.getName() + " hat die Prinzessin abgelegt und scheidet aus dem Spiel aus.");
    }
}
