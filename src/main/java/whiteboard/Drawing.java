package whiteboard;

import basemod.interfaces.PreUpdateSubscriber;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class Drawing implements PreUpdateSubscriber {
    public Color color;
    public int size = 0;
    private Pixmap pixmap;
    private Texture texture;
    private Vector2 lastPos;

    public Drawing() {
        pixmap = new Pixmap(Settings.WIDTH, Settings.HEIGHT, Pixmap.Format.RGBA8888);
        texture = new Texture(pixmap);
    }

    public void setColor(Color newColor) {
        color = newColor;
		pixmap.setColor(color);
    }

	public void clear() {
		pixmap.setColor(new Color(0,0,0,0));
		pixmap.fill();
		pixmap.setColor(color);
        updateTexture();
	}

    private void draw(Vector2 dot) {
        pixmap.fillCircle((int)dot.x, (int)dot.y, size);
    }

    private void updateTexture() {
        texture = new Texture(pixmap);
    }

    public void receivePreUpdate() {
        if (InputHelper.justClickedRight)
            Pixmap.setBlending(Pixmap.Blending.None);
        if (WhiteboardMod.open && InputHelper.isMouseDown_R) {
            InputHelper.isMouseDown_R = false;
            InputHelper.justClickedRight = false;
            Vector2 pos = new Vector2(InputHelper.mX, Settings.HEIGHT - InputHelper.mY);
            if (lastPos == null) {
                draw(pos);
                updateTexture();
                texture = new Texture(pixmap);
            } else if (!pos.equals(lastPos)) {
                float step = size / (16.0f * pos.dst(lastPos));
                for (float a = 0; a < 1f; a += step)
                    draw(pos.lerp(lastPos, a));
                updateTexture();
            }
            lastPos = new Vector2(InputHelper.mX, Settings.HEIGHT - InputHelper.mY);
        } else if (InputHelper.justReleasedClickRight) {
            lastPos = null;
            Pixmap.setBlending(Pixmap.Blending.SourceOver);
        }
    }

    public void render(SpriteBatch sb) {
        if (WhiteboardMod.open) {
            sb.setColor(Color.WHITE);
            sb.draw(texture, 0, 0);
        }
    }
}