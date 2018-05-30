package ch.supsi.texas.view;

import ch.supsi.texas.ConsoleMediatorTextArea;
import ch.supsi.texas.events.*;
import ch.supsi.texas.player.BasePlayer;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import javax.swing.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Console implements Observer {
    private ConsoleMediatorTextArea output;

    //Used for test otherwise mockito don't inject mocks
    public Console(Integer textArea){ }

    public Console() {
        output = new ConsoleMediatorTextArea();
        output.setId("console");
        this.output.setEditable(false);

        registerEvents();
    }

    public void registerEvents(){
        DeckShuffledEvent.addSubscriber(this);
        DeckCreationEvent.addSubscriber(this);
        PlayerChangedEvent.addSubscriber(this);
        PlayerWinnerEvent.addSubscriber(this);
        NewCardOnTableEvent.addSubscriber(this);
        BlindEvent.addSubscriber(this);
        PlayerDeletedEvent.addSubscriber(this);
        PlayerAddedEvent.addSubscriber(this);
        GamePhaseChangedEvent.addSubscriber(this);
        ActionEvent.addSubscriber(this);
        ClearConsoleLogEvent.addSubscriber(this);
        HandValue.addSubscriber(this);
    }

    public TextArea getOutput() {
        return output.getTextArea();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof DeckShuffledEvent) {
            try {
                write("Deck's shuffling, the match it's starting\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (arg instanceof
                DeckCreationEvent) {
            try {
                write("Deck created\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (arg instanceof PlayerChangedEvent) {
            try {
                write("Is now turn of Player " + ((PlayerChangedEvent) arg).getPayLoad().getName() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (arg instanceof PlayerWinnerEvent) {
            try {
                write("Hand has terminated!\n");
                write("Player " + ((PlayerWinnerEvent) arg).getPayLoad().getName() + " win \n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (arg instanceof NewCardOnTableEvent) {
            try {
                write("Hand finished, new card on table \n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (arg instanceof BlindEvent) {
            try {
                write("Blind assigned \n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(arg instanceof ActionEvent){
            Map.Entry<BasePlayer, String> entry = ((ActionEvent) arg).getPayLoad();
            try {
                write(entry.getKey().getName() + " has called the action: " + entry.getValue() + " \n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (arg instanceof PlayerDeletedEvent) {
            try {
                write(((PlayerDeletedEvent)arg).getPayLoad().getName() + " left the hand \n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (arg instanceof PlayerAddedEvent) {
            try {
                write(((PlayerAddedEvent)arg).getPayLoad().getName() + " join the hand \n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(arg instanceof GamePhaseChangedEvent){
            try {
                write("GamePhase has changed to ->" + ((GamePhaseChangedEvent) arg).getPayLoad() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(arg instanceof ClearConsoleLogEvent){
            output.clear();
        }else if(arg instanceof HandValue){
            try {
                write(((BasePlayer)((HandValue) arg).getPayLoad().getKey()).getName() + " as the hand with " + ((HandValue) arg).getPayLoad().getValue() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(final String outputString) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        Platform.runLater(() -> {
            output.appendText(dateFormat.format(date) + " -- " + outputString);
        });
    }
}
