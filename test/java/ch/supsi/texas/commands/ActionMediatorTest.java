package ch.supsi.texas.commands;

import ch.supsi.texas.GameModel;
import ch.supsi.texas.controller.ButtonsController;
import ch.supsi.texas.events.ActionEvent;
import ch.supsi.texas.events.ActivePlayerHasChangedEvent;
import ch.supsi.texas.events.HandEndedEvent;
import ch.supsi.texas.player.BasePlayer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.*;

public class ActionMediatorTest {

    @InjectMocks
    private ActionMediator actionMediator;

    @Mock
    private ActionEvent actionEvent;
    @Mock
    private ActivePlayerHasChangedEvent activePlayerHasChangedEvent;
    @Mock
    private HandEndedEvent handEndedEvent;

    @Mock
    private GameModel gameModel;

    @Mock
    private BasePlayer player;

    @Mock
    private Map<BasePlayer, ButtonsController> playerButtons;

    private ArrayList<BasePlayer> playersGamemodel;

    private final int ACTUALBET = 25;
    private final int MATCH_BET = 10;
    private final int PLAYER_MONEY = 1000;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(gameModel.getActualBet()).thenReturn(new Integer(ACTUALBET));
        Mockito.when(gameModel.getActivePlayer()).thenReturn(player);
        Mockito.when(player.matchBet(Mockito.anyInt())).thenReturn(new Integer(MATCH_BET));
        Mockito.when(player.getMoney()).thenReturn(new Integer(PLAYER_MONEY));

        playersGamemodel = new ArrayList<BasePlayer>(){
            {
                add(Mockito.mock(BasePlayer.class));
                add(Mockito.mock(BasePlayer.class));
                add(Mockito.mock(BasePlayer.class));
                add(Mockito.mock(BasePlayer.class));
                add(player);
            }
        };

        Mockito.when(gameModel.getActivePlayers()).thenReturn(new LinkedList<>(playersGamemodel));
        Mockito.when(gameModel.getPlayers()).thenReturn(playersGamemodel);
        /*
         * Singleton events mocking
         */

        try {
            Field instance = null;
            instance = ActionEvent.class.getDeclaredField("event");
            instance.setAccessible(true);
            instance.set(actionEvent, actionEvent);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            assertTrue(false);
        }

        try {
            Field instance = null;
            instance = ActivePlayerHasChangedEvent.class.getDeclaredField("event");
            instance.setAccessible(true);
            instance.set(activePlayerHasChangedEvent, activePlayerHasChangedEvent);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            assertTrue(false);
        }

        try {
            Field instance = null;
            instance = HandEndedEvent.class.getDeclaredField("event");
            instance.setAccessible(true);
            instance.set(handEndedEvent, handEndedEvent);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            assertTrue(false);
        }


        /*
         * Singleton instance mocking
         */

