package ch.supsi.texas.pokerPoints;

import ch.supsi.texas.cards.Card;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PokerPointsTest {

    private List<IPokerHand> original;

    @Before
    public void init() {
        original = PokerPoints.winnerHands;

        PokerPoints.winnerHands = new ArrayList<IPokerHand>() {
            {
                add(Mockito.mock(StraightFlush.class));
                add(Mockito.mock(FourOfAKind.class));
                add(Mockito.mock(FullHouse.class));
                add(Mockito.mock(Flush.class));
                add(Mockito.mock(Straight.class));
                add(Mockito.mock(ThreeOfAKind.class));
                add(Mockito.mock(TwoPair.class));
                add(Mockito.mock(OnePair.class));
                add(Mockito.mock(HighCard.class));
            }
        };
    }

    @After
    public void after(){
        PokerPoints.winnerHands = original;
    }

    @Test
    public void evaluationTestMockito(){
        for (IPokerHand hand : PokerPoints.winnerHands)
            Mockito.when(hand.hasThis(Mockito.any(), Mockito.any())).thenReturn(false);

        List<Card> cardsPlayerA = new ArrayList<Card>();
        List<Card> tableCards = new ArrayList<Card>();

        assertEquals(PokerPoints.getPointOfHand(cardsPlayerA, tableCards), 0);

        Mockito.when(PokerPoints.winnerHands.get(0).hasThis(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(PokerPoints.winnerHands.get(0).getScore()).thenReturn(98.f);
        assertEquals(PokerPoints.getPointOfHand(cardsPlayerA, tableCards), 98);
        Mockito.when(PokerPoints.winnerHands.get(0).hasThis(Mockito.any(), Mockito.any())).thenReturn(false);
        Mockito.when(PokerPoints.winnerHands.get(3).hasThis(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(PokerPoints.winnerHands.get(3).getScore()).thenReturn(13.f);
        assertEquals(PokerPoints.getPointOfHand(cardsPlayerA, tableCards), 13);

    }

    @Test
    public void listOrder(){
        List<IPokerHand> winnerHands = new ArrayList<IPokerHand>(){
            {
                add(new StraightFlush());
                add(new FourOfAKind());
                add(new FullHouse());
                add(new Flush());
                add(new Straight());
                add(new ThreeOfAKind());
                add(new TwoPair());
                add(new OnePair());
                add(new HighCard());
            }};

        //Rimuovo i mock
        after();

        for (int i = 0; i < PokerPoints.winnerHands.size(); i++)
            assertEquals(PokerPoints.winnerHands.get(i).getClass(), winnerHands.get(i).getClass());

    }

    @Test
    public void compareHandsTest(){

        List<Card> playerA = Mockito.mock(List.class);
        List<Card> playerB = Mockito.mock(List.class);
        List<Card> table = Mockito.mock(List.class);

        Mockito.when(PokerPoints.winnerHands.get(3).hasThis(playerA, table)).thenReturn(true);
        Mockito.when(PokerPoints.winnerHands.get(3).getScore()).thenReturn(13.f);
        assertEquals(PokerPoints.compareHands(playerA, playerB, table), true);
        Mockito.when(PokerPoints.winnerHands.get(3).hasThis(playerA, table)).thenReturn(false);

    }

}