package ch.supsi.texas.gamePhases;

import ch.supsi.texas.events.ActivePlayerHasChangedEvent;

import java.util.Observable;
import java.util.Observer;

public class River extends APhase implements Observer{
    int turns = 5555;

    @Override
    public void initPhase() {
        ActivePlayerHasChangedEvent.addSubscriber(this);
        this.getGameModel().revealCards(1);
        turns = this.getGameModel().getActivePlayers().size();
        System.out.println("River Phase");
    }

    @Override
    public boolean isCompleted() {
        if(turns>0)
            return false;

        return this.getGameModel().activePlayersHasSameBet();
    }

    @Override
    public APhase getNextPhase(){
        return new Showdown();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof ActivePlayerHasChangedEvent)
            turns--;
    }
}
