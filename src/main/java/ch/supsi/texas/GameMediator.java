package ch.supsi.texas;

import ch.supsi.texas.events.InvisibleHandButtonEvent;
import ch.supsi.texas.events.NextHandEvent;
import ch.supsi.texas.gamePhases.NotStarted;
import ch.supsi.texas.player.BasePlayer;
import ch.supsi.texas.player.ConcretePlayer;
import java.util.*;

//GameMediator (logica e interazione con GUI - comunicano)
public class GameMediator extends Observable implements Observer {
    private GameModel gameModel;

    public GameMediator(GameModel gameModel) {
        if(gameModel == null)
            throw new NullPointerException();
        this.gameModel = gameModel;
        this.gameModel.addObserver(this);
        NextHandEvent.addSubscriber(this);
    }

    public void initializeGame(Integer numberOfPlayers) {
        getGameModel().reset();
        String randomNames[] = {"Fuffy", "Tilde", "Grigino", "Ramon", "Boris", "Zorro"};

        for (int i = 0; i < numberOfPlayers; i++)
            gameModel.addPlayer(new ConcretePlayer(randomNames[i]));

        getGameModel().getDeck().shuffle();
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void startMatch(){
        this.gameModel.initializeGame();
        this.gameModel.setActivePlayers(this.getActivePlayersFromTable());
        this.gameModel.getCurrentPhase().initPhase();
    }

    public List<BasePlayer> getActivePlayersFromTable() {
        List<BasePlayer> activePlayers = new ArrayList<>();

        for(BasePlayer player : gameModel.getPlayers())
            if(player.getMoney() > 0) activePlayers.add(player);

        return activePlayers;
    }

    public void reset() {
        this.gameModel.setCurrentPhase(new NotStarted(this.gameModel));

        if(this.gameModel.getActivePlayers()!=null)
            this.gameModel.setActivePlayers(new ArrayList<>());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof NextHandEvent){
            gameModel.goToNextHand();
            InvisibleHandButtonEvent.sendToSub();
        }
    }
}
