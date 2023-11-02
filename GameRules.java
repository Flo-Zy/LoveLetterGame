import java.util.List;


public class GameRules {
    public static int getHeartsRequired(int AnzahlDerPlayer) {
        // Bestimme die benötigte Anzahl an Herzen basierend auf der Spieleranzahl.
        switch (AnzahlDerPlayer) {
            case 2:
                return 5;
            case 3:
                return 4;
            case 4:
                return 3;
            default:
                throw new IllegalArgumentException("Ungültige Spieleranzahl");
        }
    }

    public static boolean isGameFinished(Player player, int heartsRequired) {
        // Prüfe, ob das Spiel für einen Spieler beendet ist (basierend auf der Anzahl der Herzen).
        return player.getScore() >= heartsRequired;
    }
}
