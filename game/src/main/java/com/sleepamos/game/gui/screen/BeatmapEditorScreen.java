package com.sleepamos.game.gui.screen;

import com.jme3.audio.AudioNode;
import com.jme3.input.MouseInput;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.VAlignment;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.event.MouseListener;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.audio.Audio;
import com.sleepamos.game.beatmap.Beatmap;
import com.sleepamos.game.util.FileUtil;
import com.sleepamos.game.exceptions.NonFatalException;
import com.sleepamos.game.serializer.LoveySerializer;

import java.nio.file.Path;

public class BeatmapEditorScreen extends Screen {
    private Beatmap beatmap = new Beatmap();
    private AudioNode audioNode = new AudioNode();

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

        bg.addMouseListener(new MouseListener() {
            @Override
            public void mouseButtonEvent(MouseButtonEvent event, Spatial target, Spatial capture) {
                if(event.getButtonIndex() == MouseInput.BUTTON_LEFT) {
                    if(event.isReleased()) {
                        Vector3f offsets = bg.getLocalTranslation();
                        int xRel = event.getX() - (int) offsets.x, yRel = (int) offsets.y - event.getY();
                        System.out.println("x: " + xRel + ", y: " + yRel);

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
