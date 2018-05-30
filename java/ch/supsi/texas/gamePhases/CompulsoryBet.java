package ch.supsi.texas.gamePhases;

import ch.supsi.texas.events.ActivePlayerHasChangedEvent;
import ch.supsi.texas.player.BasePlayer;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class CompulsoryBet extends APhase{
    boolean isCompleted = false;

    @Override
    public void initPhase() {
        int bigBlind = this.getGameModel().getBigBlind();
        int smallBlind = this.getGameModel().getSmallBlind();
        this.getGameModel().setActualBet(bigBlind);
        ArrayList<BasePlayer> activePlayers = new ArrayList(this.getGameModel().getActivePlayers());
        activePlayers.get(1).matchBet(new Integer((int)Math.floor(smallBlind)));
        activePlayers.get(0).matchBet(new Integer((int)Math.floor(bigBlind)));

        this.getGameModel().addToDish(new Integer((int)Math.floor(smallBlind)) + new Integer((int)Math.floor(bigBlind)));
        isCompleted = true;
        this.goToNextPhase();
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public APhase getNextPhase(){
        return new PreFlop();
    }


}
