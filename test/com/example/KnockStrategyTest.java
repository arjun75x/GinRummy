package com.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class KnockStrategyTest {
    @Test
    public void willTakeTopDiscard() {
        ArrayList<Card> deck = new ArrayList<>(Card.getAllCards());
        KnockStrategy p1 = new KnockStrategy();
        ArrayList<Card> hand = new ArrayList<>();
        Collections.sort(deck);
        for(int i = 0; i < 10; i++) {
            hand.add(deck.get(i));
        }
        p1.receiveInitialHand(hand);
        assertEquals(p1.willTakeTopDiscard(new Card(Card.CardSuit.HEARTS, Card.CardRank.THREE)), false);
    }

    @Test
    public void drawAndDiscard() {
        KnockStrategy p1 = new KnockStrategy();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(Card.CardSuit.HEARTS, Card.CardRank.ACE));
        hand.add(new Card(Card.CardSuit.HEARTS, Card.CardRank.TWO));
        hand.add(new Card(Card.CardSuit.HEARTS, Card.CardRank.THREE));
        p1.receiveInitialHand(hand);
        assertEquals(p1.drawAndDiscard(new Card(Card.CardSuit.DIAMONDS, Card.CardRank.TWO)),
                new Card(Card.CardSuit.HEARTS, Card.CardRank.ACE));
    }

    @Test
    public void knock() {
        ArrayList<Card> deck = new ArrayList<>(Card.getAllCards());
        KnockStrategy p1 = new KnockStrategy();
        ArrayList<Card> hand = new ArrayList<>();
        Collections.sort(deck);
        for(int i = 0; i < 10; i++) {
            hand.add(deck.get(i));
        }
        p1.receiveInitialHand(hand);
        boolean value = Game.getValue(p1.getYourHand()) < 10;
        assertEquals(p1.knock(), value);
    }
}