package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;

import java.util.List;

public class ThreeOfAKind implements IPokerHand {

    @Override
    public boolean compareHands(List<Card> handA, List<Card> handB, List<Card> tableCard) {
        int[] cardsACount = new int[Card.HIGHVALUE+1];
        int[] cardsBCount = new int[Card.HIGHVALUE+1];

        handA.forEach(card -> cardsACount[card.getValue()]++);
        tableCard.forEach(card -> cardsACount[card.getValue()]++);

        handB.forEach(card -> cardsBCount[card.getValue()]++);
        tableCard.forEach(card -> cardsBCount[card.getValue()]++);

        for (int i = cardsACount.length-1; i > 0; i--) 
            if(cardsACount[i]>2 && cardsBCount[i]>2)
                return new HighCard().compareHands(handA, handB, tableCard);
            else if(cardsACount[i]>2)
                return true;
            else if(cardsBCount[i]>2)
                return false;


        return false;
    }

    @Override
    public boolean hasThis(List<Card> hand, List<Card> tableCard) {
        int[] cardCount = new int[Card.HIGHVALUE+1];

        hand.forEach(card -> cardCount[card.getValue()]++);
        tableCard.forEach(card -> cardCount[card.getValue()]++);

        for (int cardValue : cardCount)
            if(cardValue>=3)
                return true;

        return false;
    }

    @Override
    public float getProbability(List<Card> handCard, List<Card> tableCard) {
        //TODO PROBABILITY
        return 0;
    }

    @Override
    public float getScore() {
        return 4;
    }
}
