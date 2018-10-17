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
    private int time;

    public RefreshJavaFX(Core core, Canvas c) {
        this.core = core;
        this.drawer = new GameDrawer(c);
        time = 0;

    }

    @Override
    public void handle(long now) {
        core.updatePawnPosition();
        core.accept(drawer);
        if (core.getCurrentSuperviserState() == Core.Superviser_automata.READY_TO_CHANGE_PLAYER) {
            core.nextPlayer();
        }
        if(core.getCurrentSuperviserState() == Core.Superviser_automata.ANIMATING){
            time++;
            if(time == Consts.animationNumberFrames){         
                time = 0;
                this.core.stopAnim();
                //Something to do here
                this.core.notifyChoice();
                
            }
        }
        if(core.getCurrentSuperviserState() == Core.Superviser_automata.ANIMATING_END_TURN){
            time++;
            if(time == Consts.animationNumberFrames){
                time = 0;
                this.core.stopAnim();
                this.core.nextPlayer();
            }
        }
    }
}