        try {
            Field instance = null;
            instance = ActionMediator.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(actionMediator, actionMediator);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            assertTrue(false);
        }
    }

    /*
     * Call
     */

    @Test
    public void callTest(){
        actionMediator.call(player);

        //Il bet del giocatore diventi uguale al tavolo
        Mockito.verify(player, Mockito.times(1)).matchBet(ACTUALBET);
        Mockito.verify(gameModel, Mockito.times(1)).getActualBet();

        //Aggiunti i soldi al tavolo
        Mockito.verify(gameModel, Mockito.times(1)).addToDish(MATCH_BET);
        Mockito.verify(gameModel, Mockito.times(1)).passToNextPlayer(false);

        //Lanciato l'evento
        Mockito.verify(actionEvent, Mockito.times(1)).setPayLoadAndCall(new AbstractMap.SimpleEntry<>(player, "Call"));
    }

    @Test(expected = NullPointerException.class)
    public void callWithNullException(){
        ActionMediator.getInstance().call(null);
    }

    @Test(expected = NullPointerException.class)
    public void callIsValidNullException(){
        actionMediator.callIsValid(null);
    }

    @Test
    public void callIsValidTest() {
        //Se posso coprire l'actualBet
        Mockito.when(player.getMoney()).thenReturn(new Integer(ACTUALBET + (int)(Math.random()*(ACTUALBET))));
        assertTrue(actionMediator.callIsValid(player));

        //Se non riesco a coprire l'actualBet
        Mockito.when(player.getMoney()).thenReturn(new Integer((int)(Math.random()*(ACTUALBET-1))));
        assertFalse(actionMediator.callIsValid(player));

        //Il giocatore non è quello attivo
        assertFalse(actionMediator.callIsValid(Mockito.mock(BasePlayer.class)));
    }

    /*
     * CHECK
     */

    @Test
    public void checkTest(){
        actionMediator.check(player);

        //Passo al prossimo player
        Mockito.verify(gameModel, Mockito.times(1)).passToNextPlayer(false);

        //Lanciato l'evento
        Mockito.verify(actionEvent, Mockito.times(1)).setPayLoadAndCall(new AbstractMap.SimpleEntry<>(player, "Check"));
    }

    @Test
    public void checkIsValidTest(){

        //Non ho i soldi bilanciati, Il giocatore non è quello attivo
        assertFalse(actionMediator.checkIsValid(player));
        assertFalse(actionMediator.checkIsValid(Mockito.mock(BasePlayer.class)));

        Mockito.when(player.getBetMoney()).thenReturn(new Integer(ACTUALBET));
        assertTrue(actionMediator.checkIsValid(player));

        Mockito.verify(gameModel, Mockito.times(1)).getActivePlayer();
    }

    @Test(expected = NullPointerException.class)
    public void checkNullException(){
        actionMediator.check(null);
    }

    @Test(expected = NullPointerException.class)
    public void checkIsValidNullException(){
        actionMediator.checkIsValid(null);
    }

    /*
     * Raise
     */

    @Test
    public void raiseTest(){
        actionMediator.raise(player, 10);

        //Passo al prossimo player
        Mockito.verify(gameModel, Mockito.times(1)).passToNextPlayer(false);

        //Modificati i soldi
        Mockito.verify(gameModel, Mockito.times(1)).addToDish(10);
        Mockito.verify(gameModel, Mockito.times(1)).setActualBet(10 + ACTUALBET);
        Mockito.verify(player, Mockito.times(1)).matchBet(ACTUALBET + 10);

        //Lanciato l'evento
        Mockito.verify(actionEvent, Mockito.times(1)).setPayLoadAndCall(new AbstractMap.SimpleEntry<>(player, "Raise"));
    }

    @Test
    public void raiseIsValidTest(){

        assertFalse(actionMediator.raiseIsValid(Mockito.mock(BasePlayer.class), 100));
        assertFalse(actionMediator.raiseIsValid(player, PLAYER_MONEY*2));

        //Posso scommettere solo fino a tutti i miei soldi, RAISE + ACTUALBET soldi che escono
        assertTrue(actionMediator.raiseIsValid(player, 50));

    }

    @Test(expected = NullPointerException.class)
    public void raiseNullException(){
        actionMediator.raise(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void raiseIsValidNullException(){
        actionMediator.raiseIsValid(null, null);
    }

    /*
     * Fold
     */

    @Test
    public void foldTest(){
        actionMediator.fold(player);

        //Passo al prossimo player
        Mockito.verify(gameModel, Mockito.times(1)).passToNextPlayer(true);

        //Lanciato l'evento
        Mockito.verify(actionEvent, Mockito.times(1)).setPayLoadAndCall(new AbstractMap.SimpleEntry<>(player, "Fold"));
    }

    @Test
    public void foldIsValidTest(){

        //Non foldo se sono l'unico
        Queue<BasePlayer> players = Mockito.mock(Queue.class);
        Mockito.when(players.size()).thenReturn(1);
        Mockito.when(gameModel.getActivePlayers()).thenReturn(players);
        assertFalse(actionMediator.foldIsValid(Mockito.mock(BasePlayer.class)));
        assertFalse(actionMediator.foldIsValid(player));

        //Se ci sono piu giocatori
        Mockito.when(players.size()).thenReturn(5);
        assertFalse(actionMediator.foldIsValid(Mockito.mock(BasePlayer.class)));
        assertTrue(actionMediator.foldIsValid(player));
    }

    @Test(expected = NullPointerException.class)
    public void foldNullException(){
        actionMediator.fold(null);
    }

    @Test(expected = NullPointerException.class)
    public void foldIsValidNullException(){
            actionMediator.foldIsValid(null);
        }

    /*
     * General
     */

    @Test
    public void constructorTest(){
        ActionMediator mediator = new ActionMediator();

        //Verify that I register to the correct events
        Mockito.verify(activePlayerHasChangedEvent, Mockito.times(1)).addObserver(mediator);
        Mockito.verify(handEndedEvent, Mockito.times(1)).addObserver(mediator);
    }

    @Test
    public void getInstanceTest() throws Exception {
        assertSame(actionMediator, ActionMediator.getInstance());
    }

    @Test
    public void activePlayerHasChangedEventCallback() throws Throwable {
        //Imposto l'evento
        Mockito.when(activePlayerHasChangedEvent.getPayLoad()).thenReturn(player);

        //Preparo la map dei bottoni
        Map<BasePlayer,ButtonsController> map = new HashMap<BasePlayer,ButtonsController>() {
            {
                for(BasePlayer player : playersGamemodel)
                    put(player, Mockito.mock(ButtonsController.class));
            }
        };
        Mockito.when(gameModel.getPlayers()).thenReturn(playersGamemodel);
        for(BasePlayer player : playersGamemodel)
            Mockito.when(playerButtons.get(player)).thenReturn(map.get(player));

        //Impost il giocatore che possa abilitare le varie action
        Mockito.when(player.getBetMoney()).thenReturn(ACTUALBET);
        assertTrue(actionMediator.callIsValid(player));
        assertTrue(actionMediator.foldIsValid(player));
        assertTrue(actionMediator.checkIsValid(player));

        //Lancio il metodo
        actionMediator.update(Mockito.mock(Observable.class), activePlayerHasChangedEvent);

        //Pulsanti disabilitati
        for(BasePlayer player : gameModel.getPlayers())
            Mockito.verify(playerButtons.get(player), Mockito.times(1)).disableAll();

        //Vengano abilitati i pulsanti al giocatore
        Mockito.verify(playerButtons.get(player), Mockito.times(1)).enableCall();
        Mockito.verify(playerButtons.get(player), Mockito.times(1)).enableCheck();
        Mockito.verify(playerButtons.get(player), Mockito.times(1)).enableFold();
        Mockito.verify(playerButtons.get(player), Mockito.times(1)).enableRaiseAmount();
    }

    @Test
    public void HandEndedEventCallbackTest(){
        //Preparo la map dei bottoni
        Map<BasePlayer,ButtonsController> map = new HashMap<BasePlayer,ButtonsController>() {
            {
                for(BasePlayer player : playersGamemodel)
                    put(player, Mockito.mock(ButtonsController.class));
            }
        };
        Mockito.when(gameModel.getPlayers()).thenReturn(playersGamemodel);
        for(BasePlayer player : playersGamemodel)
            Mockito.when(playerButtons.get(player)).thenReturn(map.get(player));

        //Lancio il metodo
        actionMediator.update(Mockito.mock(Observable.class), handEndedEvent);

        for (BasePlayer player : this.gameModel.getPlayers())
            Mockito.verify(map.get(player), Mockito.times(1)).disableAll();
    }

    @Test(expected = NullPointerException.class)
    public void registerPlayerButtonsNull2() throws Exception {
       actionMediator.registerPlayerButtons(null, Mockito.mock(ButtonsController.class));
    }

    @Test(expected = NullPointerException.class)
    public void registerPlayerButtonsNull12() throws Exception {
        actionMediator.registerPlayerButtons(Mockito.mock(BasePlayer.class), null);
    }

    @Test
    public void registerPlayerButtons() throws Exception {
        ButtonsController contrller = Mockito.mock(ButtonsController.class);
        actionMediator.registerPlayerButtons(player, contrller);

        Mockito.verify(playerButtons, Mockito.times(1)).put(player, contrller);
    }

    @Test(expected = NullPointerException.class)
    public void setGameModelNull() throws Exception {
        actionMediator.setGameModel(null);
    }

    @Test
    public void setGameModel() throws Exception {
        GameModel gameModel = Mockito.mock(GameModel.class);
        actionMediator.setGameModel(gameModel);
    }

    @Test(expected = NullPointerException.class)
    public void activePlayerHasChangedCallbackNullExceptionPlayer() throws Throwable {
        Mockito.when(activePlayerHasChangedEvent.getPayLoad()).thenReturn(null);

        Method activePlayerHasChangedCallback = ActionMediator.class.getDeclaredMethod("activePlayerHasChangedCallback");
        activePlayerHasChangedCallback.setAccessible(true);
        try {
            activePlayerHasChangedCallback.invoke(actionMediator);
        }catch(InvocationTargetException exc){
            throw exc.getCause();
        }
    }

    @Test(expected = NullPointerException.class)
    public void activePlayerHasChangedCallbackNullExceptionControler() throws Throwable {
        Mockito.when(playerButtons.get(Mockito.any(BasePlayer.class))).thenReturn(null);

        Method activePlayerHasChangedCallback = ActionMediator.class.getDeclaredMethod("activePlayerHasChangedCallback");
        activePlayerHasChangedCallback.setAccessible(true);
        try {
            activePlayerHasChangedCallback.invoke(actionMediator);
        }catch(InvocationTargetException exc){
            throw exc.getCause();
        }
    }
}