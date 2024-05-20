package com.sleepamos.game.game;

import com.sleepamos.game.interactables.Interactable;

public class GameState {
    private long points = 0;

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

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public void addPoints(long amountToAdd) {
        this.setPoints(this.getPoints() + amountToAdd);
    }
}
