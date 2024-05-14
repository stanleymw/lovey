package com.sleepamos.game.gui.element;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.TextComponent;
import com.simsilica.lemur.style.ElementId;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.asset.Assets;
import com.sleepamos.game.gui.screen.Screen;
import com.sleepamos.game.util.AssetsUtil;

import java.lang.reflect.Field;

public class BetterButton extends Button {
    public BetterButton(String s, ElementId elementId, String style) {
        super(s, elementId, style);
        this.setBorder(null);
        this.setPreferredSize(new Vector3f(80, 80 * 2481f / 6000f, 0));
        this.setBackground(AssetsUtil.asQBC(Assets.BUTTON_BG_TEXTURE));
        this.getTextComponent().setFont(Assets.FONT);
        this.getTextComponent().setOffset(4f, 0, 0);
        this.getTextComponent().setFontSize(10);
        this.setShadowColor(new ColorRGBA(255, 255, 255, 255));
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

    public BetterButton withVAlign(VAlignment vAlign) {
        this.setTextVAlignment(vAlign);
        return this;
    }

    public BetterButton withHAlign(HAlignment hAlign) {
        this.setTextHAlignment(hAlign);
        return this;
    }

    public BetterButton withCommand(Command<? super Button> cmd) {
        this.addClickCommands(cmd);
        return this;
    }

    public BetterButton toOtherScreen(Screen screen) {
        return this.withCommand(source -> Lovey.getInstance().getScreenHandler().showScreen(screen));
    }

    public BetterButton withTextureEnabled(boolean enable) {
        if(enable) {
            this.setBackground(AssetsUtil.asQBC(Assets.BUTTON_BG_TEXTURE));
        } else {
            this.setBackground(null);
        }
        return this;
    }

    public BetterButton withOffset(float x, float y, float z) {
        this.getTextComponent().setOffset(x, y, z);
        return this;
    }

    public BetterButton square() {
        this.setPreferredSize(new Vector3f(80, 80, 0));
        return this;
    }

    public BetterButton rect() {
        this.setPreferredSize(new Vector3f(80, 80 * 2481f / 6000f, 0));
        return this;
    }

    public BetterButton withFontSize(float size) {
        this.getTextComponent().setFontSize(size);
        return this;
    }
}
