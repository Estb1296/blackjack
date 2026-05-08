package com.pluralsight;

import java.util.ArrayList;

import static com.pluralsight.App.input;

public class Hand {
    private final ArrayList<Card> cards;
    public Hand(){
        cards = new ArrayList<>();
    }
    // A Card is dealt to the Hand and the Hand is responsible
    // to store the card

    public void deal(Card card){
        cards.add(card);
    }
    // The Hand uses the methods of each card to determine
    // the value of each card - and adds up all values
    public int getValue(){
        int value = 0;
        for (Card card : cards) {
            card.flip();
            if(card.getValue().equals("A")) {
                System.out.println("You have an Ace! Choose value:");
                System.out.println("1 - Count as 1");
                System.out.println("11 - Count as 11");

                int aceChoice = 0;
                boolean validChoice = false;
                while(!validChoice) {
                    try {
                        aceChoice = Integer.parseInt(input.nextLine().trim());
                        if(aceChoice == 1 || aceChoice == 11) {
                            validChoice = true;
                        } else {
                            System.out.println("Please enter 1 or 11");
                        }
                    } catch(NumberFormatException e) {
                        System.out.println("Invalid input! Please enter 1 or 11");
                    }
                }
                value += card.getPointValue(aceChoice); // ✅ uses player choice
            } else {
               value += card.getPointValue(11); // ✅ non ace cards ignore aceValue
            }
            card.flip();
        }
        return value;

    }
    public void hit(Card card) {
        cards.add(card);
    }
//    public void displayCards() {
//        for(Card card : cards) {
//            card.flip(); // face up
//            System.out.println(card.getValue() + " of " + card.getSuit());
//            card.flip(); // face down
//        }
//    }
    public void displayFirstCard() {
        Card firstCard = cards.get(0); //  only get first card
        firstCard.flip();              // flip to see it
        System.out.println(firstCard.getValue() + " of " + firstCard.getSuit());
        firstCard.flip();              // flip back
    }
    public void displayFullHand() {
        for(Card card : cards) {
            card.flip();  // face up
            System.out.println(card.getValue() + " of " + card.getSuit());
            card.flip();  // face down
        }
    }
}
