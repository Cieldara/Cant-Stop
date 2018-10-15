/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author GONTARD Benjamin
 */
public class PairWithValue {

    private int firstDice;
    private int secondDice;
    private int thirdDice;
    private int forthDice;
    private final int firstPairValue;
    private final int secondPairValue;

    public PairWithValue(int firstDice, int secondDice, int thirdDice, int forthDice) {
        this.firstDice = firstDice;
        this.secondDice = secondDice;
        this.thirdDice = thirdDice;
        this.forthDice = forthDice;
        this.firstPairValue = firstDice + secondDice;
        this.secondPairValue = thirdDice + forthDice;
    }

    public int getFirstDice() {
        return firstDice;
    }

    public void setFirstDice(int firstDice) {
        this.firstDice = firstDice;
    }

    public int getSecondDice() {
        return secondDice;
    }

    public void setSecondDice(int secondDice) {
        this.secondDice = secondDice;
    }

    public int getThirdDice() {
        return thirdDice;
    }

    public void setThirdDice(int thirdDice) {
        this.thirdDice = thirdDice;
    }

    public int getForthDice() {
        return forthDice;
    }

    public void setForthDice(int forthDice) {
        this.forthDice = forthDice;
    }

    public int getFirstPairValue() {
        return firstPairValue;
    }

    public int getSecondPairValue() {
        return secondPairValue;
    }

    @Override
    public String toString() {
        return "Pair 1 : " + firstDice + " " + secondDice + " : " + firstPairValue + "\nPair 2 : " + thirdDice + " " + forthDice + " : " + secondPairValue;
    }
    
    

}
