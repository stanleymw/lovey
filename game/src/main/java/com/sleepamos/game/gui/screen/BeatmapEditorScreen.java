package com.sleepamos.game.gui.screen;

import com.jme3.audio.AudioNode;
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
import com.sleepamos.game.beatmap.Beatmap;
import com.sleepamos.game.exceptions.NonFatalException;
import com.sleepamos.game.serializer.LoveySerializer;
import com.sleepamos.game.util.FileUtil;

import java.nio.file.Path;

public class BeatmapEditorScreen extends Screen {
    private Beatmap beatmap = new Beatmap();
    private AudioNode audioNode = new AudioNode();

    @SuppressWarnings("unchecked")
    @Override
    protected void initialize() {
        Container leftUI = this.createAndAttachContainer();

        leftUI.addChild(this.button("Quit").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> Lovey.getInstance().getScreenHandler().hideLastShownScreen()));

        Container rightUI = this.createAndAttachContainer();
        rightUI.setLocalTranslation(this.getScreenWidth() - 135, this.getScreenHeight(), 0);

        rightUI.addChild(this.button("Load").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).toOtherScreen(new FolderSelectorScreen((selected) -> {
            try {
                beatmap = LoveySerializer.deserialize(selected.resolve("beatmap.lovey").toFile(), Beatmap.class); // load the beatmap
                Path audioPath = selected.resolve("audio.wav").toAbsolutePath();
                if (!FileUtil.exists(audioPath)) {
                    throw new NonFatalException("No audio file found for the beatmap");
                }
                audioNode = Audio.load(audioPath.toString());
            } catch(NonFatalException nfe) {
                throw new NonFatalException("An error has occured while loading the beatmap", nfe);
            }
            Lovey.getInstance().getScreenHandler().hideLastShownScreen(); // remove the folder selector screen, kicking us back to the beatmap editor.
        })));

        rightUI.addChild(this.button("Save").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> LoveySerializer.serialize(beatmap.getName() + ".lovey", beatmap)));

        Container bg = this.createAndAttachContainer();
        bg.setBackground(new QuadBackgroundComponent(ColorRGBA.fromRGBA255(50, 50, 50,255)));
        bg.setPreferredSize(new Vector3f(this.getScreenWidth() * 0.56f, this.getScreenHeight() * 0.4f, 0));
        bg.setLocalTranslation(30, this.getScreenHeight() - 170, 0);

        Slider timeSlider = new Slider(Axis.X);
        timeSlider.getIncrementButton().removeFromParent();
        timeSlider.getDecrementButton().removeFromParent();

        timeSlider.getRangePanel().setPreferredSize(new Vector3f(1700, 3, 0));
        timeSlider.getThumbButton().setText("");
        this.elements.attachChild(timeSlider);
        timeSlider.setLocalTranslation(110, 50, 0);

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

                        //System.out.println("box dims: " + dims);
                        //System.out.println("x: " + xRel + ", y: " + yRel);

                        Sphere s = new Sphere(10, 10, 5);
                        Geometry g = new Geometry("s", s);
                        Material mat = new Material(Lovey.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
                        g.setMaterial(mat);
                        mat.setColor("Color", ColorRGBA.fromRGBA255(0, 255, 0, 255));
                        bg.attachChild(g);
                        g.setLocalTranslation((event.getX() - offsets.x) / scale.x, (event.getY() - offsets.y) / scale.y, 0);
                        // System.out.println(g.getWorldTranslation());

                        // we now need to convert these coordinates into angles

                        // since the x-coordinate all the way to the left is negative, it can be used to represent -pi radians,
                        // and similarly for the positive value all the way to the right.
                        // therefore, the angle can be found as theta = pi * xRel / dims.x
                        final float xAngleRad = FastMath.PI * xRel / dims.x;

                        // the y-angle can similarly be found, but with pi / 2 instead.
                        final float yAngleRad = FastMath.HALF_PI * yRel / dims.y;

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
