package com.sleepamos.game.gui.element;

import com.simsilica.lemur.Button;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.TextComponent;
import com.simsilica.lemur.style.ElementId;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class BetterButton extends Button {
    public BetterButton(String s) {
        super(s);
    }

    public BetterButton(String s, String style) {
        super(s, style);
    }

    public BetterButton(String s, ElementId elementId) {
        super(s, elementId);
    }

    public BetterButton(String s, ElementId elementId, String style) {
        super(s, elementId, style);
    }

    public TextComponent getTextComponent() {
        try {
            Field f = Label.class.getDeclaredField("text");
            f.setAccessible(true);
            return (TextComponent) (f.get(this));
        } catch(Exception e) {
            System.out.println("err: ");
            e.printStackTrace();
            throw new RuntimeException("failed to reflect to get the text component. ????");
        }
    }

    public BetterButton withLocalTransform(float x, float y, float z) {
        this.setLocalTranslation(x, y, z);
        return this;
    }

    public BetterButton withLocalTransform(Void x, float y, float z) {
        this.getLocalTransform().getTranslation().setY(y).setZ(z);
        return this;
    }

    public BetterButton withLocalTransform(float x, Void y, float z) {
        this.getLocalTransform().getTranslation().setX(x).setZ(z);
        return this;
    }

    public BetterButton withLocalTransform(float x, float y, Void z) {
        this.getLocalTransform().getTranslation().setX(x).setY(y);
        return this;
    }

    public BetterButton withLocalTransform(Void x, Void y, float z) {
        this.getLocalTransform().getTranslation().setZ(z);
        return this;
    }

    public BetterButton withLocalTransform(float x, Void y, Void z) {
        this.getLocalTransform().getTranslation().setX(x);
        return this;
    }

    public BetterButton withLocalTransform(Void x, float y, Void z) {
        this.getLocalTransform().getTranslation().setY(y);
        return this;
    }
}
