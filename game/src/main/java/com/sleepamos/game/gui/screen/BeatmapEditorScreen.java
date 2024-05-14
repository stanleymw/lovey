package com.sleepamos.game.gui.screen;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.VAlignment;
import com.sleepamos.game.Lovey;
import com.sleepamos.game.beatmap.Beatmap;
import com.sleepamos.game.util.serializer.LoveySerializer;

public class BeatmapEditorScreen extends Screen {
    private Beatmap beatmap = new Beatmap();

    @Override
    protected void initialize() {
        Container leftUI = this.createAndAttachContainer();

        leftUI.addChild(this.button("Quit").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> Lovey.getInstance().getScreenHandler().hideLastShownScreen()));

        Container rightUI = this.createAndAttachContainer();
        rightUI.setLocalTranslation(this.getScreenWidth() - 135, this.getScreenHeight(), 0);

        rightUI.addChild(this.button("Load").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).toOtherScreen(new FolderSelectorScreen((selected) -> {
            Lovey.getInstance().getScreenHandler().hideLastShownScreen(); // remove the folder selector screen, kicking us back to the beatmap editor.
            beatmap = LoveySerializer.deserialize(selected.resolve("beatmap.lovey").toFile(), Beatmap.class);
        })));

        rightUI.addChild(this.button("Save").withHAlign(HAlignment.Center).withVAlign(VAlignment.Center).withCommand(source -> LoveySerializer.serialize(beatmap.getName() + ".lovey", beatmap)));
    }
}
