package ch.supsi.texas.gamePhases;

import ch.supsi.texas.GameModel;
import ch.supsi.texas.events.ActivePlayerHasChangedEvent;
import ch.supsi.texas.events.UtilTestEvents;
import ch.supsi.texas.player.BasePlayer;
import org.apache.commons.lang3.event.EventUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Observer;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class APhaseTest {
    @Mock
    private GameModel gameModelSpy;

    @Mock
    ActivePlayerHasChangedEvent activePlayerHasChangedEvent;

    @Before
    public void beforeEachTestMethod() {
        MockitoAnnotations.initMocks(this);
        UtilTestEvents.mockEvent(activePlayerHasChangedEvent, ActivePlayerHasChangedEvent.class);
    }

    @Test
    public void setgetGameModelTest() throws Exception {
        APhase phase = new APhase() {
            @Override
            public void initPhase() {}

            @Override
            public boolean isCompleted() {
                return false;
            }

            @Override
            public APhase getNextPhase() {
                return null;
            }
        };

        APhase.setGameModel(gameModelSpy);
        assertSame(gameModelSpy, phase.getGameModel());
    }

    @Test
    public void goToNextPhaseTest() throws Exception {
        APhase.setGameModel(gameModelSpy);
        APhase nextPhase = Mockito.mock(APhase.class);
        Mockito.when(gameModelSpy.getCurrentPhase()).thenReturn(nextPhase);
        APhase phase = new APhase() {
            @Override
            public void initPhase() {}

            @Override
            public boolean isCompleted() {
                return false;
            }

            @Override
            public APhase getNextPhase() {
                return nextPhase;
            }
        };

        phase.goToNextPhase();
        Mockito.verify(gameModelSpy, Mockito.times(1)).setCurrentPhase(nextPhase);
        Mockito.verify(gameModelSpy, Mockito.times(1)).resetOrderQueue();
        Mockito.verify(nextPhase, Mockito.times(1)).initPhase();

        //TODO
        //Mockito.verify(activePlayerHasChangedEvent, Mockito.times(1)).deleteObserver(Mockito.any());
    }

    @Test
    public void getNextPhaseTest() throws Exception {
        APhase phase = new APhase() {
            @Override
            public void initPhase() {}

            @Override
            public boolean isCompleted() {
                return false;
            }

            @Override
            public APhase getNextPhase() {
                return null;
            }
        };

        assertNull(phase.getNextPhase());
    }

}