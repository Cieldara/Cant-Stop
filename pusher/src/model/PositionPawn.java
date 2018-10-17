/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import cantstop.Consts;
import view.GameDrawer;

/**
 *
 * @author GONTARD Benjamin
 */
public class PositionPawn {

    private int track;
    private int height;
    private double xPos;
    private double yPos;
    private double velocityX;
    private double velocityY;

    public PositionPawn(double xPos, double yPos) {
        this.track = -1;
        this.height = -1;
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocityX = 0;
        this.velocityX = 0;
    }

    public boolean accept(GameDrawer drawer) {
        drawer.visit(this);
        return false;
    }

    public void updatePosition() {
        xPos += velocityX;
        yPos += velocityY;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track, int height) {
        this.track = track;
        double destX = Consts.startX + (track * Consts.slackBetweenTracks);
        double destY = Consts.startY + (height * Consts.slackWithinTracks);
        this.velocityX = destX / Consts.animationNumberFrames;
        this.velocityY = destY / Consts.animationNumberFrames;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocity) {
        this.velocityX = velocity;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocity) {
        this.velocityY = velocity;
    }

}