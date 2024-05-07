package com.sleepamos.game.game;

import com.sleepamos.game.interactables.Interactable;

public class GameState {
    private int points = 0;

    private boolean isInteracting = false;
    private Interactable interactingWith = null;

    public GameState() {

    }

    public boolean isInteracting() {
      return isInteracting;
    }

    public void setInteracting(boolean b) {
        this.isInteracting = b;
    }

    public Interactable getInteractingWith() {
        return interactingWith;
    }

    public void setInteractingWith(Interactable interactingWith) {
        this.interactingWith = interactingWith;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
