package ch.supsi.texas.events;

import java.util.Observer;

public class DeckCreationEvent extends Event {
    private static DeckCreationEvent event;

    private DeckCreationEvent(){

    }

    public static DeckCreationEvent getInstance(){
        if(event==null)
            event = new DeckCreationEvent();
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
