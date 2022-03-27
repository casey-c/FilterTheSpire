package FilterTheSpire.utils;

import com.megacrit.cardcrawl.helpers.ModHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IroncladPool extends CharacterPool {
    private static IroncladPool singleton = null;

    public static IroncladPool getInstance(){
        if (singleton == null){
            singleton = new IroncladPool();
        }
        return singleton;
    }

    private IroncladPool() {
        commonCardPool = new ArrayList<>(Arrays.asList(
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
            "Anger"
        ));

        uncommonCardPool = new ArrayList<>(Arrays.asList(
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
            "Spot Weakness"
        ));

        rareCardPool = new ArrayList<>(Arrays.asList(
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

    public List<String> getCardPool(boolean shouldReverseCardOrder) {
        ArrayList<CharacterPool> colors = new ArrayList<>();
        if (ModHelper.isModEnabled("Purple Cards")) {
            colors.add(WatcherPool.getInstance());
        }

        if (ModHelper.isModEnabled("Blue Cards")) {
            colors.add(DefectPool.getInstance());
        }

        if (ModHelper.isModEnabled("Green Cards")) {
            colors.add(SilentPool.getInstance());
        }
        colors.add(IroncladPool.getInstance());
        return CardPoolHelper.getOrderedCardPoolForColors(colors, shouldReverseCardOrder);
    }
}
