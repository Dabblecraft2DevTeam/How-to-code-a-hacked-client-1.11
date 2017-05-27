package uk.co.hexeption.thx;

import org.lwjgl.input.Keyboard;
import uk.co.hexeption.thx.event.EventManager;
import uk.co.hexeption.thx.event.EventTarget;
import uk.co.hexeption.thx.event.events.EventKeyboard;
import uk.co.hexeption.thx.module.Module;
import uk.co.hexeption.thx.module.ModuleManager;
import uk.co.hexeption.thx.tab.TabGui;
import uk.co.hexeption.thx.ttf.FontManager;
import uk.co.hexeption.thx.ui.Hud;

/**
 * Created by Hexeption on 07/01/2017.
 */
public class Thx {

    public static final Thx INSTANCE = new Thx();

    public final String NAME = "Thx";

    public final String VERSION = "Dev 1";

    public final String MC_VERSION = "1.11";

    public final ModuleManager MODULE_MANAGER = new ModuleManager();

    public final FontManager FONT_MANAGER = new FontManager();

    public final Hud HUD = new Hud();

    public final TabGui TAB = new TabGui();

    public void startClient() {

        EventManager.register(this);
        FONT_MANAGER.loadFonts();




        //Ends Client
        Runtime.getRuntime().addShutdownHook(new Thread(this::endClient));
    }

    public void endClient() {

    }

    @EventTarget
    private void eventKeyboard(EventKeyboard eventKeyboard) {

        for (Module module : Thx.INSTANCE.MODULE_MANAGER.getAllModules()) {
            if (Keyboard.getEventKey() == module.getBind()) {
                module.toggle();
            }
        }
    }

}
