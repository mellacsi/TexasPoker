package ch.supsi.texas.gamePhases;

import ch.supsi.texas.GameModel;
import ch.supsi.texas.events.ActivePlayerHasChangedEvent;
import ch.supsi.texas.player.BasePlayer;

import java.util.Observer;

public abstract class APhase {
    private static GameModel gameModel;

    static void setGameModel(GameModel gameModel){
        if(gameModel==null)
            throw new NullPointerException();

        APhase.gameModel = gameModel;
    }

    protected GameModel getGameModel(){
        if(APhase.gameModel==null)
            throw new NullPointerException();

        return APhase.gameModel;
    }

    public abstract void initPhase();

    public void goToNextPhase() {
        if(this.getNextPhase()!=null) {
            if(this.getGameModel().getCurrentPhase() instanceof Observer)
                ActivePlayerHasChangedEvent.removeSubscriber((Observer) this.getGameModel().getCurrentPhase());
            this.getGameModel().setCurrentPhase(this.getNextPhase());
            this.getGameModel().resetOrderQueue();
            this.getGameModel().getCurrentPhase().initPhase();
        }
    }

    public abstract boolean isCompleted();

    public abstract APhase getNextPhase();
}
