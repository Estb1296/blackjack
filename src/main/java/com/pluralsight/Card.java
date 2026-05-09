package com.pluralsight;

public class Card {

    private final String value;
    private final String suit;
    private boolean isFaceUp;
    private int chosenAceValue = 11;

    public Card(String suit, String value) {
        this.value = value;
        this.isFaceUp = false;
        this.suit = suit;
    }

    public int getPointValue() {
        if (!isFaceUp) return 0;
        if (value.equals("A")) return chosenAceValue; // ✅ uses stored value
        if (value.equals("K") || value.equals("Q") || value.equals("J")) return 10;
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

    public int getChosenAceValue() {
        return chosenAceValue;
    }

    public void setChosenAceValue(int chosenAceValue) {
        this.chosenAceValue = chosenAceValue;
    }
}
