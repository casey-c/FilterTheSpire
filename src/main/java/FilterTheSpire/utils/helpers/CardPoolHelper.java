package FilterTheSpire.utils.helpers;

import FilterTheSpire.utils.cache.RunInfoCache;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.*;

public class CardPoolHelper {
    public static HashMap<String, String> cardNameToId = new HashMap<>();

    // I think this is kinda jank, but since everything's in english, we shouldn't be using any Spire
    // language pack stuff because of our id checking and such.
    public static HashMap<String, String> englishCardNameToId = new HashMap<String, String>(){{
        put("Immolate", "Immolate");
        put("Grand Finale", "Grand Finale");
        put("Regret", "Regret");
        put("Crippling Poison", "Crippling Cloud");
        put("Storm", "Storm");
        put("DeusExMachina", "Deus Ex Machina");
        put("A Thousand Cuts", "A Thousand Cuts");
        put("Spot Weakness", "Spot Weakness");
        put("Genetic Algorithm", "Genetic Algorithm");
        put("Go for the Eyes", "Go for the Eyes");
        put("Zap", "Zap");
        put("Steam Power", "Overclock");
        put("Wound", "Wound");
        put("Core Surge", "Core Surge");
        put("Fission", "Fission");
        put("Writhe", "Writhe");
        put("Beta", "Beta");
        put("Hello World", "Hello World");
        put("Creative AI", "Creative AI");
        put("Dark Shackles", "Dark Shackles");
        put("Glass Knife", "Glass Knife");
        put("Consecrate", "Consecrate");
        put("Cloak And Dagger", "Cloak and Dagger");
        put("BowlingBash", "Bowling Bash");
        put("Underhanded Strike", "Sneaky Strike");
        put("Anger", "Anger");
        put("Storm of Steel", "Storm of Steel");
        put("WheelKick", "Wheel Kick");
        put("Cleave", "Cleave");
        put("Ball Lightning", "Ball Lightning");
        put("Warcry", "Warcry");
        put("Sunder", "Sunder");
        put("Glacier", "Glacier");
        put("Inflame", "Inflame");
        put("Sadistic Nature", "Sadistic Nature");
        put("J.A.X.", "J.A.X.");
        put("Offering", "Offering");
        put("Vengeance", "Simmering Fury");
        put("FlyingSleeves", "Flying Sleeves");
        put("Exhume", "Exhume");
        put("Streamline", "Streamline");
        put("Wireheading", "Foresight");
        put("Consume", "Consume");
        put("Power Through", "Power Through");
        put("Dual Wield", "Dual Wield");
        put("Deadly Poison", "Deadly Poison");
        put("Leg Sweep", "Leg Sweep");
        put("PanicButton", "Panic Button");
        put("Flex", "Flex");
        put("Redo", "Recursion");
        put("AscendersBane", "Ascender's Bane");
        put("Dagger Spray", "Dagger Spray");
        put("Bullet Time", "Bullet Time");
        put("Fusion", "Fusion");
        put("Catalyst", "Catalyst");
        put("Sanctity", "Sanctity");
        put("Halt", "Halt");
        put("Reaper", "Reaper");
        put("Shiv", "Shiv");
        put("Bane", "Bane");
        put("Tactician", "Tactician");
        put("JustLucky", "Just Lucky");
        put("Infernal Blade", "Infernal Blade");
        put("After Image", "After Image");
        put("Unload", "Unload");
        put("FlurryOfBlows", "Flurry of Blows");
        put("Blade Dance", "Blade Dance");
        put("Deflect", "Deflect");
        put("Compile Driver", "Compile Driver");
        put("TalkToTheHand", "Talk to the Hand");
        put("BattleHymn", "Battle Hymn");
        put("Protect", "Protect");
        put("Trip", "Trip");
        put("Indignation", "Indignation");
        put("Dagger Throw", "Dagger Throw");
        put("Amplify", "Amplify");
        put("ThirdEye", "Third Eye");
        put("Brutality", "Brutality");
        put("Night Terror", "Nightmare");
        put("WindmillStrike", "Windmill Strike");
        put("Iron Wave", "Iron Wave");
        put("Reboot", "Reboot");
        put("Reckless Charge", "Reckless Charge");
        put("All For One", "All for One");
        put("ForeignInfluence", "Foreign Influence");
        put("Decay", "Decay");
        put("FameAndFortune", "Fame and Fortune");
        put("Tools of the Trade", "Tools of the Trade");
        put("Aggregate", "Aggregate");
        put("Expertise", "Expertise");
        put("Dramatic Entrance", "Dramatic Entrance");
        put("Hemokinesis", "Hemokinesis");
        put("Blizzard", "Blizzard");
        put("Chaos", "Chaos");
        put("LiveForever", "Live Forever");
        put("Intimidate", "Intimidate");
        put("Echo Form", "Echo Form");
        put("Necronomicurse", "Necronomicurse");
        put("Juggernaut", "Juggernaut");
        put("Choke", "Choke");
        put("Caltrops", "Caltrops");
        put("Impatience", "Impatience");
        put("DevaForm", "Deva Form");
        put("Poisoned Stab", "Poisoned Stab");
        put("The Bomb", "The Bomb");
        put("Blur", "Blur");
        put("LikeWater", "Like Water");
        put("Body Slam", "Body Slam");
        put("True Grit", "True Grit");
        put("Insight", "Insight");
        put("Setup", "Setup");
        put("Barrage", "Barrage");
        put("Crescendo", "Crescendo");
        put("SpiritShield", "Spirit Shield");
        put("Blood for Blood", "Blood for Blood");
        put("Impervious", "Impervious");
        put("ClearTheMind", "Tranquility");
        put("EmptyBody", "Empty Body");
        put("Shrug It Off", "Shrug It Off");
        put("Meteor Strike", "Meteor Strike");
        put("Establishment", "Establishment");
        put("Fasting2", "Fasting");
        put("Clash", "Clash");
        put("Stack", "Stack");
        put("Miracle", "Miracle");
        put("CarveReality", "Carve Reality");
        put("Wallop", "Wallop");
        put("Thunderclap", "Thunderclap");
        put("Rebound", "Rebound");
        put("Flame Barrier", "Flame Barrier");
        put("Seek", "Seek");
        put("Endless Agony", "Endless Agony");
        put("WreathOfFlame", "Wreath of Flame");
        put("Collect", "Collect");
        put("SashWhip", "Sash Whip");
        put("Wraith Form v2", "Wraith Form");
        put("Melter", "Melter");
        put("Berserk", "Berserk");
        put("Pummel", "Pummel");
        put("Burning Pact", "Burning Pact");
        put("Riddle With Holes", "Riddle with Holes");
        put("Metallicize", "Metallicize");
        put("Self Repair", "Self Repair");
        put("Pommel Strike", "Pommel Strike");
        put("Pain", "Pain");
        put("Rainbow", "Rainbow");
        put("InnerPeace", "Inner Peace");
        put("Burst", "Burst");
        put("Acrobatics", "Acrobatics");
        put("Adaptation", "Rushdown");
        put("Loop", "Loop");
        put("Blind", "Blind");
        put("Doppelganger", "Doppelganger");
        put("Skewer", "Skewer");
        put("Omniscience", "Omniscience");
        put("Envenom", "Envenom");
        put("Chill", "Chill");
        put("Adrenaline", "Adrenaline");
        put("Quick Slash", "Quick Slash");
        put("Twin Strike", "Twin Strike");
        put("BootSequence", "Boot Sequence");
        put("Parasite", "Parasite");
        put("Bash", "Bash");
        put("RitualDagger", "Ritual Dagger");
        put("Gash", "Claw");
        put("Wish", "Wish");
        put("Clothesline", "Clothesline");
        put("DeceiveReality", "Deceive Reality");
        put("MentalFortress", "Mental Fortress");
        put("Shockwave", "Shockwave");
        put("BecomeAlmighty", "Become Almighty");
        put("Rampage", "Rampage");
        put("Coolheaded", "Coolheaded");
        put("Static Discharge", "Static Discharge");
        put("Alpha", "Alpha");
        put("Heatsinks", "Heatsinks");
        put("Vault", "Vault");
        put("Bandage Up", "Bandage Up");
        put("Scrawl", "Scrawl");
        put("Sever Soul", "Sever Soul");
        put("Eruption", "Eruption");
        put("Whirlwind", "Whirlwind");
        put("Bite", "Bite");
        put("LessonLearned", "Lesson Learned");
        put("Secret Technique", "Secret Technique");
        put("Calculated Gamble", "Calculated Gamble");
        put("Tempest", "Tempest");
        put("Combust", "Combust");
        put("Deep Breath", "Deep Breath");
        put("Doubt", "Doubt");
        put("Escape Plan", "Escape Plan");
        put("CutThroughFate", "Cut Through Fate");
        put("ReachHeaven", "Reach Heaven");
        put("Finisher", "Finisher");
        put("Dark Embrace", "Dark Embrace");
        put("Die Die Die", "Die Die Die");
        put("Well Laid Plans", "Well-Laid Plans");
        put("Ragnarok", "Ragnarok");
        put("Buffer", "Buffer");
        put("Electrodynamics", "Electrodynamics");
        put("FearNoEvil", "Fear No Evil");
        put("Seeing Red", "Seeing Red");
        put("SandsOfTime", "Sands of Time");
        put("Smite", "Smite");
        put("Violence", "Violence");
        put("Disarm", "Disarm");
        put("Turbo", "TURBO");
        put("Panache", "Panache");
        put("Undo", "Equilibrium");
        put("Fiend Fire", "Fiend Fire");
        put("Terror", "Terror");
        put("Force Field", "Force Field");
        put("Dazed", "Dazed");
        put("Barricade", "Barricade");
        put("Armaments", "Armaments");
        put("Havoc", "Havoc");
        put("Secret Weapon", "Secret Weapon");
        put("Apotheosis", "Apotheosis");
        put("Sweeping Beam", "Sweeping Beam");
        put("Feel No Pain", "Feel No Pain");
        put("FTL", "FTL");
        put("Rip and Tear", "Rip and Tear");
        put("Darkness", "Darkness");
        put("Corruption", "Corruption");
        put("Heel Hook", "Heel Hook");
        put("Blasphemy", "Blasphemy");
        put("Injury", "Injury");
        put("Double Energy", "Double Energy");
        put("Rage", "Rage");
        put("Headbutt", "Headbutt");
        put("Machine Learning", "Machine Learning");
        put("Reinforced Body", "Reinforced Body");
        put("Defend_P", "Defend");
        put("Limit Break", "Limit Break");
        put("Entrench", "Entrench");
        put("Noxious Fumes", "Noxious Fumes");
        put("Infinite Blades", "Infinite Blades");
        put("Phantasmal Killer", "Phantasmal Killer");
        put("WaveOfTheHand", "Wave of the Hand");
        put("Malaise", "Malaise");
        put("Conserve Battery", "Charge Battery");
        put("Defend_R", "Defend");
        put("Mayhem", "Mayhem");
        put("Reflex", "Reflex");
        put("Study", "Study");
        put("Expunger", "Expunger");
        put("Sentinel", "Sentinel");
        put("Survivor", "Survivor");
        put("Wild Strike", "Wild Strike");
        put("Defend_G", "Defend");
        put("HandOfGreed", "Hand of Greed");
        put("Meditate", "Meditate");
        put("Eviscerate", "Eviscerate");
        put("Flash of Steel", "Flash of Steel");
        put("Defend_B", "Defend");
        put("Battle Trance", "Battle Trance");
        put("Forethought", "Forethought");
        put("Dualcast", "Dualcast");
        put("Auto Shields", "Auto-Shields");
        put("Perseverance", "Perseverance");
        put("Swivel", "Swivel");
        put("Heavy Blade", "Heavy Blade");
        put("Slimed", "Slimed");
        put("Clumsy", "Clumsy");
        put("Biased Cognition", "Biased Cognition");
        put("Searing Blow", "Searing Blow");
        put("Devotion", "Devotion");
        put("Reprogram", "Reprogram");
        put("Hologram", "Hologram");
        put("Corpse Explosion", "Corpse Explosion");
        put("Second Wind", "Second Wind");
        put("Enlightenment", "Enlightenment");
        put("Purity", "Purity");
        put("Panacea", "Panacea");
        put("Lockon", "Bullseye");
        put("Dash", "Dash");
        put("Worship", "Worship");
        put("Conclude", "Conclude");
        put("ThroughViolence", "Through Violence");
        put("Transmutation", "Transmutation");
        put("Ghostly", "Apparition");
        put("Backstab", "Backstab");
        put("Chrysalis", "Chrysalis");
        put("FollowUp", "Follow-Up");
        put("Void", "Void");
        put("Scrape", "Scrape");
        put("Feed", "Feed");
        put("Vigilance", "Vigilance");
        put("Rupture", "Rupture");
        put("Venomology", "Alchemize");
        put("Discovery", "Discovery");
        put("Beam Cell", "Beam Cell");
        put("Leap", "Leap");
        put("CurseOfTheBell", "Curse of the Bell");
        put("Bouncing Flask", "Bouncing Flask");
        put("PathToVictory", "Pressure Points");
        put("Bludgeon", "Bludgeon");
        put("Finesse", "Finesse");
        put("Slice", "Slice");
        put("Recycle", "Recycle");
        put("Backflip", "Backflip");
        put("Outmaneuver", "Outmaneuver");
        put("Bloodletting", "Bloodletting");
        put("Brilliance", "Brilliance");
        put("Magnetism", "Magnetism");
        put("Concentrate", "Concentrate");
        put("Skim", "Skim");
        put("White Noise", "White Noise");
        put("Capacitor", "Capacitor");
        put("Cold Snap", "Cold Snap");
        put("CrushJoints", "Crush Joints");
        put("Master of Strategy", "Master of Strategy");
        put("Flechettes", "Flechettes");
        put("Tantrum", "Tantrum");
        put("Perfected Strike", "Perfected Strike");
        put("Strike_B", "Strike");
        put("Thunder Strike", "Thunder Strike");
        put("Carnage", "Carnage");
        put("Masterful Stab", "Masterful Stab");
        put("Nirvana", "Nirvana");
        put("Evaluate", "Evaluate");
        put("Prepared", "Prepared");
        put("Good Instincts", "Good Instincts");
        put("Dropkick", "Dropkick");
        put("Swift Strike", "Swift Strike");
        put("Normality", "Normality");
        put("Strike_G", "Strike");
        put("MasterReality", "Master Reality");
        put("Omega", "Omega");
        put("Hyperbeam", "Hyperbeam");
        put("Accuracy", "Accuracy");
        put("Sword Boomerang", "Sword Boomerang");
        put("EmptyMind", "Empty Mind");
        put("Pride", "Pride");
        put("Defragment", "Defragment");
        put("Jack Of All Trades", "Jack of All Trades");
        put("Demon Form", "Demon Form");
        put("Fire Breathing", "Fire Breathing");
        put("Ghostly Armor", "Ghostly Armor");
        put("Weave", "Weave");
        put("Safety", "Safety");
        put("Metamorphosis", "Metamorphosis");
        put("Prostrate", "Prostrate");
        put("SignatureMove", "Signature Move");
        put("Uppercut", "Uppercut");
        put("PiercingWail", "Piercing Wail");
        put("Mind Blast", "Mind Blast");
        put("Neutralize", "Neutralize");
        put("Multi-Cast", "Multi-Cast");
        put("Shame", "Shame");
        put("Doom and Gloom", "Doom and Gloom");
        put("Evolve", "Evolve");
        put("Double Tap", "Double Tap");
        put("Sucker Punch", "Sucker Punch");
        put("Burn", "Burn");
        put("ConjureBlade", "Conjure Blade");
        put("Strike_R", "Strike");
        put("Judgement", "Judgment");
        put("Footwork", "Footwork");
        put("Strike_P", "Strike");
        put("Steam", "Steam Barrier");
        put("Distraction", "Distraction");
        put("Dodge and Roll", "Dodge and Roll");
        put("Thinking Ahead", "Thinking Ahead");
        put("EmptyFist", "Empty Fist");
        put("All Out Attack", "All-Out Attack");
        put("Flying Knee", "Flying Knee");
        put("Predator", "Predator");
        put("Pray", "Pray");
        put("Madness", "Madness");
    }};

