package com.sleepamos.game.gui;

import com.jme3.scene.Node;
import com.simsilica.lemur.GuiGlobals;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.gui.screen.MainMenuScreen;
import com.sleepamos.game.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

/**
 * This class handles the hierarchy of screens that are being shown to the user.
 * The screens are stored in a hierarchy so that going back will go back to the previously shown screen.
 */
public class ScreenHandler {
    private static ScreenHandler instance;

    /**
     * Get the instance of the ScreenHandler. Requires that {@link #initialize(Lovey)} has been called.
     * @return The instance of this object.
     */
    @Nullable
    public static ScreenHandler getInstance() {
        return instance;
    }

    private final Lovey app;

    /**
     * The hierarchy of screens, in the order they were added.
     * This list should always have at least 1 screen.
     */
    private final LinkedList<Screen> screenHierarchy;

    public static void initialize(Lovey app) {
        GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");

        instance = new ScreenHandler(app); // gaming
    }

    private ScreenHandler(Lovey app) {
        this.app = app;
        this.screenHierarchy = new LinkedList<>();

        this.showScreen(new MainMenuScreen());
    }

    /**
     * Shows a screen, hiding the currently shown screen in order to do so.
     * The screen should not be an instance of {@link MainMenuScreen}, and {@link #goToMainMenu()} should be used instead to return to the main menu.
     * @param screen The screen to show. Should not be an instance of {@link MainMenuScreen}.
     * @throws IllegalArgumentException If screen is an instance of {@link MainMenuScreen}
     */
    public void showScreen(Screen screen) {
        if(screen instanceof MainMenuScreen) {
            throw new IllegalArgumentException("Screen cannot be instance of MainMenuScreen, use ScreenHandler.goToMainMenu() instead");
        }
        this.screenHierarchy.getLast().detach(this.getGuiNode());
        screen.attach(this.getGuiNode());
        this.screenHierarchy.add(screen);
    }

    /**
     * Hides the last shown screen. Equivalent to hitting the "back" button.
     * <p>
     * There must always be one screen shown (the main menu); this method will do nothing if the main menu is the only
     * existing screen.
     *
     * @return Whether a screen was successfully removed. If the hierarchy was on the main menu when this method is called, returns false.
     */
    public boolean hideLastShownScene() {
        if(this.screenHierarchy.size() > 1) {
            this.screenHierarchy.getLast().detach(this.getGuiNode());
            this.screenHierarchy.removeLast();
            this.screenHierarchy.getLast().attach(this.getGuiNode());
            return true;
        }
        return false;
    }

    /**
     * Shows no screen. Useful for entering the game state.
     */
    public void hideAllScreens() {
        this.showScreen(Screens.NONE);
    }

    /**
     * Whether a screen is being shown (i.e. visible) to the user.
     * If no screens exist in the screen hierarchy (shouldn't happen?), returns false.
     * If the most recent screen is {@link Screens#NONE}, returns false.
     * @return Whether a screen is visible to the user.
     */
    public boolean isShowingAScreen() {
        return !this.screenHierarchy.isEmpty() && this.screenHierarchy.getLast() != Screens.NONE;
    }

    /**
     * The equivalent of resetting the hierarchy. Sets the screen back to the main menu.
     */
    public void goToMainMenu() {
        Screen main = this.screenHierarchy.getFirst();
        this.screenHierarchy.clear();
        this.screenHierarchy.add(main);
    }

    private Node getGuiNode() {
        return this.app.getGuiNode();
    }
}
