package com.example;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void getValue() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(Card.CardSuit.HEARTS, Card.CardRank.ACE));
        hand.add(new Card(Card.CardSuit.HEARTS, Card.CardRank.TWO));
        hand.add(new Card(Card.CardSuit.HEARTS, Card.CardRank.THREE));
        assertEquals(Game.getValue(hand), 6);
    }

    @Test
    public void distributeCards() {
        Game.distributeCards(new RandomStrategy(), new KnockStrategy());
        assertEquals(Game.deck.size(), 31);
        assertEquals(Game.discardPile.size(), 1);
    }
}