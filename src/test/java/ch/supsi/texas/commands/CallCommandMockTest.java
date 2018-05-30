package ch.supsi.texas.commands;

import ch.supsi.texas.GameMediator;
import ch.supsi.texas.GameModel;
import ch.supsi.texas.player.BasePlayer;
import ch.supsi.texas.player.ConcretePlayer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertSame;

public class CallCommandMockTest {
    @Test(expected = NullPointerException.class)
    public void constructorWithNullReceiver() {
        BasePlayer player = new BasePlayer() {};
        CallCommand commandPlayer = new CallCommand(ActionMediator.getInstance(), player);
        assertSame(player, commandPlayer.getPlayer());
        assertSame(ActionMediator.getInstance(), commandPlayer.getReceiver());

        CallCommand commandNull = new CallCommand(null, null);
    }

}
