package ch.supsi.texas.events;

import ch.supsi.texas.player.BasePlayer;

import java.util.Observer;

public class PlayerWinnerEvent extends EventWithPayLoad<BasePlayer> {
    private static PlayerWinnerEvent event;

    private PlayerWinnerEvent() {

    }

    public static PlayerWinnerEvent getInstance() {
        if (event == null)
            event = new PlayerWinnerEvent();
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