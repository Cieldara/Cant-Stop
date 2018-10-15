/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import cantstop.Consts;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author GONTARD Benjamin
 */
public class Player {

    private String name;
    private HashMap<Integer, Integer> positions;
    private HashMap<Integer, Integer> holds;
    private HashSet<Integer> tracksConquered;

    public Player(String name) {
        this.name = name;
        this.positions = new HashMap<>();
        this.holds = new HashMap<>();
        for (int i = 0; i < Consts.nbTrack; i++) {
            this.positions.put(3 + i, 0);
            this.holds.put(3 + i, 0);
        }
        this.tracksConquered = new HashSet<>();
    }

    public boolean hasWon() {
        return this.tracksConquered.size() == 3;
    }

    public void updatePosition(int track, int newPosition) {
        this.positions.put(track, newPosition);
    }

    public void updateHold(int track, int newHoldPosition) {
        this.positions.put(track, newHoldPosition);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, Integer> getPositions() {
        return positions;
    }

    public void setPositions(HashMap<Integer, Integer> positions) {
        this.positions = positions;
    }

    public HashMap<Integer, Integer> getHolds() {
        return holds;
    }

    public void setHolds(HashMap<Integer, Integer> holds) {
        this.holds = holds;
    }

    public HashSet<Integer> getTracksConquered() {
        return tracksConquered;
    }

    public void setTracksConquered(HashSet<Integer> tracksConquered) {
        this.tracksConquered = tracksConquered;
    }

}
