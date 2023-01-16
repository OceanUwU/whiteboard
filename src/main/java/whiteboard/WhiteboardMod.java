package whiteboard;

import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
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

    public static void initialize() throws IOException {
        defaults.setProperty("color", "0");
        defaults.setProperty("size", "1");
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
    }

    @SpirePatch(clz=MainMenuScreen.class, method=SpirePatch.CONSTRUCTOR, paramtypez={boolean.class})
    public static class MainMenuPatch {
        public static void Postfix() {
            open = false;
            drawing.savePixmap();
        }
    }
}