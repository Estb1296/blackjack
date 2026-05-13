package com.pluralsight;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Game {
    static final String GREEN = "\u001B[32m";
    static final String RESET = "\u001B[0m";
    static final String RED = "\u001B[31m";
    static final String BLUE = "\u001B[34m";
    static final String CYAN = "\u001B[36m";
    static final String YELLOW = "\u001B[33m";
    private static final Scanner input = new Scanner(System.in);
    public final Deck deck;
    public final ArrayList<Hand> allHands;
    final Player dealer;
    private final ArrayList<Player> players = new ArrayList<>();

    Game() {
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
        // Display the initial table for 10 seconds
        System.out.println("=== Table ===");
        for (Player p : players) {
            System.out.println(BLUE + p.getName() + "'s visible card:" + RESET);
            p.getHand().displayFirstCard();
        }
        System.out.println(CYAN + "Dealer's visible card:" + RESET);
        dealer.getHand().displayFirstCard();
        System.out.println(CYAN + "Dealer's second card: [hidden] 🂠" + RESET);

        // 10 second pause to view the table
        pauseScreen();

        // Now go through each player individually
        for (Player player : players) {
            boolean playerTurn = true;

            // Clear console and show only this player's private hand worth
            clearConsole();
            //displayPrivateValue(player);

            while (playerTurn) {
                if (deck.isEmpty()) {
                    System.out.println(YELLOW + "Reshuffling deck..." + RESET);
                    deck.reshuffle();
                }
                System.out.println(BLUE + player.getName() +
                        " hand is worth: " + player.getHandValue() + RESET);
                if (player.getHandValue() > 21) {
                    System.out.println(RED + player.getName() + " busts! ❌" + RESET);
                    clearConsole();
                    break;
                }
                if (player.getHandValue() == 21) {
                    System.out.println(GREEN + player.getName() +
                            " has Blackjack! 🏆" + RESET);
                    clearConsole();
                    break;
                }
                boolean validChoice = false;
                while (!validChoice) {
                    System.out.println(player.getName() + " Hit or Stay? (Hit/Stay): ");
                    String choice = input.nextLine().trim();
                    if (choice.equalsIgnoreCase("Hit")) {
                        player.getHand().hit(deck.deal());
                        validChoice = true;
                    } else if (choice.equalsIgnoreCase("Stay")) {
                        playerTurn = false;
                        validChoice = true;
                    } else {
                        System.out.println("Invalid input! Please enter Hit or Stay.");
                    }
                    // Clear console after they stay
                    if (choice.equalsIgnoreCase("Stay")) {
                        clearConsole();
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

    // Add this helper method
    private void pauseScreen() {
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            System.out.println("Display interrupted");
        }
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
            } else if (player.getHandValue() == dealer.getHandValue()) {
                System.out.println(YELLOW + player.getName() + " pushes with the dealer! 🤝" + RESET);

                // Continue playing until someone wins
                boolean stillPushing = true;
                while (stillPushing) {
                    System.out.println(player.getName() + " Hit or Stay? (Hit/Stay): ");
                    String choice = input.nextLine().trim();

                    if (choice.equalsIgnoreCase("Hit")) {
                        player.getHand().hit(deck.deal());
                        System.out.println(BLUE + player.getName() + " hand is worth: " + player.getHandValue() + RESET);

                        if (player.getHandValue() > 21) {
                            System.out.println(RED + player.getName() + " busts! ❌" + RESET);
                            stillPushing = false;
                        }
                    } else if (choice.equalsIgnoreCase("Stay")) {
                        stillPushing = false;
                    } else {
                        System.out.println("Invalid input! Please enter Hit or Stay.");
                    }
                }

                // Dealer hits until 17
                while (dealer.getHandValue() < 17) {
                    dealer.getHand().hit(deck.deal());
                }

                // Determine final winner
                if (player.getHandValue() > 21) {
                    System.out.println(RED + player.getName() + " loses! ❌" + RESET);
                } else if (dealer.getHandValue() > 21) {
                    System.out.println(GREEN + player.getName() + " wins! 🏆" + RESET);
                } else if (player.getHandValue() > dealer.getHandValue()) {
                    System.out.println(GREEN + player.getName() + " wins! 🏆" + RESET);
                } else if (player.getHandValue() < dealer.getHandValue()) {
                    System.out.println(RED + player.getName() + " loses! ❌" + RESET);
                } else {
                    System.out.println(YELLOW + player.getName() + " pushes again! 🤝" + RESET);
                }
            } else if (player.getHandValue() == closest) {
                System.out.println(GREEN + player.getName() + " wins! 🏆" + RESET);
            } else {
                System.out.println(RED + player.getName() + " loses! ❌" + RESET);
            }
        }

        if (dealer.getHandValue() > 21) {
            System.out.println(RED + "Dealer busts! ❌" + RESET);
        } else if (dealer.getHandValue() == closest) {
            System.out.println(GREEN + "Dealer wins! 🏆" + RESET);
        }
    }

    public int getWinner(ArrayList<Hand> allHands, int closest) {
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

    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private record promptPlayerName(ArrayList<Player> players, Player dealer) {
    }
}
