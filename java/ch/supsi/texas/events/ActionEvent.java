package ch.supsi.texas.events;

import ch.supsi.texas.player.BasePlayer;

import java.util.Map;
import java.util.Observer;

public class ActionEvent extends EventWithPayLoad<Map.Entry<BasePlayer, String>> {
    private static ActionEvent event;

    private ActionEvent(){

    }

    public static ActionEvent getInstance(){
        if(event==null)
            event = new ActionEvent();
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
