package com.example;

import java.util.*;

/**
 * Class for all game functionality
 */
public class Game {

    //Game elements
    public static ArrayList<Card> deck;
    public static ArrayList<Card> discardPile = new ArrayList<>();

    private static final int MAX_HAND = 10;

    /**
     * Run main method to run all strategies against one another
     * @param args input
     */
    public static void main(String[] args) {

        System.out.println("Random Strategy vs Knock Strategy: ");
        System.out.println(gameEngine(new RandomStrategy(), new KnockStrategy()));

        System.out.println("\n");

        System.out.println("Random Strategy vs Append Strategy: ");
        System.out.println(gameEngine(new RandomStrategy(), new AppendStrategy()));

        System.out.println("\n");

        System.out.println("Knock Strategy vs Append Strategy: ");
        System.out.println(gameEngine(new KnockStrategy(), new AppendStrategy()));
    }

    /**
     * Method to get value of cards
     * @param cards Input list of cards
     * @return Numerical value based on point values
     */
    public static int getValue(List<Card> cards) {
        int value = 0;
        for (Card card : cards) {
            value += card.getPointValue();
        }
        return value;
    }

    /**
     * Method to distribute cards appropriately for game
     * @param p1 Strategy one
     * @param p2 Strategy two
     */
    public static void distributeCards(Strategy p1, Strategy p2) {
        deck = new ArrayList<>(Card.getAllCards());
        Collections.shuffle(deck);

        ArrayList<Card> hand1 = new ArrayList<>();
        for (int i = 0; i < MAX_HAND; i++) {
            hand1.add(deck.get(i));
        }
        for (int i = 0; i < MAX_HAND; i++) {
            deck.remove(0);
        }

        ArrayList<Card> hand2 = new ArrayList<>();
        for (int i = 0; i < MAX_HAND; i++) {
            hand2.add(deck.get(i));
        }
        for (int i = 0; i < MAX_HAND; i++) {
            deck.remove(0);
        }

        p1.receiveInitialHand(hand1);
        p2.receiveInitialHand(hand2);
        discardPile.add(deck.get(0));
        deck.remove(0);
    }

    /**
     * Game engine for playing Strategies
     * @return String that tells user the number of wins of each Strategy
     */
    private static String gameEngine(Strategy p1, Strategy p2) {
        int strategyTwoWins = 0;
        int strategyThreeWins = 0;
        for (int i = 0; i < 300; i++) {

            int strategyTwoScore = 0;
            int strategyThreeScore = 0;
            int maxScore = 50;
            int count = 0;
            int maxCount = 1000;
            distributeCards(p1, p2);

            Card topDiscard = discardPile.get(discardPile.size() - 1);
            Card topDeck = deck.get(deck.size() - 1);

            if (p1.willTakeTopDiscard(topDiscard)) {
                discardPile.add(p1.drawAndDiscard(topDiscard));
                discardPile.remove(topDiscard);
            } else if (p2.willTakeTopDiscard(topDiscard)) {
                discardPile.add(p2.drawAndDiscard(topDiscard));
                discardPile.remove(topDiscard);
            } else {
                discardPile.add(p1.drawAndDiscard(topDeck));
            }

            //boolean that alternates player
            boolean alternatePlayer = true;

            //Main game
            while (strategyTwoScore <= maxScore && strategyThreeScore <= maxScore) {
                topDiscard = discardPile.get(discardPile.size() - 1);
                topDeck = deck.get(deck.size() - 1);
                boolean gameRound = true;
                while (gameRound) {

                    //if statement alternates players
                    if (alternatePlayer) {
                        if (p2.willTakeTopDiscard(topDiscard)) {
                            discardPile.add(p2.drawAndDiscard(topDiscard));
                            discardPile.remove(topDiscard);
                        } else {
                            discardPile.add(p2.drawAndDiscard(topDeck));
                        }
                        alternatePlayer = false;
                    } else {
                        if (p1.willTakeTopDiscard(topDiscard)) {
                            discardPile.add(p1.drawAndDiscard(topDiscard));
                            discardPile.remove(topDiscard);
                        } else {
                            discardPile.add(p1.drawAndDiscard(topDeck));
                        }
                        alternatePlayer = true;
                    }
                    count++;
                    int deadwood1 = getValue(p1.getYourHand());
                    int deadwood2 = getValue(p2.getYourHand());

                    //handles knocks
                    if (p1.knock() && deadwood1 <= 10) {
                        if (deadwood1 > deadwood2) {
                            strategyThreeScore += maxScore/2 + (deadwood1 - deadwood2);
                        } else {
                            strategyTwoScore += deadwood2 - deadwood1;
                        }
                        gameRound = false;
                    } else if (p2.knock() && deadwood2 <= 10) {
                        if (deadwood2 > deadwood1) {
                            strategyTwoScore += maxScore/2 + (deadwood2 - deadwood1);
                        } else {
                            strategyThreeScore += deadwood1 - deadwood2;
                        }
                        gameRound = false;
                    //handles gins and deck ending
                    } else if (deck.size() == 0 || deadwood1 == 0 || deadwood2 == 0
                            || p1.getYourHand().size() == 0 || p2.getYourHand().size() == 0) {
                        gameRound = false;
                    }
                    if (count > maxCount) {
                        gameRound = false;
                        strategyTwoScore += maxScore;
                    }
                    if (p1.getYourHand().size() == 0) {
                        strategyTwoScore += maxScore;
                    }
                    if (p2.getYourHand().size() == 0) {
                        strategyThreeScore += maxScore;
                    }
                }
                distributeCards(p1, p2);
            }
            if (strategyTwoScore > strategyThreeScore) {
                strategyTwoWins++;
            } else {
                strategyThreeWins++;
            }
        }

        p1.reset();
        p2.reset();

        return ("Strategy 1 wins: " + strategyTwoWins + "\n" + "Strategy 2 wins: " + strategyThreeWins);
    }
}
