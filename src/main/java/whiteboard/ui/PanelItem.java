package whiteboard.ui;

import basemod.TopPanelItem;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.White;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import whiteboard.WhiteboardMod;

public class PanelItem extends TopPanelItem {
    private static final Texture IMG = new Texture("whiteboard/icon.png");
    public static final String ID = "whiteboard:WhiteboardPanelItem";

    public PanelItem() {
	    super(IMG, ID);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb, WhiteboardMod.menu.color);
    }

    @Override
    protected void onClick() {
        WhiteboardMod.open = !WhiteboardMod.open;
        if (WhiteboardMod.open)
            WhiteboardMod.menu.move(this.x + (hb_w / 2), this.y);
        else
            WhiteboardMod.menu.move(-1000, -1000);
    }
}