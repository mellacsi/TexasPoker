package ch.supsi.texas.gamePhases;

import ch.supsi.texas.events.ActivePlayerHasChangedEvent;

import java.util.Observable;
import java.util.Observer;

public class Flop extends APhase implements Observer {
    int turns = 5555;

    @Override
    public void initPhase() {
        ActivePlayerHasChangedEvent.addSubscriber(this);
        this.getGameModel().revealCards(3);
        turns = this.getGameModel().getActivePlayers().size();
    }

    @Override
    public boolean isCompleted() {
        if(turns>0)
            return false;

        return this.getGameModel().activePlayersHasSameBet();
    }
    @Override
    public APhase getNextPhase() {
        return new Turn();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof ActivePlayerHasChangedEvent)
            turns--;
    }
}
