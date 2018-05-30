package ch.supsi.texas.events;

import ch.supsi.texas.player.BasePlayer;

import java.util.Map;
import java.util.Observer;

public class HandValue extends EventWithPayLoad<Map.Entry<BasePlayer, String>> {
    private static HandValue event;

    private HandValue(){

    }

    public static HandValue getInstance(){
        if(event==null)
            event = new HandValue();
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
