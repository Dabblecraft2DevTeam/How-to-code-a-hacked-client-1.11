package uk.co.hexeption.thx.tab;

import org.lwjgl.input.Keyboard;
import uk.co.hexeption.thx.Thx;
import uk.co.hexeption.thx.module.Module;
import uk.co.hexeption.thx.utils.GLUtils;

import java.awt.*;
import java.util.ArrayList;

public class TabGui {

    private ArrayList<String> categorys = new ArrayList<>();

    private int selectedMods, selectedTab;

    private int tab;

    private int tabHeight = 20;

    public TabGui() {

        Module.Catergoy[] array;
        int j = (array = Module.Catergoy.values()).length;

        for (int i = 0; i < j; i++) {
            Module.Catergoy catergoy = array[i];

            if (catergoy.name().equalsIgnoreCase("gui")) {
                continue;
            }

            this.categorys.add(catergoy.toString().substring(0, 1) + catergoy.toString().substring(1, catergoy.toString().length()).toLowerCase());
        }
    }

    public void render() {

        int count = 0;
        for (Module.Catergoy catergoy : Module.Catergoy.values()) {
            if (!catergoy.name().equalsIgnoreCase("gui")) {
                int y = tabHeight + (count * 15);

                GLUtils.glColor(new Color(47, 47, 47, 230).hashCode());
                GLUtils.drawRect(0, y, 70, y + 15);
                count++;
            }
        }

        int count1 = 0;
        for (Module.Catergoy catergoy : Module.Catergoy.values()) {
            if (catergoy != Module.Catergoy.GUI) {
                if (catergoy.name().equalsIgnoreCase(categorys.get(this.selectedTab))) {
                    //Selected
                    Thx.INSTANCE.FONT_MANAGER.arraylist.drawStringWithShadow("> " + catergoy.name(), 6, tabHeight + count1 * 15 + 6, -1);
                } else {
                    //Not Selected
                    Thx.INSTANCE.FONT_MANAGER.arraylist.drawStringWithShadow(catergoy.name(), 6, tabHeight + count1 * 15 + 6, -1);
                }
                count1++;
            }
        }

        if (tab == 1 || tab == 2) {
            int modCount = 0;

            for (Module module : getModsForCategorys()) {
                int color;
                if (module.getState()) {
                    color = module.getCatergoy().color;
                } else {
                    color = -1;
                }

                int y = tabHeight + (modCount * 15); // This should fix that bug
                GLUtils.glColor(new Color(47, 47, 47, 230).hashCode());
                GLUtils.drawRect(73, y, 100 + this.getLongestModuleWidth(), y + 15);
                Thx.INSTANCE.FONT_MANAGER.arraylist.drawCenteredStringWithShadow(!module.getName().equalsIgnoreCase(this.getModsForCategorys().get(this.selectedMods).getName()) ? module.getName() : "> " + module.getName(), Thx.INSTANCE.FONT_MANAGER.arraylist.getStringWidth(module.getName()) / 2 + 80, y + 8, color);
                modCount++;
            }
        }


    }

    public void onKeyPressed(int key) {

        if (key == Keyboard.KEY_UP) {
            this.up();
        }

        if (key == Keyboard.KEY_DOWN) {
            this.down();
        }
        if (key == Keyboard.KEY_LEFT) {
            this.left();
        }
        if (key == Keyboard.KEY_RIGHT) {
            this.right();
        }
        if (key == Keyboard.KEY_RETURN) {
            this.enter();
        }

    }

    private void enter() {

        if (tab == 1) {
            getModsForCategorys().get(this.selectedMods).toggle();
        }
    }

    private void right() {

        if (tab == 1) {
            this.enter();
        } else {
            if (tab == 0) {
                tab = 1;
                this.selectedMods = 0;
            }
        }
    }

    private void left() {

        if (tab == 1) {
            tab = 0;
        }
    }

    private void down() {

        if (tab == 0) {
            if (this.selectedTab >= this.categorys.size() - 1) {
                this.selectedTab = -1;
            }
            this.selectedTab += 1;
        } else if (tab == 1) {
            if (this.selectedMods >= getModsForCategorys().size() - 1) {
                this.selectedMods = -1;
            }

            this.selectedMods += 1;
        }
    }

    private void up() {

        if (tab == 0) {
            if (this.selectedTab <= 0) {
                this.selectedTab = this.categorys.size();
            }
            this.selectedTab -= 1;
        } else if (tab == 1) {
            if (this.selectedTab <= 0) {
                this.selectedMods = getModsForCategorys().size();
            }

            this.selectedMods -= 1;
        }
    }

    private ArrayList<Module> getModsForCategorys() {

        ArrayList<Module> modules = new ArrayList<>();

        for (Module module : Thx.INSTANCE.MODULE_MANAGER.getAllModules()) {
            if (module.getCatergoy() == Module.Catergoy.valueOf(this.categorys.get(this.selectedTab).toUpperCase())) {
                modules.add(module);
            }
        }

        return modules;
    }

    private int getLongestModuleWidth() {

        int longest = 0;
        for (Module module : getModsForCategorys()) {
            if (Thx.INSTANCE.FONT_MANAGER.arraylist.getStringWidth(module.getName()) + 5 > longest) {
                longest = Thx.INSTANCE.FONT_MANAGER.arraylist.getStringWidth(module.getName()) + 5;
            }
        }

        return longest;
    }
}
