package ch.supsi.texas.events;

public abstract class EventWithPayLoad<T> extends Event {
    private T payLoad;

    public void setPayLoad(T payload){
        this.payLoad = payload;
    }

    public void setPayLoadAndCall(T payload){
        this.setPayLoad(payload);
        this.callEvent();
    }

    public T getPayLoad(){
        return this.payLoad;
    }
}

