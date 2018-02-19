package nl.ordina.game;

import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;

    private Player playerOnThePlay;
    private Player waitingPlayer;

    public Game() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
    }

    public void start() {
        System.out.println("Started a new game of Mancala. Good luck!");
        System.out.println(toString());
        passTurn();

        Scanner s = new Scanner(System.in);
        boolean gameRunning = true;
        while(gameRunning) {
            System.out.println("Which pit would you like to move?");
            String strInput = s.nextLine();
            try {
                int input = Integer.parseInt(strInput);
                boolean valid = validateInput(input);
                if (valid) {
                    gameRunning = play(input);
                    System.out.println(toString());
                }
            } catch(NumberFormatException e) {
                System.out.printf("%s is not a correct number\n", strInput);
            }
        }
    }

    public boolean validateInput(int input) {
        if (input < 1 || input > Player.HOLES_PER_SIDE) {
            System.out.printf("%s is not a hole on the field\n", input);
            return false;
        }
        if (playerOnThePlay.getPits()[input-1].isEmpty()) {
            System.out.printf("Hole %s is empty\n", input);
            return false;
        }
        return true;
    }

    public boolean play(int input) {
        boolean extraTurn = playerOnThePlay.play(input, waitingPlayer);

        if (player1.noMoreMoves() || player2.noMoreMoves()) {
            player1.sweepBoard();
            player2.sweepBoard();

            int player1Stones = player1.getMancala().getNumberOfStones();
            int player2Stones = player2.getMancala().getNumberOfStones();
            Player winner;
            if (player1Stones == player2Stones) {
                System.out.printf("The game ended in a draw with %s stones for each!\n", player1Stones);
                return false;
            } else if (player1Stones > player2Stones) {
                winner = player1;
            } else {
                winner = player2;
            }
            System.out.printf("%s won with %d stones!\n", winner.getName(), winner.getMancala().getNumberOfStones());
            return false;
        }

        if (extraTurn) {
            System.out.println(playerOnThePlay.getName() + " landed in the Mancala and got an extra turn!");
        } else {
            passTurn();
        }
        return true;
    }

    public void passTurn() {
        if (playerOnThePlay == player1) {
            playerOnThePlay = player2;
            waitingPlayer = player1;
        } else {
            playerOnThePlay = player1;
            waitingPlayer = player2;
        }
        System.out.println(playerOnThePlay.getName() + " has the turn.");
    }

    public Player getPlayerOnThePlay() {
        return playerOnThePlay;
    }

    public Player getWaitingPlayer() {
        return waitingPlayer;
    }

    @Override
    public String toString() {
        String player2MancalaText = player2.getMancala().toString();
        StringBuilder sb = new StringBuilder(player2MancalaText);
        Pit[] player2Holes = player2.getPits();
        for(int i = player2Holes.length - 1; i >= 0; i--) {
            sb.append(player2Holes[i]);
        }
        sb.append("\n  ");
        sb.append(player2MancalaText.replaceAll(".*", " "));
        for(Pit hole : player1.getPits()) {
            sb.append(hole);
        }
        sb.append(player1.getMancala());
        return sb.toString();
    }
}
