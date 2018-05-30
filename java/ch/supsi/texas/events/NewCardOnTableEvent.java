package ch.supsi.texas.events;

import java.util.Observer;

public class NewCardOnTableEvent extends Event {
    private static NewCardOnTableEvent event;

    private NewCardOnTableEvent() {

    }

    public static NewCardOnTableEvent getInstance() {
        if (event == null)
            event = new NewCardOnTableEvent();
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
