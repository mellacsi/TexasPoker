package ch.supsi.texas;

import ch.supsi.texas.commands.ActionMediator;
import ch.supsi.texas.controller.MenuBarController;
import ch.supsi.texas.view.Console;
import ch.supsi.texas.view.GameTableView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GameModel gameModel = new GameModel();
        GameMediator gameMediator = new GameMediator(gameModel);

        GameTableView gameTableView = new GameTableView(gameMediator.getGameModel());
        MenuBarController menuBarController = new  MenuBarController(gameTableView.getMenuBar(), gameMediator);

        gameModel.addObserver(gameTableView.getConsole());
        ActionMediator.getInstance().setGameModel(gameModel);

        Scene scene = new Scene(gameTableView,1400, 800);
        primaryStage.setTitle("texas5 - Gattini... Gattini Everywhere");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
