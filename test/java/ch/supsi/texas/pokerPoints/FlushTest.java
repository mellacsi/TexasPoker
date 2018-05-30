package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FlushTest {
    Flush flush;

    List<Card> validA;
    List<Card> validB;
    List<Card> sameAbutLess;
    List<Card> invalid;
    List<Card> table;

    @Before
    public void before(){
        flush = new Flush();

        validA = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 10));
                add(Card.create(Card.Seed.HEART, 7));
                add(Card.create(Card.Seed.CLOVER, 6));
                add(Card.create(Card.Seed.HEART, 5));
                add(Card.create(Card.Seed.PIKE, 3));
            }
        };

        validB = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.CLOVER, 12));
                add(Card.create(Card.Seed.PIKE, 11));
                add(Card.create(Card.Seed.CLOVER, 10));
                add(Card.create(Card.Seed.CLOVER, 9));
                add(Card.create(Card.Seed.CLOVER, 7));
                add(Card.create(Card.Seed.CLOVER, 2));
            }
        };

        sameAbutLess = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 9));
                add(Card.create(Card.Seed.HEART, 7));
                add(Card.create(Card.Seed.CLOVER, 6));
                add(Card.create(Card.Seed.PIKE, 5));
                add(Card.create(Card.Seed.HEART, 3));
            }
        };

        invalid = new ArrayList<Card>() {
            {
                add(Card.create(Card.Seed.PIKE, 10));
                add(Card.create(Card.Seed.PIKE, 9));
                add(Card.create(Card.Seed.HEART, 4));
                add(Card.create(Card.Seed.CLOVER, 6));
            }
        };



        table = new ArrayList<Card>() {
            {
                add(Card.create(Card.Seed.HEART, 9));
                add(Card.create(Card.Seed.HEART, 8));
            }
        };

    }

    @Test
    public void compareHands() throws Exception {
        assertFalse(flush.compareHands(validA, validB, table));
        assertTrue(flush.compareHands(validB, validA, table));

        assertTrue(flush.compareHands(validA, sameAbutLess, table));
        assertFalse(flush.compareHands(sameAbutLess, validA, table));
    }

    @Test
    public void hasThis() throws Exception {

        assertTrue(flush.hasThis(validA, table));
        assertTrue(flush.hasThis(validB, table));

        assertFalse(flush.hasThis(invalid, table));
    }

    @Ignore("todo")
    @Test
    public void getProbability() throws Exception {
    }


    @Test
    public void getScore() throws Exception {
        assertEquals(6, flush.getScore(), 0.1f);
    }
}