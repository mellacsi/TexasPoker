package ch.supsi.texas.gamePhases;

import ch.supsi.texas.GameModel;

public class NotStarted extends APhase {
    boolean completed = false;

    public NotStarted(GameModel gameModel){
        APhase.setGameModel(gameModel);
    }

    @Override
    public void initPhase() {
        this.getGameModel().setActivePlayers(this.getGameModel().getPlayers());
        this.goToNextPhase();
        completed = true;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public APhase getNextPhase(){
        return new CompulsoryBet();
    }
}
