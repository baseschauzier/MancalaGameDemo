package nl.ordina.game;

public class Pit extends Position {

    public Pit(int numberOfStones) {
        super(numberOfStones);
    }

    public boolean isEmpty() {
        return numberOfStones == 0;
    }

    public int removeStones() {
        int stones = this.numberOfStones;
        this.numberOfStones = 0;
        return stones;
    }

    @Override
    public String toString() {
        return String.format("[%2s]", numberOfStones);
    }
}