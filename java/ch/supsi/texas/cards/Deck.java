package ch.supsi.texas.cards;

import ch.supsi.texas.events.DeckShuffledEvent;

import java.util.*;

public class Deck {
    private Stack<Card> cards = new Stack<>();

    public Deck() {
        for(Card.Seed seed : Card.Seed.values())
            for(int value = 1; value < 14; value++)
                cards.add(Card.create(seed, value));
    }

    public void shuffle() {
        for(int i=0; i<2; i++)
            Collections.shuffle(cards);
        DeckShuffledEvent.getInstance().callEvent();
    }

    // Gets the top card of the deck
    public Card drawCard(){
        if(this.cards == null || cards.isEmpty())
            return null;

        return cards.pop();
    }

    // Gets a specific card from the deck (for advenced shuffling & test purpose)
    public Card getCard(Integer cardIndex) {
        if(this.cards == null || cards.isEmpty() || (cards.size() < cardIndex))
            return null;

        Card removedCard = cards.get(cardIndex);
        cards.remove(cardIndex);
        return removedCard;
    }

    // Gets a specific section of the deck, last index exclusive
    public List<Card> getCards(Integer firstCardIndex, Integer lastCardIndex) {
        if(this.cards == null || cards.isEmpty()
                || (cards.size() < lastCardIndex)
                || (lastCardIndex < firstCardIndex))
            return null;

        List<Card> removedCards = new ArrayList<>();
        for(Integer cardIndex = firstCardIndex; cardIndex < lastCardIndex; cardIndex++)
            removedCards.add(cards.elementAt(cardIndex));
        for(Card toRemove : removedCards)
            cards.remove(toRemove);

        return removedCards;
    }

    public int getCount(){
        if(this.cards == null || this.cards.isEmpty())
            return 0;

        return this.cards.size();
    }

    public boolean contains(Card card) {
        if (card == null
                || this.cards == null
                || !cards.contains(card))
            return false;

        return true;
    }
}
