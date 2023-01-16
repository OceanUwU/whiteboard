package whiteboard;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import whiteboard.ui.Menu;
import whiteboard.ui.PanelItem;

import java.io.IOException;
import java.util.Properties;

@SpireInitializer
public class WhiteboardMod implements PostInitializeSubscriber {
    public static String ID = "whiteboard";
    private static Properties defaults = new Properties(); 
    public static SpireConfig config;

    public static Drawing drawing;
    public static Menu menu;
    public static PanelItem panelItem;
    public static boolean open = false;    
    public static ModPanel settingsPanel = new ModPanel();

    public static void initialize() throws IOException {
        defaults.setProperty("color", "0");
        defaults.setProperty("size", "1");
        defaults.setProperty("middle", "false");
        config = new SpireConfig(ID, "config", defaults);
        BaseMod.subscribe(new WhiteboardMod());
    }

    public void receivePostInitialize() {
        panelItem = new PanelItem();
        BaseMod.addTopPanelItem(panelItem);
        drawing = new Drawing();
        BaseMod.subscribe(drawing);
        menu = new Menu();
        //BaseMod.subscribe(menu);
        settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabeledToggleButton(
            "Use middle click instead of right click to draw. Can also open whiteboard by right clicking anywhere.",
            350,
            800,
            Settings.CREAM_COLOR,
            FontHelper.charDescFont,
            WhiteboardMod.config.getBool("middle"),
            settingsPanel,
            (label) -> {},
            (button) -> {
                config.setBool("middle", button.enabled);
                try {
                    config.save();
                } catch (Exception e) {}
                drawing.middle = button.enabled;
            }
        ));
        BaseMod.registerModBadge(new Texture("whiteboard/badge.jpg"), "Whiteboard", "ocean", "draw over the spire", settingsPanel);
    }

    @SpirePatch(clz=MainMenuScreen.class, method=SpirePatch.CONSTRUCTOR, paramtypez={boolean.class})
    public static class MainMenuPatch {
        public static void Postfix() {
            open = false;
            drawing.savePixmap();
        }
    }
}