package com.sleepamos.game;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.simsilica.lemur.*;
import com.simsilica.lemur.style.BaseStyles;
import com.sleepamos.game.gui.GuiHandler;


/**
 * The JMonkeyEngine game entry, you should only do initializations for your game here, game logic is handled by
 * Custom states {@link com.jme3.app.state.BaseAppState}, Custom controls {@link com.jme3.scene.control.AbstractControl}
 * and your custom entities implementations of the previous.
 *
 */
public class Lovey extends SimpleApplication {
    public Lovey(AppState... initialStates) {
        super(initialStates);
    }

    @Override
    public void simpleInitApp() {
        GuiHandler.initialize(this);

        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");

        Container window = new Container();
        guiNode.attachChild(window);
        // Put it somewhere that we will see it.
        // Note: Lemur GUI elements grow down from the upper left corner.
        window.setLocalTranslation(300, 300, 0);

        // Add some elements
        window.addChild(new Label("Hello, World."));
        Button clickMe = window.addChild(new Button("Click Me"));
        clickMe.addClickCommands(source -> System.out.println("The world is yours."));
    }

    @Override
    public void simpleUpdate(float tpf) {

    }
}
