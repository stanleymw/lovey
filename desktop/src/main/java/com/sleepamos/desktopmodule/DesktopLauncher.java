package com.sleepamos.desktopmodule;

import com.sleepamos.game.Lovey;
import com.jme3.system.AppSettings;

/**
 * Used to launch a jme application in desktop environment
 *
 */
public class DesktopLauncher {
    public static void main(String[] args) {
        final Lovey game = new Lovey();

        final AppSettings appSettings = new AppSettings(true);

        appSettings.setResolution(1080, 720);
        appSettings.setGammaCorrection(true);
        appSettings.setTitle("Lovey");

        game.setSettings(appSettings);
        game.start();
    }
}
