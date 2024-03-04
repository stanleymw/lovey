package com.sleepamos.game.gui.screen;

import com.jme3.scene.Node;

public abstract class Screen {
    protected final Node elements;
    private Node parent;

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
