package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HighCardTest {

    HighCard highCard;

    List<Card> validA;
    List<Card> validB;
    List<Card> sameAbutLess;
    List<Card> table;

    @Before
    public void before(){
        highCard = new HighCard();

        validA = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 10));
                add(Card.create(Card.Seed.TILE, 7));
                add(Card.create(Card.Seed.CLOVER, 6));
                add(Card.create(Card.Seed.PIKE, 5));
                add(Card.create(Card.Seed.PIKE, 3));
            }
        };

        validB = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 12));
                add(Card.create(Card.Seed.PIKE, 11));
                add(Card.create(Card.Seed.CLOVER, 10));
                add(Card.create(Card.Seed.CLOVER, 9));
                add(Card.create(Card.Seed.PIKE, 7));
                add(Card.create(Card.Seed.PIKE, 2));
            }
        };

        sameAbutLess = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 10));
                add(Card.create(Card.Seed.TILE, 7));
                add(Card.create(Card.Seed.CLOVER, 6));
                add(Card.create(Card.Seed.PIKE, 4));
                add(Card.create(Card.Seed.PIKE, 3));
            }
        };

        table = new ArrayList<Card>() {
            {
                add(Card.create(Card.Seed.PIKE, 9));
                add(Card.create(Card.Seed.CLOVER, 8));
            }
        };

    }

    @Test
    public void compareHands() throws Exception {
        assertFalse(highCard.compareHands(validA, validB, table));
        assertTrue(highCard.compareHands(validB, validA, table));

        assertTrue(highCard.compareHands(validA, sameAbutLess, table));
        assertFalse(highCard.compareHands(sameAbutLess, validA, table));
    }

    @Test
    public void hasThis() throws Exception {

        assertTrue(highCard.hasThis(validA, table));
        assertTrue(highCard.hasThis(validB, table));
        assertTrue(highCard.hasThis(sameAbutLess, table));
    }

    @Ignore("todo")
    @Test
    public void getProbability() throws Exception {
    }


    @Test
    public void getScore() throws Exception {
        assertEquals(1, highCard.getScore(), 0.1f);
    }

}