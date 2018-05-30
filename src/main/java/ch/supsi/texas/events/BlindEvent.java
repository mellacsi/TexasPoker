package ch.supsi.texas.events;

import ch.supsi.texas.player.BasePlayer;

import java.util.Observer;

public class BlindEvent extends Event{
    private static BlindEvent event;

    private BasePlayer owner;

    private BlindEvent(){

    }

    public static BlindEvent getInstance(){
        if(event==null)
            event = new BlindEvent();
        return event;
    }

    public void setPlayer(BasePlayer player){
        this.owner = player;
    }

    public void getPlayer(BasePlayer player){
        this.owner = player;
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
