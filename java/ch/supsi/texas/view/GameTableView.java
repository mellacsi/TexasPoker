package ch.supsi.texas.view;

import ch.supsi.texas.GameModel;
import ch.supsi.texas.cards.Card;
import ch.supsi.texas.commands.ActionMediator;
import ch.supsi.texas.events.*;
import ch.supsi.texas.player.BasePlayer;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;

public class GameTableView extends VBox implements Observer {
    // GUI elements composing the game table:
    private MenuBar menuBar;
    private HBox dealerBox;
    private HBox opponentsBox;
    private HBox bottomBox;
    private int id = 0;
    private boolean first = true;
    private HBox dealerFunc;
    private Console console;

    public GameTableView(GameModel gameModel) {
        if(gameModel == null)
            throw new NullPointerException();

        this.menuBar = new MenuBar();
        Menu files = new Menu("Files");
        files.setId("files");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setId("newGame");
        MenuItem exit = new MenuItem("Exit");
        exit.setId("exit");

        menuBar.getMenus().addAll(files);
        files.getItems().addAll(newGame, exit);
        this.dealerBox = new HBox();
        dealerBox.setSpacing(10);

        this.opponentsBox = new HBox();
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        this.opponentsBox.getChildren().add(grid);
        this.bottomBox = new HBox();
        this.console = new Console();
        bottomBox.getChildren().add(console.getOutput());
        bottomBox.setVisible(false);

        dealerFunc = new HBox();
        dealerFunc.setSpacing(200);
        Button nextHandButton = new Button();
        nextHandButton.setAlignment(Pos.TOP_LEFT);
        nextHandButton.setText("Go to next hand!");
        nextHandButton.setId("nextHandButton");
        nextHandButton.setOnAction((event)->{
            NextHandEvent.sendToSub();
        });
        nextHandButton.setDisable(true);

        Text pot = new Text();
        pot.setFont(Font.font(20));
        dealerFunc.getChildren().addAll(nextHandButton, pot);
        dealerFunc.setVisible(false);

        this.getChildren().addAll(menuBar, dealerBox, dealerFunc, opponentsBox, bottomBox);

        CardChangedEvent.addSubscriber(this);
        PlayerAddedEvent.addSubscriber(this);
        GameResetEvent.addSubscriber(this);
        DeckChangedEvent.addSubscriber(this);
        PotChangedEvent.addSubscriber(this);
        PlayerWinnerEvent.addSubscriber(this);
        HandTerminatedEvent.addSubscriber(this);
        InvisibleHandButtonEvent.addSubscriber(this);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
    public Console getConsole(){ return console; }

    @Override
    public void update(Observable o, Object arg) {
        // Dealer Update
        if(arg instanceof CardChangedEvent) {
            dealerBox.getChildren().clear();
            List<Card> cards = CardChangedEvent.getInstance().getPayLoad();

            cards.forEach((card) -> {
                ImageView cardView = CardView.getImageView(card);
                dealerBox.getChildren().add(cardView);
            });
        }

        else if(arg instanceof PlayerAddedEvent) {
            BasePlayer addedPlayer = ((PlayerAddedEvent) arg).getPayLoad();
            PlayerView playerView = new PlayerView(addedPlayer, ActionMediator.getInstance(), id);
            ((GridPane)opponentsBox.getChildren().get(0)).add(playerView, id, 0);
            id++;
            opponentsBox.getChildren().add(playerView);
        }

        else if(arg instanceof GameResetEvent) {
            opponentsBox.getChildren().clear();
        }

        else if (arg instanceof DeckChangedEvent) {
            if(first) {
                bottomBox.setVisible(true);
                dealerFunc.setVisible(true);
                first = false;
            }
            dealerBox.getChildren().clear();
        }

        else if(arg instanceof PotChangedEvent){
            Integer newPot = PotChangedEvent.getInstance().getPayLoad();
            ((Text)dealerFunc.getChildren().get(1)).setText("Pot: " + newPot);
        }

        else if(arg instanceof HandTerminatedEvent){
            dealerFunc.getChildren().get(0).setDisable(false);
        }

        else if(arg instanceof InvisibleHandButtonEvent){
            dealerFunc.getChildren().get(0).setDisable(true);
        }
    }
}
