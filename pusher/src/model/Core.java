/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import cantstop.Consts;
import java.util.ArrayList;
import java.util.Random;
import view.GameDrawer;
import view.Observer;

/**
 *
 * @author GONTARD Benjamin
 */
public class Core {

    public enum Superviser_automata {
        WAITING_TO_PLAY,
        READY_TO_CHANGE_PLAYER,
        ANIMATING
    };

    private GameState currentGameState;
    private ArrayList<PairWithValue> dicePairs;
    private Superviser_automata currentSuperviserState;
    private Observer scoreListener;
    private Observer diceListener;
    private ArrayList<PositionPawn> pawns;

    public Core(int numberOfPlayers, ArrayList<String> playerNames) {
        this.currentGameState = new GameState(numberOfPlayers, playerNames);
        this.dicePairs = new ArrayList<>();
        this.currentSuperviserState = Superviser_automata.WAITING_TO_PLAY;
        this.pawns = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pawns.add(new PositionPawn(Consts.initialPostionsPawns[i][0], Consts.initialPostionsPawns[i][1]));
        }
    }

    public boolean accept(GameDrawer drawer) {
        drawer.visit(this);
        this.currentGameState.accept(drawer);
        for (PositionPawn p : pawns) {
            p.accept(drawer);
        }
        return false;
    }

    public void addScoreLister(Observer o) {
        this.scoreListener = o;
    }

    public void addDiceListener(Observer o) {
        this.diceListener = o;
    }

    public void throwDices() {
        ArrayList<Integer> dices = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            dices.add(r.nextInt(6) + 1);
        }

        dicePairs.clear();
        dicePairs.add(new PairWithValue(dices.get(0), dices.get(1), dices.get(2), dices.get(3)));
        dicePairs.add(new PairWithValue(dices.get(0), dices.get(2), dices.get(1), dices.get(3)));
        dicePairs.add(new PairWithValue(dices.get(0), dices.get(3), dices.get(1), dices.get(2)));
        diceListener.handle();
    }

    public void updateCurrentPlayerPosition(ArrayList<Integer> tracks) {

    }

    public void fall() {
        //update Position of current player according to his holds
        Player currentPlayer = this.currentGameState.getPlayers().get(this.currentGameState.getCurrentPlayer());
        for (int i = 0; i < Consts.nbTrack; i++) {
            currentPlayer.getPositions().put(i, currentPlayer.getHolds().get(i));
        }

        this.currentSuperviserState = Superviser_automata.READY_TO_CHANGE_PLAYER;
    }

    public void stopTurn() {

        for (PositionPawn p : pawns) {
            p.setTrack(-1, -1);
        }

        //update current player holds
        Player currentPlayer = this.currentGameState.getPlayers().get(this.currentGameState.getCurrentPlayer());
        for (int i = 0; i < Consts.nbTrack; i++) {
            currentPlayer.getHolds().put(i, currentPlayer.getPositions().get(i));
        }
        scoreListener.handle();
        this.currentSuperviserState = Superviser_automata.READY_TO_CHANGE_PLAYER;
    }

    public void updatePawnPosition() {
        for (PositionPawn p : pawns) {
            p.updatePosition();
        }
    }

    public void nextPlayer() {
        this.currentGameState.getTrackForThisTurn().clear();
        this.currentGameState.setCurrentPlayer((this.currentGameState.getCurrentPlayer() + 1) % this.currentGameState.getNumberOfPlayers());
        this.currentSuperviserState = Superviser_automata.WAITING_TO_PLAY;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public ArrayList<PairWithValue> getDicePairs() {
        return dicePairs;
    }

    public Superviser_automata getCurrentSuperviserState() {
        return currentSuperviserState;
    }

    public void setCurrentSuperviserState(Superviser_automata currentSuperviserState) {
        this.currentSuperviserState = currentSuperviserState;
    }

}
