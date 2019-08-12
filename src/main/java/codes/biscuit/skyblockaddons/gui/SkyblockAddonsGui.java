package codes.biscuit.skyblockaddons.gui;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonFeature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonToggle;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSolid;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import codes.biscuit.skyblockaddons.utils.Feature;
import codes.biscuit.skyblockaddons.utils.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.client.GuiIngameForge;

import java.awt.*;

public class SkyblockAddonsGui extends GuiScreen {

    public static final int BUTTON_MAX_WIDTH = 140;

    private SkyblockAddons main;
    private int page;

    private long timeOpened = System.currentTimeMillis();

    /**
     * The main gui, opened with /sba.
     */
    public SkyblockAddonsGui(SkyblockAddons main, int page) {
        this.main = main;
        this.page = page;
    }

    @Override
    public void initGui() {
        // Add the buttons for each page.
        if (page == 1) {
            addButton(1, Message.SETTING_ENCHANTS_AND_REFORGES, Feature.SHOW_ENCHANTMENTS_REFORGES, 1, EnumUtils.ButtonType.TOGGLE);
            addButton(1, Message.SETTING_SHOW_BACKPACK_PREVIEW, Feature.SHOW_BACKPACK_PREVIEW, 2, EnumUtils.ButtonType.TOGGLE);
            addButton(1, Message.SETTING_FULL_MINION, Feature.MINION_FULL_WARNING, 3, EnumUtils.ButtonType.TOGGLE);
            addButton(2, Message.SETTING_HIDE_SKELETON_HAT_BONES, Feature.HIDE_BONES, 1, EnumUtils.ButtonType.TOGGLE);
            addButton(2, Message.SETTING_FULL_INVENTORY_WARNING, Feature.FULL_INVENTORY_WARNING, 2, EnumUtils.ButtonType.TOGGLE);
            addButton(2, Message.SETTING_DISABLE_EMBER_ROD_ABILITY, Feature.DISABLE_EMBER_ROD, 3, EnumUtils.ButtonType.TOGGLE);
            addButton(3, Message.SETTING_AUCTION_HOUSE_PLAYERS, Feature.HIDE_AUCTION_HOUSE_PLAYERS, 1, EnumUtils.ButtonType.TOGGLE);
            addButton(3, Message.SETTING_IGNORE_ITEM_FRAME_CLICKS, Feature.IGNORE_ITEM_FRAME_CLICKS, 2, EnumUtils.ButtonType.TOGGLE);
            addButton(3, Message.SETTING_HIDE_FOOD_AND_ARMOR, Feature.HIDE_FOOD_ARMOR_BAR, 3, EnumUtils.ButtonType.TOGGLE);
            addButton(4, Message.SETTING_HIDE_HEALTH_BAR, Feature.HIDE_HEALTH_BAR, 1, EnumUtils.ButtonType.TOGGLE);
            addButton(4, Message.SETTING_MINION_STOP_WARNING, Feature.MINION_STOP_WARNING, 2, EnumUtils.ButtonType.TOGGLE);
            addButton(4, Message.SETTING_MAGMA_BOSS_HEALTH_BAR, Feature.MAGMA_BOSS_BAR, 3, EnumUtils.ButtonType.TOGGLE);
            addButton(5, Message.SETTING_ITEM_DROP_CONFIRMATION, Feature.DROP_CONFIRMATION, 1, EnumUtils.ButtonType.TOGGLE);
            addButton(5, Message.SETTING_MAGMA_BOSS_WARNING, Feature.MAGMA_WARNING, 2, EnumUtils.ButtonType.TOGGLE);
            addButton(5, Message.SETTING_HIDE_DURABILITY, Feature.HIDE_DURABILITY, 3, EnumUtils.ButtonType.TOGGLE);

            addButton(6, Message.SETTING_NEXT_PAGE, Feature.NEXT_PAGE, 2, EnumUtils.ButtonType.SOLID);
        } else if (page == 2) {

            addButton(2, Message.SETTING_DEFENCE_TEXT, Feature.DEFENCE_TEXT, 1, EnumUtils.ButtonType.TOGGLE);
            addButton(2, Message.SETTING_MANA_BAR, Feature.MANA_BAR, 2, EnumUtils.ButtonType.TOGGLE);
            addButton(2, Message.SETTING_HEALTH_BAR, Feature.HEALTH_BAR, 3, EnumUtils.ButtonType.TOGGLE);
            addButton(3, Message.SETTING_DEFENCE_ICON, Feature.DEFENCE_ICON, 1, EnumUtils.ButtonType.TOGGLE);
            addButton(3, Message.SETTING_MANA_TEXT, Feature.MANA_TEXT, 2, EnumUtils.ButtonType.TOGGLE);
            addButton(3, Message.SETTING_HEALTH_TEXT, Feature.HEALTH_TEXT, 3, EnumUtils.ButtonType.TOGGLE);
            addButton(4, Message.SETTING_DEFENCE_PERCENTAGE, Feature.DEFENCE_PERCENTAGE, 1, EnumUtils.ButtonType.TOGGLE);
            addButton(4, Message.SETTING_SKELETON_HAT_BONES_BAR, Feature.SKELETON_BAR, 2, EnumUtils.ButtonType.TOGGLE);
            addButton(4, Message.SETTING_HEALTH_UPDATES, Feature.HEALTH_UPDATES, 3, EnumUtils.ButtonType.TOGGLE);
            addButton(5, Message.SETTING_ITEM_PICKUP_LOG, Feature.ITEM_PICKUP_LOG, 1, EnumUtils.ButtonType.TOGGLE);

            addButton(6, Message.SETTING_PREVIOUS_PAGE, Feature.PREVIOUS_PAGE, 2, EnumUtils.ButtonType.SOLID);
        }
        addButton(8.5, Message.SETTING_EDIT_LOCATIONS, Feature.EDIT_LOCATIONS, 1, EnumUtils.ButtonType.SOLID);
        addButton(8.5, Message.SETTING_EDIT_SETTINGS, Feature.SETTINGS, 2, EnumUtils.ButtonType.SOLID);
        addButton(8.5, Message.LANGUAGE, Feature.LANGUAGE, 3, EnumUtils.ButtonType.SOLID);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        long timeSinceOpen = System.currentTimeMillis() - timeOpened;
        float alphaMultiplier; // This all calculates the alpha for the fade-in effect.
        alphaMultiplier = 0.5F;
        if (main.getUtils().isFadingIn()) {
            int fadeMilis = 500;
            if (timeSinceOpen <= fadeMilis) {
                alphaMultiplier = (float) timeSinceOpen / (fadeMilis * 2);
            }
        }
        int alpha = (int)(255*alphaMultiplier); // Alpha of the text will increase from 0 to 127 over 500ms.

        int startColor = new Color(0,0, 0, alpha).getRGB(); // Black
        int endColor = new Color(0,0, 0, (int)(alpha*1.5)).getRGB(); // Orange
        drawGradientRect(0, 0, width, height, startColor, endColor);
        GlStateManager.enableBlend();

        if (alpha < 4) alpha = 4; // Text under 4 alpha appear 100% transparent for some reason o.O
        int defaultBlue = main.getUtils().getDefaultBlue(alpha*2);

        // The text at the top of the GUI
        drawScaledString("SkyblockAddons", 0.12, defaultBlue, 2.5F);
        drawScaledString("v" + SkyblockAddons.VERSION + " by Biscut", 0.12, defaultBlue, 1.3, 50, 17);
        drawScaledString(main.getConfigValues().getMessage(Message.SETTING_SETTINGS), 0.8, defaultBlue, 1.5);
        if (page == 1) {
            if (main.isUsingLabymod() || (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) { // Show the labymod message also when i'm testing it to make sure it looks fine.
                drawScaledString(main.getConfigValues().getMessage(Message.MESSAGE_LABYMOD), 0.75, defaultBlue, 1);
            }
        } else if (page == 2) {
            drawScaledString("GUI Items", 0.26, defaultBlue, 1.8F);
        }
        super.drawScreen(mouseX, mouseY, partialTicks); // Draw buttons.
    }

    /**
     * Code to perform the button toggles, openings of other gui's/pages, and language changes.
     */
    @Override
    protected void actionPerformed(GuiButton abstractButton) {
        if (abstractButton instanceof ButtonFeature) {
            Feature feature = ((ButtonFeature)abstractButton).getFeature();
            if (feature == Feature.SETTINGS) {
                main.getUtils().setFadingIn(false);
                Minecraft.getMinecraft().displayGuiScreen(new SettingsGui(main));
            } else if (feature == Feature.LANGUAGE) {
                main.getConfigValues().setLanguage(main.getConfigValues().getLanguage().getNextLanguage());
                main.getConfigValues().loadLanguageFile();
                main.getUtils().setFadingIn(false);
                Minecraft.getMinecraft().displayGuiScreen(new SkyblockAddonsGui(main, page));
            }  else if (feature == Feature.EDIT_LOCATIONS) {
                main.getUtils().setFadingIn(false);
                Minecraft.getMinecraft().displayGuiScreen(new LocationEditGui(main));
            } else if (feature == Feature.NEXT_PAGE) {
                main.getUtils().setFadingIn(false);
                Minecraft.getMinecraft().displayGuiScreen(new SkyblockAddonsGui(main, 2));
            } else if (feature == Feature.PREVIOUS_PAGE) {
                main.getUtils().setFadingIn(false);
                Minecraft.getMinecraft().displayGuiScreen(new SkyblockAddonsGui(main, 1));
            } else {
                if (main.getConfigValues().getDisabledFeatures().contains(feature)) {
                    main.getConfigValues().getDisabledFeatures().remove(feature);
                } else {
                    main.getConfigValues().getDisabledFeatures().add(feature);
                    if (feature == Feature.HIDE_FOOD_ARMOR_BAR) { // Reset the vanilla bars when disabling these two features.
                        GuiIngameForge.renderArmor = true; // The food gets automatically enabled, no need to include it.
                    } else if (feature == Feature.HIDE_HEALTH_BAR) {
                        GuiIngameForge.renderHealth = true;
                    }
                }
            }
        }
    }

    private void drawScaledString(String text, double yMultiplier, int color, double scale) {
        drawScaledString(text, yMultiplier, color, scale, 0, 0);
    }

    /**
     * To avoid repeating the code for scaled text, use this instead.
     */
    private void drawScaledString(String text, double yMultiplier, int color, double scale, int xOff, int yOff) {
        double x = width/2;
        double y = height*yMultiplier;
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 1);
        drawCenteredString(fontRendererObj, text,
                (int)(x/scale)+xOff, (int)(y/scale)+yOff, color);
        GlStateManager.popMatrix();
    }

