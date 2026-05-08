package com.pluralsight;

public class Card {

    private final String value;
    private boolean isFaceUp;

    public Card(String value) {
        this.value = value;
        this.isFaceUp = false;
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
    public void flip() {
        isFaceUp = !isFaceUp;
    }
}
