package com.sleepamos.game.gui.screen;

import com.jme3.math.ColorRGBA;
import com.simsilica.lemur.Container;
import com.sleepamos.game.asset.Assets;
import com.sleepamos.game.gui.element.BetterLabel;
import com.sleepamos.game.util.AssetsUtil;

public class CreditsScreen extends EscapableScreen {
    @Override
    protected void initialize() {
        this.bigBackgroundMakerMethodThatWeDidntAddAtTheLastMinute();
        Container window = this.createAndAttachContainer();

        BetterLabel label = new BetterLabel("""
                 Made by:
                 Addison Chan
                 Soham Dev
                 Ayushi Mehrotra
                 Stanley Wang
                                \s
                 APCSA Final Project 2023-24
                                \s
                                \s
                 Built using:
                 JMonkeyEngine, our base game engine.
                 Lemur, a GUI Library for JME.
                 Objenesis, to support our custom serialization system.
                 Lombok, to bypass Java's issues with declaring IOExceptions in method declarations.
                 Croissant One font, to help with the game's look and feel.
                 JUnit5, for testing our internal components.
                \s""");
        label.setPreferredSize(label.getPreferredSize().mult(1.3f));
        label.setBackground(AssetsUtil.asQBC(Assets.BUTTON_BG_TEXTURE));
        label.setColor(ColorRGBA.White);
        label.getTextComponent().setOffset(50f, -20f, 0);
        window.addChild(label);
    }
}
