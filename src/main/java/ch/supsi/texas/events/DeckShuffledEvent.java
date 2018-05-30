package ch.supsi.texas.events;

import java.util.Observer;

public class DeckShuffledEvent extends Event {
    private static DeckShuffledEvent event;

    private DeckShuffledEvent(){

    }

    public static DeckShuffledEvent getInstance(){
        if(event==null)
            event = new DeckShuffledEvent();
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