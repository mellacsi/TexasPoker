package ch.supsi.texas.tools;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class statisticOfShuffleTest {
    @Test
    public void getProbabilityOfCards() throws Exception {
        Integer[] count = statisticOfShuffle.getProbabilityOfCards(200);
        int numberOfCards = 0;
        for(Integer number : count) {
            assertNotNull(number);
            assertTrue(number>=0);
            numberOfCards+=number;
        }

        assertEquals(200, numberOfCards);

    }

    @Test
    public void getMaxMinDifference() throws Exception {
        Integer[] values = new Integer[10];

        IntStream.range(0, 10).forEach(i -> values[i] = new Integer(i));

        assertEquals(9, statisticOfShuffle.getMaxMinDifference(values));

    }

}