package ch.supsi.texas.commands;

import ch.supsi.texas.player.BasePlayer;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;

public class RaiseCommandMockTest{
    private Integer payLoad = new Integer(10);

    @InjectMocks
    RaiseCommand raise;

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
        RaiseCommand commandPlayer = new RaiseCommand(ActionMediator.getInstance(), player);
        assertSame(player, commandPlayer.getPlayer());
        assertSame(ActionMediator.getInstance(), commandPlayer.getReceiver());

        RaiseCommand commandNull = new RaiseCommand(null, null);
    }

    @Test
    public void execute_test() {
        raise.execute();

        Mockito.verify(receiver, Mockito.times(1)).raise(player, null);
    }
    @Test
    public void setMoney_test(){
        raise.setMoney(500);

        assertEquals(new Integer(500), raise.money);

    }

}
