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
        defaults.put("bossSwapFilter", "{}");

        try {
            spireConfig = new SpireConfig("FilterTheSpire", "config", defaults);
            spireConfig.save();

            String s = spireConfig.getString("bossSwapFilter");
            System.out.println("Successfully loaded config: " + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBossSwapFilter(ArrayList<String> enabledList) {
        if (spireConfig != null) {
            JsonArray arr = new JsonArray();

            for (String s : enabledList)
                arr.add(s);

            spireConfig.setString("bossSwapFilter", arr.toString());
            System.out.println("Set bossSwapFilter to " + arr.toString());
            try {
                spireConfig.save();
                System.out.println("Successfully saved config");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Could not save config");
            }
        }
    }

    public ArrayList<String> getBossSwapFilter() {
        if (spireConfig != null) {
            if (spireConfig.has("bossSwapFilter")) {
                String s = spireConfig.getString("bossSwapFilter");
                System.out.println("Spire config bossSwapFilter is: '" + s + "'");

                JsonParser parser = new JsonParser();
                JsonArray json = (JsonArray) parser.parse(s);

                ArrayList<String> res = new ArrayList<>();
                for (JsonElement relic : json) {
                    res.add(relic.getAsString());
                }

                System.out.println("Resulting array: " + res.toString());
                return res;

            }
            else {
                System.out.println("spire config does not have boss swap filter");
            }
        }
        else {
            System.out.println("spire config is null");
        }

        return new ArrayList<>();
    }

    public static void setupConfigMenu() {
        BaseMod.registerModBadge(new Texture("FilterTheSpire/images/fts_icon.png"),
                "Filter the Spire",
                "ojb",
                "Customize your Slay the Spire experience",
                new AlternateConfigMenu());
    }
}
