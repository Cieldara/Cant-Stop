package cantstop;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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

        int nbPlayers = 2;
        String names[] = {"Ben", "Antoine"};
        Core core = new Core(nbPlayers, new ArrayList<>(Arrays.asList(names)));
        BorderPane root = new BorderPane();

        MainGameScene scene = new MainGameScene(core, root, 500, 500);

        primaryStage.setTitle("Hello World!");
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