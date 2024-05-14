package com.sleepamos.game.gui.screen;

import com.jme3.scene.Node;
import com.simsilica.lemur.*;
import com.simsilica.lemur.style.ElementId;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.gui.element.BetterButton;

public abstract class Screen {
    protected final Node elements;
    protected final boolean escapable;
    private Node parent;
    private boolean initialized = false;

    protected int getScreenWidth() {
        return Lovey.getInstance().getSettings().getWindowWidth();
    }

    protected int getScreenHeight() {
        return Lovey.getInstance().getSettings().getWindowHeight();
    }

    /**
     * Constructs this screen with the class name as the node name.
     */
    protected Screen(boolean escapable) {
        this.elements = new Node(this.getClass().getSimpleName());
        this.escapable = escapable;
    }

    protected Screen() {
        this(false);
    }

    /**
     * Initialize the {@link #elements} with this screen's sub-elements.
     */
    protected abstract void initialize();

    /**
     * Attach all parts of this Screen onto the given node.
     * @param base The base which this Screen's elements should be attached to.
     */
    public void attach(Node base) {
        if(!this.initialized) {
            this.initialize();
            this.initialized = true;
        }
        base.attachChild(this.elements);
        this.parent = base;
    }

    /**
     * Detaches all parts of this Screen from its parent.
     */
    public void detach() {
        this.parent.detachChild(this.elements);
        this.parent = null;
    }

    public boolean isEscapable() {
        return this.escapable;
    }

    /**
     * Creates and attaches a container to the internal elements node.
     * @return The created container, already attached. Attach all further nodes to this container.
     */
    protected Container createAndAttachContainer() {
        Container c = new Container();
        c.setBackground(null);

        // Make the container visible by default.
        c.setLocalTranslation(10, this.getScreenHeight(), 0);
        c.scale(1.75f);

        this.elements.attachChild(c);
        return c;
    }

    protected BetterButton button(String buttonName) {
        return new BetterButton(buttonName, new ElementId("button"), "glass");
    }

    protected BetterButton buttonWithAlign(String buttonName, HAlignment hAlignment) {
        BetterButton b = button(buttonName);
        b.setTextHAlignment(hAlignment);
        return b;
    }

    protected BetterButton buttonWithAlign(String buttonName, VAlignment vAlignment) {
        BetterButton b = button(buttonName);
        b.setTextVAlignment(vAlignment);
        return b;
    }

    protected BetterButton buttonWithAlign(String buttonName, HAlignment hAlignment, VAlignment vAlignment) {
        BetterButton b = button(buttonName);
        b.setTextHAlignment(hAlignment);
        b.setTextVAlignment(vAlignment);
        return b;
    }

    protected BetterButton buttonToOtherScreen(String buttonName, Screen next) {
        BetterButton b = button(buttonName);
        b.addClickCommands(source -> Lovey.getInstance().getScreenHandler().showScreen(next));
        return b;
    }

    protected BetterButton buttonWithCommand(String buttonName, Command<? super Button> run) {
        BetterButton b = button(buttonName);
        b.addClickCommands(run);
        return b;
    }

    protected <T extends Button> T buttonToOtherScreen(T b, Screen next) {
        b.addClickCommands(source -> Lovey.getInstance().getScreenHandler().showScreen(next));
        return b;
    }

    protected <T extends Button> T buttonWithCommand(T b, Command<? super Button> run) {
        b.addClickCommands(run);
        return b;
    }

    public void onEscape() {
        if(!this.isEscapable()) {
            throw new IllegalStateException("Non-escapable screen was escaped.");
        }
    }
}