    /**
     * Adds a button, limiting its width and setting the correct position.
     */
    private void addButton(double row, Message message, Feature feature, int collumn, EnumUtils.ButtonType buttonType) {
        String text = null;
        if (message != null) {
            text = main.getConfigValues().getMessage(message);
        }
        int halfWidth = width/2;
        int oneThird = width/3;
        int twoThirds = oneThird*2;
        int boxWidth = fontRendererObj.getStringWidth(text)+10;
        if (boxWidth > BUTTON_MAX_WIDTH) boxWidth = BUTTON_MAX_WIDTH;
        int boxHeight = 20;
        int x = 0;
        if (collumn == 1) {
            x = oneThird-boxWidth-30;
        } else if (collumn == 2) {
            x = halfWidth-(boxWidth/2);
        } else if (collumn == 3) {
            x = twoThirds+30;
        } else if (collumn == 4) {
            x = oneThird-(boxWidth/2);
        } else if (collumn == 5) {
            x = twoThirds-(boxWidth/2);
        }
        if (buttonType == EnumUtils.ButtonType.TOGGLE) {
            buttonList.add(new ButtonToggle(x, getRowHeight(row), boxWidth, boxHeight, text, main, feature));
        } else if (buttonType == EnumUtils.ButtonType.SOLID) {
            buttonList.add(new ButtonSolid(x, getRowHeight(row), boxWidth, boxHeight, text, main, feature));
        }
    }

    // Each row is spaced 0.08 apart, starting at 0.17.
    private double getRowHeight(double row) {
        return height * (0.17+(row*0.08));
    }

    /**
     * Save the config when exiting.
     */
    @Override
    public void onGuiClosed() {
        main.getConfigValues().saveConfig();
    }
}
