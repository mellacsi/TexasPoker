package ch.supsi.texas.view;

import ch.supsi.texas.cards.Card;
import ch.supsi.texas.commands.ActionMediator;
import ch.supsi.texas.controller.ButtonsController;
import ch.supsi.texas.events.*;
import ch.supsi.texas.player.BasePlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javax.swing.text.Position;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PlayerView extends GridPane implements Observer {
    private BasePlayer owner;

    private HBox cardBox = new HBox();
    private Label playerName = new Label("default name");
    private Label moneyText = new Label("999");
    private Label betMoneyText = new Label("1");

    // Per simulare le scelte in assenza id IA ogni giocatore ha i suoi bottoni
    private ButtonsView buttonsView;
    private ButtonsController buttonsController;

    public PlayerView(BasePlayer owner, ActionMediator mediator, int id) {
        this.owner = owner;

        this.setPadding(new Insets(10, 10, 10, 10));
        this.setAlignment(Pos.CENTER);

        playerName.setAlignment(Pos.CENTER);
        playerName.setText("Player: " + owner.getName());
        cardBox.setAlignment(Pos.TOP_CENTER);
        cardBox.setSpacing(10);

        VBox moneyBox = new VBox();
        moneyBox.setSpacing(10);
        moneyBox.getChildren().addAll(new Label("Money:"), moneyText);

        VBox betBox = new VBox();
        betBox.setSpacing(10);
        betBox.getChildren().addAll(new Label("Bet:"), betMoneyText);

        System.out.println(id  +";");
        buttonsView = new ButtonsView(owner, id);
        buttonsView.setAlignment(Pos.CENTER);

        // this is only temporary (as long as every player has his buttons)
        buttonsController = new ButtonsController(buttonsView, mediator);

        this.setConstraints(playerName, 1, 0);
        this.setConstraints(moneyBox, 0, 1);
        this.setConstraints(cardBox, 1, 1);
        this.setConstraints(betBox, 2, 1);
        this.setConstraints(buttonsView, 1, 2);
        this.getChildren().addAll(playerName, moneyBox, cardBox, betBox, buttonsView);

        ActionMediator.getInstance().registerPlayerButtons(this.owner, this.buttonsController);

        CardGivenToPlayer.getInstance().addObserver(this);
        PlayerMoneyChangedEvent.getInstance().addObserver(this);
        PlayerBetHasChangedEvent.getInstance().addObserver(this);
        DeckChangedEvent.addSubscriber(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof PlayerMoneyChangedEvent) {
            if(!PlayerMoneyChangedEvent.getInstance().getPayLoad().equals(this.owner))
                return;
            Integer money = PlayerMoneyChangedEvent.getInstance().getPayLoad().getMoney();
            moneyText.setText(money.toString());
        }

        if (arg instanceof PlayerBetHasChangedEvent) {
            if(!PlayerBetHasChangedEvent.getInstance().getPayLoad().equals(this.owner))
                return;
            Integer betMoney = PlayerBetHasChangedEvent.getInstance().getPayLoad().getBetMoney();
            betMoneyText.setText(betMoney.toString());
        }

        if (arg instanceof CardGivenToPlayer) {
            if(!CardGivenToPlayer.getInstance().getPayLoad().equals(this.owner))
                return;

            cardBox.getChildren().clear();
            List<Card> cards = CardGivenToPlayer.getInstance().getPayLoad().getCards();

            cards.forEach((card) -> {
                ImageView cardView = CardView.getImageView(card);
                cardBox.getChildren().add(cardView);
            });
        }

        if (arg instanceof DeckChangedEvent) {
            cardBox.getChildren().clear();
        }

    }
}
