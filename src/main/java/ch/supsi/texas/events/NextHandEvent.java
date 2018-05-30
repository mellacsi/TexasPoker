package ch.supsi.texas.events;

import java.util.Observer;

public class NextHandEvent extends Event{
    private static NextHandEvent event;

    private NextHandEvent(){

    }

    public static NextHandEvent getInstance(){
        if(event==null)
            event = new NextHandEvent();
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
