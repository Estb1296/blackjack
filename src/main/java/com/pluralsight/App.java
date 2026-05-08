package com.pluralsight;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {


        boolean isPlaying = true;
        while (isPlaying) {
            int numberOfPlayers=0;
            boolean validInput=true;
            try{
            while(validInput){
            System.out.println("Enter the number of players");
             numberOfPlayers = input.nextInt();
            input.nextLine();
            validInput=false;
            }
            }catch(InputMismatchException e){
                System.out.println("Invalid Input!Please try again.");
                input.nextLine();
            }
            ArrayList<Player> players = new ArrayList<>();
            for(int i = 0; i < numberOfPlayers; i++) {
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

            ArrayList<Integer> allHands=new ArrayList<>();
            //getting the point value of each hand
            getPointValue(players, allHands, dealer);

            int closest = allHands.get(0);
            closest = getWinner(allHands, closest);
            for (Player player : players) {
                System.out.println(player.getName() + " hand is worth: " + player.getHandValue());
            }
            System.out.println("Dealer hand is worth: " + dealer.getHandValue());
            for(Player player : players) {
                if(player.getHandValue() == closest) {
                    System.out.println(player.getName() + " wins! 🏆");
                }
            }
            System.out.println("Dealer hand is worth: " + dealer.getHandValue());

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
            System.out.println("Do you wanna keep playing");
            String answer = input.nextLine();
            if (!answer.equalsIgnoreCase("Yes")) {
                deck = new Deck();
                deck.shuffle();
                if (answer.equalsIgnoreCase("No")) {
                    isPlaying = false;
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

    private static int getWinner(ArrayList<Integer> allHands, int closest) {
        for(int value : allHands) {
            if(value <= 21 && Math.abs(21 - value) < Math.abs(21 - closest)) {
                closest = value;
            }
        }
        return closest;
    }

    private static void getPointValue(ArrayList<Player> players, ArrayList<Integer> allHands, Player dealer) {
        for(Player player : players) {
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

