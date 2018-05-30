package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TwoPairTest {

    TwoPair twoPair;

    List<Card> validA;
    List<Card> validB;
    List<Card> sameAbutLess;
    List<Card> invalid;
    List<Card> table;

    @Before
    public void before(){
        twoPair = new TwoPair();

        validA = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.PIKE, 10));
                add(Card.create(Card.Seed.PIKE, 9));
                add(Card.create(Card.Seed.HEART, 9));
                add(Card.create(Card.Seed.TILE, 7));
                add(Card.create(Card.Seed.CLOVER, 6));
                add(Card.create(Card.Seed.PIKE, 8));
                add(Card.create(Card.Seed.PIKE, 2));
            }
        };

        validB = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 12));
                add(Card.create(Card.Seed.PIKE, 11));
                add(Card.create(Card.Seed.CLOVER, 12));
                add(Card.create(Card.Seed.CLOVER, 9));
                add(Card.create(Card.Seed.PIKE, 7));
                add(Card.create(Card.Seed.PIKE, 2));
            }
        };

        sameAbutLess = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.PIKE, 9));
                add(Card.create(Card.Seed.PIKE, 9));
                add(Card.create(Card.Seed.HEART, 9));
                add(Card.create(Card.Seed.TILE, 7));
                add(Card.create(Card.Seed.CLOVER, 6));
                add(Card.create(Card.Seed.PIKE, 8));
                add(Card.create(Card.Seed.PIKE, 2));
            }
        };

        invalid = new ArrayList<Card>() {
            {
                add(Card.create(Card.Seed.PIKE, 10));
                add(Card.create(Card.Seed.PIKE, 4));
                add(Card.create(Card.Seed.HEART, 7));
                add(Card.create(Card.Seed.CLOVER, 5));
                add(Card.create(Card.Seed.PIKE, 11));
                add(Card.create(Card.Seed.PIKE, 11));
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
        assertFalse(twoPair.compareHands(validA, validB, table));
        assertTrue(twoPair.compareHands(validB, validA, table));

        assertTrue(twoPair.compareHands(validA, sameAbutLess, table));
        assertFalse(twoPair.compareHands(sameAbutLess, validA, table));
    }

    @Test
    public void hasThis() throws Exception {

        assertTrue(twoPair.hasThis(validA, table));
        assertTrue(twoPair.hasThis(validB, table));
        assertTrue(twoPair.hasThis(sameAbutLess, table));

        assertFalse(twoPair.hasThis(invalid, table));
    }

    @Ignore("todo")
    @Test
    public void getProbability() throws Exception {
    }


    @Test
    public void getScore() throws Exception {
        assertEquals(3, twoPair.getScore(), 0.1f);
    }
}