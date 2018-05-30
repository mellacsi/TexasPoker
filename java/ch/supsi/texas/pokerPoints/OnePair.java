package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class OnePair implements IPokerHand {
    @Override
    public boolean compareHands(List<Card> handA, List<Card> handB, List<Card> tableCard) {
        List<Card> handAPlusTable = new ArrayList<Card>();
        List<Card> handBPlusTable = new ArrayList<Card>();
        handAPlusTable.addAll(handA);
        handAPlusTable.addAll(tableCard);

        handBPlusTable.addAll(handB);
        handBPlusTable.addAll(tableCard);

        Integer coupleA = getHigherCouple(handA);
        Integer coupleB = getHigherCouple(handB);
        if(coupleA>coupleB)
            return true;
        if(coupleB.equals(coupleA))
            return new HighCard().compareHands(handA, handB, tableCard);
        return false;
    }

    @Override
    public boolean hasThis(List<Card> hand, List<Card> tableCard) {
        return getHigherCouple(new ArrayList<Card>()
        {
            {
                addAll(hand);
                addAll(tableCard);
            }
        })>=0;
    }

    /*
     * return the value of the higher value couple, if there is no couple return -1
     */
    private int getHigherCouple(List<Card> cards){
        int maxValue = -1;
        for(int i=0; i<cards.size()-1; i++)
            for(int b=i+1; b<cards.size(); b++)
                if(cards.get(i).getValue().equals(cards.get(b).getValue()))
                    if(maxValue<cards.get(i).getValue())
                        maxValue= cards.get(i).getValue();

        return maxValue;
    }

    @Override
    public float getProbability(List<Card> handCard, List<Card> tableCard) {
        //TODO PROBABILITY
        return 2_860/NUMBER_OF_DISTINC_HAND;
    }

    @Override
    public float getScore() {
        return 2;
    }
}
