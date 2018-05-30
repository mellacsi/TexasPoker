package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;

import java.util.List;

public class FourOfAKind implements IPokerHand {
    @Override
    public boolean compareHands(List<Card> handA, List<Card> handB, List<Card> tableCard) {
        int valueOfA = getValueOfKind(handA, tableCard);
        int valueOfB = getValueOfKind(handB, tableCard);

        if(valueOfA>valueOfB)
            return true;
        if(valueOfA==valueOfB)
            return new HighCard().compareHands(handA, handB, tableCard);

        return false;
    }

    @Override
    public boolean hasThis(List<Card> hand, List<Card> tableCard) {
        return getValueOfKind(hand, tableCard)>=0;
    }

    private int getValueOfKind(List<Card> hand, List<Card> tableCard){
        int[] cardCount = new int[Card.HIGHVALUE+1];

        hand.forEach(card -> cardCount[card.getValue()]++);
        tableCard.forEach(card -> cardCount[card.getValue()]++);

        for (int cardValue : cardCount)
            if(cardValue>=4)
                return cardValue;

        return -1;
    }

    @Override
    public float getProbability(List<Card> handCard, List<Card> tableCard) {
        return 0;
    }

    @Override
    public float getScore() {
        return 8;
    }
}
