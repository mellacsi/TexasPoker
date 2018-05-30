package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StraightFlushTest {
    StraightFlush straight;

    List<Card> validA;
    List<Card> validB;
    List<Card> sameAbutLess;
    List<Card> invalid;
    List<Card> invalidB;
    List<Card> table;

    @Before
    public void before(){
        straight = new StraightFlush();

        validA = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 10));
                add(Card.create(Card.Seed.HEART, 9));
                add(Card.create(Card.Seed.HEART, 8));
                add(Card.create(Card.Seed.HEART, 7));
                add(Card.create(Card.Seed.HEART, 6));
                add(Card.create(Card.Seed.CLOVER, 5));
            }
        };

        validB = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.CLOVER, 12));
                add(Card.create(Card.Seed.CLOVER, 11));
                add(Card.create(Card.Seed.CLOVER, 10));
                add(Card.create(Card.Seed.CLOVER, 9));
                add(Card.create(Card.Seed.CLOVER, 7));
                add(Card.create(Card.Seed.CLOVER, 2));
            }
        };

        sameAbutLess = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 10));
                add(Card.create(Card.Seed.HEART, 9));
                add(Card.create(Card.Seed.HEART, 8));
                add(Card.create(Card.Seed.HEART, 7));
                add(Card.create(Card.Seed.HEART, 6));
                add(Card.create(Card.Seed.CLOVER, 4));
            }
        };

        invalid = new ArrayList<Card>() {
            {
                add(Card.create(Card.Seed.HEART, 10));
                add(Card.create(Card.Seed.HEART, 9));
                add(Card.create(Card.Seed.CLOVER, 8));
                add(Card.create(Card.Seed.HEART, 7));
                add(Card.create(Card.Seed.HEART, 6));
                add(Card.create(Card.Seed.CLOVER, 4));
            }
        };

        invalidB = new ArrayList<Card>() {
            {
                add(Card.create(Card.Seed.HEART, 10));
                add(Card.create(Card.Seed.HEART, 9));
                add(Card.create(Card.Seed.HEART, 7));
                add(Card.create(Card.Seed.HEART, 7));
                add(Card.create(Card.Seed.HEART, 6));
                add(Card.create(Card.Seed.CLOVER, 4));
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
        assertFalse(straight.compareHands(validA, validB, table));
        assertTrue(straight.compareHands(validB, validA, table));

        assertTrue(straight.compareHands(validA, sameAbutLess, table));
        assertFalse(straight.compareHands(sameAbutLess, validA, table));
    }

    @Test
    public void hasThis() throws Exception {


        assertTrue(straight.hasThis(validA, table));
        assertTrue(straight.hasThis(validB, table));

        assertFalse(straight.hasThis(invalid, table));
        assertFalse(straight.hasThis(invalidB, table));
    }

    @Ignore("todo")
    @Test
    public void getProbability() throws Exception {
    }


    @Test
    public void getScore() throws Exception {
        assertEquals(9, straight.getScore(), 0.1f);
    }
}