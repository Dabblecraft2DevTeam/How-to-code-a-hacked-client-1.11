package uk.co.hexeption.thx.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import uk.co.hexeption.thx.Thx;
import uk.co.hexeption.thx.event.EventManager;
import uk.co.hexeption.thx.event.EventTarget;
import uk.co.hexeption.thx.event.events.EventKeyboard;
import uk.co.hexeption.thx.event.events.EventRender2D;
import uk.co.hexeption.thx.module.Module;

/**
 * Created by Hexeption on 25/01/2017.
 */
public class Hud {

    public Hud() {

        EventManager.register(this);
    }

    @EventTarget
    public void onRender(EventRender2D event) {

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        Thx.INSTANCE.FONT_MANAGER.hud.drawString(Thx.INSTANCE.NAME + " Rel-" + Thx.INSTANCE.VERSION, 2, 5, -1);
        Thx.INSTANCE.TAB.render();

        renderArrayList(scaledResolution);

    }

    @EventTarget
    public void onKeyPress(EventKeyboard event) {

        Thx.INSTANCE.TAB.onKeyPressed(event.getKey());
    }

    private void renderArrayList(ScaledResolution scaledResolution) {

        int yCount = 5;
        int right = scaledResolution.getScaledWidth();

        for (Module module : Thx.INSTANCE.MODULE_MANAGER.getAllModules()) {
            if (module.getState() && module.getCatergoy() != Module.Catergoy.GUI) {
                Thx.INSTANCE.FONT_MANAGER.arraylist.drawString(module.getName(), right - Thx.INSTANCE.FONT_MANAGER.arraylist.getStringWidth(module.getName()), yCount, module.getCatergoy().color);
                yCount += 10;
            }
        }
    }


}
