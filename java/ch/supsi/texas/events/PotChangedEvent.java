package ch.supsi.texas.events;

import java.util.Observer;

public class PotChangedEvent  extends EventWithPayLoad<Integer> {
    private static PotChangedEvent event;

    private PotChangedEvent() {

    }

    public static PotChangedEvent getInstance() {
        if (event == null)
            event = new PotChangedEvent();
        return event;
    }

    public static void addSubscriber(Observer sub) {
        getInstance().addObserver(sub);
    }

    public static void removeSubscriber(Observer sub) {
        getInstance().deleteObserver(sub);
    }

    public static void sendToSub() {
        getInstance().callEvent();
    }
}
