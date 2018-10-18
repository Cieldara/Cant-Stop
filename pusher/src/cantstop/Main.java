package cantstop;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Core;
import view.MainGameScene;

/**
 *
 * @author GONTARD Benjamin
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        int nbPlayers = 4;
        String names[] = {"Ben", "Antoine", "Victor", "Violetta"};
        Core core = new Core(nbPlayers, new ArrayList<>(Arrays.asList(names)));
        BorderPane root = new BorderPane();

        MainGameScene scene = new MainGameScene(core, root, 1000, 1000);

        primaryStage.setTitle("Can't Stop !");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
