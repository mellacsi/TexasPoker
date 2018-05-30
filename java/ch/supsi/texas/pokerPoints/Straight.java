package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Straight implements IPokerHand {
    @Override
    public boolean compareHands(List<Card> handA, List<Card> handB, List<Card> tableCard) {
        int valueA = getValueOfStraight(new ArrayList<Card>(){
            {
                addAll(handA);
                addAll(tableCard);
            }
        });
        int valueB = getValueOfStraight(new ArrayList<Card>(){
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

        if(getValueOfStraight(cards)>0)
            return true;
        return false;
    }

    private int getValueOfStraight(List<Card> cards){
        Collections.sort(cards, Collections.reverseOrder());

        int straight = 1;
        Card prevCard = null;
        for(Card card : cards){
            if(prevCard!=null)
                if(prevCard.getValue() == card.getValue()+1) {
                    straight++;
                    if(straight==5)
                        return card.getValue();
                }else{
                    if(!card.getValue().equals(prevCard.getValue()))
                        straight = 1;
                }
            prevCard = card;
        }

        return -1;
    }

    @Override
    public float getProbability(List<Card> handCard, List<Card> tableCard) {
        //TODO PROBABILITY
        return 0;
    }

    @Override
    public float getScore() {
        return 5;
    }
}
