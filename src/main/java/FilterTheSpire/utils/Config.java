package FilterTheSpire.utils;

import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.ui.screens.AlternateConfigMenu;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.google.gson.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public FilterGroupConfig getFilterList(String key){
        FilterGroupConfig filterGroupConfig = null;

        if (spireConfig != null && spireConfig.has(key)) {
            String s = spireConfig.getString(key);
            Gson gson = new Gson();
            filterGroupConfig = gson.fromJson(s, FilterGroupConfig.class);
        }

        return filterGroupConfig;
    }

    public void setFilterList(String key, List<FilterObject> activeFilters, List<PresetFilterGroup> presetGroups){
        FilterGroupConfig filterConfigValue = new FilterGroupConfig(activeFilters, presetGroups);
        setFilterList(key, filterConfigValue);
    }

    public void setFilterList(String key, FilterGroupConfig configValue){
        Gson gson = new Gson();

        spireConfig.setString(key, gson.toJson(configValue));

        try {
            spireConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBooleanKey(String key, boolean enabled){
        spireConfig.setBool(key, enabled);
    }

    public boolean getBooleanKey(String key){
        return spireConfig.getBool(key);
    }

    // --------------------------------------------------------------------------------

    public void setBossSwapFilter(ArrayList<String> enabledList) {
        setStringList("bossSwapFilter", enabledList);

        // just to test the config, lets also store the new pattern
        FilterObject f = new FilterObject(FilterType.NthBossRelic, enabledList);
        setFilterList("filterGroups", Collections.singletonList(f), null);
    }

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

    public void setEnableAllNeowBonuses(boolean enabled) {

    }
}
