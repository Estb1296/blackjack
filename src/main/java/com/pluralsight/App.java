package com.pluralsight;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    static Scanner input = new Scanner(System.in);
    static final String GREEN = "\u001B[32m";
    static final String RESET = "\u001B[0m";
    static final String RED = "\u001B[31m";
    static final String BLUE = "\u001B[34m";
    static final String CYAN = "\u001B[36m";
    static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
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
            ArrayList<Player> players = new ArrayList<>();
            for (int i = 0; i < numberOfPlayers; i++) {
                System.out.println("Enter name for Player " + (i + 1) + ": ");

                String playerName = input.nextLine();
                players.add(new Player(playerName));
            }
            Player dealer = new Player("Dealer");
//        Hand hand1 = new Hand();
//        Hand hand2=new Hand();
//        // deal 5 cards
//        Hand player = new Hand();
//        Hand dealer = new Hand();
            Deck deck = new Deck();
            deck.shuffle();

            // assigning the player to their hand(dealing)
            deal(players, deck, dealer);
            //asking if the player's want to hit turn by turn
            //Made the dealer to auto hit until 17 is reached at least
            hit(players, deck, dealer);

            ArrayList<Integer> allHands = new ArrayList<>();
            //getting the point value of each hand
            getPointValue(players, allHands, dealer);

            int closest = allHands.get(0);
            closest = getWinner(allHands, closest);
            displayHandWorth(players, dealer);
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

//        int handValue = hand1.getValue();
//        int handSecondValue = hand2.getValue();
//        int handThirdValue = player.getValue();
//        int dealerHandValue = dealer.getValue();
//
//        allHands.add(handValue);
//        allHands.add(handSecondValue);
//        allHands.add(handThirdValue);
//        allHands.add(dealerHandValue);
//        int closest = allHands.get(0);
//        for(Player player : players) {
//            allHands.add(player.getHandValue()); //
//        }
//
//            // check if this value is closer to 21 than current closest
//            // and not over 21 (bust)
//            if(value <= 21 && Math.abs(21 - value) < Math.abs(21 - closest)) {
//                closest = value;
//            }

//        System.out.println("Closest to 21: " + closest);
//        System.out.println("This hand is worth: " + handValue);
//        System.out.println("This hand is worth: " + handSecondValue);
//        System.out.println("Player hand is worth: " + handThirdValue);
//        System.out.println("Dealer hand is worth: " + dealerHandValue);
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
//        }
//        for(int i = 0; i < 2; i++) {
//            // get a card from the deck
//            hand1.deal(deck.deal());
//            hand2.deal(deck.deal());
//            player.deal(deck.deal());
//            dealer.deal(deck.deal());
//        }
//        handValue = hand1.getValue();
//        handSecondValue = hand2.getValue();
//        handThirdValue = player.getValue();
//        dealerHandValue = dealer.getValue();
//        System.out.println("This hand is worth: " + handValue);
//        System.out.println("This hand is worth: " + handSecondValue);
//        System.out.println("Player hand is worth: " + handThirdValue);
//        System.out.println("Dealer hand is worth: " + dealerHandValue);
//        if(player.getValue() == 21) {
//            System.out.println("Blackjack! Player wins!");
//        }
        }
    }

    private static void hit(ArrayList<Player> players, Deck deck, Player dealer) {
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

// dealer hits until 17
        System.out.println(YELLOW + "Dealer's turn..." + RESET);
        while (dealer.getHandValue() < 17) {
            System.out.println(YELLOW + "Dealer hits..." + RESET);
            dealer.getHand().hit(deck.deal());
        }
        System.out.println(YELLOW + "Dealer stays at: " +
                dealer.getHandValue() + RESET);
    }

    private static void displayHandWorth(ArrayList<Player> players, Player dealer) {
        for (Player player : players) {
            System.out.println(BLUE + player.getName() + " hand is worth: " + player.getHandValue() + RESET);
        }
        System.out.println(CYAN + "Dealer hand is worth: " + dealer.getHandValue() + RESET);
    }

    private static int getWinner(ArrayList<Integer> allHands, int closest) {
        for (int value : allHands) {
            if (value <= 21 && Math.abs(21 - value) < Math.abs(21 - closest)) {
                closest = value;
            }
        }
        return closest;
    }

    private static void getPointValue(ArrayList<Player> players, ArrayList<Integer> allHands, Player dealer) {
        for (Player player : players) {
            allHands.add(player.getHandValue());
        }
        allHands.add(dealer.getHandValue());
    }

    private static void deal(ArrayList<Player> players, Deck deck, Player dealer) {
        for (int i = 0; i < 2; i++) {
            for (Player player : players) {
                player.getHand().deal(deck.deal());
            }
            dealer.getHand().deal(deck.deal());
        }
    }
}

