package ch.supsi.texas.events;

import java.util.Observable;

public abstract class Event extends Observable {

    public void callEvent() {
        this.setChanged();
        this.notifyObservers(this);
    }
}
