package ch.supsi.texas.gamePhases;

import ch.supsi.texas.GameMediator;
import ch.supsi.texas.GameModel;
import ch.supsi.texas.events.ActivePlayerHasChangedEvent;
import ch.supsi.texas.events.UtilTestEvents;
import ch.supsi.texas.player.BasePlayer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class NotStartedTest {


    @InjectMocks
    NotStarted phase;

    @Mock
    GameModel gameModel;

    @Mock
    BasePlayer playerA;
    @Mock
    BasePlayer playerB;
    @Mock
    BasePlayer playerC;


    @Mock
    ActivePlayerHasChangedEvent activePlayerHasChangedEvent;

    final int BIGBLIND = 100;
    final int SMALLBLIND = 50;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        UtilTestEvents.mockEvent(activePlayerHasChangedEvent, ActivePlayerHasChangedEvent.class);

        phase.setGameModel(gameModel);

        Mockito.when(gameModel.getPlayers()).thenReturn(new ArrayList<BasePlayer>(){
            {
                add(playerA);
                add(playerB);
                add(playerC);
            }
        });

        Mockito.when(gameModel.getActivePlayers()).thenReturn(new LinkedList<BasePlayer>(){
            {
                add(playerA);
                add(playerB);
                add(playerC);
            }
        });

    }

    @Test
    public void initPhaseTest() {
        assertFalse(phase.isCompleted());
        phase = Mockito.spy(phase);
        Mockito.doNothing().when(phase).goToNextPhase();

        phase.initPhase();

        Mockito.verify(gameModel, Mockito.times(1)).setActivePlayers(new ArrayList<BasePlayer>(){
            {
                add(playerA);
                add(playerB);
                add(playerC);
            }
        });
        Mockito.verify(phase, Mockito.times(1)).goToNextPhase();

        assertTrue(phase.isCompleted());
    }

    @Test
    public void getNextPhase(){
        assertTrue(phase.getNextPhase() instanceof CompulsoryBet);
    }

}