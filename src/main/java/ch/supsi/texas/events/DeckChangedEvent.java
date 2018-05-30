package ch.supsi.texas.events;

import java.util.Observer;

public class DeckChangedEvent extends Event {
    private static DeckChangedEvent event;

    private DeckChangedEvent(){

    }

    public static DeckChangedEvent getInstance(){
        if(event==null)
            event = new DeckChangedEvent();
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