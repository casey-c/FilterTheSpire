package FilterTheSpire.utils.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ModHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class WatcherPool extends CharacterPool {
    private static WatcherPool singleton = null;

    public static WatcherPool getInstance(){
        if (singleton == null){
            singleton = new WatcherPool();
        }
        return singleton;
    }

    private WatcherPool(){
        commonCardPool = new ArrayList<>(Arrays.asList(
            "EmptyFist",
            "Prostrate",
            "Evaluate",
            "CrushJoints",
            "PathToVictory",
            "FollowUp",
            "CutThroughFate",
            "SashWhip",
            "EmptyBody",
            "ClearTheMind",
            "Crescendo",
            "ThirdEye",
            "Protect",
            "FlurryOfBlows",
            "JustLucky",
            "Halt",
            "FlyingSleeves",
            "BowlingBash",
            "Consecrate"
        ));

        uncommonCardPool = new ArrayList<>(Arrays.asList(
            "Pray",
            "SignatureMove",
            "Weave",
            "EmptyMind",
            "Nirvana",
            "Tantrum",
            "Conclude",
            "Worship",
            "Swivel",
            "Perseverance",
            "Meditate",
            "Study",
            "WaveOfTheHand",
            "SandsOfTime",
            "FearNoEvil",
            "ReachHeaven",
            "MentalFortress",
            "DeceiveReality",
            "Adaptation",
            "InnerPeace",
            "Collect",
            "WreathOfFlame",
            "Wallop",
            "CarveReality",
            "Fasting2",
            "LikeWater",
            "ForeignInfluence",
            "WindmillStrike",
            "Indignation",
            "BattleHymn",
            "TalkToTheHand",
            "Sanctity",
            "Wireheading",
            "Vengeance",
            "WheelKick"
        ));

        rareCardPool = new ArrayList<>(Arrays.asList(
            "Judgement",
            "ConjureBlade",
            "MasterReality",
            "Brilliance",
            "Devotion",
            "Blasphemy",
            "Ragnarok",
            "LessonLearned",
            "Scrawl",
            "Vault",
            "Alpha",
            "Wish",
            "Omniscience",
            "Establishment",
            "SpiritShield",
            "DevaForm",
            "DeusExMachina"
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

    public List<String> getCardPool(TreeMap<AbstractCard.CardRarity, Boolean> cardRarities) {
        ArrayList<CharacterPool> colors = new ArrayList<>();
        // for some reason she adds her own color twice
        if (ModHelper.isModEnabled("Purple Cards")) {
            colors.add(WatcherPool.getInstance());
        }

        if (ModHelper.isModEnabled("Blue Cards")) {
            colors.add(DefectPool.getInstance());
        }

        if (ModHelper.isModEnabled("Green Cards")) {
            colors.add(SilentPool.getInstance());
        }

        if (ModHelper.isModEnabled("Red Cards")) {
            colors.add(IroncladPool.getInstance());
        }
        colors.add(WatcherPool.getInstance());
        return CardPoolHelper.getOrderedCardPoolForColors(colors, cardRarities);
    }
}
