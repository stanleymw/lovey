package com.sleepamos.desktopmodule;

import com.jme3.app.StatsAppState;
import com.jme3.audio.AudioListenerState;
import com.sleepamos.game.Lovey;
import com.jme3.system.AppSettings;
import com.sleepamos.game.appstates.InGameAppState;
import com.sleepamos.game.appstates.ScreenAppState;

/**
 * Used to launch a jme application in desktop environment
 *
 */
public class DesktopLauncher {
    public static void main(String[] args) {
        final Lovey game = new Lovey(new ScreenAppState(), new StatsAppState(), new AudioListenerState(), new InGameAppState());

        final AppSettings appSettings = new AppSettings(true);

        appSettings.setResolution(1080, 720);
        appSettings.setGammaCorrection(true);
        appSettings.setTitle("Lovey");


        game.setSettings(appSettings);
        game.start();
    }
}
