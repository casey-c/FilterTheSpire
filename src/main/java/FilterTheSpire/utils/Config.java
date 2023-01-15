package FilterTheSpire.utils;

import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.ui.screens.AlternateConfigMenu;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.google.gson.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Config {
    private SpireConfig spireConfig;
    private FilterGroupConfig currentFilters;
    private final String filterKey = "filters";
    public final String threadCountKey = "threadCount";
    public final String allNeowBonusesKey = "allNeowBonuses";

    public Config() {
        Properties defaults = new Properties();
        defaults.put("bossSwapFilter", "[]");
        defaults.put("shopRelicFilter", "[]");

        try {
            spireConfig = new SpireConfig("FilterTheSpire", "config", defaults);
            spireConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeFilterList();
    }

    private void initializeFilterList(){
        if (spireConfig != null && spireConfig.has(filterKey)) {
            String s = spireConfig.getString(filterKey);
            Gson gson = new Gson();
            currentFilters = gson.fromJson(s, FilterGroupConfig.class);
        } else {
            currentFilters = new FilterGroupConfig(new HashMap<>(), null);
        }
    }

    public FilterObject getFilter(FilterType filterType){
        return getFilter(filterType, Collections.singletonList(0));
    }

    public FilterObject getFilter(FilterType filterType, List<Integer> indices){
        return currentFilters.activeFilters.getOrDefault(filterType, new FilterObject(filterType));
    }

    public void updateFilter(FilterObject filterObject){
        currentFilters.activeFilters.put(filterObject.filterType, filterObject);

        // update settings
        Gson gson = new Gson();
        spireConfig.setString(filterKey, gson.toJson(currentFilters));

        try {
            spireConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeFilter(FilterType key) {
        currentFilters.activeFilters.remove(key);

        Gson gson = new Gson();
        spireConfig.setString(filterKey, gson.toJson(currentFilters));

        try {
            spireConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBooleanKey(String key, boolean enabled){
        spireConfig.setBool(key, enabled);

        try {
            spireConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getBooleanKeyOrSetDefault(String key, boolean defaultValue){
        if (!spireConfig.has(key)){
            setBooleanKey(key, defaultValue);
        }
        return getBooleanKey(key);
    }

    public boolean getBooleanKey(String key){
        return spireConfig.getBool(key);
    }

    public void setIntKey(String key, int val){
        spireConfig.setInt(key, val);

        try {
            spireConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getIntKeyOrSetDefault(String key, int defaultValue){
        if (!spireConfig.has(key)){
            setIntKey(key, defaultValue);
        }
        return getIntKey(key);
    }

    public int getIntKey(String key){
        return spireConfig.getInt(key);
    }

    // (Main Menu -> Mods) menu setup
    public static void setupConfigMenu() {
        BaseMod.registerModBadge(new Texture("FilterTheSpire/images/fts_icon.png"),
                "Filter the Spire",
                "ojb",
                "Customize your Slay the Spire experience",
                new AlternateConfigMenu());
    }

    public void clearFilters(){
        currentFilters.activeFilters = new HashMap<>();

        // update settings
        Gson gson = new Gson();
        spireConfig.setString(filterKey, gson.toJson(currentFilters));

        try {
            spireConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // for eventual multiple of same filter but different indices
    private String generateHashKey(FilterType filterType, List<Integer> possibleEncounterIndices){
        String indices = possibleEncounterIndices.stream().map(String::valueOf).collect(Collectors.joining(""));
        return filterType.toString() + indices;
    }
}
