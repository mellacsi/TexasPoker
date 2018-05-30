package ch.supsi.texas.events;

import ch.supsi.texas.player.BasePlayer;

import java.util.Map;
import java.util.Observer;

public class InvisibleHandButtonEvent extends EventWithPayLoad<Map.Entry<BasePlayer, String>> {
    private static InvisibleHandButtonEvent event;

    private InvisibleHandButtonEvent(){

    }

    public static InvisibleHandButtonEvent getInstance(){
        if(event==null)
            event = new InvisibleHandButtonEvent();
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