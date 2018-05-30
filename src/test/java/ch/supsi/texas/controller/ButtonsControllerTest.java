package ch.supsi.texas.controller;

import ch.supsi.texas.cards.Deck;
import ch.supsi.texas.events.UtilTestEvents;
import ch.supsi.texas.view.ButtonsView;
import javafx.scene.control.Button;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class ButtonsControllerTest {
    @InjectMocks
    ButtonsController controller;

    @Mock
    ButtonsView buttonView;

    @Mock
    Button callButton;
    @Mock
    Button foldButton;
    @Mock
    Button checkButton;
    @Mock
    Button raiseButton;

    @Before
    public void setUp() throws Exception {
        UtilTestEvents.clearEventSingleton();
        MockitoAnnotations.initMocks(this);

        controller.setButtonView(buttonView);
        Mockito.when(controller.getButtonView()).thenReturn(Mockito.mock(ButtonsView.class));
    }

    @Ignore("problem with JFX")
    @Test
    public void enableCall() throws Exception {
        //controller.enableCall();
        //Mockito.verify(callButton, Mockito.times(1)).setDisable(false);
    }
}