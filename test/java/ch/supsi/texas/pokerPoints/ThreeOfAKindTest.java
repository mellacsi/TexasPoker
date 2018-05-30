package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ThreeOfAKindTest {

    ThreeOfAKind threeOfAKind;

    List<Card> validA;
    List<Card> validB;
    List<Card> sameAbutLess;
    List<Card> invalid;
    List<Card> table;

    @Before
    public void before(){
        threeOfAKind = new ThreeOfAKind();

        validA = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 10));
                add(Card.create(Card.Seed.TILE, 12));
                add(Card.create(Card.Seed.CLOVER, 12));
                add(Card.create(Card.Seed.PIKE, 11));
                add(Card.create(Card.Seed.PIKE, 3));
            }
        };

        validB = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 12));
                add(Card.create(Card.Seed.PIKE, 11));
                add(Card.create(Card.Seed.CLOVER, 11));
                add(Card.create(Card.Seed.CLOVER, 11));
                add(Card.create(Card.Seed.PIKE, 7));
            }
        };

        sameAbutLess = new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 9));
                add(Card.create(Card.Seed.TILE, 12));
                add(Card.create(Card.Seed.CLOVER, 12));
                add(Card.create(Card.Seed.PIKE, 7));
                add(Card.create(Card.Seed.PIKE, 3));
            }
        };

        invalid = new ArrayList<Card>() {
            {
                add(Card.create(Card.Seed.PIKE, 10));
                add(Card.create(Card.Seed.PIKE, 9));
                add(Card.create(Card.Seed.HEART, 4));
                add(Card.create(Card.Seed.PIKE, 5));
                add(Card.create(Card.Seed.PIKE, 2));
            }
        };



        table = new ArrayList<Card>() {
            {
                add(Card.create(Card.Seed.PIKE, 9));
                add(Card.create(Card.Seed.CLOVER, 12));
            }
        };

    }

    @Test
    public void compareHands() throws Exception {
        assertTrue(threeOfAKind.compareHands(validA, validB, table));
        assertFalse(threeOfAKind.compareHands(validB, validA, table));

        assertTrue(threeOfAKind.compareHands(validA, sameAbutLess, table));
        assertFalse(threeOfAKind.compareHands(sameAbutLess, validA, table));
    }

    @Test
    public void hasThis() throws Exception {

        assertTrue(threeOfAKind.hasThis(validA, table));
        assertTrue(threeOfAKind.hasThis(validB, table));

        assertFalse(threeOfAKind.hasThis(invalid, table));
    }

    @Ignore("todo")
    @Test
    public void getProbability() throws Exception {
    }


    @Test
    public void getScore() throws Exception {
        assertEquals(4, threeOfAKind.getScore(), 0.1f);
    }

}