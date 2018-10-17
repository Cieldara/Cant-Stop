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

    private Core core;
    private ArrayList<Integer> tracksToMove;
    
    @Override
    public void handle(MouseEvent event) {
        core.updateCurrentPlayerPosition(tracksToMove);
    }
    
}
