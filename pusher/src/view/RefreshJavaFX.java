/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import cantstop.Consts;
import javafx.animation.AnimationTimer;
import model.Core;
import javafx.scene.canvas.Canvas;

/**
 *
 * @author GONTARD Benjamin
 */
public class RefreshJavaFX extends AnimationTimer {

    private final Core core;
    private final GameDrawer drawer;
    private final int time;

    public RefreshJavaFX(Core core, Canvas c) {
        this.core = core;
        this.drawer = new GameDrawer(c);
        time = 0;

    }

    @Override
    public void handle(long now) {
        core.updatePawnPosition();
        core.accept(drawer);
    }

}
