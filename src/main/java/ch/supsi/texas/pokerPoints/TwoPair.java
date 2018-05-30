package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;

import java.util.*;

public class TwoPair implements IPokerHand {
    @Override
    public boolean compareHands(List<Card> handA, List<Card> handB, List<Card> tableCard) {
        List<Integer> pairsA = this.getPairs(new ArrayList<Card>(){{
            this.addAll(handA);
            this.addAll(tableCard);
        }});
        List<Integer> pairsB = this.getPairs(new ArrayList<Card>(){{
            this.addAll(handA);
            this.addAll(tableCard);
        }});

        Collections.sort(pairsA);
        Collections.sort(pairsB);

        if(pairsA.get(0)>pairsB.get(0))
            return true;
        if(pairsA.get(0).equals(pairsB.get(0)))
            return new HighCard().compareHands(handA, handB, tableCard);
        return false;
    }

    @Override
    public boolean hasThis(List<Card> hand, List<Card> tableCard) {
        List<Card> cards = new ArrayList<>();
        cards.addAll(hand);
        cards.addAll(tableCard);

        if(getPairs(cards).size()>1)
            return true;

        return false;
    }

    private List<Integer> getPairs(List<Card> cards){
        Set<Integer> value = new HashSet<>();

        for(int i=0; i<cards.size()-1; i++)
            for(int b=i+1; b<cards.size(); b++)
                if(cards.get(i).getValue().equals(cards.get(b).getValue()))
                    value.add(cards.get(i).getValue());

        return new ArrayList<>(value);
    }

    @Override
    public float getProbability(List<Card> handCard, List<Card> tableCard) {
        //TODO PROBABILITY
        //Probabilita se ho una copia

        //Probabilita se non ho una copia

        return 0;
    }

    @Override
    public float getScore() {
        return 3;
    }
}
