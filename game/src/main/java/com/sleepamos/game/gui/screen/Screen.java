package com.sleepamos.game.gui.screen;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.VAlignment;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.style.ElementId;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.asset.Assets;
import com.sleepamos.game.gui.element.BetterButton;
import com.sleepamos.game.util.AssetsUtil;

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
        BetterButton b = new BetterButton(buttonName, new ElementId("button"), "glass");
        b.setBorder(null);
        b.setPreferredSize(new Vector3f(80, 80 * 2481f / 6000f, 0));
        var q = AssetsUtil.asQBC(Assets.BUTTON_BG_TEXTURE);
        q.setMargin(1f, 1f);
        b.setBackground(q);
        b.getTextComponent().setFont(Assets.FONT);
        b.getTextComponent().setOffset(4f, 0, 0);
        b.getTextComponent().setFontSize(10);
        b.setShadowColor(new ColorRGBA(255, 255, 255, 255));
        return b;
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
}
