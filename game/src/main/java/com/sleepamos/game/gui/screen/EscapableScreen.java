package com.sleepamos.game.gui.screen;

public abstract class EscapableScreen extends Screen {
    protected EscapableScreen() {
        super(true);
    }

    protected void onEscape() {}
}
