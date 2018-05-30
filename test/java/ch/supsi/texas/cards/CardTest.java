package ch.supsi.texas.cards;

import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {
    @Test
    public void basicMethodsTest() {
        Card nullCard1 = Card.create(null, 5);
        Card nullCard2 = Card.create(Card.Seed.CLOVER, 17);
        Card nullCard3 = Card.create(Card.Seed.CLOVER, -4);

        assertNull(nullCard1);
        assertNull(nullCard2);
        assertNull(nullCard3);

        Card card = Card.create(Card.Seed.HEART, 5);
        assertNotNull(card);

        assertEquals(Card.Seed.HEART, card.getSeed());
        assertEquals((Integer) 5, card.getValue());

        card.setSeed(Card.Seed.CLOVER);
        assertEquals(Card.Seed.CLOVER, card.getSeed());

        card.setValue(9);
        assertEquals((Integer) 9, card.getValue());
    }

    @Test
    public void equalsTest() {
        Card H5 = Card.create(Card.Seed.HEART, 5);
        Card H5_2 = Card.create(Card.Seed.HEART, 5);
        Card H7 = Card.create(Card.Seed.HEART, 7);
        Card C5 = Card.create(Card.Seed.CLOVER, 5);

        assertEquals(H5, H5_2);
        assertNotEquals(H5, C5);
        assertNotEquals(H5, H7);
    }

    @Test
    public void hashCodeTest() {
        Card H5 = Card.create(Card.Seed.HEART, 5);
        Card H5_2 = Card.create(Card.Seed.HEART, 5);
        Card H7 = Card.create(Card.Seed.HEART, 7);
        Card C5 = Card.create(Card.Seed.CLOVER, 5);

        assertEquals(H5.hashCode(), H5_2.hashCode());
        assertNotEquals(H5.hashCode(), C5.hashCode());
        assertNotEquals(H5.hashCode(), H7.hashCode());
    }

    @Test
    public void compareToTest() {
        Card H5 = Card.create(Card.Seed.HEART, 5);
        Card H7 = Card.create(Card.Seed.HEART, 7);
        Card C5 = Card.create(Card.Seed.CLOVER, 5);
        Card C8 = Card.create(Card.Seed.CLOVER, 8);

        Card P7 = Card.create(Card.Seed.PIKE, 7);
        Card P2 = Card.create(Card.Seed.PIKE, 2);

        assertTrue(H5.compareTo(H7) < 0);
        assertTrue(H5.compareTo(H5) == 0);
        assertTrue(C5.compareTo(H5) < 0);
        assertTrue(C8.compareTo(H5) > 0);
        assertTrue(P7.compareTo(P2) > 0);
        assertTrue(P2.compareTo(P7) < 0);
    }

    @Test
    public void stringTest(){
        Card card = Card.create(Card.Seed.HEART, 5);

        assertEquals("5 HEART", card.toString());
    }
}