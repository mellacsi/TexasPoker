package ch.supsi.texas.gamePhases;

import ch.supsi.texas.pokerPoints.PokerPoints;
import ch.supsi.texas.cards.Card;
import ch.supsi.texas.events.HandTerminatedEvent;
import ch.supsi.texas.events.HandValue;
import ch.supsi.texas.events.PlayerWinnerEvent;
import ch.supsi.texas.player.BasePlayer;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Showdown extends APhase {

    boolean turnCompleted = false;

    @Override
    public void initPhase() {
        //Check the winner and give money
        BasePlayer winner = this.getGameModel().getActivePlayers().peek();
        for (BasePlayer player : this.getGameModel().getActivePlayers()) {
            List<Card> actualBetHand = new ArrayList<>();
            List<Card> playerHand = new ArrayList<>();

            actualBetHand.addAll(winner.getCards());
            playerHand.addAll(player.getCards());

            if(!PokerPoints.compareHands(actualBetHand, playerHand, this.getGameModel().getDealtCards()))
                winner = player;
        }

        for(BasePlayer player : this.getGameModel().getActivePlayers()){
            String nameHand = PokerPoints.winnerHands.get(PokerPoints.winnerHands.size()-PokerPoints.getPointOfHand(player.getCards(), this.getGameModel().getDealtCards())).getClass().getSimpleName();
            HandValue.getInstance().setPayLoadAndCall(new AbstractMap.SimpleEntry<BasePlayer, String>(player, nameHand));
        }

        this.getGameModel().giveBetAt(winner);

        turnCompleted = true;
        this.getGameModel().handEnded();
        PlayerWinnerEvent.getInstance().setPayLoadAndCall(winner);
        HandTerminatedEvent.getInstance().setPayLoadAndCall(winner);
    }

    @Override
    public boolean isCompleted() {
        return turnCompleted;
    }

    @Override
    public APhase getNextPhase(){
        return null;
    }
}
