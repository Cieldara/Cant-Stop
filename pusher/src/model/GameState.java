/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import view.GameDrawer;

/**
 *
 * @author GONTARD Benjamin
 */
public class GameState {

    private int numTurn;
    private int numberOfPlayers;
    private ArrayList<Player> players;
    private int currentPlayer;
    private HashSet trackForThisTurn;

    public GameState(int numberOfPlayers, ArrayList<String> playerNames) {
        this.numTurn = 0;
        this.numberOfPlayers = numberOfPlayers;
        this.players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println(playerNames.get(i));
            players.add(new Player(playerNames.get(i), i));
        }
        this.currentPlayer = 0;
        this.trackForThisTurn = new HashSet();

    }

    public HashSet getAllTracksConquered() {
        HashSet tracks = new HashSet();
        for (int i = 0; i < numberOfPlayers; i++) {
            Iterator it = players.get(i).getTracksConquered().iterator();
            while (it.hasNext()) {
                tracks.add(it.next());
            }
        }
        return tracks;
    }

    public boolean accept(GameDrawer drawer) {
        drawer.visit(this);
        for (int i = 0; i < this.numberOfPlayers; i++) {
            this.players.get(i).accept(drawer);
        }
        return false;
    }

    public int getNumTurn() {
        return numTurn;
    }

    public void setNumTurn(int numTurn) {
        this.numTurn = numTurn;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public HashSet getTrackForThisTurn() {
        return trackForThisTurn;
    }

    public void setTrackForThisTurn(HashSet trackForThisTurn) {
        this.trackForThisTurn = trackForThisTurn;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.numTurn;
        hash = 53 * hash + this.numberOfPlayers;
        hash = 53 * hash + Objects.hashCode(this.players);
        hash = 53 * hash + this.currentPlayer;
        hash = 53 * hash + Objects.hashCode(this.trackForThisTurn);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameState other = (GameState) obj;
        if (this.numTurn != other.numTurn) {
            return false;
        }
        if (this.numberOfPlayers != other.numberOfPlayers) {
            return false;
        }
        if (this.currentPlayer != other.currentPlayer) {
            return false;
        }
        if (!Objects.equals(this.players, other.players)) {
            return false;
        }
        if (!Objects.equals(this.trackForThisTurn, other.trackForThisTurn)) {
            return false;
        }
        return true;
    }

}
