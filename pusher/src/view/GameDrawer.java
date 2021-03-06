/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Visitor;
import cantstop.Consts;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.Core;
import model.GameState;
import model.Player;
import model.PositionPawn;

/**
 *
 * @author GONTARD Benjamin
 */
public class GameDrawer implements Visitor {

    private final Canvas canvas;
    private final GraphicsContext gc;
    public Color playerColor[] = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};

    public GameDrawer(Canvas can) {
        this.canvas = can;
        this.gc = can.getGraphicsContext2D();
    }

    @Override
    public boolean visit(Core c) {
        gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        for (int i = 0; i < Consts.nbTrack; i++) {
            double xPos = Consts.startX + Consts.slackBetweenTracks * i;
            for (int j = 0; j < Consts.tabTrackLength[i]; j++) {
                double yPos = Consts.startY + Consts.slackWithinTracks * j;
                gc.strokeOval(xPos, yPos, Consts.tileCircleSize, Consts.tileCircleSize);
            }
            gc.strokeText(new String("" + (i + Consts.slackHashMap)), Consts.startX + Consts.slackBetweenTracks * i, Consts.startY + Consts.slackWithinTracks * Consts.tabTrackLength[i]);
        }
        return false;
    }

    @Override
    public boolean visit(GameState g) {

        return false;
    }

    @Override
    public boolean visit(Player p) {
        for (int i = 0; i < Consts.nbTrack; i++) {
            double xPos = Consts.startX + Consts.slackBetweenTracks * i;
            for (int j = 0; j < Consts.tabTrackLength[i]; j++) {
                double yPos = Consts.startY + Consts.slackWithinTracks * j;
                if (p.getTracksConquered().contains(i + Consts.slackHashMap)) {
                    gc.setFill(playerColor[p.getNumber()]);
                    gc.fillOval(xPos, yPos, Consts.tileCircleSize, Consts.tileCircleSize);

                } else if (p.getHolds().get(i) == j) {

                    gc.setStroke(playerColor[p.getNumber()]);
                    gc.strokeOval(xPos + (p.getNumber() * 2), yPos + (p.getNumber() * 2), Consts.tileCircleSize - (p.getNumber() * 4), Consts.tileCircleSize - (p.getNumber() * 4));

                }
            }
            gc.setFill(Color.TRANSPARENT);
        }
        gc.setStroke(Color.BLACK);
        return false;
    }

    @Override
    public boolean visit(PositionPawn p) {
        gc.setFill(Color.BLACK);
        gc.fillOval(p.getxPos(), p.getyPos(), Consts.pawnSize, Consts.pawnSize);
        gc.setFill(Color.TRANSPARENT);
        return false;
    }

}
