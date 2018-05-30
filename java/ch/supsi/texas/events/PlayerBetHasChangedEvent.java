package ch.supsi.texas.events;

import ch.supsi.texas.player.BasePlayer;

import java.util.Observer;

public class PlayerBetHasChangedEvent extends EventWithPayLoad<BasePlayer>{
    private static PlayerBetHasChangedEvent event;

    private PlayerBetHasChangedEvent() {

    }

    public static PlayerBetHasChangedEvent getInstance() {
        if (event == null)
            event = new PlayerBetHasChangedEvent();
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
