package whiteboard.ui;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.TipHelper;
import whiteboard.WhiteboardMod;

import java.io.IOException;

public class Menu {
    private static int Y_BELOW_TOP = 40;
    private static Color[] colors = {
        new Color(1.00f, 0.16f, 0.16f, 1), //red
        new Color(1.00f, 1.00f, 0.00f, 1), //yellow
        new Color(0.05f, 0.42f, 1.00f, 1), //blue
        new Color(0.05f, 1.00f, 0.15f, 1), //green
        new Color(0.96f, 0.26f, 0.81f, 1), //pink
        new Color(1.00f, 0.65f, 0.22f, 1), //orange
        new Color(1.00f, 1.00f, 1.00f, 1), //white
        new Color(0.00f, 0.00f, 0.00f, 1), //black
    };
    private static int[] sizes = {1, 2, 4, 6};

    private float x;
    private float y;
    private int colorIndex = WhiteboardMod.config.getInt("color");
    public Color color;
    private int size = WhiteboardMod.config.getInt("size");
    private boolean erasing = false;

    private Button penTypeButton = new Button("whiteboard/pen.png") {
        public void onClick() {
            toggleErasing();
        }
    };
    private Button colorButton = new Button("whiteboard/color.png") {
        public void onClick() {
            try {nextColor();} catch(Exception e) {}
        }
    };
    private Button sizeButton = new Button("whiteboard/size"+String.valueOf(size)+".png") {
        public void onClick() {
            try {nextSize();} catch(Exception e) {}
        }
    };
    private Button clearButton = new Button("whiteboard/clear.png") {
        public void onClick() {
            clear();
        }
    };

    public Menu() {
        color = colors[colorIndex];
        WhiteboardMod.drawing.setColor(color);
        WhiteboardMod.drawing.size = sizes[size];
    }

    public void move(float newX, float newY) {
        x = newX - (float)ReflectionHacks.getPrivateStatic(TipHelper.class, "BOX_W") * 0.5f;
        y = newY - Y_BELOW_TOP;
        float btnX = x+28;
        float btnY = y-44;
        penTypeButton.move(btnX, btnY);
        colorButton.move(btnX+64, btnY);
        sizeButton.move(btnX+128, btnY);
        clearButton.move(btnX+196, btnY);
    }

    private void toggleErasing() {
        erasing = !erasing;
        WhiteboardMod.drawing.setColor(erasing ? new Color(0,0,0,0) : color);
        WhiteboardMod.drawing.size = sizes[size] + (erasing ? 8 : 0);
        penTypeButton.loadTexture(erasing ? "whiteboard/eraser.png" : "whiteboard/pen.png");
    }

    private void nextColor() throws IOException {
        colorIndex = ++colorIndex % colors.length;
        color = colors[colorIndex];
        WhiteboardMod.drawing.setColor(color);
        WhiteboardMod.config.setInt("color", colorIndex);
        WhiteboardMod.config.save();
    }

    private void nextSize() throws IOException {
        size = ++size % sizes.length;
        WhiteboardMod.drawing.size = sizes[size] + (erasing ? 8 : 0);
        sizeButton.loadTexture("whiteboard/size"+String.valueOf(size)+".png");
        WhiteboardMod.config.setInt("size", size);
        WhiteboardMod.config.save();
    }

    private void clear() {
        WhiteboardMod.drawing.clear();
    }

    public void renderTip(SpriteBatch sb) {
        if (WhiteboardMod.open) {
            ReflectionHacks.setPrivateStatic(TipHelper.class, "renderedTipThisFrame", false);
            TipHelper.renderGenericTip(x, y, "", "");
        }
    }

    public void renderButtons(SpriteBatch sb) {
        if (WhiteboardMod.open) {
            penTypeButton.render(sb);
            sb.setColor(color);
            colorButton.render(sb);
            sb.setColor(Color.WHITE);
            sizeButton.render(sb);
            clearButton.render(sb);
        }
    }
}