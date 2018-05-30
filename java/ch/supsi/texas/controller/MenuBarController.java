package ch.supsi.texas.controller;

import ch.supsi.texas.GameMediator;
import ch.supsi.texas.player.ConcretePlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class MenuBarController {
    private MenuBar menuBar;
    private GameMediator gameMediator;

    public MenuBarController(MenuBar menuBar, GameMediator gameMediatorParam) {
        if(menuBar == null)
            throw new NullPointerException();
        if(gameMediatorParam == null)
            throw  new NullPointerException();

        this.menuBar = menuBar;
        this.gameMediator = gameMediatorParam;

        MenuItem newGame = this.menuBar.getMenus().get(0).getItems().get(0);
        MenuItem exit = this.menuBar.getMenus().get(0).getItems().get(1);

        exit.setOnAction(actionEvent -> System.exit(0));
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //prompt asking how many players
                TextInputDialog dialog = new TextInputDialog("2");
                dialog.setTitle("New Game Options");
                dialog.setHeaderText("Select number of Players between 2 and 5");
                dialog.setContentText("Players:");

                Optional<String> numberOfPlayers = dialog.showAndWait();
                int receivedValue = 0;

                if(StringUtils.isNumeric(numberOfPlayers.get()) && numberOfPlayers.get().matches("[2-5]")){
                    receivedValue = Integer.parseInt(numberOfPlayers.get());
                    gameMediator.initializeGame(Integer.parseInt(numberOfPlayers.get()));
                    gameMediator.startMatch();
                }
            }
        });
    }
}
