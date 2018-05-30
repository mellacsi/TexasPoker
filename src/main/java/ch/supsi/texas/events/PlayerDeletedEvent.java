package ch.supsi.texas.events;

import ch.supsi.texas.player.BasePlayer;

import java.util.Observer;

public class PlayerDeletedEvent  extends EventWithPayLoad<BasePlayer> {
    private static PlayerDeletedEvent event;

    private PlayerDeletedEvent() {

    }

    public static PlayerDeletedEvent getInstance() {
        if (event == null)
            event = new PlayerDeletedEvent();
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