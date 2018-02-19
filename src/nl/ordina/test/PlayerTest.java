package nl.ordina.test;

import nl.ordina.game.Pit;
import nl.ordina.game.Player;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void testPitCorrectlyCreated() {
        Player player = new Player("test_player");
        Pit[] pits = player.getPits();
        assertEquals(Player.HOLES_PER_SIDE, pits.length);
        for(Pit pit : pits) {
            assertEquals(Player.STONES_PER_HOLE, pit.removeStones());
        }
    }

    @Test
    public void testPlayOwnBoard() {
        Player player1 = new Player("test_player_1");
        Player player2 = new Player("test_player_2");

        player1.play(3, player2);

        int init = Player.STONES_PER_HOLE;
        int[] expectedValues = new int[] { init, init, 0, init+1, init+1, init+1 };
        Pit[] pits = player1.getPits();

        for(int i = 0; i < Player.HOLES_PER_SIDE; i++) {
            assertEquals(expectedValues[i], pits[i].removeStones());
        }
        assertEquals(1, player1.getMancala().getNumberOfStones());
    }

    @Test
    public void testPlayWithOpponentBoard() {
        Player player1 = new Player("test_player_1");
        Player player2 = new Player("test_player_2");

        player1.play(5, player2);

        int init = Player.STONES_PER_HOLE;

        int[] expectedValues = new int[] { init, init, init, init, 0, init+1 };
        Pit[] pits = player1.getPits();

        for(int i = 0; i < Player.HOLES_PER_SIDE; i++) {
            assertEquals(expectedValues[i], pits[i].removeStones());
        }
        assertEquals(1, player1.getMancala().getNumberOfStones());

        expectedValues = new int[] { init+1, init+1, init, init, init, init };
        pits = player2.getPits();
        for(int i = 0; i < Player.HOLES_PER_SIDE; i++) {
            assertEquals(expectedValues[i], pits[i].removeStones());
        }
    }

    @Test
    public void testNoMoreMoves() {
        Player player = new Player("test_player");

        assertEquals(false, player.noMoreMoves());

        for (Pit pit : player.getPits()) {
            pit.removeStones();
        }

        assertEquals(true, player.noMoreMoves());
    }
}
