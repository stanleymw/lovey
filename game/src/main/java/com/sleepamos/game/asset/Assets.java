package com.sleepamos.game.asset;

import com.jme3.texture.Texture;
import com.sleepamos.game.Lovey;

/**
 * Holds all the assets
 */
public final class Assets {
    // public static final Texture BUTTON_BG = Lovey.getInstance().getAssetManager().loadTexture("button_bg");

    /**
     * This class takes advantage of Java's lazy class loading in order to access the assetManager without needing it passed in.
     * Since this class isn't actually initialized until it's first accessed, we can get away with having {@link Lovey#simpleInitApp()}
     * run this method once it's ready, and then we can access whatever we need.
     */
    public static void initialize() {
    }
}
