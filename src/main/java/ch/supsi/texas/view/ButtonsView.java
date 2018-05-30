package ch.supsi.texas.view;

import ch.supsi.texas.player.BasePlayer;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

// Mediator abilita le azioni, che poi modificano il modello
public class ButtonsView extends VBox {
    private BasePlayer owner;
    private Button callButton = new Button();
    private Button foldButton = new Button();
    private Button checkButton = new Button();
    private Button raiseButton = new Button();

    private TextField raiseAmount = new TextField() {
        @Override
        public void replaceText(int start, int end, String text)
        {
            if (validate(text))
            {
                raiseButton.setDisable(false);
                super.replaceText(start, end, text);
            }else{
                raiseButton.setDisable(true);
            }
        }

        @Override
        public void replaceSelection(String text)
        {
            if (validate(text))
            {
                raiseButton.setDisable(false);
                super.replaceSelection(text);
            }else{
                raiseButton.setDisable(true);
            }
        }

    private boolean validate(String text)
    {
        return text.matches("[0-9]*");
    }

};

    public BasePlayer getOwner() {
        return owner;
    }

    public Button getCallButton() {
        return callButton;
    }

    public Button getFoldButton() {
        return foldButton;
    }

    public Button getCheckButton() {
        return checkButton;
    }

    public Button getRaiseButton() {
        return raiseButton;
    }

    public TextField getRaiseAmount() {
        return raiseAmount;
    }

    public ButtonsView(BasePlayer owner, int id) {
        this.owner = owner;

        callButton.setText("callButton");
        callButton.setId("callButton" + id);
        foldButton.setText("foldButton");
        foldButton.setId("foldButton" + id);
        checkButton.setText("checkButton");
        checkButton.setId("checkButton" + id);
        raiseButton.setText("raiseButton");
        raiseButton.setId("raiseButton" + id);
        raiseAmount.setText("0");
        raiseAmount.setId("raiseAmount" + id);

        this.getChildren().addAll(callButton, foldButton, checkButton, raiseButton, raiseAmount);
    }
}
