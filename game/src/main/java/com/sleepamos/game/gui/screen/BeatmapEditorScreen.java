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
    Container bg;
    private Beatmap beatmap = new Beatmap();
    private TrackedAudioNode audioNode = new TrackedAudioNode();
    private boolean isPlaying = false;
    private Slider timeSlider;
    private Path currentPath;

    private void onBeatmapLoad(Path selected) {
        try {
            currentPath = selected.resolve("beatmap.lovey");
            beatmap = LoveySerializer.deserialize(currentPath.toFile(), Beatmap.class); // load the beatmap
            if (beatmap == null) {
                beatmap = new Beatmap();
            } else {
                Vector3f scale = bg.getWorldScale();
                Vector3f dims = bg.getSize().mult(scale);

//                beatmap.getSpawner().getTargetsToSpawn().forEach(t -> {
//                    if (t.interactable() instanceof Shootable shootable) {
//                        double x = shootable.getAngleX();
//                        double y = shootable.getAngleZ();
//
//                        final float xRel = ((float) (x * dims.x / FastMath.PI) + dims.x / 2) / scale.x;
//                        final float yRel = -(dims.y - ((float) (y * dims.y / FastMath.HALF_PI))) / scale.y; // this is some good code once again
//
//                        System.out.println("x/yrel: " + xRel + " " + yRel);
//                        // now we re-add them in as a debug mode
//                        Sphere s = new Sphere(10, 10, 5);
//                        Geometry g = new Geometry("s", s);
//                        Material mat = new Material(Lovey.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
//                        g.setMaterial(mat);
//                        mat.setColor("Color", ColorRGBA.fromRGBA255(0, 255, 0, 255));
//                        bg.attachChild(g);
//                        g.setLocalTranslation(xRel, yRel, 0);
//                        System.out.println("at: " + g.getWorldTranslation());
//                    }
//                });
            }
            Path audioPath = selected.resolve("audio.wav").toAbsolutePath();
            if (!FileUtil.exists(audioPath)) {
                throw new NonFatalException("No audio file found for the beatmap");
            }

            audioNode = Audio.load(audioPath);
            audioNode.setCallback(() -> timeSlider.getModel().setPercent(audioNode.getPlaybackTime() / audioNode.getAudioData().getDuration()));
        } catch (Exception e) {
            throw new NonFatalException("An error has occured while loading the beatmap", e);
        }
    }

    @Override
    protected void initialize() {
        Container leftUI = this.createAndAttachContainer();

        leftUI.addChild(this.button("Quit").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> {
            this.audioNode.stop();
            Lovey.getInstance().getScreenHandler().hideLastShownScreen();
        }));

        Container rightUI = this.createAndAttachContainer();
        rightUI.setLocalTranslation(this.getScreenWidth() - 135, this.getScreenHeight(), 0);

        rightUI.addChild(this.button("Load Beatmap").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).toOtherScreen(() -> new FolderSelectorScreen((selected) -> {
            onBeatmapLoad(selected);
            Lovey.getInstance().getScreenHandler().hideLastShownScreen(); // remove the folder selector screen, kicking us back to the beatmap editor.
        })));

        rightUI.addChild(this.button("Save").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> {
            beatmap.getSpawner().getTargetsToSpawn().sort((a, b) -> (int) ((a.hitTime() - a.reactionTime()) - (b.hitTime() - b.reactionTime())));

            if (currentPath != null) {
                LoveySerializer.serialize(currentPath, beatmap);
            } else {
                System.out.println("current path null, serializing to default folder for safety.");
                LoveySerializer.serialize("beatmap.lovey", beatmap);
            }
        }));
        rightUI.addChild(this.button("New Beatmap").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).toOtherScreen(() -> new BeatmapCreationScreen((selected) -> {
            try {
                if (!selected.resolve("beatmap.lovey").toFile().createNewFile()) {
                    throw new NonFatalException("File already exists");
                }
            } catch (Exception e) {
                throw new NonFatalException("Error while creating beatmap file", e);
            }
            onBeatmapLoad(selected);
            Lovey.getInstance().getScreenHandler().hideLastShownScreen(); // remove the folder selector screen, kicking us back to the beatmap editor.
        })));

        bg = this.createAndAttachContainer();
        bg.setBackground(new QuadBackgroundComponent(ColorRGBA.fromRGBA255(50, 50, 50, 2)));
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
            if (isPlaying) {
                audioNode.pause();
            } else {
                audioNode.play();
            }
            isPlaying = !isPlaying;
        }));

        bg.addMouseListener(new MouseListener() {
            @Override
            public void mouseButtonEvent(MouseButtonEvent event, Spatial target, Spatial capture) {
                if (event.getButtonIndex() == MouseInput.BUTTON_LEFT) {
                    if (event.isReleased()) {
                        Vector3f offsets = bg.getLocalTranslation();
                        Vector3f scale = bg.getWorldScale();
                        Vector3f dims = bg.getSize().mult(scale);

                        // xRel and yRel originate from the bottom-center and are relative to the beatmap creation area
                        final float xRel = (event.getX() - offsets.x) - dims.x / 2, yRel = dims.y - (offsets.y - event.getY());

                        Sphere s = new Sphere(10, 10, 5);
                        Geometry g = new Geometry("s", s);
                        Material mat = new Material(Lovey.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
                        g.setMaterial(mat);
                        mat.setColor("Color", ColorRGBA.fromRGBA255(0, 255, 0, 255));
                        bg.attachChild(g);
                        g.setLocalTranslation((event.getX() - offsets.x) / scale.x, (event.getY() - offsets.y) / scale.y, 0);

                        System.out.println("click at: " + xRel + " " + yRel);

                        // we now need to convert these coordinates into angles

                        // since the x-coordinate all the way to the left is negative, it can be used to represent -pi radians,
                        // and similarly for the positive value all the way to the right.
                        // therefore, the angle can be found as theta = pi * xRel / dims.x
                        final float xAngleRad = FastMath.PI * xRel / dims.x;

                        // the y-angle can similarly be found, but with pi / 2 instead.
                        final float yAngleRad = FastMath.HALF_PI * yRel / dims.y;

                        System.out.println("storing at: " + xAngleRad + " " + yAngleRad);
                        beatmap.getSpawner().getTargetsToSpawn().add(new Spawn(xAngleRad, yAngleRad, audioNode.getPlaybackTime(), 2));

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
