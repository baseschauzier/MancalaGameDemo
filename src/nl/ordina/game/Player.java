package nl.ordina.game;

public class Player {

    public static int HOLES_PER_SIDE = 6;
    public static int STONES_PER_HOLE = 4;

    private Pit[] pits = new Pit[HOLES_PER_SIDE];
    private Mancala mancala = new Mancala();

    private String name;

    public Player(String name) {
        this.name = name;
        for (int i = 0; i < HOLES_PER_SIDE; i++) {
            pits[i] = new Pit(STONES_PER_HOLE);
        }
    }

    public Pit[] getPits() {
        return pits;
    }

    public boolean play(int pitNumber, Player otherPlayer) {
        int stonesToSort = pits[pitNumber - 1].removeStones();

        return addStones(stonesToSort, pitNumber, otherPlayer);
    }

    private boolean addStones(int stonesToSort, int startIndex, Player otherPlayer) {
        Pit[] opponentsHoles = otherPlayer.getPits();

        // Pass own holes
        for(int i = startIndex; i < HOLES_PER_SIDE; i++) {
            stonesToSort--;
            if (stonesToSort == 0 && pits[i].isEmpty()) {
                int stonesCaptured = opponentsHoles[getOppositePosition(i)].removeStones();
                mancala.addStones(stonesCaptured);
                mancala.addStone();
                return false;
            } else {
                pits[i].addStone();
                if (stonesToSort == 0) {
                    return false;
                }
            }
        }

        // Pass mancala
        stonesToSort--;
        mancala.addStone();
        if(stonesToSort == 0) {
            return true;
        }

        // Pass opponents holes
        for(int i = 0; i < HOLES_PER_SIDE; i++) {
            stonesToSort--;
            opponentsHoles[i].addStone();
            if (stonesToSort == 0) {
                return false;
            }
        }

        // If still stones left to divide, start from beginning
        return addStones(stonesToSort, 0, otherPlayer);
    }

    private int getOppositePosition(int index) {
        return Math.abs(index - (HOLES_PER_SIDE - 1));
    }

    public boolean noMoreMoves() {
        for (Pit pit : pits) {
            if (!pit.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void sweepBoard() {
        for (Pit pit : pits) {
            mancala.addStones(pit.removeStones());
        }
    }

    public String getName() {
        return name;
    }

    public Mancala getMancala() {
        return mancala;
    }
}
