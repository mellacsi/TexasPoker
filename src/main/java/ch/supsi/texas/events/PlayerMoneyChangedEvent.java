package ch.supsi.texas.events;

import ch.supsi.texas.player.BasePlayer;

import java.util.Observer;

public class PlayerMoneyChangedEvent extends EventWithPayLoad<BasePlayer>{
        static PlayerMoneyChangedEvent event;

        private PlayerMoneyChangedEvent() {

        }

        public static PlayerMoneyChangedEvent getInstance() {
            if (event == null)
                event = new PlayerMoneyChangedEvent();
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
