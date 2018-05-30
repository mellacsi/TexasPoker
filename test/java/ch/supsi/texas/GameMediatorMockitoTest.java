package ch.supsi.texas;

import ch.supsi.texas.view.Console;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GameMediatorMockitoTest {
    private GameModel gameModel;
    private GameMediator gameMediatorSpy;

    @Before
    public void beforeEachTestMethod() {
        gameModel = new GameModel();
        gameMediatorSpy = Mockito.spy(new GameMediator(gameModel));
    }
    @Test
    public void startGameTest() {
        gameMediatorSpy.initializeGame(4); // +2 getGameModel
        gameMediatorSpy.reset();
        gameMediatorSpy.startMatch();

        Mockito.verify(gameMediatorSpy, Mockito.times(1)).getActivePlayersFromTable();
        Mockito.verify(gameMediatorSpy, Mockito.times(1)).reset();
        Mockito.verify(gameMediatorSpy, Mockito.times(1)).startMatch();
        Mockito.verify(gameMediatorSpy, Mockito.times(2)).getGameModel();
        Mockito.verify(gameMediatorSpy, Mockito.times(1)).initializeGame(4);
    }
}
