package com.pluralsight;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        for(String suit : suits) {
            for(String value : values) {
                cards.add(new Card(suit, value));
            }
        }
}
    public void shuffle(){
        Collections.shuffle(cards);
    }
    public Card deal(){
        if(!cards.isEmpty()){
            return cards.remove(0);
        } else {
            return null;
        }
    }


    public boolean isEmpty() {
        return cards.isEmpty();
    }
    public void reshuffle() {
        cards.clear();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        for(String suit:suits){
            for(String value : values) {
                cards.add(new Card(suit,value));
            }
        }
        Collections.shuffle(cards); // ✅ shuffle fresh deck
    }

}


