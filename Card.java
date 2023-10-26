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

    public abstract void performEffect();
}
class GuardCard extends Card {
    public GuardCard() {
        super("Wächterin", "Errätst du die Handkarte eines Mitspielers, " +
                "scheidet dieser aus. Gilt nicht für \"Wächterin\"!",1);
    }
    @Override
    public void performEffect (){

    }
}

class PriestCard extends Card {
    public PriestCard() {
        super("Priester", "Schaue dir die Handkarte eines Mitspielers an.",2);
    }
    @Override
    public void performEffect (){

    }
}

class BaronCard extends Card {
    public BaronCard() {
        super("Baron", "Vergleiche deine Handkarte mit der eines Mitspielers." +
                "Der Spieler mit dem niedrigeren Wert scheidet aus.",3);
    }
    @Override
    public void performEffect (){

    }
}

class HandmaidCard extends Card {
    public HandmaidCard() {
        super("Zofe", "Du bist bis zu deinem nächsten Zug geschützt.",4);
    }
    @Override
    public void performEffect (){

    }
}

class PrinceCard extends Card {
    public PrinceCard() {
        super("Prinz", "Wähle einen Spieler, der seine Handkarte ablegt und eine neue Karte zieht.",5);
    }
    @Override
    public void performEffect (){

    }
}

class KingCard extends Card {
    public KingCard() {
        super("König", "Tausche deine Handkarte mit der eines Mitspielers.",6);
    }
    @Override
    public void performEffect (){

    }
}

class CountessCard extends Card {
    public CountessCard() {
        super("Gräfin", "Wenn du zusätzlich König oder Prinz auf der Hand hast," +
                "musst du die Gräfin ausspielen.",7);
    }
    @Override
    public void performEffect (){

    }
}

class PrincessCard extends Card {
    public PrincessCard() {
        super("Prinzessin", "Wenn du die Prinzessin ablegst, scheidest du aus.",8);
    }
    @Override
    public void performEffect (){

    }
}