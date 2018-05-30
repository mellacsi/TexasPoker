package ch.supsi.texas.events;

import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import static org.junit.Assert.*;

public class testEvents{

    static class EventReciver implements Observer{
        Object receivedEvent;

        @Override
        public void update(Observable o, Object arg) {
            receivedEvent = arg;
        }
    }

    EventReciver receiver;

    @Before
    public void before(){
        receiver = new EventReciver();
    }

    @Test
    public void allTest() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Reflections reflections = new Reflections("ch.supsi.texas.events");
        Set<Class<? extends Event>> eventClasses = reflections.getSubTypesOf(Event.class);
        for(Class<? extends Event> eventClass : eventClasses){
            if(eventClass==Event.class)
                continue;
            if(eventClass==EventWithPayLoad.class)
                continue;

            //Rimuovo il singleton che potrebbe essere un mock oppure istanzato da altri test
            try {
                Field instance = eventClass.getDeclaredField("event");
                instance.setAccessible(true);
                instance.set(null, null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                assertTrue(false);
            }

            //Creo un oggetto e mi assicuro che il costruttore sia privato
            Constructor<? extends Event> construction = eventClass.getDeclaredConstructor();
            assertFalse(construction.isAccessible());
            construction.setAccessible(true);
            Object objectEvent = construction.newInstance(null);

            //Testo getInstance/singleton
            Method getInstance = eventClass.getMethod("getInstance");

            Object instance = getInstance.invoke(null);
            Object instance2 = getInstance.invoke(null);

            //System.out.println(eventClass.getSimpleName());

            assertTrue(instance.getClass()==objectEvent.getClass());
            assertSame(instance, instance2);

            //Observer add / notify
            Method addSubscriber = eventClass.getMethod("addSubscriber", Observer.class);
            addSubscriber.invoke(null, receiver);
            Method sendToSub = eventClass.getMethod("sendToSub");
            sendToSub.invoke(null);

            assertSame(instance, receiver.receivedEvent);
            receiver.receivedEvent = null;

            //Remove SUB
            Method removeSubscriber = eventClass.getMethod("removeSubscriber", Observer.class);
            removeSubscriber.invoke(null,receiver);
            sendToSub.invoke(null);
            assertNull(receiver.receivedEvent);
        }
    }

    @Test
    public void EventWithPayloadTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Reflections reflections = new Reflections("ch.supsi.texas.events");
        Set<Class<? extends EventWithPayLoad>> eventClasses = reflections.getSubTypesOf(EventWithPayLoad.class);
        for(Class<? extends EventWithPayLoad> eventClass : eventClasses){
            if(eventClass==EventWithPayLoad.class)
                continue;

            //Creo un oggetto e mi assicuro che il costruttore sia privato
            Constructor<? extends EventWithPayLoad> construction = eventClass.getDeclaredConstructor();
            assertFalse(construction.isAccessible());
            construction.setAccessible(true);
            EventWithPayLoad objectEvent = construction.newInstance(null);

            objectEvent.setPayLoad(this);

            assertSame(this, objectEvent.getPayLoad());

            objectEvent.addObserver(receiver);
            objectEvent.callEvent();

            assertSame(objectEvent, receiver.receivedEvent);
            receiver.receivedEvent = null;

            objectEvent.setPayLoadAndCall(this);
            assertSame(objectEvent, receiver.receivedEvent);
            receiver.receivedEvent = null;
        }
    }
}
