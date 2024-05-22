package com.sleepamos.game.gui.element;

import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.TextComponent;
import com.simsilica.lemur.style.ElementId;

import java.lang.reflect.Field;

public class BetterLabel extends Label {
    public BetterLabel(String s) {
        super(s);
    }

    public TextComponent getTextComponent() {
        try {
            Field f = Label.class.getDeclaredField("text");
            f.setAccessible(true);
            return (TextComponent) (f.get(this));
        } catch (Exception e) {
            // System.out.println("err: ");
            e.printStackTrace();
            throw new RuntimeException("failed to reflect to get the text component. ????");
        }
    }
}
