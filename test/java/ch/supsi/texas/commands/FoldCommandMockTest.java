package ch.supsi.texas.commands;

import ch.supsi.texas.player.BasePlayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertSame;

@RunWith(MockitoJUnitRunner.class)
public class FoldCommandMockTest {

    @InjectMocks
    FoldCommand fold;

    @Mock
    private ActionMediator receiver;
    @Mock
    protected BasePlayer player;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }


    @Test(expected = NullPointerException.class)
    public void constructorWithNullReceiver() {
        BasePlayer player = new BasePlayer() {};
        FoldCommand commandPlayer = new FoldCommand(ActionMediator.getInstance(), player);
        assertSame(player, commandPlayer.getPlayer());
        assertSame(ActionMediator.getInstance(), commandPlayer.getReceiver());
        FoldCommand commandNull = new FoldCommand(null, null);
    }

    @Test
    public void execute_test(){
        fold = new FoldCommand(receiver, player);
        fold.execute();
        Mockito.verify(receiver, Mockito.times(1)).fold(player);
    }
}
