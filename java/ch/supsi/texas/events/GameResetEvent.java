package ch.supsi.texas.events;

import java.util.Observer;

public class GameResetEvent extends Event {
    private static GameResetEvent event;

    private GameResetEvent() {

    }

    public static GameResetEvent getInstance() {
        if (event == null)
            event = new GameResetEvent();
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