    public static ArrayList<String> getOrderedCardPoolForColors(ArrayList<CharacterPool> colors, TreeMap<AbstractCard.CardRarity, Boolean> cardRaritiesAndShouldReverse){
        int hashKey = cardRaritiesAndShouldReverse.hashCode() + colors.hashCode();
        if (RunInfoCache.rarityMapCardPool.containsKey(hashKey)) {
            return RunInfoCache.rarityMapCardPool.get(hashKey);
        }
        ArrayList<String> cardPool = new ArrayList<>();
        ArrayList<String> rarityCardPool = new ArrayList<>();

        boolean hasColorlessEnabled = RunInfoCache.modList.contains("Colorless Cards");
        for (AbstractCard.CardRarity rarity: cardRaritiesAndShouldReverse.keySet()) {
            for (int i = 0; i < colors.size(); i++) {
                CharacterPool color = colors.get(i);
                switch (rarity){
                    case COMMON:
                        rarityCardPool.addAll(color.commonCardPool);
                        break;
                    case UNCOMMON:
                        rarityCardPool.addAll(color.uncommonCardPool);

                        if (i == (colors.size() - 1) && hasColorlessEnabled) {
                            rarityCardPool.addAll(getUncommonColorlessCards());
                        }
                        break;
                    case RARE:
                        rarityCardPool.addAll(color.rareCardPool);

                        if (i == (colors.size() - 1) && hasColorlessEnabled) {
                            rarityCardPool.addAll(getRareColorlessCards());
                        }
                        break;
                }
            }
            if (cardRaritiesAndShouldReverse.get(rarity)){
                Collections.reverse(rarityCardPool);
            }
            cardPool.addAll(rarityCardPool);
            rarityCardPool.clear();
        }
        RunInfoCache.rarityMapCardPool.put(hashKey, cardPool);
        return cardPool;
    }

