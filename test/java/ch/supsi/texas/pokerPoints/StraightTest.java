package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StraightTest {

    Straight straight;

    List<Card> validA;
    List<Card> validB;
    List<Card> sameAbutLess;
    List<Card> invalid;
    List<Card> table;

    @Before
    public void before(){
        straight = new Straight();

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

        invalid = new ArrayList<Card>() {
            {
                add(Card.create(Card.Seed.PIKE, 10));
                add(Card.create(Card.Seed.PIKE, 9));
                add(Card.create(Card.Seed.HEART, 4));
                add(Card.create(Card.Seed.CLOVER, 6));
                add(Card.create(Card.Seed.PIKE, 5));
                add(Card.create(Card.Seed.PIKE, 2));
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
    }

    @Ignore("todo")
    @Test
    public void getProbability() throws Exception {
    }


    @Test
    public void getScore() throws Exception {
        assertEquals(5, straight.getScore(), 0.1f);
    }

}