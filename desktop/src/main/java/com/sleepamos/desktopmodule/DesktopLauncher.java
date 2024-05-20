package com.sleepamos.desktopmodule;

import com.jme3.system.AppSettings;
import com.sleepamos.game.Lovey;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Used to launch a jme application in desktop environment
 */
public class DesktopLauncher {
    public static void main(String[] args) throws IOException {
        final Lovey game = new Lovey();

        final AppSettings appSettings = new AppSettings(true);

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();

        DisplayMode biggest = modes[0];
        for (DisplayMode mode : modes) {
            if (mode.getWidth() > biggest.getWidth()) {
                biggest = mode;
            } else if (mode.getWidth() == biggest.getWidth() && mode.getHeight() > biggest.getHeight()) {
                biggest = mode;
            } else if (mode.getWidth() == biggest.getWidth() && mode.getHeight() == biggest.getHeight() && mode.getRefreshRate() > biggest.getRefreshRate()) {
                biggest = mode;
            }
        }

        System.out.println("using mode " + biggest);

        appSettings.setResolution(biggest.getWidth(), biggest.getHeight());
        appSettings.setFrequency(biggest.getRefreshRate());
        appSettings.setBitsPerPixel(biggest.getBitDepth());
//        appSettings.setFullscreen(device.isFullScreenSupported());

        appSettings.setGammaCorrection(true);
        appSettings.setTitle("Lovey");
        appSettings.setVSync(false);

        try (InputStream icon = DesktopLauncher.class.getResourceAsStream("/Textures/icon.png")) {
            if (icon != null) {
                appSettings.setIcons(new BufferedImage[]{ImageIO.read(icon)});
            }
        }

        game.setSettings(appSettings);
        game.start();
    }
}
