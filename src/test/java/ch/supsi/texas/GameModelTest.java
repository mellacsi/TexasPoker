package ch.supsi.texas;

import ch.supsi.texas.cards.Card;
import ch.supsi.texas.cards.Deck;
import ch.supsi.texas.events.*;
import ch.supsi.texas.gamePhases.APhase;
import ch.supsi.texas.gamePhases.NotStarted;
import ch.supsi.texas.gamePhases.Turn;
import ch.supsi.texas.player.BasePlayer;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.*;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

public class GameModelTest{

    @InjectMocks
    GameModel gameModel;

    @Mock
    private Deck deck;

    @Mock
    private List<Card> dealtCards;

    @Spy
    private List<BasePlayer> players = new ArrayList<BasePlayer>();

    @Spy
    private Queue<BasePlayer> currentPlayers = new LinkedList<BasePlayer>();

    @Mock
    APhase currentPhase;

    @Spy
    private BasePlayer firstPlayer;

    @Mock
    BasePlayer player1;
    @Mock
    BasePlayer player2;
    @Mock
    BasePlayer player3;
    @Mock
    BasePlayer player4;

    final int NUMBEROFCARDDEALT = 4;

    @Mock
    CardChangedEvent cardChangedEvent;
    @Mock
    PlayerAddedEvent playerAddedEvent;
    @Mock
    PlayerDeletedEvent playerDeletedEvent;
    @Mock
    GamePhaseChangedEvent gamePhaseChangedEvent;
    private int numberOFPLAYER = 8;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);

        players.add(player1);
        players.add(player2);
        players.add(Mockito.mock(BasePlayer.class));
        players.add(Mockito.mock(BasePlayer.class));
        players.add(player3);
        players.add(firstPlayer);
        players.add(Mockito.mock(BasePlayer.class));
        players.add(player4);

        currentPlayers.add(player1);
        currentPlayers.add(player2);
        currentPlayers.add(player3);
        currentPlayers.add(firstPlayer);
        currentPlayers.add(player4);

        for(int i=0; i<NUMBEROFCARDDEALT; i++)
            dealtCards.add(Mockito.mock(Card.class));

        /*
         * Singleton events mocking
         */
        UtilTestEvents.mockEvent(cardChangedEvent, CardChangedEvent.class);
        UtilTestEvents.mockEvent(playerAddedEvent, PlayerAddedEvent.class);
        UtilTestEvents.mockEvent(playerDeletedEvent, PlayerDeletedEvent.class);
        UtilTestEvents.mockEvent(gamePhaseChangedEvent, GamePhaseChangedEvent.class);
    }

    @Test
    public void resetOrderQueue(){
        gameModel.resetOrderQueue();
        //Se la lista non è allineata
        Queue<BasePlayer> orderedActivePlayerList = gameModel.getActivePlayers();
        assertSame(firstPlayer, orderedActivePlayerList.poll());
        assertSame(player4, orderedActivePlayerList.poll());
        assertSame(player1, orderedActivePlayerList.poll());
        assertSame(player2, orderedActivePlayerList.poll());
        assertSame(player3, orderedActivePlayerList.poll());

        //Rimescolo la lista
        currentPlayers.clear();
        currentPlayers.add(player1);
        currentPlayers.add(player2);
        currentPlayers.add(player3);
        currentPlayers.add(player4);

        gameModel.resetOrderQueue();
        //Se il primo giocatore non è piu in lista
        orderedActivePlayerList = gameModel.getActivePlayers();
        assertSame(player4, orderedActivePlayerList.poll());
        assertSame(player1, orderedActivePlayerList.poll());
        assertSame(player2, orderedActivePlayerList.poll());
        assertSame(player3, orderedActivePlayerList.poll());


    }

    @Test
    public void setgetDeckTest(){
        Deck newDeck = Mockito.mock(Deck.class);
        gameModel.setDeck(newDeck);
        assertSame(newDeck, gameModel.getDeck());
    }

    @Test
    public void initializeTest(){

        gameModel = Mockito.spy(gameModel);

        gameModel.initializeGame();

        Mockito.verify(gameModel, Mockito.times(1)).clearDealtCards();
        Mockito.verify(gameModel, Mockito.times(1)).setDeck(Mockito.any(Deck.class));
        Mockito.verify(gameModel, Mockito.times(1)).setPot(0);
        Mockito.verify(gameModel, Mockito.times(1)).setActualBet(0);
        Mockito.verify(gameModel, Mockito.times(1)).setActualBet(0);
        //        Mockito.verify(deck, Mockito.times(1)).shuffle();

        for(BasePlayer player : players) {
            Mockito.verify(player, Mockito.times(1)).clearCards();
            Mockito.verify(player, Mockito.times(1)).setBetMoney(0);
        }
    }

    @Test
    public void dealtCardTest(){
        gameModel.revealCards(5);

        Mockito.verify(deck, Mockito.times(5)).drawCard();
        Mockito.verify(dealtCards, Mockito.times(5+NUMBEROFCARDDEALT)).add(Mockito.any());
    }

    @Test
    public void dealtCardEvent(){
        gameModel.revealCards(1);
        Mockito.verify(cardChangedEvent, Mockito.times(1)).setPayLoadAndCall(dealtCards);
    }

    @Test
    public void dealtClear(){
        gameModel.clearDealtCards();
        Mockito.verify(dealtCards, Mockito.times(1)).clear();
    }

    @Test
    public void getPlayersTest() {
        //Mi assicuro che torni una copia
        assertNotSame(gameModel.getPlayers(), players);

        //Che i giocatori siano li stessi
        for(int i=0; i<numberOFPLAYER; i++)
            assertSame(gameModel.getPlayers().get(i), players.get(i));
    }

    @Test
    public void addPlayerTest(){
        BasePlayer player1 = Mockito.mock(BasePlayer.class);
        gameModel.addPlayer(player1);

        Mockito.verify(players, Mockito.times(1)).add(player1);
        Mockito.verify(playerAddedEvent, Mockito.times(1)).setPayLoadAndCall(player1);
    }

    @Test
    public void addPlayersTest(){
        BasePlayer player1 = Mockito.mock(BasePlayer.class);
        BasePlayer player2 = Mockito.mock(BasePlayer.class);

        List<BasePlayer> newPlayers = new ArrayList<BasePlayer>(){
            {
                add(player1);
                add(player2);
            }
        };

        gameModel.addPlayers(newPlayers);

        Mockito.verify(players, Mockito.times(1)).add(player1);
        Mockito.verify(players, Mockito.times(1)).add(player2);

        //Events
        Mockito.verify(playerAddedEvent, Mockito.times(1)).setPayLoadAndCall(player1);
        Mockito.verify(playerAddedEvent, Mockito.times(1)).setPayLoadAndCall(player2);
    }

    @Test
    public void removePlayerTest(){
        gameModel.removePlayer(player1);

        Mockito.verify(players, Mockito.times(1)).remove(player1);

        //Events
        Mockito.verify(playerDeletedEvent, Mockito.times(1)).setPayLoadAndCall(player1);
    }

    @Test
    public void setgetActualBetTest() {
        gameModel.setActualBet(500);

        assertEquals(new Integer(500), gameModel.getActualBet());
    }

     @Test
     public void getBigBlind(){
        assertTrue(gameModel.getBigBlind()>0);
     }

     @Test
     public void getSmallBlind(){
         assertTrue(gameModel.getSmallBlind()<gameModel.getBigBlind());
     }

     @Test
     public void giveBetAt(){
         gameModel.setPot(500);
         gameModel.giveBetAt(player1);

         Mockito.verify(player1, Mockito.times(1)).giveMoney(500);

         assertEquals(new Integer(0), gameModel.getActualBet());
         assertEquals(new Integer(0), gameModel.getPot());
     }

     @Test
     public void setCurrentPhaseTest(){
         APhase phase = Mockito.mock(APhase.class);
         gameModel.setCurrentPhase(phase);

         assertSame(phase, gameModel.getCurrentPhase());

         //Event
         Mockito.verify(gamePhaseChangedEvent, Mockito.times(1)).setPayLoadAndCall(phase.getClass().getSimpleName());
     }

     @Test
     public void getCurrentPlayers(){
         assertSame(currentPlayers, gameModel.getActivePlayers());
     }

     @Test
     public void getFirstPlayerOfHand(){
         assertSame(firstPlayer, gameModel.getFirstPlayerOfHand());
     }

    @Test
    public void setFirstPlayerOfHand(){
         gameModel.setFirstPlayerOfHand(player1);
        assertSame(player1, gameModel.getFirstPlayerOfHand());
    }

     @Test
     public void getActivePlayerTest(){
         assertSame(player1, gameModel.getActivePlayer());
     }

     @Test
    public void passToNextPlayer(){
         //Player don't left
         gameModel.passToNextPlayer(false);
         assertSame(player2, gameModel.getActivePlayer());
         assertTrue(currentPlayers.contains(player1));

         //Player left
         gameModel.passToNextPlayer(true);
         assertSame(player3, gameModel.getActivePlayer());
         assertFalse(currentPlayers.contains(player2));
    }

    @Test
    public void dishTest(){
        gameModel.addToDish(500);

        assertEquals(new Integer(500), gameModel.getPot());
        gameModel.addToDish(100);

        assertEquals(new Integer(600), gameModel.getPot());

        gameModel.setPot(10);
        assertEquals(new Integer(10), gameModel.getPot());
    }
}
