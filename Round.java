class Round {
    private Deck deck;
    private List<Player> players;
    private int currentPlayerIndex;

    public Round(Deck deck, List<Player> players) {
        this.deck = deck;
        this.players = players;
        currentPlayerIndex = 0;
    }

    public void startRound() {

        deck.shuffle();

    }

    public void playTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}