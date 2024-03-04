package com.sleepamos.game.gui.screen;

import com.jme3.scene.Node;
import com.simsilica.lemur.Container;

public abstract class Screen {
    protected final Node elements;

    private Node parent;

    /**
     * Constructs this screen with the class name as the node name.
     */
    public Screen() {
        this.elements = new Node(this.getClass().getSimpleName());  // we can't just call this(this.getClass().getSimpleName()) because thanks java
        this.initialize();
    }

    /**
     * Constructs this screen with a specific name as the node name.
     * @param name The name to use.
     */
    public Screen(String name) {
        this.elements = new Node(name);
        this.initialize();
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

    /**
     * Creates and attaches a container to the internal elements node.
     * @return The created container, already attached.
     */
    protected Container createAndAttachContainer() {
        Container c = new Container();
        this.elements.attachChild(c);
        return c;
    }
}
