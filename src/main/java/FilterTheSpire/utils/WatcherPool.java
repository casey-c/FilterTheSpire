package FilterTheSpire.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class WatcherPool extends CharacterPool {
    private static WatcherPool singleton = null;

    public static WatcherPool getInstance(){
        if (singleton == null){
            singleton = new WatcherPool();
        }
        return singleton;
    }

    private WatcherPool(){
        cardPool = new ArrayList<>(Arrays.asList(
            "Empty Fist",
            "Prostrate",
            "Evaluate",
            "Crush Joints",
            "Pressure Points",
            "Follow-Up",
            "Cut Through Fate",
            "Sash Whip",
            "Empty Body",
            "Tranquility",
            "Crescendo",
            "Third Eye",
            "Protect",
            "Flurry of Blows",
            "Just Lucky",
            "Halt",
            "Flying Sleeves",
            "Bowling Bash",
            "Consecrate",
            "Pray",
            "Signature Move",
            "Weave",
            "Empty Mind",
            "Nirvana",
            "Tantrum",
            "Conclude",
            "Worship",
            "Swivel",
            "Perseverance",
            "Meditate",
            "Study",
            "Wave of the Hand",
            "Sands of Time",
            "Fear No Evil",
            "Reach Heaven",
            "Mental Fortress",
            "Deceive Reality",
            "Rushdown",
            "Inner Peace",
            "Collect",
            "Wreath of Flame",
            "Wallop",
            "Carve Reality",
            "Fasting",
            "Like Water",
            "Foreign Influence",
            "Windmill Strike",
            "Indignation",
            "Battle Hymn",
            "Talk to the Hand",
            "Sanctity",
            "Foresight",
            "Simmering Fury",
            "Wheel Kick",
            "Judgment",
            "Conjure Blade",
            "Master Reality",
            "Brilliance",
            "Devotion",
            "Blasphemy",
            "Ragnarok",
            "Lesson Learned",
            "Scrawl",
            "Vault",
            "Alpha",
            "Wish",
            "Omniscience",
            "Establishment",
            "Spirit Shield",
            "Deva Form",
            "Deus Ex Machina"
        ));

        commonRelicPool = getSharedCommonRelicPool();
        commonRelicPool.add("Damaru");

        uncommonRelicPool = getSharedUncommonRelicPool();
        uncommonRelicPool.add("Yang");
        uncommonRelicPool.add("TeardropLocket");

        rareRelicPool = getSharedRareRelicPool();
        rareRelicPool.add("CloakClasp");
        rareRelicPool.add("GoldenEye");

        bossRelicPool = getSharedBossRelicPool();
        bossRelicPool.add("HolyWater");
        bossRelicPool.add("VioletLotus");

        shopRelicPool = getSharedShopRelicPool();
        shopRelicPool.add("Melange");
    }
}
