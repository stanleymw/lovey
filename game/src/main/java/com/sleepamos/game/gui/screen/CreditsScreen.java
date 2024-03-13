package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;

public class CreditsScreen extends Screen {
    @Override
    protected void initialize() {
        Container window = this.createAndAttachContainer();

        window.setLocalTranslation(300, 300, 0);
        window.scale(1.75f);

        window.addChild(new Label("""
                Made by:
                Addison Chan
                Soham Dev
                Ayushi Mehrotra
                Stanley Wang
                
                APCSA Final Project 2023-24
                
                
                Built with JMonkeyEngine and the Lemur GUI Library for JME.
                """));
    }
}
