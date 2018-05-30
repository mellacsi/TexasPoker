package ch.supsi.texas.gamePhases;

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

import java.util.LinkedList;

import static org.junit.Assert.*;

public class RiverTest {
    @InjectMocks
    River phase;

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

        Mockito.when(gameModel.getActivePlayers()).thenReturn(new LinkedList<BasePlayer>(){
            {
                add(playerA);
                add(playerB);
                add(playerC);
            }
        });

        Mockito.when(gameModel.getBigBlind()).thenReturn(BIGBLIND);
        Mockito.when(gameModel.getSmallBlind()).thenReturn(SMALLBLIND);
    }

    @Test
    public void initPhaseTest() {
        MockitoAnnotations.initMocks(this);
        UtilTestEvents.mockEvent(activePlayerHasChangedEvent, ActivePlayerHasChangedEvent.class);


        phase.setGameModel(gameModel);

        Mockito.when(gameModel.getActivePlayers()).thenReturn(new LinkedList<BasePlayer>(){
            {
                add(playerA);
                add(playerB);
                add(playerC);
            }
        });

        Mockito.when(gameModel.getBigBlind()).thenReturn(BIGBLIND);
        Mockito.when(gameModel.getSmallBlind()).thenReturn(SMALLBLIND);
    }

    @Test
    public void isCompletedTest(){
        Mockito.when(gameModel.activePlayersHasSameBet()).thenReturn(false);
        assertFalse(phase.isCompleted());

        phase.initPhase();

        Mockito.when(gameModel.activePlayersHasSameBet()).thenReturn(true);
        assertFalse(phase.isCompleted());

        for(int i=0; i<3; i++)
            phase.update(null, activePlayerHasChangedEvent);
        assertTrue(phase.isCompleted());
    }

    @Test
    public void getNextPhase(){
        assertTrue(phase.getNextPhase() instanceof Showdown);
    }
}