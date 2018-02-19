package nl.ordina.game;

public class Mancala extends Position {

    public Mancala() {
        super(0);
    }

    public void addStones(int amount) {
        this.numberOfStones += amount;
    }

    public int getNumberOfStones() {
        return numberOfStones;
    }

    @Override
    public String toString() {
        return String.format("{%2s}", numberOfStones);
    }
}
