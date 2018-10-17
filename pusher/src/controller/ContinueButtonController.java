/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Core;

/**
 *
 * @author GONTARD Benjamin
 */
public class ContinueButtonController implements EventHandler<MouseEvent> {

    private final Core core;

    public ContinueButtonController(Core c) {
        this.core = c;
    }

    @Override
    public void handle(MouseEvent event) {
        core.continueTurn();
    }

}
