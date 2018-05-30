package ch.supsi.texas.commands;

import ch.supsi.texas.GameModel;
import ch.supsi.texas.controller.ButtonsController;
import ch.supsi.texas.events.ActionEvent;
import ch.supsi.texas.events.ActivePlayerHasChangedEvent;
import ch.supsi.texas.events.HandEndedEvent;
import ch.supsi.texas.player.BasePlayer;

import java.util.*;

public class ActionMediator implements Observer{
    static ActionMediator instance;
    Map<BasePlayer, ButtonsController> playerButtons = new HashMap<>();

    protected ActionMediator(){
        ActivePlayerHasChangedEvent.getInstance().addObserver(this);
        HandEndedEvent.addSubscriber(this);
    }

    private GameModel gameModel;

    public static ActionMediator getInstance(){
        if(instance == null)
            instance = new ActionMediator();

        return instance;
    }

    public boolean callIsValid(BasePlayer owner) {
        if(owner==null)
            throw new NullPointerException();
        if((this.getGameModel().getActualBet() - owner.getBetMoney()) <= owner.getMoney())
            return true;
        return false;
    }

    public void call(BasePlayer owner) {
        if(owner==null)
            throw new NullPointerException();

        Integer money = owner.matchBet(this.getGameModel().getActualBet());
        this.getGameModel().addToDish(money);
        this.getGameModel().passToNextPlayer(false);

        Map.Entry entry =new AbstractMap.SimpleEntry<BasePlayer, String>(owner, "Call");
        ActionEvent.getInstance().setPayLoadAndCall(entry);
    }

    /*
    Bussare o passare (check): in questo caso il giocatore passa il turno al giocatore successivo nell'ordine di gioco senza effettuare alcuna puntata.
    */
    public void check(BasePlayer owner) {
        if(owner==null)
            throw new NullPointerException();
        this.getGameModel().passToNextPlayer(false);
        Map.Entry entry =new AbstractMap.SimpleEntry<BasePlayer, String>(owner, "Check");
        ActionEvent.getInstance().setPayLoadAndCall(entry);
    }

    public boolean checkIsValid(BasePlayer owner) {
        if(owner==null)
            throw new NullPointerException();
        if(!this.getGameModel().getActualBet().equals(owner.getBetMoney()))
            return false;
        if(!this.getGameModel().getActivePlayer().equals(owner))
            return false;

        return true;
    }

    //fold: abbandonare la mano.
    public void fold(BasePlayer owner){
        if(owner==null)
            throw new NullPointerException();

        this.getGameModel().passToNextPlayer(true);
        Map.Entry entry =new AbstractMap.SimpleEntry<BasePlayer, String>(owner, "Fold");
        ActionEvent.getInstance().setPayLoadAndCall(entry);
    }

    public boolean foldIsValid(BasePlayer owner) {
        if(owner==null)
            throw new NullPointerException();

        if(!this.getGameModel().getActivePlayer().equals(owner) || this.getGameModel().getActivePlayers().size()==1)
            return false;
        return true;
    }

    //raise: effettuare una puntata superiore rispetto alla puntata più alta effettuata fino a quel momento.
    //      se punto punto più di quello che ho, la puntata mi manda in allin
    public void raise(BasePlayer owner, Integer RAISE_AMOUNT){
        if(owner==null)
            throw new NullPointerException();

        Integer money = owner.matchBet(this.getGameModel().getActualBet()+RAISE_AMOUNT);
        this.getGameModel().addToDish(money);
        this.getGameModel().setActualBet(this.getGameModel().getActualBet()+RAISE_AMOUNT);
        this.getGameModel().passToNextPlayer(false);
        Map.Entry entry =new AbstractMap.SimpleEntry<BasePlayer, String>(owner, "Raise");
        ActionEvent.getInstance().setPayLoadAndCall(entry);
    }

    public boolean raiseIsValid(BasePlayer owner, Integer RAISE_AMOUNT) {
        if(owner.getMoney()-((this.getGameModel().getActualBet()+RAISE_AMOUNT)-owner.getBetMoney())>=0)
            return true;
        if(!this.getGameModel().getActivePlayer().equals(owner))
            return false;
        return false;
    }

    private GameModel getGameModel(){
        return this.gameModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof ActivePlayerHasChangedEvent){
           activePlayerHasChangedCallback();
        }else if(arg instanceof HandEndedEvent){
            for(BasePlayer player : this.gameModel.getPlayers()){
                //disable all buttons
                ButtonsController controlsOfPlayer =this.playerButtons.get(player);
                controlsOfPlayer.disableAll();
            }
        }
    }

    private void activePlayerHasChangedCallback(){
        BasePlayer playerActive = ActivePlayerHasChangedEvent.getInstance().getPayLoad();
        if(playerActive==null)
            throw new NullPointerException();
        ButtonsController buttonsController = this.playerButtons.get(playerActive);

        if(buttonsController==null)
            return;

        for(BasePlayer player : this.gameModel.getPlayers()){
            ButtonsController controlsOfPlayer =this.playerButtons.get(player);
            if(controlsOfPlayer==null)
                continue;
            controlsOfPlayer.disableAll();
        }

        if(callIsValid(playerActive))
            buttonsController.enableCall();
        if(checkIsValid(playerActive))
            buttonsController.enableCheck();
        if(foldIsValid(playerActive))
            buttonsController.enableFold();
        if(raiseIsValid(playerActive, 10)) {
            //button raise will be enabled when player digits a number
            //buttonsController.enableRaise();
            buttonsController.enableRaiseAmount();
        }
    }

    public void registerPlayerButtons(BasePlayer owner, ButtonsController buttonsController) {
        if(owner==null || buttonsController==null)
            throw new NullPointerException();

        this.playerButtons.put(owner, buttonsController);
    }

    public void setGameModel(GameModel gameModel) {
        if(gameModel==null)
            throw new NullPointerException();

        this.gameModel = gameModel;
        this.gameModel.addObserver(this);
    }
}

