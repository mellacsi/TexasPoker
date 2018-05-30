package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StraightFlush implements IPokerHand {
    @Override
    public boolean compareHands(List<Card> handA, List<Card> handB, List<Card> tableCard) {
        int valueA = getValueOfStraightFlush(new ArrayList<Card>(){
            {
                addAll(handA);
                addAll(tableCard);
            }
        });
        int valueB = getValueOfStraightFlush(new ArrayList<Card>(){
            {
                addAll(handB);
                addAll(tableCard);
            }
        });

        if(valueA==valueB)
            return new HighCard().compareHands(handA, handB, tableCard);
        if(valueA>valueB)
            return true;
        return false;
    }

    @Override
    public boolean hasThis(List<Card> hand, List<Card> tableCard) {
        List<Card> cards = new ArrayList<Card>(){
            {
                addAll(hand);
                addAll(tableCard);
            }
        };

        if(getValueOfStraightFlush(cards)>0)
            return true;
        return false;
    }

    @Override
    public float getProbability(List<Card> handCard, List<Card> tableCard) {
        //TODO Probability
        return 0;
    }

    private int getValueOfStraightFlush(List<Card> cards){
        Collections.sort(cards, Collections.reverseOrder());

        int straight = 1;
        Card prevCard = null;
        for(Card card : cards){
            if(prevCard!=null) {
                if (prevCard.getValue() == card.getValue() + 1 && card.getSeed() == prevCard.getSeed()) {
                    straight++;
                    prevCard = card;
                    if (straight == 5)
                        return card.getValue();
                } else {
                    if (card.getValue() != prevCard.getValue()) {
                        prevCard = card;
                        straight = 0;
                    }
                }
            }else{
                prevCard = card;
            }
        }

        return -1;
    }

    @Override
    public float getScore() {
        return 9;
    }
}
