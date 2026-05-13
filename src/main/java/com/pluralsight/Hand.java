package com.pluralsight;

import java.util.ArrayList;



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
            return cachedValue;

        int total = 0;
        int aceCount = 0;

        // First pass: count aces and add all card values (treating aces as 11)
        for (Card card : cards) {
            card.flip();
            if (card.getValue().equals("A")) {
                aceCount++;
                total += 11;
            } else {
                total += card.getPointValue();
            }
            card.flip();
        }

        // Adjust aces from 11 to 1 if busting (total > 21)
        while (total > 21 && aceCount > 0) {
            total -= 10;  // Convert one ace from 11 to 1 (difference of 10)
            aceCount--;
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

    }

