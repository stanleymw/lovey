package com.sleepamos.game.gui.screen;

public abstract class EscapableScreen extends Screen {
    protected EscapableScreen() {
        super(true);
    }

    @Override
    public void onEscape() {}
}
