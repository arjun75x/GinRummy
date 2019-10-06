package com.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class StrategyTest {
    @Test
    public void findSetMeldsTest() {
        ArrayList<Card> deck = new ArrayList<>(Card.getAllCards());
        AppendStrategy p1 = new AppendStrategy();
        ArrayList<Card> hand = new ArrayList<>();
        Collections.sort(deck);
        for(int i = 0; i < 10; i++) {
            hand.add(deck.get(i));
        }
        p1.receiveInitialHand(hand);
        ArrayList<Card> test = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            test.add(deck.get(i));
        }
        Meld meld1 = Meld.buildSetMeld(test);
        assertArrayEquals(meld1.getCards(), p1.getMelds().get(0).getCards());
    }

    @Test
    public void findRunMeldsTest() {
        AppendStrategy p1 = new AppendStrategy();
        ArrayList<Card> deck = new ArrayList<>(Card.getAllCards());
        Collections.sort(deck);
        ArrayList<Card> test = new ArrayList<>();
        test.add(deck.get(1));
        test.add(deck.get(5));
        test.add(deck.get(8));
        p1.receiveInitialHand(test);
        Meld meld1 = Meld.buildRunMeld(test);
        if (meld1 != null) {
            assertArrayEquals(meld1.getCards(), p1.getMelds().get(0).getCards());
        }
    }

    @Test
    public void getMelds() {
        ArrayList<Card> deck = new ArrayList<>(Card.getAllCards());
        AppendStrategy p1 = new AppendStrategy();
        ArrayList<Card> hand = new ArrayList<>();
        Collections.sort(deck);
        for(int i = 0; i < 10; i++) {
            hand.add(deck.get(i));
        }
        p1.receiveInitialHand(hand);
        Card[] test = {new Card(Card.CardSuit.CLUBS, Card.CardRank.ACE),
                new Card(Card.CardSuit.SPADES, Card.CardRank.ACE),
                new Card(Card.CardSuit.HEARTS, Card.CardRank.ACE),
                new Card(Card.CardSuit.DIAMONDS, Card.CardRank.ACE)};
        assertEquals(p1.getMelds().get(0).getCards().length, test.length);
    }

    @Test
    public void receiveInitialHand() {
        ArrayList<Card> deck = new ArrayList<>(Card.getAllCards());
        AppendStrategy p1 = new AppendStrategy();
        ArrayList<Card> hand = new ArrayList<>();
        Collections.sort(deck);
        for(int i = 0; i < 10; i++) {
            hand.add(deck.get(i));
        }
        p1.receiveInitialHand(hand);
        assertEquals(p1.getYourHand(), hand);
        p1.reset();
    }

    @Test
    public void getYourHand() {
        ArrayList<Card> deck = new ArrayList<>(Card.getAllCards());
        KnockStrategy p1 = new KnockStrategy();
        ArrayList<Card> hand = new ArrayList<>();
        Collections.sort(deck);
        for(int i = 0; i < 10; i++) {
            hand.add(deck.get(i));
        }
        p1.receiveInitialHand(hand);
        assertEquals(p1.getYourHand(), hand);
    }
}