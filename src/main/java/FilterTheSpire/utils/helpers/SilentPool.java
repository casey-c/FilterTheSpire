package FilterTheSpire.utils.helpers;

import FilterTheSpire.utils.cache.RunInfoCache;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class SilentPool extends CharacterPool {
    private static SilentPool singleton = null;

    public static SilentPool getInstance(){
        if (singleton == null){
            singleton = new SilentPool();
        }
        return singleton;
    }

    private SilentPool(){
        commonCardPool = new ArrayList<>(Arrays.asList(
            "Flying Knee",
            "Dodge and Roll",
            "Sucker Punch",
            "PiercingWail",
            "Prepared",
            "Outmaneuver",
            "Backflip",
            "Slice",
            "Quick Slash",
            "Acrobatics",
            "Poisoned Stab",
            "Dagger Throw",
            "Deflect",
            "Blade Dance",
            "Bane",
            "Dagger Spray",
            "Deadly Poison",
            "Underhanded Strike",
            "Cloak And Dagger"
        ));

        uncommonCardPool = new ArrayList<>(Arrays.asList(
            "Predator",
            "All Out Attack",
            "Distraction",
            "Footwork",
            "Accuracy",
            "Masterful Stab",
            "Flechettes",
            "Concentrate",
            "Bouncing Flask",
            "Backstab",
            "Dash",
            "Eviscerate",
            "Reflex",
            "Infinite Blades",
            "Noxious Fumes",
            "Heel Hook",
            "Terror",
            "Well Laid Plans",
            "Finisher",
            "Escape Plan",
            "Calculated Gamble",
            "Skewer",
            "Riddle With Holes",
            "Endless Agony",
            "Setup",
            "Blur",
            "Caltrops",
            "Choke",
            "Expertise",
            "Tactician",
            "Catalyst",
            "Leg Sweep",
            "Crippling Poison"
        ));

        rareCardPool = new ArrayList<>(Arrays.asList(
            "Venomology",
            "Corpse Explosion",
            "Malaise",
            "Phantasmal Killer",
            "Die Die Die",
            "Adrenaline",
            "Envenom",
            "Doppelganger",
            "Burst",
            "Wraith Form v2",
            "Tools of the Trade",
            "Night Terror",
            "Unload",
            "After Image",
            "Bullet Time",
            "Storm of Steel",
            "Glass Knife",
            "A Thousand Cuts",
            "Grand Finale"
        ));

        commonRelicPool = getSharedCommonRelicPool();
        commonRelicPool.add("Snake Skull");

        uncommonRelicPool = getSharedUncommonRelicPool();
        uncommonRelicPool.add("Ninja Scroll");
        uncommonRelicPool.add("Paper Crane");

        rareRelicPool = getSharedRareRelicPool();
        rareRelicPool.add("Tough Bandages");
        rareRelicPool.add("The Specimen");
        rareRelicPool.add("Tingsha");

        bossRelicPool = getSharedBossRelicPool();
        bossRelicPool.add("WristBlade");
        bossRelicPool.add("HoveringKite");
        bossRelicPool.add("Ring of the Serpent");

        shopRelicPool = getSharedShopRelicPool();
        shopRelicPool.add("TwistedFunnel");
    }

    public List<String> getCardPool(TreeMap<AbstractCard.CardRarity, Boolean> cardRarities) {
        ArrayList<CharacterPool> colors = new ArrayList<>();
        if (RunInfoCache.modList.contains("Purple Cards")) {
            colors.add(WatcherPool.getInstance());
        }

        if (RunInfoCache.modList.contains("Blue Cards")) {
            colors.add(DefectPool.getInstance());
        }

        if (RunInfoCache.modList.contains("Red Cards")) {
            colors.add(IroncladPool.getInstance());
        }
        colors.add(SilentPool.getInstance());
        return CardPoolHelper.getOrderedCardPoolForColors(colors, cardRarities);
    }
}
