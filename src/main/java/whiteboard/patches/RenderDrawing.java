package whiteboard.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import whiteboard.WhiteboardMod;

@SpirePatch(clz=TopPanel.class, method="render")
public class RenderDrawing {
    public static void Prefix(TopPanel __instance, SpriteBatch sb) {
        WhiteboardMod.drawing.render(sb);
    }
}