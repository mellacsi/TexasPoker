package ch.supsi.texas.events;

import ch.supsi.texas.player.BasePlayer;
import java.util.Observer;

public class HandTerminatedEvent extends EventWithPayLoad<BasePlayer> {
    private static HandTerminatedEvent event;

    private HandTerminatedEvent() {

    }

    public static HandTerminatedEvent getInstance() {
        if (event == null)
            event = new HandTerminatedEvent();
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