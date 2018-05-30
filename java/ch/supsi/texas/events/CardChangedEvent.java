package ch.supsi.texas.events;

import ch.supsi.texas.cards.Card;

import java.util.List;
import java.util.Observer;

public class CardChangedEvent extends EventWithPayLoad<List<Card>> {
    private static CardChangedEvent event;

    private CardChangedEvent(){

    }

    public static CardChangedEvent getInstance(){
        if(event==null)
            event = new CardChangedEvent();
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
