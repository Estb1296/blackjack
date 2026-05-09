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

    public int getValue() {
        // ✅ tracks if hand changed
        boolean valueChanged = true;
        // The Hand uses the methods of each card to determine
        // the value of each card - and adds up all values
        // -1 means not calculated yet
        int cachedValue = -1;
        if(!valueChanged) return cachedValue;
        int total = 0;
        for(Card card : cards) {
            card.flip();

            // ✅ only ask if ace value not yet chosen
            if(card.getValue().equals("A") && card.getChosenAceValue() == 11) {
                System.out.println("You have an Ace! Choose value:");
                System.out.println("1 - Count as 1");
                System.out.println("11 - Count as 11");

                boolean validChoice = false;
                while(!validChoice) {
                    try {
                        int aceChoice = Integer.parseInt(input.nextLine().trim());
                        if(aceChoice == 1 || aceChoice == 11) {
                            card.setChosenAceValue(aceChoice);
                            validChoice = true;
                        } else {
                            System.out.println("Please enter 1 or 11");
                        }
                    } catch(NumberFormatException e) {
                        System.out.println("Invalid input! Please enter 1 or 11");
                    }
                }
            }

            total += card.getPointValue(); // ✅ uses stored value, no parameter needed
            card.flip();
        }
        return total;
    }
    public void hit(Card card) {
        cards.add(card);
    }
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
