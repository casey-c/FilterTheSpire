package FilterTheSpire.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class DefectPool extends CharacterPool {
    private static DefectPool singleton = null;

    public static DefectPool getInstance(){
        if (singleton == null){
            singleton = new DefectPool();
        }
        return singleton;
    }

    private DefectPool(){
        cardPool = new ArrayList<>(Arrays.asList(
            "Steam",
            "Cold Snap",
            "Leap",
            "Beam Cell",
            "Hologram",
            "Conserve Battery",
            "Sweeping Beam",
            "Turbo",
            "Coolheaded",
            "Gash",
            "Rebound",
            "Stack",
            "Barrage",
            "Compile Driver",
            "Redo",
            "Streamline",
            "Ball Lightning",
            "Go for the Eyes",
            "Doom and Gloom",
            "Defragment",
            "Capacitor",
            "White Noise",
            "Skim",
            "Recycle",
            "Scrape",
            "Lockon",
            "Reprogram",
            "Auto Shields",
            "Reinforced Body",
            "Double Energy",
            "Darkness",
            "Rip and Tear",
            "FTL",
            "Force Field",
            "Undo",
            "Tempest",
            "Heatsinks",
            "Static Discharge",
            "BootSequence",
            "Chill",
            "Loop",
            "Self Repair",
            "Melter",
            "Chaos",
            "Blizzard",
            "Aggregate",
            "Fusion",
            "Consume",
            "Glacier",
            "Sunder",
            "Hello World",
            "Steam Power",
            "Genetic Algorithm",
            "Storm",
            "Multi-Cast",
            "Hyperbeam",
            "Thunder Strike",
            "Biased Cognition",
            "Machine Learning",
            "Electrodynamics",
            "Buffer",
            "Rainbow",
            "Seek",
            "Meteor Strike",
            "Echo Form",
            "All For One",
            "Reboot",
            "Amplify",
            "Creative AI",
            "Fission",
            "Core Surge"
        ));

        commonRelicPool = getSharedCommonRelicPool();
        commonRelicPool.add("DataDisk");

        uncommonRelicPool = getSharedUncommonRelicPool();
        uncommonRelicPool.add("Symbiotic Virus");
        uncommonRelicPool.add("Cables");

        rareRelicPool = getSharedRareRelicPool();
        rareRelicPool.add("Emotion Chip");

        bossRelicPool = getSharedBossRelicPool();
        bossRelicPool.add("Inserter");
        bossRelicPool.add("FrozenCore");
        bossRelicPool.add("Nuclear Battery");

        shopRelicPool = getSharedShopRelicPool();
        shopRelicPool.add("Runic Capacitor");
    }
}
