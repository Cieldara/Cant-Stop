/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import cantstop.Consts;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import view.GameDrawer;
import view.Observer;

/**
 *
 * @author GONTARD Benjamin
 */
public class Core {

    public enum Superviser_automata {
        WAITING_TO_CHOOSE_WAY,
        WAITING_TO_CHOOSE_CONTINUE,
        READY_TO_CHANGE_PLAYER,
        ANIMATING,
        ANIMATING_END_TURN
    };

    private GameState currentGameState;
    private ArrayList<PairWithValue> dicePairs;
    private Superviser_automata currentSuperviserState;
    private Observer scoreListener;
    private Observer diceListener;
    private Observer continueListener;
    private ArrayList<PositionPawn> pawns;
    private ArrayList<ArrayList<ArrayList<Integer>>> possibleMoves;

    public Core(int numberOfPlayers, ArrayList<String> playerNames) {
        this.currentGameState = new GameState(numberOfPlayers, playerNames);
        this.dicePairs = new ArrayList<>();
        this.currentSuperviserState = Superviser_automata.WAITING_TO_CHOOSE_WAY;
        this.pawns = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pawns.add(new PositionPawn(i, Consts.initialPostionsPawns[i][0], Consts.initialPostionsPawns[i][1]));
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
    
    public void addContinueListener(Observer o){
        this.continueListener = o;
    }

    public void stopAnim() {
        for (PositionPawn p : pawns) {
            p.stop();
        }
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
        this.currentSuperviserState = Superviser_automata.WAITING_TO_CHOOSE_WAY;
        findPossibleMoves();
        diceListener.handle();

    }
    /* Bug friendly function I guess */
    public void findPossibleMoves() {
        boolean movePossible = false;
        HashSet tracksConquered = currentGameState.getAllTracksConquered();

        ArrayList<ArrayList<ArrayList<Integer>>> list = new ArrayList<>();
        for (int i = 0; i < dicePairs.size(); i++) {
            ArrayList<ArrayList<Integer>> values = null;
            Integer firstValue = dicePairs.get(i).getFirstPairValue();
            Integer secondValue = dicePairs.get(i).getSecondPairValue();
            //Si on peut avancer sur les deux
            if ((!tracksConquered.contains(firstValue) && !tracksConquered.contains(secondValue))
                    && ((currentGameState.getTrackForThisTurn().size() < 2)
                    || (currentGameState.getTrackForThisTurn().contains(firstValue) && currentGameState.getTrackForThisTurn().contains(secondValue))
                    || (currentGameState.getTrackForThisTurn().size() == 2 && (currentGameState.getTrackForThisTurn().contains(firstValue) || currentGameState.getTrackForThisTurn().contains(secondValue)))
                    || (firstValue.equals(secondValue) && (currentGameState.getTrackForThisTurn().contains(firstValue) || currentGameState.getTrackForThisTurn().size() < 3)))) {
                values = new ArrayList<>();
                movePossible = true;
                ArrayList<Integer> tracks = new ArrayList<>();
                tracks.add(firstValue);
                tracks.add(secondValue);
                values.add(tracks);
            } //Si on ne peut avancer que sur une
            else {

                if (currentGameState.getTrackForThisTurn().size() == 3 && currentGameState.getTrackForThisTurn().contains(firstValue) && !tracksConquered.contains(firstValue)) {
                    values = new ArrayList<>();
                    movePossible = true;
                    ArrayList<Integer> tracks = new ArrayList<>();
                    tracks.add(firstValue);
                    values.add(tracks);
                }
                if (currentGameState.getTrackForThisTurn().size() == 3 && currentGameState.getTrackForThisTurn().contains(secondValue) && !tracksConquered.contains(secondValue)) {
                    values = new ArrayList<>();
                    movePossible = true;
                    ArrayList<Integer> tracks = new ArrayList<>();
                    tracks.add(secondValue);
                    values.add(tracks);
                }
                if (currentGameState.getTrackForThisTurn().size() < 3) {
                    
                    if(!tracksConquered.contains(firstValue) || !tracksConquered.contains(secondValue)){
                        values = new ArrayList<>();
                        movePossible = true;
                    }

                    if (!tracksConquered.contains(firstValue)) {
                        ArrayList<Integer> tracks = new ArrayList<>();
                        movePossible = true;
                        tracks.add(firstValue);
                        values.add(tracks);
                    }
                    if (!tracksConquered.contains(secondValue)) {
                        ArrayList<Integer> tracks = new ArrayList<>();
                        tracks.add(secondValue);
                        values.add(tracks);
                    }
                }

            }
            list.add(values);
        }
        this.possibleMoves = list;
        if (!movePossible) {
            fall();
        }
    }

    public ArrayList<ArrayList<ArrayList<Integer>>> getPossibleMoves() {
        return this.possibleMoves;
    }

    public void updateCurrentPlayerPosition(ArrayList<Integer> tracks) {
        for (Integer t : tracks) {
            this.currentGameState.getTrackForThisTurn().add(t);
            //Pour correspondre à la HashMap
            Integer adaptedT = t - Consts.slackHashMap;
            Integer maxHeight = Math.min(currentGameState.getPlayers().get(currentGameState.getCurrentPlayer()).getPositions().get(adaptedT) + 1, Consts.tabTrackLength[adaptedT]);

            //Find the pawn associated with the tracks
            PositionPawn p = null;
            for (int i = 0; i < 3; i++) {
                if ((p == null && pawns.get(i).getTrack() == -1) || pawns.get(i).getTrack() == adaptedT) {
                    p = pawns.get(i);
                }
            }

            p.setTrack(adaptedT, maxHeight);

            currentGameState.getPlayers().get(currentGameState.getCurrentPlayer()).getPositions().put(adaptedT, maxHeight);
        }
        this.currentSuperviserState = Superviser_automata.ANIMATING;
    }

    public void fall() {
        //update Position of current player according to his holds
        Player currentPlayer = this.currentGameState.getPlayers().get(this.currentGameState.getCurrentPlayer());
        for (int i = 0; i < Consts.nbTrack; i++) {
            currentPlayer.getPositions().put(i, currentPlayer.getHolds().get(i));
        }
        for (PositionPawn p : pawns) {
            p.setTrack(-1, -1);
        }
        this.currentSuperviserState = Superviser_automata.ANIMATING_END_TURN;
    }
    
    public void continueTurn(){
        throwDices();   
    }
    
    public void notifyChoice(){
        this.currentSuperviserState = Superviser_automata.WAITING_TO_CHOOSE_CONTINUE;
        continueListener.handle();
    }

    public void stopTurn() {
        //update current player holds and check if the player has won one line
        Player currentPlayer = this.currentGameState.getPlayers().get(this.currentGameState.getCurrentPlayer());
        for (int i = 0; i < Consts.nbTrack; i++) {
            if (currentPlayer.getPositions().get(i) == Consts.tabTrackLength[i]) {
                currentPlayer.getTracksConquered().add(i+Consts.slackHashMap);
            } else {
                
                currentPlayer.getHolds().put(i, currentPlayer.getPositions().get(i));
            }
        }
        scoreListener.handle();
        for (PositionPawn p : pawns) {
            p.setTrack(-1, -1);
        }
        this.currentSuperviserState = Superviser_automata.ANIMATING_END_TURN;

    }

    public void updatePawnPosition() {
        for (PositionPawn p : pawns) {
            p.updatePosition();
        }
    }

    public void nextPlayer() {
        this.currentGameState.getTrackForThisTurn().clear();
        this.currentGameState.setCurrentPlayer((this.currentGameState.getCurrentPlayer() + 1) % this.currentGameState.getNumberOfPlayers());
        throwDices();
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
