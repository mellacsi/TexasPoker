package ch.supsi.texas;

import ch.supsi.texas.cards.Card;
import ch.supsi.texas.cards.Deck;
import ch.supsi.texas.events.*;
import ch.supsi.texas.gamePhases.APhase;
import ch.supsi.texas.gamePhases.NotStarted;
import ch.supsi.texas.gamePhases.Showdown;
import ch.supsi.texas.player.BasePlayer;
import java.util.*;

// GameModel observed by gui
public class GameModel extends Observable {
    private Deck deck = new Deck();
    private List<Card> dealtCards = new Stack<>();
    /* First player has Blind, and this list changes every turn, dropping
        out players with no money left, and rotates who is in first
        position.
    */
    private List<BasePlayer> players = new LinkedList<>();
    private int actualBet;
    private int pot;
    private final int bigBlind = 10;
    private final double smallBlindFactor = 1.0/2.0;

    //Phase manager
    APhase currentPhase = new NotStarted(this);
    //firstPlayerOfHand
    private BasePlayer firstPlayer;
    private Queue<BasePlayer> currentPlayers = new LinkedList<>();

    public GameModel() {}

    public void initializeGame() {
        ClearConsoleLogEvent.sendToSub();
        this.setDeck(new Deck());
        this.clearDealtCards();
        this.setPot(0);
        this.setActualBet(0);

        for(BasePlayer player : players) {
            player.clearCards();
            player.setBetMoney(0);
        }
        this.deck.shuffle();
    }

    public void reset() {
        this.setDeck(new Deck());
        clearDealtCards();

        this.actualBet = 0;
        this.setCurrentPhase(new NotStarted(this));
        players.clear();
        if(currentPlayers!=null)
            currentPlayers.clear();
        setAndNotify(GameResetEvent.getInstance());
    }

    public List<Card> getDealtCards() {
        return dealtCards;
    }

    public void revealCards(int amount) {
        for(int i = 0; i < amount; i++)
            revealCard();
    }

    public void revealCard() {
        Card card = deck.drawCard();
        this.dealtCards.add(card);
        CardChangedEvent.getInstance().setPayLoadAndCall(this.dealtCards);
    }

    public void clearDealtCards() {
        this.dealtCards.clear();
    }

    // returns a copy of players, to make searchs only, all modifications must
    // go trough addPlayer & other methods to have the observers notified
    public List<BasePlayer> getPlayers() {
        return new ArrayList<BasePlayer>(players);
    }

    public void addPlayer(BasePlayer player) {
        players.add(player);

        PlayerAddedEvent.getInstance().setPayLoadAndCall(player);
    }

    public void addPlayers(List<BasePlayer> players) {
        for(BasePlayer player : players)
            addPlayer(player);
    }

    public void removePlayer(BasePlayer player) {
        players.remove(player);
        PlayerDeletedEvent.getInstance().setPayLoadAndCall(player);
    }

    public Deck getDeck() {
        return deck;
    }

    public Integer getActualBet() {
        return actualBet;
    }

    public void setActualBet(int actualBet) {
        this.actualBet = actualBet;
    }

    public Integer getBigBlind() {
        return bigBlind;
    }

    public Integer getSmallBlind() {
        return new Integer((int)Math.floor(this.smallBlindFactor*this.bigBlind));
    }

    public void giveBetAt(BasePlayer winner) {
        winner.giveMoney(this.pot);
        this.setPot(0);
        this.setActualBet(0);
    }

    public void setCurrentPhase(APhase currentPhase) {
        GamePhaseChangedEvent.getInstance().setPayLoadAndCall(currentPhase.getClass().getSimpleName());

        this.currentPhase = currentPhase;
    }

    public Queue<BasePlayer> getActivePlayers() {
        return this.currentPlayers;
    }

    public APhase getCurrentPhase() {
        return currentPhase;
    }

    public void setActivePlayers(List<BasePlayer> activePlayersFromTable){
        if(this.currentPlayers==null)
            this.currentPlayers = new LinkedList<>();

        this.currentPlayers.clear();
        this.currentPlayers.addAll(activePlayersFromTable);
        this.firstPlayer = this.currentPlayers.peek();
    }

    public boolean activePlayersHasSameBet(){
        Integer bet = this.getActualBet();
        for (BasePlayer player : this.getActivePlayers())
            if(!player.getBetMoney().equals(bet))
                return false;
        return true;
    }

    public void setFirstPlayerOfHand(BasePlayer player){
        this.firstPlayer = player;
    }

    public BasePlayer getFirstPlayerOfHand() {
        return this.firstPlayer;
    }

    public void resetOrderQueue() {
        //Reset queue order
        Queue<BasePlayer> allPlayers = new LinkedList<>(this.getPlayers());
        Queue<BasePlayer> activePlayers = this.getActivePlayers();
        BasePlayer firstPlayer = this.getFirstPlayerOfHand();

        if(this.getActivePlayers().size()==0)
            return;

        while(allPlayers.peek()!=firstPlayer)
            allPlayers.add(allPlayers.poll());

        while(!activePlayers.contains(allPlayers.peek()))
            allPlayers.poll();

        while(activePlayers.peek()!=allPlayers.peek())
            activePlayers.add(activePlayers.poll());

        ActivePlayerHasChangedEvent.getInstance().setPayLoadAndCall(this.getActivePlayer());
    }

    public void setAndNotify(Object event) {
        this.setChanged();
        this.notifyObservers(event);
    }

    public BasePlayer getActivePlayer() {
        return this.getActivePlayers().peek();
    }

    public void passToNextPlayer(boolean playerHasLeft) {
        if(!playerHasLeft)
            this.getActivePlayers().add(this.getActivePlayer());
        this.getActivePlayers().poll();

        ActivePlayerHasChangedEvent.getInstance().setPayLoadAndCall(this.getActivePlayer());

        if(this.getActivePlayers().size()==1) {
            this.setCurrentPhase(new Showdown());
            this.currentPhase.initPhase();
            HandEndedEvent.sendToSub();
        }

        if(this.getCurrentPhase()!=null) {
            if (this.getCurrentPhase().isCompleted())
                this.getCurrentPhase().goToNextPhase();
        }
    }

    public void handEnded(){
        this.setChanged();
        this.notifyObservers(HandEndedEvent.getInstance());
    }

    //For testing pourpose
    public void setDeck(Deck deck) {
        DeckChangedEvent.sendToSub();
        this.deck = deck;
    }

    public void addToDish(Integer toAdd){
        this.pot +=toAdd;
        PotChangedEvent.getInstance().setPayLoadAndCall(this.pot);
    }

    public void setPot(Integer toAdd){
        this.pot = toAdd;
        PotChangedEvent.getInstance().setPayLoadAndCall(this.pot);
    }

    public Integer getPot() {
        return pot;
    }

    public void goToNextHand(){
        this.initializeGame();

        BasePlayer player = players.get(0);
        players.remove(player);
        players.add(player);

        this.setCurrentPhase(new NotStarted(this));
        this.getCurrentPhase().initPhase();
    }
}
