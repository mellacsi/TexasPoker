package ch.supsi.texas.controller;

import ch.supsi.texas.commands.*;
import ch.supsi.texas.view.ButtonsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ButtonsController {
    private ButtonsView buttonView;

    public ButtonsController(ButtonsView buttonView, ActionMediator mediator) {
        this.buttonView = buttonView;
        Button callButton = buttonView.getCallButton();

        callButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CallCommand call = new CallCommand(mediator, buttonView.getOwner());
                call.execute();
            }
        });

        Button foldButton = buttonView.getFoldButton();
        foldButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FoldCommand fold = new FoldCommand(mediator, buttonView.getOwner());
                fold.execute();
            }
        });

        Button checkButton = buttonView.getCheckButton();
        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CheckCommand check = new CheckCommand(mediator, buttonView.getOwner());
                check.execute();
            }
        });

        Button raiseButton = buttonView.getRaiseButton();
        raiseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RaiseCommand raise = new RaiseCommand(mediator, buttonView.getOwner());
                Integer value = Integer.parseInt(buttonView.getRaiseAmount().getText());
                if(value>0) {
                    raise.setMoney(value);
                    raise.execute();
                    buttonView.getRaiseAmount().setText("0");
                }
            }
        });

        buttonView.getRaiseAmount().textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()!=0 && Integer.parseInt(newValue)<=0) {
                buttonView.getRaiseButton().setDisable(true);
                return;
            }
            buttonView.getRaiseButton().setDisable(false);
        });
    }

    public ButtonsView getButtonView() {
        return buttonView;
    }

    public void setButtonView(ButtonsView buttonsController){
        this.buttonView = buttonsController;
    }

    public void enableCall() {
        if(this.buttonView==null)
            return;
        buttonView.getCallButton().setDisable(false);
    }

    public void disableCall() {
        if(this.buttonView==null)
            return;
        buttonView.getCallButton().setDisable(true);
    }

    public void enableFold() {
        if(this.buttonView==null)
            return;
        buttonView.getFoldButton().setDisable(false);
    }

    public void disableFold() {
        if(this.buttonView==null)
            return;
        buttonView.getFoldButton().setDisable(true);
    }

    public void enableCheck() {
        if(this.buttonView==null)
            return;
        buttonView.getCheckButton().setDisable(false);
    }

    public void disableCheck() {
        if(this.buttonView==null)
            return;
        buttonView.getCheckButton().setDisable(true);
    }

    public void enableRaiseAmount() {
        if(this.buttonView==null)
            return;
        buttonView.getRaiseAmount().setDisable(false);
    }

    public void disableRaise() {
        if(this.buttonView==null)
            return;
        buttonView.getRaiseButton().setDisable(true);
    }

    public void disableRaiseAmount() {
        if(this.buttonView==null)
            return;
        buttonView.getRaiseAmount().setDisable(true);
    }

    public void disableAll() {
        if(this.buttonView==null)
            return;
        disableCall();
        disableCheck();
        disableFold();
        disableRaise();
        disableRaiseAmount();
    }
}
