package ch.supsi.texas.gamePhases;

import ch.supsi.texas.GameModel;
import ch.supsi.texas.cards.Card;
import ch.supsi.texas.events.ActivePlayerHasChangedEvent;
import ch.supsi.texas.events.HandValue;
import ch.supsi.texas.events.PlayerWinnerEvent;
import ch.supsi.texas.events.UtilTestEvents;
import ch.supsi.texas.player.BasePlayer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ShowdownTest {
    @InjectMocks
    Showdown phase;

    @Mock
    GameModel gameModel;

    @Mock
    BasePlayer playerA;
    @Mock
    BasePlayer playerB;
    @Mock
    BasePlayer playerC;


    @Mock
    PlayerWinnerEvent playerWinnerEvent;
    @Mock
    HandValue handValue;

    final int BIGBLIND = 100;
    final int SMALLBLIND = 50;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        UtilTestEvents.mockEvent(playerWinnerEvent, PlayerWinnerEvent.class);
        UtilTestEvents.mockEvent(handValue, HandValue.class);

        phase.setGameModel(gameModel);

        Mockito.when(gameModel.getActivePlayers()).thenReturn(new LinkedList<BasePlayer>(){
            {
                add(playerA);
                add(playerB);
                add(playerC);
            }
        });

        Mockito.when(playerA.getCards()).thenReturn(new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 7));
                add(Card.create(Card.Seed.HEART, 8));
                add(Card.create(Card.Seed.HEART, 9));
                add(Card.create(Card.Seed.HEART, 10));
                add(Card.create(Card.Seed.HEART, 11));
            }
        });
        Mockito.when(playerC.getCards()).thenReturn(new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 1));
                add(Card.create(Card.Seed.HEART, 1));
                add(Card.create(Card.Seed.HEART, 1));
                add(Card.create(Card.Seed.HEART, 1));
            }
        });
        Mockito.when(playerB.getCards()).thenReturn(new ArrayList<Card>(){
            {
                add(Card.create(Card.Seed.HEART, 1));
                add(Card.create(Card.Seed.HEART, 1));
                add(Card.create(Card.Seed.HEART, 1));
                add(Card.create(Card.Seed.HEART, 1));
            }
        });

        Mockito.when(gameModel.getBigBlind()).thenReturn(BIGBLIND);
        Mockito.when(gameModel.getSmallBlind()).thenReturn(SMALLBLIND);
    }


    @Test
    public void initPhaseTest() {

        phase.initPhase();


        Mockito.verify(gameModel, Mockito.times(1)).giveBetAt(playerA);
        Mockito.verify(gameModel, Mockito.times(1)).handEnded();
        Mockito.verify(playerWinnerEvent).setPayLoadAndCall(playerA);

        Mockito.verify(handValue, Mockito.times(gameModel.getActivePlayers().size())).setPayLoadAndCall(Mockito.any());
    }

    @Test
    public void isCompletedTest(){
        assertFalse(phase.isCompleted());

        phase.initPhase();

        assertTrue(phase.isCompleted());
    }

    @Test
    public void getNextPhase(){
        assertNull(phase.getNextPhase());
    }

}
