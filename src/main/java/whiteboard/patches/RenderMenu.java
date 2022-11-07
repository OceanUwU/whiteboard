package whiteboard.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.TipHelper;
import whiteboard.WhiteboardMod;

@SpirePatch(clz=TipHelper.class, method="render")
public class RenderMenu {
    public static void Prefix(SpriteBatch sb) {
        WhiteboardMod.menu.renderTip(sb);
    }

    public static void Postfix(SpriteBatch sb) {
        WhiteboardMod.menu.renderButtons(sb);
    }
}