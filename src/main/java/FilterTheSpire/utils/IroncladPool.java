package FilterTheSpire.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class IroncladPool extends CharacterPool {
    private static IroncladPool singleton = null;

    public static IroncladPool getInstance(){
        if (singleton == null){
            singleton = new IroncladPool();
        }
        return singleton;
    }

    private IroncladPool() {
        cardPool = new ArrayList<>(Arrays.asList(
            "Sword Boomerang",
            "Perfected Strike",
            "Heavy Blade",
            "Wild Strike",
            "Headbutt",
            "Havoc",
            "Armaments",
            "Clothesline",
            "Twin Strike",
            "Pommel Strike",
            "Thunderclap",
            "Clash",
            "Shrug It Off",
            "True Grit",
            "Body Slam",
            "Iron Wave",
            "Flex",
            "Warcry",
            "Cleave",
            "Anger",
            "Evolve",
            "Uppercut",
            "Ghostly Armor",
            "Fire Breathing",
            "Dropkick",
            "Carnage",
            "Bloodletting",
            "Rupture",
            "Second Wind",
            "Searing Blow",
            "Battle Trance",
            "Sentinel",
            "Entrench",
            "Rage",
            "Feel No Pain",
            "Disarm",
            "Seeing Red",
            "Dark Embrace",
            "Combust",
            "Whirlwind",
            "Sever Soul",
            "Rampage",
            "Shockwave",
            "Metallicize",
            "Burning Pact",
            "Pummel",
            "Flame Barrier",
            "Blood for Blood",
            "Intimidate",
            "Hemokinesis",
            "Reckless Charge",
            "Infernal Blade",
            "Dual Wield",
            "Power Through",
            "Inflame",
            "Spot Weakness",
            "Double Tap",
            "Demon Form",
            "Bludgeon",
            "Feed",
            "Limit Break",
            "Corruption",
            "Barricade",
            "Fiend Fire",
            "Berserk",
            "Impervious",
            "Juggernaut",
            "Brutality",
            "Reaper",
            "Exhume",
            "Offering",
            "Immolate"
        ));

        commonRelicPool = getSharedCommonRelicPool();
        commonRelicPool.add("Red Skull");

        uncommonRelicPool = getSharedUncommonRelicPool();
        uncommonRelicPool.add("Self Forming Clay");
        uncommonRelicPool.add("Paper Frog");

        rareRelicPool = getSharedRareRelicPool();
        rareRelicPool.add("Magic Flower");
        rareRelicPool.add("Charon's Ashes");
        rareRelicPool.add("Champion Belt");

        bossRelicPool = getSharedBossRelicPool();
        bossRelicPool.add("Black Blood");
        bossRelicPool.add("Mark of Pain");
        bossRelicPool.add("Runic Cube");

        shopRelicPool = getSharedShopRelicPool();
        shopRelicPool.add("Brimstone");
    }
}
