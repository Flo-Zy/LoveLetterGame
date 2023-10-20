import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoveLetterGame {
    private List<Player> players;
    private int AnzahlDerPlayer;

    public LoveLetterGame() {
        players = new ArrayList<>();
        AnzahlDerPlayer = 0;
    }

    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    public void startGame() {
        //Spiel wird gestartet
    }

    public void playCard(String card) {
        //Karte wird gespielt
    }

    public void showHand() {
        //Spielhand wird gezeigt
    }

    public void showScore() {
        //Zeigt den Score
    }

    public static void main(String[] args) {
        try {
            LoveLetterGame game = new LoveLetterGame();
            Scanner scanner = new Scanner(System.in);
            System.out.println("************************************************");
            System.out.println("**********  Willkomen zu Love Letter  **********");
            System.out.println("************************************************");
            Thread.sleep(1500);
            int numPlayers;
            do {
                System.out.println("Wie viele Spieler möchten Spielen");
                System.out.println("(min: 2 max: 4)");
                numPlayers = scanner.nextInt();
                scanner.nextLine();
            } while (numPlayers < 2 || numPlayers > 4);

            for (int i = 0; i < numPlayers; i++) {
                System.out.print("Name von Spieler Nr." + (i + 1) + ":    ");
                String playerName = scanner.nextLine();
                game.addPlayer(playerName);
            }
            Thread.sleep(1500);
            System.out.println("Spielbefehle: \\start (Startet das Spiel)");
            System.out.println("\\start         Startet das Spiel");
            System.out.println("\\help          Zeigt dir alle möglichen Befehle");

            while (true) {
                System.out.print("Gebe einen Spielbefehl ein: ");
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("\\start")) {
                    game.startGame();
                } else if (command.equals("\\playCard")) {
                    System.out.print("Welche Karte willst du spielen ?: ");
                    String card = scanner.nextLine();
                    game.playCard(card);
                } else if (command.equalsIgnoreCase("\\showHand")) {
                    game.showHand();
                } else if (command.equalsIgnoreCase("\\showScore")) {
                    game.showScore();
                } else if (command.equalsIgnoreCase("\\help")) {
                    System.out.println("Befehle:");
                    System.out.println("\\start         Spiel wird gestartet");
                    System.out.println("\\playCard      Karte wird gespielt");
                    System.out.println("\\showHand      Spielhand wird dir gezeigt");
                    System.out.println("\\showScore     Zeigt dir den Score");
                } else {
                    System.out.println("Unzulässiger Befehl \\help für Befehlsliste");
                }
            }
        } catch (Exception e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }

}