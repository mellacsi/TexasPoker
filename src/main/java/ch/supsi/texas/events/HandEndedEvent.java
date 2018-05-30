package ch.supsi.texas.events;

import java.util.Observer;

public class HandEndedEvent  extends Event {
    private static HandEndedEvent event;

    private HandEndedEvent() {

    }

    public static HandEndedEvent getInstance() {
        if (event == null)
            event = new HandEndedEvent();
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