package ch.supsi.texas.events;

import ch.supsi.texas.player.BasePlayer;

import java.util.Observer;

public class CardGivenToPlayer extends EventWithPayLoad<BasePlayer> {
    private static CardGivenToPlayer event;

    private CardGivenToPlayer(){

    }

    public static CardGivenToPlayer getInstance(){
        if(event==null)
            event = new CardGivenToPlayer();
        return event;
    }

    public static void addSubscriber(Observer sub){
        getInstance().addObserver(sub);
    }
    public static void removeSubscriber(Observer sub){
        getInstance().deleteObserver(sub);
    }

    public static void sendToSub(){
        getInstance().callEvent();
    }

}
