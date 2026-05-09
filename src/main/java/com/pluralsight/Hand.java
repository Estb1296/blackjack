package com.pluralsight;

import java.util.ArrayList;

import static com.pluralsight.App.input;

public class Hand {
    private final ArrayList<Card> cards;
    private int cachedValue = -1;
    private boolean valueChanged = true;//make sure the value of ace is stored through instances of the hand object

    public Hand() {
        cards = new ArrayList<>();
    }
    // A Card is dealt to the Hand and the Hand is responsible
    // to store the card

    public void deal(Card card) {
        cards.add(card);
        valueChanged = true;
    }


    public int getValue() {
        if (!valueChanged)
            return cachedValue; // returns immediately if nothing changed doesn't loop through the whole hand to check status of Ace
        int total = 0;
        for (Card card : cards) {
            card.flip();

            if (card.getValue().equals("A") && card.getChosenAceValue() == 11) {
                System.out.println("You have an Ace! Choose value:");
                System.out.println("1 - Count as 1");
                System.out.println("11 - Count as 11");

                boolean validChoice = false;
                while (!validChoice) {
                    try {
                        int aceChoice = Integer.parseInt(input.nextLine().trim());
                        if (aceChoice == 1 || aceChoice == 11) {
                            card.setChosenAceValue(aceChoice); // ✅ stored in card
                            validChoice = true;
                        } else {
                            System.out.println("Please enter 1 or 11");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter 1 or 11");
                    }
                }
            }

            total += card.getPointValue();
            card.flip();
        }
        cachedValue = total;
        valueChanged = false;
        return cachedValue;
    }

    public void hit(Card card) {
        cards.add(card);
        valueChanged = true;
    }

    public void displayFirstCard() {
        Card firstCard = cards.get(0); //  only get first card
        firstCard.flip();              // flip to see it
        System.out.println(firstCard.getValue() + " of " + firstCard.getSuit());
        firstCard.flip();              // flip back
    }

    public void displayFullHand() {
        for (Card card : cards) {
            card.flip();  // face up
            System.out.println(card.getValue() + " of " + card.getSuit());
            card.flip();  // face down
        }
    }
}
