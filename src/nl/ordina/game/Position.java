package nl.ordina.game;

public class Position {

    protected int numberOfStones;

    public Position(int numberOfStones) {
        this.numberOfStones = numberOfStones;
    }

    public void addStone() {
        numberOfStones++;
    }
}
