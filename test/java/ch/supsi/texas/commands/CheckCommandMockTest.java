package ch.supsi.texas.commands;

import ch.supsi.texas.player.BasePlayer;
import org.junit.Test;

import static junit.framework.TestCase.assertSame;

public class CheckCommandMockTest {

    @Test(expected = NullPointerException.class)
    public void constructorWithNullReceiver() {
        BasePlayer player = new BasePlayer() {};
        CheckCommand commandPlayer = new CheckCommand(ActionMediator.getInstance(), player);
        assertSame(player, commandPlayer.getPlayer());
        assertSame(ActionMediator.getInstance(), commandPlayer.getReceiver());

        CheckCommand commandNull = new CheckCommand(null, null);
    }
}
