package ch.supsi.texas.events;

import org.mockito.internal.util.MockUtil;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class UtilTestEvents {

    public static <T> void mockEvent(T eventMocked, Class<T> classe){
        if(!MockUtil.isMock(eventMocked))
            throw new IllegalArgumentException("Element not mocked");
        try {
            Field instance = classe.getDeclaredField("event");
            instance.setAccessible(true);
            instance.set(eventMocked, eventMocked);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            assertTrue(false);
        }
    }

    public static <T> void clearEventSingleton(){
        Reflections reflections = new Reflections("ch.supsi.texas.events");
        Set<Class<? extends Event>> eventClasses = reflections.getSubTypesOf(Event.class);
        for(Class<? extends Event> eventClass : eventClasses) {
            if (eventClass == Event.class)
                continue;
            if (eventClass == EventWithPayLoad.class)
                continue;

            //Rimuovo il singleton che potrebbe essere un mock oppure istanzato da altri test
            try {
                Field instance = eventClass.getDeclaredField("event");
                instance.setAccessible(true);
                instance.set(null, null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                assertTrue(false);
            }
        }
    }
}
