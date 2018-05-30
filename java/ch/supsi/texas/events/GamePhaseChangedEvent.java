package ch.supsi.texas.events;

import java.util.Observer;

public class GamePhaseChangedEvent extends EventWithPayLoad<String> {
    private static GamePhaseChangedEvent event;

    private GamePhaseChangedEvent() {

    }

    public static GamePhaseChangedEvent getInstance() {
        if (event == null)
            event = new GamePhaseChangedEvent();
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