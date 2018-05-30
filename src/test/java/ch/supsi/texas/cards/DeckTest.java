package ch.supsi.texas.cards;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;

public class DeckTest {

    @InjectMocks
    Deck deck;

    @Spy
    private Stack<Card> cards = new Stack<>();

    @Before
    public void before(){

        MockitoAnnotations.initMocks(this);

        for(int i=0; i<52; i++){
            cards.add(Mockito.mock(Card.class));
        }
    }

    @Test
    public void constructorTest() {
        Deck deck = new Deck();
        Card.Seed seed = Card.Seed.HEART;

        assertTrue(deck.getCount() == 52);
        for (int i = 0; i < 4; i++) {
            if(i == 1) seed = Card.Seed.TILE;
            if(i == 2) seed = Card.Seed.CLOVER;
            if(i == 3) seed = Card.Seed.PIKE;
            for(int j = 0; j < 13; j++) {
                assertTrue(deck.drawCard().equals(Card.create(seed, 13 - j)));
            }
        }
    }

    @Test
    public void shuffleTest() {
        Deck deck = new Deck();
        deck.shuffle();
        Card.Seed seed = Card.Seed.HEART;
        int counter = 0;
        assertTrue(deck.getCount() == 52);
        for (int i = 0; i < 4; i++) {
            if(i == 1) seed = Card.Seed.TILE;
            if(i == 2) seed = Card.Seed.CLOVER;
            if(i == 3) seed = Card.Seed.PIKE;
            for(int j = 0; j < 13; j++) {
                if(deck.drawCard().equals(Card.create(seed, 13 - j))){
                    counter ++;
                }
            }
        }
        //se dopo lo shuffle più di 10 carte sono ancora al loro posto c'è qualcosa che non va
        assertTrue(counter < 10);
    }
    @Test
    public void drawTest() {
        Deck deck = new Deck();
        HashSet<Card> cardsTaken = new HashSet<>();

        for(int i = 52; i > 0; i--){
            assertEquals(i, deck.getCount());
            Card card = deck.drawCard();
            assertFalse(cardsTaken.contains(card));
            cardsTaken.add(card);
        }

        assertEquals(0, deck.getCount());
        assertNull(deck.drawCard());
    }

    @Test
    public void getCardsTest() {
        Deck deck = new Deck();

        assertTrue(deck.contains(Card.create(Card.Seed.PIKE, 5)));
        assertEquals(52, deck.getCount());
        List<Card> allPikes = deck.getCards(0,13);
        assertEquals(39, deck.getCount());
        List<Card> allClover = deck.getCards(0,13);
        assertEquals(26, deck.getCount());
        List<Card> allTile = deck.getCards(0,13);
        assertEquals(13, deck.getCount());
        List<Card> allHearth = deck.getCards(0,13);
        assertEquals(0, deck.getCount());

        assertFalse(deck.contains(Card.create(Card.Seed.PIKE, 5)));
        assertEquals(13, allPikes.size());
        assertEquals(13, allClover.size());
        assertEquals(13, allTile.size());
        assertEquals(13, allHearth.size());

        for(Card card : allPikes)
            assertEquals(Card.Seed.PIKE, card.getSeed());
        for(Card card : allClover)
            assertEquals(Card.Seed.CLOVER, card.getSeed());
        for(Card card : allTile)
            assertEquals(Card.Seed.TILE, card.getSeed());
        for(Card card : allHearth)
            assertEquals(Card.Seed.HEART, card.getSeed());

    }


    @Test
    public void getCard(){
        Card getCard = deck.getCard(2);

        Mockito.verify(cards, Mockito.times(1)).remove(new Integer(2));
        assertSame(getCard, cards.get(2));
    }

    @Test
    public void getCardEmptyCards(){
        cards.clear();

        assertNull(deck.getCard(2));
    }

    @Test
    public void getCardHighIndex(){
        assertNull(deck.getCard(Integer.MAX_VALUE));
    }
}