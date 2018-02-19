package nl.ordina.test;

import nl.ordina.game.Game;
import nl.ordina.game.Pit;
import nl.ordina.game.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GameTest {

    @Test
    public void testInputValidation() {
        Game game = new Game();
        game.passTurn();

        assertEquals(false, game.validateInput(0));
        assertEquals(false, game.validateInput(-100));
        assertEquals(false, game.validateInput(Player.HOLES_PER_SIDE + 1));
        assertEquals(false, game.validateInput(Player.HOLES_PER_SIDE + 100));

        assertEquals(true, game.validateInput(Player.HOLES_PER_SIDE));
        assertEquals(true, game.validateInput(1));
    }

    @Test
    public void testPassTurn() {
        Game game = new Game();
        game.passTurn();


        Player playerOnThePlay = game.getPlayerOnThePlay();
        Player waitingPlayer = game.getWaitingPlayer();
        assertNotEquals(playerOnThePlay, waitingPlayer);

        game.passTurn();

        assertNotEquals(playerOnThePlay, game.getPlayerOnThePlay());
        assertNotEquals(waitingPlayer, game.getWaitingPlayer());

        assertEquals(playerOnThePlay, game.getWaitingPlayer());
        assertEquals(waitingPlayer, game.getPlayerOnThePlay());
    }

    @Test
    public void testGameEnded() {
        Game game = new Game();
        game.passTurn();

        for (Pit pit : game.getPlayerOnThePlay().getPits()) {
            pit.removeStones();
        }
        for (Pit pit : game.getWaitingPlayer().getPits()) {
            pit.removeStones();
        }

        assertEquals(true, game.getPlayerOnThePlay().noMoreMoves());

        Pit[] pitsOfPlayerOnThePlay = game.getPlayerOnThePlay().getPits();
        pitsOfPlayerOnThePlay[pitsOfPlayerOnThePlay.length-1].addStone();

        assertEquals(false, game.getPlayerOnThePlay().noMoreMoves());
    }
}
