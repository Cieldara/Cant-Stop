/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import cantstop.Consts;

/**
 *
 * @author GONTARD Benjamin
 */
public class PositionPawn {

    private int number;
    private int track;
    private int height;
    private double xPos;
    private double yPos;
    private double velocityX;
    private double velocityY;
    private double time;

    public PositionPawn(int number, double xPos, double yPos) {
        this.number = number;
        this.track = -1;
        this.height = -1;
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocityX = 0;
        this.velocityX = 0;
        this.time = Consts.animationNumberFrames + 1;
    }

    public boolean accept(Visitor drawer) {
        drawer.visit(this);
        return false;
    }

    public void updatePosition() {
        
  
        xPos += velocityX;
        yPos += velocityY;
        if(this.time < Consts.animationNumberFrames){
            time++;
        }
        if(this.time == Consts.animationNumberFrames){
            stop();
        }
    }

    public int getTrack() {
        return track;
    }

    public void stop() {
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public void setTrack(int track, int height) {
        this.track = track;
        double destX = Consts.initialPostionsPawns[this.number][0];
        double destY = Consts.initialPostionsPawns[this.number][1];
        if (track != -1) {
            destX = Consts.startX + (track * Consts.slackBetweenTracks);
            destY = Consts.startY + (height * Consts.slackWithinTracks);
        }
        this.velocityX = (destX - xPos) / Consts.animationNumberFrames;
        this.velocityY = (destY - yPos) / Consts.animationNumberFrames;
        this.time = 0;

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
