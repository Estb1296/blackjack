package com.pluralsight;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    static Scanner input = new Scanner(System.in);

    public void blackjackGameDisplay() {
        boolean isPlaying = true;
        while (isPlaying) {
            int numberOfPlayers = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.println("Enter the number of players (1-7): ");
                    numberOfPlayers = input.nextInt();
                    input.nextLine();
                    if (numberOfPlayers < 1 || numberOfPlayers > 7) {
                        throw new IllegalArgumentException("Players must be between 1 and 7(including the dealer)");
                    }
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    input.nextLine();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            Game game = new Game();
            ArrayList<Hand> allHands = game.allHands;
            Deck deck = game.deck;
            Player dealer = game.dealer;
            ArrayList<Player> players = game.getPlayer();

            game.getPromptPlayerName(numberOfPlayers);
            game.deal(players, deck, dealer);


            // Ask each player to hit or stay
            game.hit(players, deck, dealer);

            // Clear console before showing winner
            game.clearConsole();

            // Now get point values and display results
            game.getPointValue(players, allHands, dealer);
            int closest = allHands.get(0).getValue();
            closest = game.getWinner(allHands, closest);

            game.decideWinner(players, closest, dealer);
            game.displayHandWorth(players, dealer);
            boolean validAnswer = true;
            while (validAnswer) {
                System.out.println("Do you wanna keep playing");
                String answer = input.nextLine();
                if (answer.equalsIgnoreCase("Yes")) {
                    deck = new Deck();
                    deck.shuffle();
                    validAnswer = false;
                } else if (answer.equalsIgnoreCase("No")) {
                    isPlaying = false;
                    System.out.println("Thank you for playing!");
                    validAnswer = false;
                } else {
                    System.out.println("Invalid input");
                }
            }

        }
    }
}
