/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Core;

/**
 *
 * @author GONTARD Benjamin
 */
public class ButtonController implements EventHandler<MouseEvent> {

    private final Core core;
    private final ArrayList<Integer> tracksToMove;

    public ButtonController(Core core, ArrayList<Integer> tracksToMove) {
        this.core = core;
        this.tracksToMove = tracksToMove;
    }

    @Override
    public void handle(MouseEvent event) {
        if (this.core.getCurrentSuperviserState() == Core.Superviser_automata.WAITING_TO_CHOOSE_WAY) {
            this.core.updateCurrentPlayerPosition(tracksToMove);
        }

    }

}
