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
        System.out.println("Spiel wird gestartet......");
    }

    public void playCard(String card) {
        //Karte wird gespielt
        System.out.println("Karte wird gespielt......");
    }

    public void showHand() {
        //Spielhand wird gezeigt
        System.out.println("Spielhand wird gezeigt......");
    }

    public void showScore() {
        //Zeigt den Score
        System.out.println("Dein Score ist: ...... ");
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
            int numPlayers;
            do {
                System.out.println("Wie viele Spieler möchten Spielen ? (min: 2 max: 4)");
                input = scanner.nextLine();
                try {
                    numPlayers = Integer.parseInt(input);
                    if (numPlayers < 2 || numPlayers > 4) {
                        System.out.println("Die Anzahl der Spieler muss zwischen 2 und 4 liegen.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ungültige Eingabe. \nBitte geben Sie eine Zahl zwischen 2 und 4 ein.");
                    numPlayers = 0;
                }
            } while (numPlayers < 2 || numPlayers > 4);

            for (int i = 0; i < numPlayers; i++) {
                System.out.print("Name von Spieler Nr." + (i + 1) + ":    ");
                String playerName = scanner.nextLine();
                game.addPlayer(playerName);
            }
            System.out.println("Wir können nun loslegen!!!");
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
        } catch (InterruptedException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }
}