package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class PokerPoints {


    static public List<IPokerHand> winnerHands = new ArrayList<IPokerHand>(){
        {
            add(new StraightFlush());
            add(new FourOfAKind());
            add(new FullHouse());
            add(new Flush());
            add(new Straight());
            add(new ThreeOfAKind());
            add(new TwoPair());
            add(new OnePair());
            add(new HighCard());
        }
    };

    static public int getPointOfHand(List<Card> hand, List<Card> table){
        for (IPokerHand winnerHand : winnerHands)
            if(winnerHand.hasThis(hand, table))
                return Math.round(winnerHand.getScore());
        return 0;
    }

    /*
        Return true if the first parameter is better than second
     */
    static public boolean compareHands(List<Card> handA, List<Card> handB, List<Card> table){

        int scoreA = getPointOfHand(handA, table);
        int scoreB = getPointOfHand(handB, table);

        if(scoreA>scoreB)
            return true;
        if(scoreB==scoreA)
            return winnerHands.get(winnerHands.size()-scoreA).compareHands(handA, handB, table);
        return false;
    }
}
