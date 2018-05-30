package ch.supsi.texas;

import ch.supsi.texas.events.*;
import ch.supsi.texas.player.BasePlayer;
import ch.supsi.texas.view.Console;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleTest{


    @Mock
    private ConsoleMediatorTextArea output;

    @Mock
    private DeckShuffledEvent deckShuffledEvent;
    @Mock
    private DeckCreationEvent deckCreationEvent;
    @Mock
    private PlayerChangedEvent playerChangedEvent;
    @Mock
    private PlayerWinnerEvent playerWinnerEvent;
    @Mock
    private NewCardOnTableEvent newCardOnTableEvent;
    @Mock
    private BlindEvent blindEvent;
    @Mock
    private PlayerDeletedEvent playerDeletedEvent;
    @Mock
    private PlayerAddedEvent playerAddedEvent;
    @Mock
    private GamePhaseChangedEvent gamePhaseChangedEvent;
    @Mock
    private ActionEvent actionEvent;
    @Mock
    private ClearConsoleLogEvent clearConsoleLogEvent;

    @InjectMocks
    private Console console;
    @Before
    public void before() {
        console = new Console(7);
        MockitoAnnotations.initMocks(this);

        /*
         * Mocks Events
         */
        UtilTestEvents.mockEvent(deckShuffledEvent, DeckShuffledEvent.class);
        UtilTestEvents.mockEvent(deckCreationEvent, DeckCreationEvent.class);
        UtilTestEvents.mockEvent(playerChangedEvent, PlayerChangedEvent.class);
        UtilTestEvents.mockEvent(playerWinnerEvent, PlayerWinnerEvent.class);
        UtilTestEvents.mockEvent(newCardOnTableEvent, NewCardOnTableEvent.class);
        UtilTestEvents.mockEvent(blindEvent, BlindEvent.class);
        UtilTestEvents.mockEvent(playerDeletedEvent, PlayerDeletedEvent.class);
        UtilTestEvents.mockEvent(playerAddedEvent, PlayerAddedEvent.class);
        UtilTestEvents.mockEvent(gamePhaseChangedEvent, GamePhaseChangedEvent.class);
        UtilTestEvents.mockEvent(actionEvent, ActionEvent.class);
        UtilTestEvents.mockEvent(clearConsoleLogEvent, ClearConsoleLogEvent.class);

        console.registerEvents();
    }


    @Test
    public void registerEventsTest(){
        Mockito.verify(deckShuffledEvent, Mockito.times(1)).addObserver(console);
        Mockito.verify(deckCreationEvent, Mockito.times(1)).addObserver(console);
        Mockito.verify(playerChangedEvent, Mockito.times(1)).addObserver(console);
        Mockito.verify(playerWinnerEvent, Mockito.times(1)).addObserver(console);
        Mockito.verify(newCardOnTableEvent, Mockito.times(1)).addObserver(console);
        Mockito.verify(blindEvent, Mockito.times(1)).addObserver(console);
        Mockito.verify(playerDeletedEvent, Mockito.times(1)).addObserver(console);
        Mockito.verify(playerAddedEvent, Mockito.times(1)).addObserver(console);
        Mockito.verify(gamePhaseChangedEvent, Mockito.times(1)).addObserver(console);
        Mockito.verify(actionEvent, Mockito.times(1)).addObserver(console);
        Mockito.verify(clearConsoleLogEvent, Mockito.times(1)).addObserver(console);
    }

    @Test
    public void eventUpdate() throws IOException {
        Console spy = Mockito.spy(console);
        Mockito.doNothing().when(spy).write(Mockito.any());

        spy.update(null, deckShuffledEvent);
        Mockito.verify(spy, Mockito.times(1)).write(Mockito.notNull());

        spy.update(null, deckCreationEvent);
        Mockito.verify(spy, Mockito.times(2)).write(Mockito.notNull());


        BasePlayer basePlayer = Mockito.mock(BasePlayer.class);
        Mockito.when(playerChangedEvent.getPayLoad()).thenReturn(basePlayer);
        Mockito.when(basePlayer.getName()).thenReturn("");
        spy.update(null, playerChangedEvent);
        Mockito.verify(spy, Mockito.times(3)).write(Mockito.notNull());

        spy.update(null, newCardOnTableEvent);
        Mockito.verify(spy, Mockito.times(4)).write(Mockito.notNull());

        spy.update(null, blindEvent);
        Mockito.verify(spy, Mockito.times(5)).write(Mockito.notNull());

        Mockito.when(playerDeletedEvent.getPayLoad()).thenReturn(basePlayer);
        spy.update(null, playerDeletedEvent);
        Mockito.verify(spy, Mockito.times(6)).write(Mockito.notNull());

        Mockito.when(playerAddedEvent.getPayLoad()).thenReturn(basePlayer);
        spy.update(null, playerAddedEvent);
        Mockito.verify(spy, Mockito.times(7)).write(Mockito.notNull());

        spy.update(null, gamePhaseChangedEvent);
        Mockito.verify(spy, Mockito.times(8)).write(Mockito.notNull());

        Map.Entry<BasePlayer, String> entry = Mockito.mock(Map.Entry.class);
        Mockito.when(entry.getKey()).thenReturn(basePlayer);
        Mockito.when(entry.getValue()).thenReturn("");
        Mockito.when(actionEvent.getPayLoad()).thenReturn(entry);
        spy.update(null, actionEvent);
        Mockito.verify(spy, Mockito.times(9)).write(Mockito.notNull());

        spy.update(null, clearConsoleLogEvent);
        Mockito.verify(output, Mockito.times(1)).clear();
    }
}