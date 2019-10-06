package com.example;

import java.util.*;

/**
 * Strategy base class
 */
public abstract class Strategy implements PlayerStrategy {

    List<Card> yourHand;
    List<Meld> melds = new ArrayList<>();
    List<Card> oppHand = new ArrayList<>();
    List<Meld> oppMelds = new ArrayList<>();

    static final Random random = new Random(70);

    /**
     * Called by the game engine for each player at the beginning of each round to receive and
     * process their initial hand dealt.
     *
     * @param hand The initial hand dealt to the player
     */
    public void receiveInitialHand(List<Card> hand) {
        this.yourHand = hand;
        findSetMelds();
        findRunMelds();
    }

    /**
     * Helper method to find all SetMelds in hand
     */
    private void findSetMelds() {
        int[] rank = new int[14];
        for (Card card : yourHand) {
            int index = card.getRankValue();
            rank[index]++;
        }
        for (int i = 0; i < rank.length; i++) {
            if (rank[i] >= 3) {
                ArrayList<Card> newMeld = new ArrayList<>();

                for(Card card : yourHand) {
                    if (card.getRankValue() == i) {
                        newMeld.add(card);
                    }
                }
                for (Card card : newMeld) {
                    yourHand.remove(card);
                }

                SetMeld setMeld = Meld.buildSetMeld(newMeld);
                melds.add(setMeld);
            }
        }
    }

    /**
     * Helper Method to find all run melds in hand
     */
    private void findRunMelds() {
        List<Card> sortedHand = yourHand;
        Collections.sort(sortedHand);
        ArrayList<Card> hearts = new ArrayList<>();
        ArrayList<Card> diamonds = new ArrayList<>();
        ArrayList<Card> spades = new ArrayList<>();
        ArrayList<Card> clubs = new ArrayList<>();
        for (Card card : yourHand) {
            if (card.getSuit() == Card.CardSuit.HEARTS) {
                hearts.add(card);
            }
            if (card.getSuit() == Card.CardSuit.DIAMONDS) {
                diamonds.add(card);
            }
            if (card.getSuit() == Card.CardSuit.SPADES) {
                spades.add(card);
            }
            if (card.getSuit() == Card.CardSuit.CLUBS) {
                clubs.add(card);
            }
        }
        runMeldHelper(hearts);
        runMeldHelper(spades);
        runMeldHelper(clubs);
        runMeldHelper(diamonds);
    }

    /**
     * Helper to helper method for run Melds
     * @param input takes input and tries to create run melds
     */
    private void runMeldHelper(ArrayList<Card> input) {
        for (int i = 0; i < input.size(); i++) {
            RunMeld newMeld = Meld.buildRunMeld(input);
            if (newMeld != null) {
                melds.add(newMeld);
                return;
            }
            input.remove(i);
        }
    }

    /**
     * Called by the game engine to prompt the player on whether they want to take the top card
     * from the discard pile or from the deck.
     *
     * @param card The card on the top of the discard pile
     * @return whether the user takes the card on the discard pile
     */
    public abstract boolean willTakeTopDiscard(Card card);

    /**
     * Called by the game engine to prompt the player to take their turn given a
     * dealt card (and returning their card they've chosen to discard).
     *
     * @param drawnCard The card the player was dealt
     * @return The card the player has chosen to discard
     */
    public abstract Card drawAndDiscard(Card drawnCard);

    /**
     * Called by the game engine to prompt the player is whether they would like to
     * knock.
     *
     * @return True if the player has decided to knock
     */
    public abstract boolean knock();

    /**
     * Called by the game engine when the opponent has finished their turn to provide the player
     * information on what the opponent just did in their turn.
     *
     * @param drewDiscard Whether the opponent took from the discard
     * @param previousDiscardTop What the opponent could have drawn from the discard if they chose to
     * @param opponentDiscarded The card that the opponent discarded
     */
    public abstract void opponentEndTurnFeedback(boolean drewDiscard, Card previousDiscardTop, Card opponentDiscarded);

    /**
     * Called by the game engine when the round has ended to provide this player strategy
     * information about their opponent's hand and selection of Melds at the end of the round.
     *
     * @param opponentHand The opponent's hand at the end of the round
     * @param opponentMelds The opponent's Melds at the end of the round
     */
    public void opponentEndRoundFeedback(List<Card> opponentHand, List<Meld> opponentMelds) {
        oppHand = opponentHand;
        oppMelds = opponentMelds;
    }

    /**
     * Called by the game engine to allow access the player's current list of Melds.
     *
     * @return The player's list of melds.
     */
    public List<Meld> getMelds() {
        return melds;
    }

    /**
     * Called by the game engine to allow this player strategy to reset its internal state before
     * competing it against a new opponent.
     */
    public void reset() {
        receiveInitialHand(new ArrayList<>());
    }

    /**
     * Method to get hand
     * @return hand
     */
    public List<Card> getYourHand() {
        return yourHand;
    }

}