    public static ArrayList<String> getUncommonColorlessCards(){
        return new ArrayList<>(Arrays.asList(
            "Madness",
            "Mind Blast",
            "Jack Of All Trades",
            "Swift Strike",
            "Good Instincts",
            "Finesse",
            "Discovery",
            "Panacea",
            "Purity",
            "Enlightenment",
            "Forethought",
            "Flash of Steel",
            "Deep Breath",
            "Bandage Up",
            "Blind",
            "Impatience",
            "Dramatic Entrance",
            "Trip",
            "PanicButton",
            "Dark Shackles"
        ));
    }

    public static ArrayList<String> getRareColorlessCards(){
        return new ArrayList<>(Arrays.asList(
            "Thinking Ahead",
            "Metamorphosis",
            "Master of Strategy",
            "Magnetism",
            "Chrysalis",
            "Transmutation",
            "HandOfGreed",
            "Mayhem",
            "Apotheosis",
            "Secret Weapon",
            "Panache",
            "Violence",
            "Secret Technique",
            "The Bomb",
            "Sadistic Nature"
        ));
    }

    // the card pools are set depending on the settings the player sets (custom runs usually), reset it when we find a seed for the next run
    public static void resetCharacterCardPoolsForSettings() {
        IroncladPool.getInstance().resetCardPoolsForSettings();
        SilentPool.getInstance().resetCardPoolsForSettings();
        DefectPool.getInstance().resetCardPoolsForSettings();
        WatcherPool.getInstance().resetCardPoolsForSettings();
    }
}
