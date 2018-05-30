package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;

import java.util.List;

public interface IPokerHand {

    int NUMBER_OF_DISTINC_HAND = 2_598_960;

    //Compare two hand and give true if the first is the better on this hand.
    boolean compareHands(final List<Card> handA, final List<Card> handB, final List<Card> tableCard);

    boolean hasThis(final List<Card> hand, final List<Card> tableCard);

    //Return the probability of this hand to be possible
    float getProbability(List<Card> handCard, List<Card> tableCard);

    //Get score for this point
    float getScore();
}
