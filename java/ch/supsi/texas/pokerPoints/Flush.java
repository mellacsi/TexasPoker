package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;

import java.util.List;

public class Flush implements IPokerHand {
    @Override
    public boolean compareHands(List<Card> handA, List<Card> handB, List<Card> tableCard) {
        return new HighCard().compareHands(handA, handB, tableCard);
    }

    @Override
    public boolean hasThis(List<Card> hand, List<Card> tableCard) {
        int[] seeds = new int[Card.Seed.values().length+1];

        hand.forEach(card -> seeds[card.getSeed().ordinal()]++);
        tableCard.forEach(card -> seeds[card.getSeed().ordinal()]++);

        for (int seed: seeds)
            if(seed>=5)
                return true;

        return false;
    }

    @Override
    public float getProbability(List<Card> handCard, List<Card> tableCard) {
        return 0;
    }

    @Override
    public float getScore() {
        return 6;
    }
}
