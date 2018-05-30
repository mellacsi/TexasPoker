package ch.supsi.texas.gamePhases;

import ch.supsi.texas.events.ActivePlayerHasChangedEvent;

import java.util.Observable;
import java.util.Observer;

public class PreFlop extends APhase implements Observer{
    private int turns = 5555;

    @Override
    public void initPhase() {
        ActivePlayerHasChangedEvent.addSubscriber(this);
        for(int i = 0; i < 2; i++)
            this.getGameModel().getActivePlayers().forEach(player -> player.giveCard(this.getGameModel().getDeck().drawCard()));

        turns = this.getGameModel().getActivePlayers().size();
    }

    @Override
    public boolean isCompleted() {
        if(turns>0)
            return false;

        return this.getGameModel().activePlayersHasSameBet();
    }

    @Override
    public APhase getNextPhase(){
        return new Flop();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof ActivePlayerHasChangedEvent)
            turns--;
    }
}
