package com.pluralsight;

public class Player {
    private final Hand hand;
    private final String name;
    Player(String name) {
        this.name = name;
        this.hand = new Hand();
    }
    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }
    public int getHandValue() {
        return hand.getValue();
    }
}
