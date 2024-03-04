package com.sleepamos.game.gui.screen;

import com.jme3.scene.Node;

public abstract class Screen {
    protected final Node elements;
    private Node parent;

    /**
     * Constructs this screen with the class name as the node name.
     */
    public Screen() {
        this.elements = new Node(this.getClass().getSimpleName());  // we can't just call this(this.getClass().getSimpleName()) because thanks java
    }

    /**
     * Constructs this screen with a specific name as the node name.
     * @param name The name to use.
     */
    public Screen(String name) {
        this.elements = new Node(name);
    }

    /**
     * Attach all parts of this Screen onto the given node.
     * @param base The base which this Screen's elements should be attached to.
     */
    public void attach(Node base) {
        base.attachChild(elements);
        this.parent = base;
    }

    /**
     * Detaches all parts of this Screen from the given node.
     * @param base The base which this Screen's elements were previously attached to.
     */
    public void detach(Node base) {
        if(this.parent != base) {
            throw new IllegalStateException("Attempted to detach the Screen from a Node it was not attached to.");
        }
        base.detachChild(elements);
        this.parent = null;
    }
}
