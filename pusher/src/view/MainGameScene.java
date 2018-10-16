/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Core;

/**
 *
 * @author GONTARD Benjamin
 */
public class MainGameScene extends Scene {

    public Core core;
    public ArrayList<Text> scoreText;
    public RefreshJavaFX refreshor;

    public MainGameScene(Core core, Parent root) {
        super(root);
        this.core = core;
        initScene();
    }

    public MainGameScene(Core core, Parent root, double width, double height) {
        super(root, width, height);
        this.core = core;
        initScene();
    }
    

    public void initScene() {
        scoreText = new ArrayList<>();
        BorderPane root = (BorderPane) this.getRoot();
        Canvas canvas = new Canvas(330,330);
        root.setCenter(canvas);

        BorderPane scoreAndButtonPane = new BorderPane();
        VBox scoreBox = new VBox();
        VBox buttonBox = new VBox();
        scoreAndButtonPane.setTop(scoreBox);
        scoreAndButtonPane.setCenter(buttonBox);
        root.setRight(scoreAndButtonPane);

        for (int i = 0; i < this.core.getCurrentGameState().getNumberOfPlayers(); i++) {
            Text t = new Text("Score du joueur " + this.core.getCurrentGameState().getPlayers().get(i).getName() + " : " + this.core.getCurrentGameState().getPlayers().get(i).getTracksConquered().size());
            scoreBox.getChildren().add(t);
            scoreText.add(t);
        }

        this.core.addScoreLister(new Observer() {
            @Override
            public void handle() {
                updateScore();
            }
        });
        System.out.println(canvas.getWidth());
        refreshor = new RefreshJavaFX(core, canvas);
        refreshor.start();

    }

    public void updateScore() {
        for (int i = 0; i < this.core.getCurrentGameState().getNumberOfPlayers(); i++) {
            scoreText.get(i).setText("Score du joueur " + this.core.getCurrentGameState().getPlayers().get(i).getName() + " : " + this.core.getCurrentGameState().getPlayers().get(i).getTracksConquered().size());
        }
    }

}
