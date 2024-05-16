package com.sleepamos.game.asset;

import com.jme3.font.BitmapFont;
import com.jme3.texture.Texture;
import com.sleepamos.game.Lovey;

/**
 * Holds all the assets
 */
public final class Assets {
    public static final Texture BUTTON_BG_TEXTURE = Lovey.getInstance().getAssetManager().loadTexture("Textures/button.png");
    public static final BitmapFont FONT = Lovey.getInstance().getAssetManager().loadFont("Interface/Fonts/uwu2.fnt");
    public static final BitmapFont DEFAULT_FONT = Lovey.getInstance().getAssetManager().loadFont("Interface/Fonts/Default.fnt");

    /**
     * This class takes advantage of Java's lazy class loading in order to access the assetManager without needing it passed in.
     * Since this class isn't actually initialized until it's first accessed, we can get away with having {@link Lovey#simpleInitApp()}
     * run this method once it's ready, and then we can access whatever we need.
     */
    public static void initialize() {
    }
}
