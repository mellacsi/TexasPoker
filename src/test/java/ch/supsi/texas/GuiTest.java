package ch.supsi.texas;

import ch.supsi.texas.events.UtilTestEvents;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;

public class GuiTest extends ApplicationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuiTest.class);
    private static final int ACTIONS_INTERVAL = 0;
    private int stepNo;

    @Override
    public void start(Stage stage) throws Exception {
        //REMOVE SINGLETON EVENTS
        UtilTestEvents.clearEventSingleton();

        Main main = new Main();
        main.start(stage);
    }


    @Test
    public void walkThrough() {
        componentsWalkThrough01();
        menuBarWalkThrough01();
        newGameDialogWalkThrough01();
       // exit();
    }

    private void componentsWalkThrough01() {
        step("Components", () -> {
            verifyThat("#files", isVisible());
            verifyThat("#console", isVisible());
        });
    }

    private void exit() {
        step("File menu", () -> {
            clickOn("#files");
            sleep(ACTIONS_INTERVAL);
            clickOn("#exit");
        });
    }

    private void menuBarWalkThrough01() {
        step("Exit from menu", () -> {
            sleep(ACTIONS_INTERVAL);
            clickOn("#files");
            verifyThat("#newGame", isVisible());
            verifyThat("#exit", isVisible());
        });
    }

    private void newGameDialogWalkThrough01() {
        step("New game dialog", () -> {
            sleep(ACTIONS_INTERVAL);
            verifyThat("#newGame", isVisible());
            clickOn("#newGame");

            sleep(ACTIONS_INTERVAL);
            type(KeyCode.DIGIT0);
            type(KeyCode.ENTER);

            sleep(ACTIONS_INTERVAL);
            clickOn("#files");
            clickOn("#newGame");

            sleep(ACTIONS_INTERVAL);
            type(KeyCode.DIGIT7);
            type(KeyCode.ENTER);


            sleep(ACTIONS_INTERVAL);
            clickOn("#files");
            clickOn("#newGame");

            sleep(ACTIONS_INTERVAL);
            type(KeyCode.D);
            type(KeyCode.ENTER);

            sleep(ACTIONS_INTERVAL);
            clickOn("#files");
            clickOn("#newGame");

            sleep(ACTIONS_INTERVAL);
            type(KeyCode.BACK_SPACE);
            type(KeyCode.ENTER);

            sleep(ACTIONS_INTERVAL);
            clickOn("#files");
            clickOn("#newGame");

            sleep(ACTIONS_INTERVAL);
            type(KeyCode.DIGIT4);
            type(KeyCode.ENTER);


            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 4; i++) {

                    if(i == 0)
                        clickOn("#checkButton" + i);
                    else
                        clickOn("#callButton" + i);

                }
            }
        });
    }

    private void step(final String step, final Runnable runnable) {
        ++stepNo;
        LOGGER.info("STEP {}: {}", stepNo, step);
        runnable.run();
        LOGGER.info("STEP {}: End", stepNo);
    }

}
