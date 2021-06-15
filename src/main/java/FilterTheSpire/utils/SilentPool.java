package FilterTheSpire.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class SilentPool extends CharacterPool {
    private static SilentPool singleton = null;

    public static SilentPool getInstance(){
        if (singleton == null){
            singleton = new SilentPool();
        }
        return singleton;
    }

    private SilentPool(){
        cardPool = new ArrayList<>(Arrays.asList(
            "Flying Knee",
            "Dodge and Roll",
            "Sucker Punch",
            "Piercing Wail",
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
            "Sneaky Strike",
            "Cloak and Dagger",
            "Predator",
            "All-Out Attack",
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
            "Well-Laid Plans",
            "Finisher",
            "Escape Plan",
            "Calculated Gamble",
            "Skewer",
            "Riddle with Holes",
            "Endless Agony",
            "Setup",
            "Blur",
            "Caltrops",
            "Choke",
            "Expertise",
            "Tactician",
            "Catalyst",
            "Leg Sweep",
            "Crippling Cloud",
            "Alchemize",
            "Corpse Explosion",
            "Malaise",
            "Phantasmal Killer",
            "Die Die Die",
            "Adrenaline",
            "Envenom",
            "Doppelganger",
            "Burst",
            "Wraith Form",
            "Tools of the Trade",
            "Nightmare",
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
}
