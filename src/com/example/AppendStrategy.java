package com.example;

import java.util.Collections;

/**
 * Append Strategy class
 * Strategy is built off appending existing melds if possible
 */
public class AppendStrategy extends Strategy {

   /**
     * Called by the game engine to prompt the player on whether they want to take the top card
     * from the discard pile or from the deck.
     *
     * @param card The card on the top of the discard pile
     * @return whether the user takes the card on the discard pile
     */
    public boolean willTakeTopDiscard(Card card) {
        if (yourHand.size() == 0) {
            return false;
        }
        for(Meld meld : melds) {
            if(meld.canAppendCard(card)) {
                return true;
            }
        }
        Collections.sort(yourHand);
        return card.getPointValue() < yourHand.get(yourHand.size() - 1).getPointValue();
    }

    /**
     * Called by the game engine to prompt the player to take their turn given a
     * dealt card (and returning their card they've chosen to discard).
     *
     * @param drawnCard The card the player was dealt
     * @return The card the player has chosen to discard
     */
    public Card drawAndDiscard(Card drawnCard) {
        yourHand.add(drawnCard);
        Card discard = yourHand.get(yourHand.size() - 2);
        yourHand.remove(discard);
        receiveInitialHand(yourHand);
        return discard;
    }

    /**
     * Called by the game engine to prompt the player is whether they would like to
     * knock.
     *
     * @return True if the player has decided to knock
     */
    public boolean knock() {
        return Game.getValue(yourHand) < 10;
    }

    /**
     * Called by the game engine when the opponent has finished their turn to provide the player
     * information on what the opponent just did in their turn.
     *
     * @param drewDiscard Whether the opponent took from the discard
     * @param previousDiscardTop What the opponent could have drawn from the discard if they chose to
     * @param opponentDiscarded The card that the opponent discarded
     */
    public void opponentEndTurnFeedback(boolean drewDiscard, Card previousDiscardTop, Card opponentDiscarded) {

    }
}
