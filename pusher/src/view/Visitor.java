/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Core;
import model.GameState;
import model.Player;
import model.PositionPawn;

/**
 *
 * @author GONTARD Benjamin
 */
public interface Visitor {

    public abstract boolean visit(Core c);

    public abstract boolean visit(GameState g);

    public abstract boolean visit(Player p);

    public abstract boolean visit(PositionPawn p);
}
