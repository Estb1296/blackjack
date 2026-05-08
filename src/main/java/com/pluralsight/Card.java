package com.pluralsight;

public class Card {

    private final String value;
    private final String suit;
    private boolean isFaceUp;

    public Card(String suit, String value) {
        this.value = value;
        this.isFaceUp = false;
        this.suit=suit;
    }
    public int getPointValue(int aceValue) {
        if (!isFaceUp) {
            return 0;
        }

        // A = 11 or 1 depending on what the user picked.
        if (value.equals("A")) {
            return aceValue;
        }
        if (value.equals("K") || value.equals("Q") || value.equals("J")) {
            return 10;
        }

        // numeric cards equal their face value
        return Integer.parseInt(value);
    }
    public void flip() {
        isFaceUp = !isFaceUp;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }
}
