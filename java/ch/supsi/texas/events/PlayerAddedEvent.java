package ch.supsi.texas.events;

import ch.supsi.texas.player.BasePlayer;

import java.util.Observer;

public class PlayerAddedEvent extends EventWithPayLoad<BasePlayer> {
    private static PlayerAddedEvent event;

    private PlayerAddedEvent() {

    }

    public static PlayerAddedEvent getInstance() {
        if (event == null)
            event = new PlayerAddedEvent();
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
