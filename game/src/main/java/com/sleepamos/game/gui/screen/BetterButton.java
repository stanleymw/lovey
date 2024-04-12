package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Button;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.TextComponent;
import com.simsilica.lemur.style.ElementId;

import java.lang.reflect.Field;

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
        } catch(Exception ignored) {
            System.out.println("err: ");
            ignored.printStackTrace();
            throw new RuntimeException("failed to reflect to get the text component. ????");
        }
    }
}
