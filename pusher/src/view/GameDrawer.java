/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import cantstop.Consts;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.Core;
import model.GameState;
import model.Player;

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
                if (p.getHolds().get(i) > -1) {
                    double yPos = Consts.startY + Consts.slackWithinTracks * j;
                    gc.setStroke(playerColor[p.getNumber()]);
                    gc.strokeOval(xPos + (p.getNumber() * 2), yPos + (p.getNumber() * 2), Consts.tileCircleSize - (p.getNumber() * 4), Consts.tileCircleSize - (p.getNumber() * 4));

                }
            }
        }
        gc.setStroke(Color.BLACK);
        return false;
    }

}
