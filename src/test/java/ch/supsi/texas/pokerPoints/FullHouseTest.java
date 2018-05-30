package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FullHouseTest {

    FullHouse fullHouse;

    List<Card> validA;
    List<Card> validB;

    List<Card> invalid;
    List<Card> table;

    @Before
    public void before(){
        fullHouse = new FullHouse();

        validA = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 10));
                add(Card.create(Card.Seed.TILE, 10));
                add(Card.create(Card.Seed.CLOVER, 10));
                add(Card.create(Card.Seed.PIKE, 5));
                add(Card.create(Card.Seed.PIKE, 5));
            }
        };

        validB = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 9));
                add(Card.create(Card.Seed.PIKE, 9));
                add(Card.create(Card.Seed.CLOVER, 9));
                add(Card.create(Card.Seed.CLOVER, 4));
                add(Card.create(Card.Seed.PIKE, 4));
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
                add(Card.create(Card.Seed.PIKE, 4));
                add(Card.create(Card.Seed.CLOVER, 8));
            }
        };

    }

    @Test
    public void compareHands() throws Exception {
        assertTrue(fullHouse.compareHands(validA, validB, table));
        assertFalse(fullHouse.compareHands(validB, validA, table));
    }

    @Test
    public void hasThis() throws Exception {

        assertTrue(fullHouse.hasThis(validA, table));
        assertTrue(fullHouse.hasThis(validB, table));

        assertFalse(fullHouse.hasThis(invalid, table));
    }

    @Ignore("todo")
    @Test
    public void getProbability() throws Exception {
    }


    @Test
    public void getScore() throws Exception {
        assertEquals(7, fullHouse.getScore(), 0.1f);
    }


}