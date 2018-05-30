package ch.supsi.texas.events;

import ch.supsi.texas.player.BasePlayer;

import java.util.Observer;

public class ActivePlayerHasChangedEvent extends EventWithPayLoad<BasePlayer>{

    private static ActivePlayerHasChangedEvent event;

    private ActivePlayerHasChangedEvent(){

    }

    public static ActivePlayerHasChangedEvent getInstance(){
        if(event==null)
            event = new ActivePlayerHasChangedEvent();
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
