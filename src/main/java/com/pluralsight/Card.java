package com.pluralsight;

public class Card {
    private final String suit;
    private final String value;
    private boolean isFaceUp;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        this.isFaceUp = false;
    }

    public String getSuit() {
        // only return the suit if the card is face up
        if (isFaceUp) {
            return suit;
        } else {
            return "#";
        }
    }
    public String getValue() {
        // only return the value if the card is face up
        if (isFaceUp) {
           getPointValue(); // this is the string value of the card
            // i.e. A, K, Q, J, 10, 9 ...
            return value;
        } else {
            return "#";
        }
    }

    public int getPointValue() {
        if (!isFaceUp) {
            return 0;
        }

        // A = 11
        if (value.equals("A")) {
            return 11;
        }

        if (value.equals("K") || value.equals("Q") || value.equals("J")) {
            return 10;
        }

        // numeric cards equal their face value
        return Integer.parseInt(value);
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void flip() {
        isFaceUp = !isFaceUp;
    }
}
