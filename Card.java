import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Darstellung einer Spielkarte im Spiel.
 */
public abstract class Card {
    private String name;
    private String effect;
    private int score;

    /**
     * Konstruktor.
     *
     * @param name Name der Karte.
     * @param effect Wirkung der Karte.
     * @param score Kartenwert.
     */
    public Card(String name, String effect, int score) {
        this.name = name;
        this.effect = effect;
        this.score = score;
    }

    /**
     * Gibt den Namen der Karte.
     *
     * @return Name der Karte.
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Wirkung der Karte.
     *
     * @return Wirkung der Karte.
     */
    public String getEffect() {
        return effect;
    }

    /**
     * Gibt den Kartenwert.
     *
     * @return Punktwert der Karte.
     */
    public int getScore(){
        return score;
    }

    /**
     * Führ den spezifischen Effekt der gewünschten Karte aus.
     *
     * @param currentPlayer Spieler der die Karte spielt.
     * @param players Liste aller Spieler im Spiel.
     * @param deck Kartendeck des Spiels.
     */
    public abstract void performEffect(Player currentPlayer, List<Player> players, Deck deck);
}
/**
 * Die GuardCard-Klasse stellt die Wächterin-Karte im Spiel dar.
 */
class GuardCard extends Card {
    public GuardCard() {
        super("Wächterin", "Errätst du die Handkarte eines Mitspielers, " +
                "scheidet dieser aus. Gilt nicht für \"Wächterin\"!", 1);
    }
    @Override
    public void performEffect(Player currentPlayer, List<Player> players, Deck deck) {
        Scanner scanner = new Scanner(System.in);

        List<Player> selectablePlayers = new ArrayList<>();

        for (Player player : players) {
            if (!player.isEliminated() && !player.isProtected() && player != currentPlayer) {
                selectablePlayers.add(player);
            }
        }

        if (selectablePlayers.isEmpty()) {
            System.out.println("Es gibt keine wählbaren Spieler, die nicht geschützt oder bereits ausgeschieden sind.");
            System.out.println("Deine Karte wird ohne Effekt ausgespielt.");
            return;
        }

        Player targetPlayer = null;
        int guessedCard = 0;

        while (true) {
            System.out.println("Wähle einen anderen Spieler (1-" + selectablePlayers.size() + "):");
            for (int i = 0; i < selectablePlayers.size(); i++) {
                Player player = selectablePlayers.get(i);
                System.out.println((i + 1) + ". " + player.getName());
            }

            int targetPlayerNumber;
            boolean validSelection = false;

            while (!validSelection) {
                try {
                    targetPlayerNumber = scanner.nextInt();
                    if (targetPlayerNumber >= 1 && targetPlayerNumber <= selectablePlayers.size()) {
                        targetPlayer = selectablePlayers.get(targetPlayerNumber - 1);
                        validSelection = true;
                    } else {
                        System.out.println("Ungültige Auswahl. Bitte wähle einen gültigen Spieler.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Ungültige Eingabe. Bitte gib die Nummer des Spielers an.");
                    scanner.nextLine();
                }
            }

            validSelection = false;

            while (!validSelection) {
                System.out.println("2.Priester  3.Baron     4.Zofe      5.Prinz     ");
                System.out.println("6.König     7.Gräfin    8.Prinzessin  ");
                System.out.print("Errate die Handkarte des ausgewählten Spielers (2-8): ");

                try {
                    guessedCard = scanner.nextInt();
                    if (guessedCard >= 2 && guessedCard <= 8) {
                        validSelection = true;
                    } else {
                        System.out.println("Ungültige Auswahl. Bitte wähle eine Karte zwischen 2 und 8.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Ungültige Eingabe. Bitte gib eine gültige Karte an.");
                    scanner.nextLine();
                }
            }

            // Überprüfung
            if (targetPlayer.getHand().get(0).getScore() == guessedCard) {
                System.out.println("Richtig erraten! " + targetPlayer.getName() + " scheidet aus.");
                targetPlayer.eliminate();
            } else {
                System.out.println("Falsch geraten. " + targetPlayer.getName() + " scheidet nicht aus.");
            }

            break;
        }
    }
}
/**
 * Die PriestCard-Klasse stellt die Priester-Karte im Spiel dar.
 */
class PriestCard extends Card {
    public PriestCard() {
        super("Priester", "Schaue dir die Handkarte eines Mitspielers an.", 2);
    }

    public void performEffect(Player currentPlayer, List<Player> players, Deck deck) {
        Scanner scanner = new Scanner(System.in);

        List<Player> selectablePlayers = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (!player.isProtected() && !player.equals(currentPlayer) && !player.isEliminated()) {
                selectablePlayers.add(player);
                System.out.println((selectablePlayers.size()) + ". " + player.getName());
            }
        }

        if (selectablePlayers.isEmpty()) {
            System.out.println("Es gibt keine wählbaren Spieler, die nicht geschützt oder bereits ausgeschieden sind.");
            System.out.println("Deine Karte wird ohne Effekt ausgespielt.");
            return;
        }

        int chosenPlayerIndex;

        while (true) {
            System.out.print("Wähle einen Mitspieler (1-" + selectablePlayers.size() + "): ");
            try {
                chosenPlayerIndex = scanner.nextInt();
                if (chosenPlayerIndex >= 1 && chosenPlayerIndex <= selectablePlayers.size()) {
                    break;
                } else {
                    System.out.println("Ungültige Auswahl. Bitte wähle einen Mitspieler.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ungültige Eingabe. Bitte gib die Nummer des Spielers an.");
                scanner.nextLine();
            }
        }

        Player chosenPlayer = selectablePlayers.get(chosenPlayerIndex - 1);
        Card chosenCard = chosenPlayer.getHand().get(0);

        // Zeige die Handkarte des gewählten Spielers
        System.out.println(currentPlayer.getName() + " schaut sich die Handkarte von " + chosenPlayer.getName() + " an.");
        System.out.println(chosenPlayer.getName() + " hat die Karte: " + chosenCard.getName());
    }
}
/**
 * Die BaronCard-Klasse stellt die Baron-Karte im Spiel dar.
 */
class BaronCard extends Card {
    public BaronCard() {
        super("Baron", "Vergleiche deine Handkarte mit der eines Mitspielers." +
                "Der Spieler mit dem niedrigeren Wert scheidet aus.", 3);
    }

    @Override
    public void performEffect(Player currentPlayer, List<Player> players, Deck deck) {
        Scanner scanner = new Scanner(System.in);


        List<Player> selectablePlayers = new ArrayList<>();
        for (Player player : players) {
            if (!player.isProtected() && !player.isEliminated() && !player.equals(currentPlayer)) {
                selectablePlayers.add(player);
            }
        }

        if (selectablePlayers.isEmpty()) {
            System.out.println("Es gibt keine wählbaren Spieler, die nicht geschützt oder bereits ausgeschieden sind.");
            System.out.println("Deine Karte wird ohne Effekt ausgespielt.");
            return;
        }

        boolean validSelection = false;
        while (!validSelection) {

            System.out.println("Wähle einen anderen Spieler (1-" + selectablePlayers.size() + ") zum Vergleich:");
            for (int i = 0; i < selectablePlayers.size(); i++) {
                Player player = selectablePlayers.get(i);
                System.out.println((i + 1) + ". " + player.getName());
            }

            int selectedPlayerIndex = -1;
            try {
                selectedPlayerIndex = scanner.nextInt() - 1;
            } catch (InputMismatchException e) {
                System.out.println("Ungültige Eingabe. Bitte gib eine gültige Spielerzahl an.");
                scanner.next();
            }

            if (selectedPlayerIndex >= 0 && selectedPlayerIndex < selectablePlayers.size()) {
                validSelection = true;

                Player selectedPlayer = selectablePlayers.get(selectedPlayerIndex);
                Card currentPlayerCard = currentPlayer.getHand().get(0);
                Card selectedPlayerCard = selectedPlayer.getHand().get(0);

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
            } else {
                System.out.println("Ungültige Auswahl. Bitte wähle einen Spieler innerhalb des angegebenen Bereichs.");
            }
        }
    }
}
/**
 * Die HandmaidCard-Klasse stellt die Zofe-Karte im Spiel dar.
 */
class HandmaidCard extends Card {
    public HandmaidCard() {
        super("Zofe", "Du bist bis zu deinem nächsten Zug geschützt.",4);
    }
    @Override
    public void performEffect (Player currentPlayer, List<Player> players, Deck deck){
        // Setze den Schutz-Status des aktuellen Spielers auf "geschützt"
        currentPlayer.setProtected(true);
        System.out.println(currentPlayer.getName() + " ist bis zu seinem nächsten Zug geschützt.");
    }
}
/**
 * Die PrinceCard-Klasse stellt die Prinz-Karte im Spiel dar.
 */
class PrinceCard extends Card {
    public PrinceCard() {
        super("Prinz", "Wähle einen Spieler, der seine Handkarte ablegt und eine neue Karte zieht.", 5);
    }
    @Override
    public void performEffect(Player currentPlayer, List<Player> players, Deck deck) {
        Scanner scanner = new Scanner(System.in);

        List<Player> selectablePlayers = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (!player.isProtected() && !player.isEliminated() && !player.equals(currentPlayer)) {
                selectablePlayers.add(player);
                System.out.println((selectablePlayers.size()) + ". " + player.getName());
            }
        }

        int chosenPlayerIndex = 0;

        if (selectablePlayers.isEmpty()) {
            System.out.println("Es gibt keine wählbaren Spieler, die nicht geschützt oder bereits ausgeschieden sind.");
            System.out.println("Du wirst dich selbst wählen.");
        } else {
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Wähle einen anderen Spieler (1-" + selectablePlayers.size() + "):");
                if (scanner.hasNextInt()) {
                    chosenPlayerIndex = scanner.nextInt();
                    if (chosenPlayerIndex >= 0 && chosenPlayerIndex <= selectablePlayers.size()) {
                        validInput = true;
                    } else {
                        System.out.println("Ungültige Auswahl. Bitte wähle einen gültigen Spieler.");
                    }
                } else {
                    scanner.next();
                    System.out.println("Ungültige Auswahl. Bitte wähle einen gültigen Spieler.");
                }
            }
        }

        Player targetPlayer;
        if (chosenPlayerIndex == 0) {

            targetPlayer = currentPlayer;
        } else {

            targetPlayer = selectablePlayers.get(chosenPlayerIndex - 1);
        }

        List<Card> targetPlayerHand = targetPlayer.getHand();
        Card discardedCard = targetPlayerHand.get(0);

        targetPlayerHand.remove(discardedCard);

        // Ziehe eine neue Karte für den Ziel-Spieler vom Deck
        Card newCard = deck.drawCard();
        targetPlayerHand.add(newCard);

        System.out.println(targetPlayer.getName() + " hat seine Handkarte abgelegt und eine neue Karte gezogen.");
    }
}
/**
 * Die KingCard-Klasse stellt die König-Karte im Spiel dar.
 */
