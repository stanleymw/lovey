package com.sleepamos.game.gui.screen;

import com.jme3.input.MouseInput;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.event.MouseListener;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.audio.Audio;
import com.sleepamos.game.audio.TrackedAudioNode;
import com.sleepamos.game.beatmap.Beatmap;
import com.sleepamos.game.beatmap.Spawn;
import com.sleepamos.game.exceptions.NonFatalException;
import com.sleepamos.game.interactables.Shootable;
import com.sleepamos.game.serializer.LoveySerializer;
import com.sleepamos.game.util.FileUtil;

import java.nio.file.Path;

/**
 * Handles beatmap creation.
 * <p>
 * The screen uses the song and based off of timestamps, renders which targets should intersect at that given point in time.
 * Note that the spawn time of the targets (and therefore their visibility) is not explicitly handled by this screen.
 * Responsibility for that is handled by the beatmap via a constant value per map for now due to the relative difficulty of implementing dynamic spawn locations and times.
 */
public class BeatmapEditorScreen extends Screen {
    private Beatmap beatmap = new Beatmap();
    private TrackedAudioNode audioNode = new TrackedAudioNode();
    private boolean isPlaying = false;

    private Slider timeSlider;

    @Override
    protected void initialize() {
        Container leftUI = this.createAndAttachContainer();

        leftUI.addChild(this.button("Quit").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> {
            this.audioNode.stop();
            Lovey.getInstance().getScreenHandler().hideLastShownScreen();
        }));

        Container rightUI = this.createAndAttachContainer();
        rightUI.setLocalTranslation(this.getScreenWidth() - 135, this.getScreenHeight(), 0);

        rightUI.addChild(this.button("Load Beatmap").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).toOtherScreen(new FolderSelectorScreen((selected) -> {
            try {
                beatmap = LoveySerializer.deserialize(selected.resolve("beatmap.lovey").toFile(), Beatmap.class); // load the beatmap
                if(beatmap == null) {
                    beatmap = new Beatmap();
                }
                Path audioPath = selected.resolve("audio.wav").toAbsolutePath();
                if (!FileUtil.exists(audioPath)) {
                    throw new NonFatalException("No audio file found for the beatmap");
                }

                audioNode = Audio.load(audioPath);
                System.out.println("setting to: " + audioNode.getPlaybackTime() / audioNode.getAudioData().getDuration());
                audioNode.setCallback(() -> timeSlider.getModel().setPercent(audioNode.getPlaybackTime() / audioNode.getAudioData().getDuration()));
                audioNode.updateLogicalState(0);
            } catch(Exception e) {
                throw new NonFatalException("An error has occured while loading the beatmap", e);
            }
            Lovey.getInstance().getScreenHandler().hideLastShownScreen(); // remove the folder selector screen, kicking us back to the beatmap editor.
        })));

        rightUI.addChild(this.button("Save").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> LoveySerializer.serialize(beatmap.getName() + ".lovey", beatmap)));
        rightUI.addChild(this.button("New Beatmap").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).toOtherScreen(new BeatmapCreationScreen((selected) -> {
            try {
                if(!selected.resolve("beatmap.lovey").toFile().createNewFile()) {
                    throw new NonFatalException("File already exists");
                }
                beatmap = new Beatmap();
            } catch(Exception e) {
                throw new NonFatalException("Error while creating beatmap file", e);
            }
            Lovey.getInstance().getScreenHandler().hideLastShownScreen(); // remove the folder selector screen, kicking us back to the beatmap editor.
        })));

        Container bg = this.createAndAttachContainer();
        bg.setBackground(new QuadBackgroundComponent(ColorRGBA.fromRGBA255(50, 50, 50,255)));
        bg.setPreferredSize(new Vector3f(this.getScreenWidth() * 0.56f, this.getScreenHeight() * 0.4f, 0));
        bg.setLocalTranslation(30, this.getScreenHeight() - 170, 0);

        timeSlider = new Slider(Axis.X);
        timeSlider.getIncrementButton().removeFromParent();
        timeSlider.getDecrementButton().removeFromParent();

        timeSlider.getRangePanel().setPreferredSize(new Vector3f(1700, 3, 0));
        timeSlider.getThumbButton().setText("");
        this.elements.attachChild(timeSlider);
        timeSlider.setLocalTranslation(110, 50, 0);

        Container bottomUi = this.createAndAttachContainer();
        bottomUi.setLocalTranslation(this.getScreenWidth() - 200, 100, 0);
        bottomUi.addChild(this.button("Play").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> {
            if(isPlaying) {
                audioNode.pause();
            } else {
                audioNode.play();
            }
            isPlaying = !isPlaying;
        }));

        bg.addMouseListener(new MouseListener() {
            @Override
            public void mouseButtonEvent(MouseButtonEvent event, Spatial target, Spatial capture) {
                if(event.getButtonIndex() == MouseInput.BUTTON_LEFT) {
                    if(event.isReleased()) {
                        Vector3f offsets = bg.getLocalTranslation();
                        Vector3f scale = bg.getWorldScale();
                        Vector3f dims = bg.getSize().mult(scale);

                        // xRel and yRel originate from the top-center and are relative to the beatmap creation area
                        final float xRel = (event.getX() - offsets.x) - dims.x / 2, yRel = offsets.y - event.getY();

                        Sphere s = new Sphere(10, 10, 5);
                        Geometry g = new Geometry("s", s);
                        Material mat = new Material(Lovey.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
                        g.setMaterial(mat);
                        mat.setColor("Color", ColorRGBA.fromRGBA255(0, 255, 0, 255));
                        bg.attachChild(g);
                        g.setLocalTranslation((event.getX() - offsets.x) / scale.x, (event.getY() - offsets.y) / scale.y, 0);

                        // we now need to convert these coordinates into angles

                        // since the x-coordinate all the way to the left is negative, it can be used to represent -pi radians,
                        // and similarly for the positive value all the way to the right.
                        // therefore, the angle can be found as theta = pi * xRel / dims.x
                        final float xAngleRad = FastMath.PI * xRel / dims.x;

                        // the y-angle can similarly be found, but with pi / 2 instead.
                        final float yAngleRad = FastMath.HALF_PI * yRel / dims.y;

                        beatmap.getSpawner().getTargetsToSpawn().add(new Spawn(new Shootable("", new Sphere(10, 10, 1), null, xAngleRad, yAngleRad, 10), audioNode.getPlaybackTime(), 2));

                        // make sure we don't accidentally continue to propagate anything (though it shouldn't happen)
                        event.setConsumed();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseMotionEvent event, Spatial target, Spatial capture) {

            }

            @Override
            public void mouseExited(MouseMotionEvent event, Spatial target, Spatial capture) {

            }

            @Override
            public void mouseMoved(MouseMotionEvent event, Spatial target, Spatial capture) {

            }
        });
    }
}
