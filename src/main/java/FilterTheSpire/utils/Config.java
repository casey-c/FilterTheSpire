package FilterTheSpire.utils;

import FilterTheSpire.ui.screens.AlternateConfigMenu;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.io.IOException;
import java.util.Properties;
import java.util.function.Consumer;

public class Config {
    public static void setupConfigMenu() {
        //ModPanel modPanel = new ModPanel();
        //BetterModPanel modPanel = new BetterModPanel();
        //AlternateConfigMenu modPanel = new AlternateConfigMenu();

        BaseMod.registerModBadge(new Texture("FilterTheSpire/images/fts_icon.png"),
                "Filter the Spire",
                "ojb",
                "Customize your Slay the Spire experience",
                new AlternateConfigMenu());

//        modPanel.addUIElement(new InfoModConfigWrappedLabel("Info Mod Config", leftColX, titleY, Settings.CREAM_COLOR, FontHelper.bannerFont, modPanel));
//
//        // LEFT COLUMN --------------------------------------------------------------------------------
//        modPanel.addUIElement(new InfoModConfigDescBool(
//                leftColX, firstDescY,
//                "Monster Compendium",
//                "Right click an enemy while in combat to see their AI and moveset. Right click again to close this overlay.",
//                modPanel,
//                ConfigOptions.SHOW_MONSTER_DETAILS
//        ));
//
//        modPanel.addUIElement(new InfoModConfigDescBool(
//                leftColX, firstDescY - itemOffsetY,
//                "Monster Compendium -- Require Shift",
//                "The monster compendium requires SHIFT to be held while right clicking to open the overlay. (Prevents accidental openings)",
//                modPanel,
//                ConfigOptions.MONSTER_OVERLAY_REQ_SHIFT
//        ));
//
//        modPanel.addUIElement(new InfoModConfigDescBool(
//                leftColX, firstDescY - 2 * itemOffsetY,
//                "Potion Chance Tracker",
//                "Displays the chance to see a potion after the next few combats. Shown as text on the top bar.",
//                modPanel,
//                ConfigOptions.SHOW_POTIONS
//                //ConfigHelper.BooleanSettings.SHOW_POTIONS
//        ));
//        modPanel.addUIElement(new InfoModConfigDescBool(
//                leftColX, firstDescY - 3 * itemOffsetY,
//                "Event Chance Tracker",
//                "Displays the possible events you can get in the remaining question mark floors of the act. Shown as a [?] box on the top bar.",
//                modPanel,
//                ConfigOptions.SHOW_QBOX
//                //ConfigHelper.BooleanSettings.SHOW_QBOX
//        ));
//
//        // RIGHT COLUMN --------------------------------------------------------------------------------
//        modPanel.addUIElement(new InfoModConfigDescBool(
//                rightColX, firstDescY,
//                "Map Tool Tip Override (Show Bosses)",
//                "Mousing over the map icon in the top right now shows the bosses you face throughout the run.",
//                modPanel,
//                ConfigOptions.SHOW_MAP_TIP,
//                //ConfigHelper.BooleanSettings.SHOW_MAP_TIP,
//                modToggleButton -> {
//                    bossTipItem.enabled = modToggleButton.enabled;
//                }
//        ));
//        modPanel.addUIElement(new InfoModConfigDescBool(
//                rightColX, firstDescY - itemOffsetY,
//                "Deck Tool Tip Override",
//                "Mousing over the deck icon in the top right now shows the contents of your deck in a quick access tool tip.",
//                modPanel,
//                ConfigOptions.SHOW_DECK_TIP,
//                //ConfigHelper.BooleanSettings.SHOW_DECK_TIP,
//                modToggleButton -> {
//                    deckTipItem.enabled = modToggleButton.enabled;
//                }
//        ));
//        modPanel.addUIElement(new InfoModConfigDescBool(
//                rightColX, firstDescY - itemOffsetY - itemOffsetY,
//                "Special 80% Potion Chance Effect",
//                "Inspired by twitch.tv/terrenceMHS",
//                modPanel,
//                ConfigOptions.TERR80
//                //ConfigHelper.BooleanSettings.TERR80
//        ));

//        BaseMod.registerModBadge(new Texture("images/icon_32.png"),
//                "Info Mod",
//                "ojb",
//                "Displays tedious to calculate information",
//                modPanel);
    }
}
