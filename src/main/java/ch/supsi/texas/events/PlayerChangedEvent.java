package ch.supsi.texas.events;

import ch.supsi.texas.cards.Card;
import ch.supsi.texas.player.BasePlayer;

import java.util.Observer;

public class PlayerChangedEvent  extends EventWithPayLoad<BasePlayer> {
    private static PlayerChangedEvent event;

    private PlayerChangedEvent() {

    }

    public static PlayerChangedEvent getInstance() {
        if (event == null)
            event = new PlayerChangedEvent();
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
