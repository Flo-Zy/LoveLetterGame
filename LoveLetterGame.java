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
        LoveLetterGame game = new LoveLetterGame();
        Scanner scanner = new Scanner(System.in);
        System.out.println("******Willkomen zu Love Letter******");

        int numPlayers;
        do {
            System.out.println("Wie viele Spieler möchten Spielen (min:2 , max:4)");
            numPlayers = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } while (numPlayers < 2 || numPlayers > 4);

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Name von Spieler Nr." + (i + 1)+":    ");
            String playerName = scanner.nextLine();
            game.addPlayer(playerName);
        }

        System.out.println("Spielbefehle: \\start (Startet das Spiel)");
        System.out.println("              \\help (Zeigt dir alle möglichen Befehle)");

        while (true) {
            System.out.print("Enter a game command: ");
            String command = scanner.nextLine();
            if (command.equals("\\start")) {
                game.startGame();
            } else if (command.equals("\\playCard")) {
                System.out.print("Enter the card you want to play: ");
                String card = scanner.nextLine();
                game.playCard(card);
            } else if (command.equals("\\showHand")) {
                game.showHand();
            } else if (command.equals("\\showScore")) {
                game.showScore();
            } else if (command.equals("\\help")) {
                System.out.println("Available commands: \\start, \\playCard, \\showHand, \\showScore, \\help");
            } else {
                System.out.println("Unzulässiger Befehl. \\help für weitere Möglichkeiten.");
            }
        }
    }
}
