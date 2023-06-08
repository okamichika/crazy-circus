package fr.iut.crazycircus.components;

import java.util.ArrayList;

public class Player {
    private String name;
    boolean canPlay;
    int score;

    public Player(String name){
        this.name = name;
        this.canPlay = true;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public boolean canPlay(){
        return canPlay;
    }

    public void setCanPlay(boolean values){
        this.canPlay = values;
    }

    public int getScore(){
        return score;
    }

    public void addScore(){
        this.score += 1;
    }

    public int asWin(){
        return score++;
    }
}
