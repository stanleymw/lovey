package com.sleepamos.game.util;

import com.jme3.texture.Texture;
import com.simsilica.lemur.component.QuadBackgroundComponent;

public final class AssetsUtil {
    private AssetsUtil() {
        throw new UnsupportedOperationException("Utility Class");
    }

    public static QuadBackgroundComponent asQBC(Texture t) {
        QuadBackgroundComponent q = new QuadBackgroundComponent(t);
        q.setMargin(1f, 1f);
        return q;
    }
}
