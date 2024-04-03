package com.sleepamos.game.gui.screen;

import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.sleepamos.game.Lovey;

public abstract class Screen {
    protected final Node elements;
    protected final boolean escapable;
    private Node parent;

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

        this.initialize();
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
     * @return The created container, already attached.
     */
    protected Container createAndAttachContainer() {
        Container c = new Container();
        this.elements.attachChild(c);
        return c;
    }

    protected Button createButtonToOtherScreen(String buttonName, Screen next) {
        Button b = new Button(buttonName);
        b.addClickCommands(source -> Lovey.getInstance().getScreenHandler().showScreen(next));
        return b;
    }
}
