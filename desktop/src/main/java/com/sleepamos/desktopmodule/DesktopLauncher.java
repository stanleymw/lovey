package com.sleepamos.desktopmodule;

import com.jme3.system.AppSettings;
import com.sleepamos.game.Lovey;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Used to launch a jme application in desktop environment
 *
 */
public class DesktopLauncher {
    public static void main(String[] args) throws IOException {
        final Lovey game = new Lovey();

        final AppSettings appSettings = new AppSettings(true);

        appSettings.setResolution(1080, 720);
        appSettings.setGammaCorrection(true);
        appSettings.setTitle("Lovey");

        try(InputStream icon = DesktopLauncher.class.getResourceAsStream("/Textures/icon.png")) {
            if (icon != null) {
                appSettings.setIcons(new BufferedImage[]{ImageIO.read(icon)});
            }
        }

        game.setSettings(appSettings);
        game.start();
    }
}
