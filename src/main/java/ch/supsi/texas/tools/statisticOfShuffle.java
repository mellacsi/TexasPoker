package ch.supsi.texas.tools;

import ch.supsi.texas.cards.Deck;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class statisticOfShuffle {

    public static void main(String[] args){
        System.out.println("Pesco " + Integer.MAX_VALUE/2000 + " carte");

        Integer[] count = getProbabilityOfCards(Integer.MAX_VALUE/2000);
        Integer[] count2 = getProbabilityOfCards(Integer.MAX_VALUE/2000);

        for(int i=0; i<count.length-1; i++)
            System.out.println((count[i] - count2[i]));


        for(Integer take: count)
            System.out.println(take);

        System.out.println("con una differenza massima " + getMaxMinDifference(count));

    }

    public static Integer[] getProbabilityOfCards(int numberOfTimes){
        ReentrantLock[] locks = new ReentrantLock[13];
        Integer[] taked = new Integer[13];

        for(int i=0; i<13; i++) {
            locks[i] = new ReentrantLock();
            taked[i] = 0;
        }

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for(int i=0; i<numberOfTimes; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    Deck deck = new Deck();

                    deck.shuffle();
                    int firstValue = deck.drawCard().getValue() - 1;
                    locks[firstValue].lock();
                    try {
                        taked[firstValue]++;
                    }finally {
                        locks[firstValue].unlock();
                    }
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return taked;
    }

    public static int getMaxMinDifference(Integer[] count){
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for(Integer number : count) {
            if (number<min)
                min = number;
            if(number>max)
                max = number;
        }
        return max-min;
    }

}
