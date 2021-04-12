package FilterTheSpire.utils;

import FilterTheSpire.ui.screens.AlternateConfigMenu;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.google.gson.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Config {
    private Properties defaults = new Properties();
    private SpireConfig spireConfig;

    public Config() {
        defaults.put("bossSwapFilter", "[]");
        defaults.put("shopRelicFilter", "[]");

        try {
            spireConfig = new SpireConfig("FilterTheSpire", "config", defaults);
            spireConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setStringList(String key, ArrayList<String> values) {
        JsonArray arr = new JsonArray();
        for (String s : values)
            arr.add(s);

        spireConfig.setString(key, arr.toString());

        try {
            spireConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getStringList(String key) {
        ArrayList<String> res = new ArrayList<>();

        if (spireConfig != null && spireConfig.has(key)) {
            String s = spireConfig.getString(key);

            JsonParser parser = new JsonParser();
            JsonArray json = (JsonArray) parser.parse(s);

            for (JsonElement e : json) {
                if (e.isJsonPrimitive())
                    res.add(e.getAsString());
            }
        }

        return res;
    }

    // --------------------------------------------------------------------------------

    public void setBossSwapFilter(ArrayList<String> enabledList) { setStringList("bossSwapFilter", enabledList); }
    public ArrayList<String> getBossSwapFilter() { return getStringList("bossSwapFilter"); }

    // --------------------------------------------------------------------------------

    public void setShopRelicFilter(ArrayList<String> enabledList) { setStringList("shopRelicFilter", enabledList); }
    public ArrayList<String> getShopRelicFilter() { return getStringList("shopRelicFilter"); }

    // --------------------------------------------------------------------------------

    // (Main Menu -> Mods) menu setup
    public static void setupConfigMenu() {
        BaseMod.registerModBadge(new Texture("FilterTheSpire/images/fts_icon.png"),
                "Filter the Spire",
                "ojb",
                "Customize your Slay the Spire experience",
                new AlternateConfigMenu());
    }
}
