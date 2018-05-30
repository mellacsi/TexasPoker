package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;

import java.util.List;

public class FullHouse implements IPokerHand {
    @Override
    public boolean compareHands(List<Card> handA, List<Card> handB, List<Card> tableCard) {
        int trisA = getTrisCardValue(handA, tableCard);
        int trisB = getTrisCardValue(handB, tableCard);

        if(trisA>trisB)
            return true;
        return false;
    }

    @Override
    public boolean hasThis(List<Card> hand, List<Card> tableCard) {
        int[] cardCount = new int[Card.HIGHVALUE+1];

        hand.forEach(card -> cardCount[card.getValue()]++);
        tableCard.forEach(card -> cardCount[card.getValue()]++);

        boolean tris =false;
        boolean couple = false;
        for(int cardNumber : cardCount){
            if(cardNumber>=3)
                if(tris)
                    return true;
                else
                    tris=true;
            else if(cardNumber>=2)
                couple=true;
        }

        return tris && couple;
    }

    private int getTrisCardValue(List<Card> hand, List<Card> tableCard){
        int[] cardCount = new int[Card.HIGHVALUE+1];

        hand.forEach(card -> cardCount[card.getValue()]++);
        tableCard.forEach(card -> cardCount[card.getValue()]++);

        for(int i=Card.HIGHVALUE-1; i>0; i--)
            if(cardCount[i]>=3)
                return i;
        return -1;
    }

    @Override
    public float getProbability(List<Card> handCard, List<Card> tableCard) {
        //TODO PROBABILITY
        return 0;
    }

    @Override
    public float getScore() {
        return 7;
    }
}
