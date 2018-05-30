package ch.supsi.texas.events;

import java.util.Observer;

public class ClearConsoleLogEvent extends Event {
    private static ClearConsoleLogEvent event;

    private ClearConsoleLogEvent(){}

    public static ClearConsoleLogEvent getInstance(){
        if(event==null)
            event = new ClearConsoleLogEvent();
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
