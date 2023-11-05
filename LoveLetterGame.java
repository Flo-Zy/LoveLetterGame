import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;

/**
 *Primäre Klasse des Spiels LoveLetter.
 */
public class LoveLetterGame {
    private static List<Player> players;
    private int AnzahlDerPlayer;
    private static Round round;

    /**
     * Konstruktor, der Spielerzahl nimmt und die Liste der Spieler initialisiert.
     */
    public LoveLetterGame() {
        players = new ArrayList<>();
        AnzahlDerPlayer = 0;
    }

    /**
     * Nimmt die Spieler für das Spiel.
     *
     * @param name Name des Spielers.
     * @param daysUntilLastDate Tage seit dem letzten Date. Wichtig für die Spielerreihenfolge.
     */
    public void addPlayer(String name, int daysUntilLastDate) {
        players.add(new Player(name, daysUntilLastDate));
    }

    /**
     * Startet und initialisiert eine neue Runde.
     */
    public void startGame() {
        //Spiel wird gestartet
        System.out.println("Spiel wird gestartet......");
        round = new Round(new Deck(), players);
        round.startRound();
    }

    /**
     * Führt die Rundenlogik für das Spielen eines Kartenzuges aus.
     */
    public void playCard() {
        round.playRound();
        //Karte wird gespielt
    }

    /**
     * Zeigt die Karte, die der Spieler gerade in der Hand hält.
     */
    public void showHand() {
        Player currentPlayer = players.get(round.getCurrentPlayerIndex());
        System.out.println(currentPlayer.getName() + "'s Hand:");
        List<Card> hand = currentPlayer.getHand();
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i).getName());
        }
    }

    /**
     * Der aktuelle Punktestand der Spieler wird angezeigt.
     */
    public void showScore() {
        System.out.println("Punktestand der Spieler:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore() + " Punkt(e)");
        }
    }

    /**
     * Spieler und deren aktueller Status werden angezeigt.
     */
    public void showPlayers() {
        System.out.println("Spielerübersicht:");
        for (Player player : players) {
            String status = player.isEliminated() ? "Ausgeschieden" : "Aktiv";
            System.out.println(player.getName() + " - Status: " + status);
        }
    }

    /**
     * Main-Methode des Spiels mit einer Spieler-Initialisierung und
     * Endlosschleife für das Eingeben der wichtigen Spielbefehle.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            LoveLetterGame game = new LoveLetterGame();
            Scanner scanner = new Scanner(System.in);
            System.out.println("*************************************************");
            System.out.println("**********  Willkommen zu Love Letter  **********");
            System.out.println("*************************************************");
            Thread.sleep(1500);

            String input;
            int AnzahlDerPlayer;
            do {
                System.out.println("Wie viele Spieler möchten Spielen ? (min: 2 max: 4)");
                input = scanner.nextLine();
                try {
                    AnzahlDerPlayer = Integer.parseInt(input);
                    if (AnzahlDerPlayer < 2 || AnzahlDerPlayer > 4) {
                        System.out.println("Die Anzahl der Spieler muss zwischen 2 und 4 liegen.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ungültige Eingabe. \nBitte geben Sie eine Zahl zwischen 2 und 4 ein.");
                    AnzahlDerPlayer = 0;
                }
            } while (AnzahlDerPlayer < 2 || AnzahlDerPlayer > 4);

            for (int i = 0; i < AnzahlDerPlayer; i++) {
                System.out.print("Name von Spieler Nr." + (i + 1) + ": ");
                String playerName = scanner.nextLine();

                int daysUntilLastDate = 0;
                boolean validInput = false;

                while (!validInput) {
                    System.out.print(playerName + "vor wie vielen Tagen hattest du dein letztes Date ? :   ");
                    String tage = scanner.nextLine();
                    try {
                        daysUntilLastDate = Integer.parseInt(tage);
                        validInput = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Ungültige Eingabe. Bitte gib eine ganze Zahl ein.");
                    }
                }
                game.addPlayer(playerName, daysUntilLastDate);
            }
            players.sort(Comparator.comparing(Player::getDaysUntilLastDate));
            System.out.println("Reihenfolge der Spieler:");
            for (Player player : players) {
                System.out.println(player.getName());
            }
            Thread.sleep(1000);
            System.out.println("Wir können nun loslegen !!!");
            Thread.sleep(1500);

            System.out.println("Spielbefehle: ");
            System.out.println("\\start         Startet das Spiel");
            System.out.println("\\help          Zeigt dir alle möglichen Befehle");

            while (true) { //While-Schleife läuft erstmal endlos
                System.out.print("Gebe einen Spielbefehl ein: ");
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("\\start")) {
                    game.startGame();
                } else if (command.equalsIgnoreCase("\\playCard")) {
                    game.playCard();
                } else if (command.equalsIgnoreCase("\\showHand")) {
                    game.showHand();
                } else if (command.equalsIgnoreCase("\\showScore")) {
                    game.showScore();
                } else if (command.equalsIgnoreCase("\\showPlayers")) {
                    game.showPlayers();
                } else if (command.equalsIgnoreCase("\\showAsideCards")) {
                    round.showAsideCards();
                } else if (command.equalsIgnoreCase("\\help")) {
                    System.out.println("Befehle:");
                    System.out.println("\\start            Spiel wird gestartet");
                    System.out.println("\\playCard         Karte wird gespielt");
                    System.out.println("\\showHand         Spielhand wird dir gezeigt");
                    System.out.println("\\showScore        Zeigt dir den Score");
                    System.out.println("\\showPlayers      Zeigt dir die Spieler und ob sie ausgeschieden sind oder nicht");
                    System.out.println("\\showAsideCards   Zeigt dir die Karten die beiseite gelegt worden sind");
                } else {
                    System.out.println("Unzulässiger Befehl \\help für Befehlsliste");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }
}