class KingCard extends Card {
    public KingCard() {
        super("König", "Tausche deine Handkarte mit der eines Mitspielers.", 6);
    }

    @Override
    public void performEffect(Player currentPlayer, List<Player> players, Deck deck) {
        Scanner scanner = new Scanner(System.in);

        List<Player> selectablePlayers = new ArrayList<>();
        List<Card> currentPlayerHand = currentPlayer.getHand();
        for (Player player : players) {
            if (!player.isProtected() && !player.isEliminated() && !player.equals(currentPlayer)) {
                selectablePlayers.add(player);
            }
        }


        if (selectablePlayers.isEmpty()) {
            System.out.println("Es gibt keine wählbaren Spieler, die nicht geschützt oder bereits ausgeschieden sind.");
            System.out.println("Deine Karte wird ohne Effekt ausgespielt.");
            return;
        }

        int chosenPlayerIndex = -1;

        while (chosenPlayerIndex < 1 || chosenPlayerIndex > selectablePlayers.size()) {
            System.out.println("Wähle einen anderen Spieler (1-" + selectablePlayers.size() + "):");
            for (int i = 0; i < selectablePlayers.size(); i++) {
                System.out.println((i + 1) + ". " + selectablePlayers.get(i).getName());
            }

            if (scanner.hasNextInt()) {
                chosenPlayerIndex = scanner.nextInt();

                if (chosenPlayerIndex < 1 || chosenPlayerIndex > selectablePlayers.size()) {
                    System.out.println("Ungültige Auswahl. Bitte wähle einen gültigen Spieler.");
                }
            } else {
                System.out.println("Ungültige Auswahl. Bitte wähle einen Spieler innerhalb des angegebenen Bereichs.");
                scanner.next();
            }
        }
        Card unplayedCard = currentPlayerHand.get(0);
        if (currentPlayer.getPlayedCards().contains(currentPlayerHand.get(0))) {
            unplayedCard = currentPlayerHand.get(1);
        }

        Player targetPlayer = selectablePlayers.get(chosenPlayerIndex - 1);


        //List<Card> currentPlayerHand = currentPlayer.getHand();
        List<Card> targetPlayerHand = targetPlayer.getHand();

        //Card currentPlayerCard = unplayedCard;
        Card targetPlayerCard = targetPlayerHand.get(0);

        //currentPlayerHand.set(currentPlayerHand.indexOf(currentPlayerCard), targetPlayerCard);
        //targetPlayerHand.set(0, currentPlayerCard);
        currentPlayerHand.remove(unplayedCard);
        currentPlayerHand.add(targetPlayerCard);

        targetPlayerHand.remove(targetPlayerCard);
        targetPlayerHand.add(unplayedCard);

        /*System.out.println("Du tauschst deine " + currentPlayerCard.getName() + " Karte mit der " + targetPlayerCard.getName() +
                " Karte von " + targetPlayer.getName() + ".");*/
        System.out.println("Du tauschst deine " + unplayedCard.getName() + " Karte mit der " + targetPlayerCard.getName() +
                " Karte von " + targetPlayer.getName() + ".");
    }
}
/**
 * Die CountessCard-Klasse stellt die Gräfin-Karte im Spiel dar.
 */
class CountessCard extends Card {
    public CountessCard() {
        super("Gräfin", "Wenn du zusätzlich König oder Prinz auf der Hand hast," +
                "musst du die Gräfin ausspielen.",7);
    }
    @Override
    public void performEffect (Player currentPlayer, List<Player> players, Deck deck){
        System.out.println(currentPlayer.getName() + " hat die Gräfin gespielt.");
    }
}
/**
 * Die PrincessCard-Klasse stellt die Prinzessin-Karte im Spiel dar.
 */
class PrincessCard extends Card {
    public PrincessCard() {
        super("Prinzessin", "Wenn du die Prinzessin ablegst, scheidest du aus.", 8);
    }

    @Override
    public void performEffect(Player currentPlayer, List<Player> players, Deck deck) {
        // Entferne die Prinzessin aus der Hand des aktuellen Spielers
        //List<Card> currentPlayerHand = currentPlayer.getHand();
        //currentPlayerHand.remove(this);

        //players.remove(currentPlayer);
        // Der Spieler scheidet aus dem Spiel aus
        currentPlayer.eliminate();

        System.out.println(currentPlayer.getName() + " hat die Prinzessin abgelegt und scheidet aus dem Spiel aus.");
    }
}