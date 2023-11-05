import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;

public class LoveLetterGame {
    private static List<Player> players;
    private int AnzahlDerPlayer;
    private static Round round;

    public LoveLetterGame() {
        players = new ArrayList<>();
        AnzahlDerPlayer = 0;
    }

    public void addPlayer(String name, int daysUntilLastDate) {
        players.add(new Player(name, daysUntilLastDate));
    }

    public void startGame() {
        //Spiel wird gestartet
        System.out.println("Spiel wird gestartet......");
        round = new Round (new Deck(), players);
        round.startRound();
    }

    public void playCard() {
        round.playRound();
        //Karte wird gespielt
    }

    public void showHand() {
        Player currentPlayer = players.get(round.getCurrentPlayerIndex());
        System.out.println(currentPlayer.getName() + "'s Hand:");
        List<Card> hand = currentPlayer.getHand();
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i).getName());
        }
    }

    public void showScore() {
        System.out.println("Punktestand der Spieler:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore() + " Punkt(e)");
        }
    }

    public void showPlayers() {
        System.out.println("Spielerübersicht:");
        for (Player player : players) {
            String status = player.isEliminated() ? "Ausgeschieden" : "Aktiv";
            System.out.println(player.getName() + " - Status: " + status);
        }
    }

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
                System.out.print("Name von Spieler Nr." + (i + 1) + ":    ");
                String playerName = scanner.nextLine();
                System.out.print( playerName + ", vor wie vielen Tagen hattest du dein letztes Date ? :    ");
                int daysUntilLastDate = scanner.nextInt();
                scanner.nextLine();
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