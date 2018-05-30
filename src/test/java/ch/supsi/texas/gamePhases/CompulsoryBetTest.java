package ch.supsi.texas.gamePhases;

import ch.supsi.texas.GameModel;
import ch.supsi.texas.events.ActivePlayerHasChangedEvent;
import ch.supsi.texas.events.UtilTestEvents;
import ch.supsi.texas.player.BasePlayer;
import org.apache.commons.lang3.event.EventUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.LinkedList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CompulsoryBetTest {

    @InjectMocks
    CompulsoryBet phase;

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
        //Mockito.when(gameModel.getCurrentPhase()).thenReturn(phase);
    }

    @Test
    public void initPhaseTest() {

        phase = Mockito.spy(phase);
        Mockito.doNothing().when(phase).goToNextPhase();

        assertFalse(phase.isCompleted());
        phase.initPhase();

        Mockito.verify(playerA, Mockito.times(1)).matchBet(BIGBLIND);
        Mockito.verify(playerB, Mockito.times(1)).matchBet(SMALLBLIND);

        Mockito.verify(gameModel, Mockito.times(1)).setActualBet(BIGBLIND);
        Mockito.verify(gameModel, Mockito.times(1)).addToDish(BIGBLIND+SMALLBLIND);

        assertTrue(phase.isCompleted());
    }

    @Test
    public void isCompletedTest(){
        assertFalse(phase.isCompleted());

        phase = Mockito.spy(phase);
        Mockito.doNothing().when(phase).goToNextPhase();
        phase.initPhase();

        assertTrue(phase.isCompleted());

    }

    @Test
    public void getNextPhase(){
        assertTrue(phase.getNextPhase() instanceof PreFlop);
    }
}