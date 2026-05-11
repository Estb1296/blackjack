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
            // assigning the player to their hand(dealing)

            game.deal(players, deck, dealer);
            // game.displayAllCards(dealer);


            //asking if the player's want to hit turn by turn
            //Made the dealer to auto hit until 17 is reached at least

            game.hit(players, deck, dealer);
            game.clearConsole();

            //getting the point value of each hand
            game.getPointValue(players, allHands, dealer);

            int closest = allHands.get(0).getValue();
            closest = game.getWinner(allHands, closest);
            game.clearConsole();
            game.displayHandWorth(players, dealer);
            game.decideWinner(players, closest, dealer);
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
