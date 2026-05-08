package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

import static com.pluralsight.App.*;

public class Game {
    private final ArrayList<Player> players = new ArrayList<>();
    public final Deck deck;
    final Player dealer;
    public final ArrayList<Hand> allHands;


    Game(){
        this.allHands = new ArrayList<>();
        this.deck = new Deck();
        this.dealer = new Player("Dealer");
        this.deck.shuffle();
   }
    public ArrayList<Player> getPlayer() {
        return players;
    }
    public void deal(List<Player> players, Deck deck, Player dealer) {
        for (int i = 0; i < 2; i++) {
            for (Player player : players) player.getHand().deal(deck.deal());
            dealer.getHand().deal(deck.deal());
        }
    }
    public void hit(ArrayList<Player> players, Deck deck, Player dealer) {
        for (Player player : players) {
            boolean playerTurn = true;
            while (playerTurn) {
                System.out.println(BLUE + player.getName() +
                        " hand is worth: " + player.getHandValue() + RESET);

                if (player.getHandValue() > 21) {
                    System.out.println(RED + player.getName() + " busts! ❌" + RESET);
                    break;
                }
                if (player.getHandValue() == 21) {
                    System.out.println(GREEN + player.getName() +
                            " has Blackjack! 🏆" + RESET);
                    break;
                }
                boolean validChoice = false;
                while (!validChoice) {
                    System.out.println(player.getName() + " Hit or Stay? (Hit/Stay): ");
                    String choice = input.nextLine();

                    if (choice.equalsIgnoreCase("Hit")) {
                        player.getHand().hit(deck.deal());
                        validChoice = true;
                    } else if (choice.equalsIgnoreCase("Stay")) {
                        playerTurn = false;
                        validChoice = true;
                    } else {
                        System.out.println("Invalid input! Please enter Hit or Stay.");
                    }
                }
            }
        }

        System.out.println(YELLOW + "Dealer's turn..." + RESET);
        while (dealer.getHandValue() < 17) {
            System.out.println(YELLOW + "Dealer hits..." + RESET);
            dealer.getHand().hit(deck.deal());
        }
        System.out.println(YELLOW + "Dealer stays at: " +
                dealer.getHandValue() + RESET);
    }
    public void getPointValue(ArrayList<Player> players, ArrayList<Hand> allHands, Player dealer) {
        for (Player player : players) {
            allHands.add(player.getHand());
        }
        allHands.add(dealer.getHand());
    }
    public void displayHandWorth(ArrayList<Player> players, Player dealer) {
        for (Player player : players) {
            System.out.println(BLUE + player.getName() + " hand is worth: " + player.getHandValue() + RESET);
        }
        System.out.println(CYAN + "Dealer hand is worth: " + dealer.getHandValue() + RESET);
    }
    public void decideWinner(ArrayList<Player> players, int closest, Player dealer) {
        for (Player player : players) {
            if (player.getHandValue() > 21) {
                System.out.println(RED + player.getName() + " busts! ❌" + RESET);
            } else if (player.getHandValue() == closest) {
                System.out.println(GREEN + player.getName() + " wins! 🏆" + RESET);
            }
        }
        if (dealer.getHandValue() > 21) {
            System.out.println(RED + "Dealer busts! ❌" + RESET);
        } else if (dealer.getHandValue() == closest) {
            System.out.println(GREEN + "Dealer wins! 🏆" + RESET);
        }
    }
    public int getWinner( ArrayList<Hand> allHands, int closest) {
        for (Hand value : allHands) {
            if (value.getValue() <= 21 && Math.abs(21 - value.getValue()) < Math.abs(21 - closest)) {
                closest = value.getValue();
            }
        }
        return closest;
    }
    public void getPromptPlayerName(int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter name for Player " + (i + 1) + ": ");

            String playerName = input.nextLine();
            players.add(new Player(playerName));
        }
        Player dealer = new Player("Dealer");
        new promptPlayerName(players, dealer);
    }

    private record promptPlayerName(ArrayList<Player> players, Player dealer) {
    }
}
