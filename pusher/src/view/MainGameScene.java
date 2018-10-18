/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import cantstop.Consts;
import model.Observer;
import controller.ButtonController;
import controller.ContinueButtonController;
import controller.StopButtonController;
import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Core;

/**
 *
 * @author GONTARD Benjamin
 */
public class MainGameScene extends Scene {

    private Core core;
    private ArrayList<Text> scoreText;
    private VBox buttonBox;
    private RefreshJavaFX refreshor;

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
        Canvas canvas = new Canvas(Consts.canvasSize, Consts.canvasSize);
        root.setCenter(canvas);

        BorderPane scoreAndButtonPane = new BorderPane();
        VBox scoreBox = new VBox();
        this.buttonBox = new VBox();
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

        this.core.addDiceListener(new Observer() {
            @Override
            public void handle() {
                updateDice();
            }
        });

        this.core.addContinueListener(new Observer() {
            @Override
            public void handle() {
                updateChoice();
            }
        });

        refreshor = new RefreshJavaFX(core, canvas);
        refreshor.start();
        core.throwDices();

    }

    public void updateScore() {
        for (int i = 0; i < this.core.getCurrentGameState().getNumberOfPlayers(); i++) {
            scoreText.get(i).setText("Score of " + this.core.getCurrentGameState().getPlayers().get(i).getName() + " : " + this.core.getCurrentGameState().getPlayers().get(i).getTracksConquered().size());
        }
    }

    public void updateDice() {
        for (int i = 0; !this.buttonBox.getChildren().isEmpty();) {
            this.buttonBox.getChildren().remove(i);
        }
        ArrayList<ArrayList<ArrayList<Integer>>> possiblePlays = core.getPossibleMoves();
        for (int i = 0; i < possiblePlays.size(); i++) {
            VBox box = new VBox();
            box.getChildren().add(new Text(core.getDicePairs().get(i).toString()));
            ArrayList<ArrayList<Integer>> moveList = possiblePlays.get(i);
            if (moveList == null) {
                box.getChildren().add(new Text("No Possible move"));
            } else {
                for (int j = 0; j < moveList.size(); j++) {
                    ArrayList<Integer> list = moveList.get(j);
                    Button b = new Button("Move on" + list.toString());
                    b.setOnMousePressed(new ButtonController(this.core, list));
                    box.getChildren().add(b);
                }
            }

            this.buttonBox.getChildren().add(box);
        }

    }

    public void updateChoice() {
        for (int i = 0; !this.buttonBox.getChildren().isEmpty();) {
            this.buttonBox.getChildren().remove(i);
        }

        Button cont = new Button("Continue your turn");
        cont.setOnMousePressed(new ContinueButtonController(this.core));
        this.buttonBox.getChildren().add(cont);

        Button stop = new Button("Stop your turn");
        stop.setOnMousePressed(new StopButtonController(this.core));
        this.buttonBox.getChildren().add(stop);

    }
}
