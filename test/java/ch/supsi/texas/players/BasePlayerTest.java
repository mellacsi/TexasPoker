package ch.supsi.texas.players;

import ch.supsi.texas.cards.Card;
import ch.supsi.texas.events.CardGivenToPlayer;
import ch.supsi.texas.events.PlayerBetHasChangedEvent;
import ch.supsi.texas.events.UtilTestEvents;
import ch.supsi.texas.player.BasePlayer;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BasePlayerTest {

    static class SimplePlayer extends BasePlayer{

    }

    @InjectMocks
    SimplePlayer basePlayer;

    @Spy
    List<Card> cards = new ArrayList<>();

    @Mock
    Card randomCard;

    List<Card> randomCards;

    @Mock
    CardGivenToPlayer cardGivenToPlayer;
    @Mock
    PlayerBetHasChangedEvent playerBetHasChangedEvent;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);

        randomCards = new ArrayList<Card>(){
            {
                add(Mockito.mock(Card.class));
                add(Mockito.mock(Card.class));
                add(Mockito.mock(Card.class));
                add(Mockito.mock(Card.class));
            }
        };

        //Events
        UtilTestEvents.mockEvent(cardGivenToPlayer, CardGivenToPlayer.class);
        UtilTestEvents.mockEvent(playerBetHasChangedEvent, PlayerBetHasChangedEvent.class);
    }

    @Test
    public void constructor(){
        assertEquals(basePlayer.getName(), "default name");
        assertTrue(basePlayer.getBetMoney() == 0);
        assertTrue(basePlayer.getCards().size() == 0);
        assertTrue(basePlayer.getMoney() == 0);

        basePlayer.setName("Ciao");
        assertEquals("Ciao", basePlayer.getName());

        assertNull(basePlayer.getCard(0));

    }

    @Test
    public void moneyTest() {
        basePlayer.setMoney(500);
        assertEquals(new Integer(500), basePlayer.getMoney());

        basePlayer.giveMoney(10);
        assertEquals(new Integer(510), basePlayer.getMoney());
    }

    @Test
    public void betTest(){
        basePlayer.setMoney(510);

        basePlayer.matchBet(200);
        assertEquals(new Integer(510 - 200), basePlayer.getMoney());
        assertEquals(new Integer(200), basePlayer.getBetMoney());

        Mockito.verify(playerBetHasChangedEvent, Mockito.times(1)).setPayLoadAndCall(basePlayer);

        basePlayer.setBetMoney(new Integer(10));
        assertEquals(new Integer(10), basePlayer.getBetMoney());

        Mockito.verify(playerBetHasChangedEvent, Mockito.times(2)).setPayLoadAndCall(basePlayer);

        basePlayer.matchBet(9999);
        assertTrue(basePlayer.getMoney() ==0.);
        assertTrue(basePlayer.getBetMoney()== 320);

        Mockito.verify(playerBetHasChangedEvent, Mockito.times(3)).setPayLoadAndCall(basePlayer);

    }

    @Test
    public void cardTest() {
        basePlayer.giveCard(randomCards.get(0));
        basePlayer.giveCard(randomCards.get(1));
        basePlayer.giveCard(randomCards.get(2));

        assertEquals(randomCards.get(0), basePlayer.getCard(0));
        assertEquals(randomCards.get(1), basePlayer.getCard(1));
        assertEquals(randomCards.get(2), basePlayer.getCard(2));

        assertEquals(3, basePlayer.getCards().size());
    }

    @Test
    public void giveCard(){
        basePlayer.giveCard(randomCard);

        Mockito.verify(cards, Mockito.times(1)).add(randomCard);

        Mockito.verify(cardGivenToPlayer, Mockito.times(1)).setPayLoadAndCall(basePlayer);
    }

    @Test
    public void giveCards(){
        basePlayer.giveCards(randomCards);

        for(Card card : randomCards)
            assertTrue(cards.contains(card));
    }
}