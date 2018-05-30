package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class HighCard implements IPokerHand {

    @Override
    public boolean compareHands(final List<Card> handA, final List<Card> handB, final List<Card> tableCard) {
        ArrayList<Integer> handAOrderedInteger = new ArrayList<Integer>(){
            {
                handA.forEach(card -> this.add(card.getValue()));
            }
        };
        ArrayList<Integer> handBOrderedInteger = new ArrayList<Integer>(){
            {
                handB.forEach(card -> this.add(card.getValue()));
            }
        };

        Collections.sort(handAOrderedInteger, Collections.reverseOrder());
        Collections.sort(handBOrderedInteger, Collections.reverseOrder());

        for(int i = 0; i<((handAOrderedInteger.size()>handBOrderedInteger.size()) ? handBOrderedInteger.size() : handAOrderedInteger.size()); i++){
            if(handAOrderedInteger.get(i)==handBOrderedInteger.get(i))
                continue;
            if(handAOrderedInteger.get(i)>handBOrderedInteger.get(i))
                return true;
            return false;
        }

        //Compare with seed in the rare case that the 2 deck as the same values
        ArrayList<Card> handAOrdered = new ArrayList<>(new HashSet<>(handA));
        ArrayList<Card> handBOrdered = new ArrayList<>(new HashSet<>(handB));

        Collections.sort(handAOrdered, Collections.reverseOrder());
        Collections.sort(handBOrdered, Collections.reverseOrder());

        for(int i = 0; i<((handAOrdered.size()>handBOrdered.size()) ? handBOrdered.size() : handAOrdered.size()); i++){
            int compare = handAOrdered.get(i).compareTo(handBOrdered.get(i));
            if(compare == 0)
                continue;
            return (compare > 0) ? true : false;
        }
        return false;
    }

    @Override
    public boolean hasThis(List<Card> hand, List<Card> tableCard) {
        return hand.size()>0 || tableCard.size()>0;
    }

    @Override
    public float getProbability(List<Card> handCard, List<Card> tableCard) {
        int highValue = Integer.MIN_VALUE;

        for(Card card : handCard)
            if(card.getValue()>highValue)
                highValue = card.getValue();
        
        return highValue/Card.HIGHVALUE;
    }

    @Override
    public float getScore() {
        return 1;
    }
}
