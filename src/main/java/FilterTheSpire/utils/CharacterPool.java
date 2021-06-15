package FilterTheSpire.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CharacterPool {
    public List<String> cardPool;
    public ArrayList<String> commonRelicPool;;
    public ArrayList<String> uncommonRelicPool;
    public ArrayList<String> rareRelicPool;
    public ArrayList<String> bossRelicPool;
    public ArrayList<String> shopRelicPool;

    protected ArrayList<String> getSharedCommonRelicPool() {
        return new ArrayList<>(Arrays.asList(
            "Whetstone",
            "Boot",
            "Blood Vial",
            "MealTicket",
            "Pen Nib",
            "Akabeko",
            "Lantern",
            "Regal Pillow",
            "Bag of Preparation",
            "Ancient Tea Set",
            "Smiling Mask",
            "Potion Belt",
            "PreservedInsect",
            "Omamori",
            "MawBank",
            "Art of War",
            "Toy Ornithopter",
            "CeramicFish",
            "Vajra",
            "Centennial Puzzle",
            "Strawberry",
            "Happy Flower",
            "Oddly Smooth Stone",
            "War Paint",
            "Bronze Scales",
            "Juzu Bracelet",
            "Dream Catcher",
            "Nunchaku",
            "Tiny Chest",
            "Orichalcum",
            "Anchor",
            "Bag of Marbles"
        ));
    }

    protected ArrayList<String> getSharedUncommonRelicPool() {
        return new ArrayList<>(Arrays.asList(
            "Bottled Tornado",
            "Sundial",
            "Kunai",
            "Pear",
            "Blue Candle",
            "Eternal Feather",
            "StrikeDummy",
            "Singing Bowl",
            "Matryoshka",
            "InkBottle",
            "The Courier",
            "Frozen Egg 2",
            "Ornamental Fan",
            "Bottled Lightning",
            "Gremlin Horn",
            "HornCleat",
            "Toxic Egg 2",
            "Letter Opener",
            "Question Card",
            "Bottled Flame",
            "Shuriken",
            "Molten Egg 2",
            "Meat on the Bone",
            "Darkstone Periapt",
            "Mummified Hand",
            "Pantograph",
            "White Beast Statue",
            "Mercury Hourglass"
        ));
    }

    protected ArrayList<String> getSharedRareRelicPool() {
        return new ArrayList<>(Arrays.asList(
            "Ginger",
            "Old Coin",
            "Bird Faced Urn",
            "Unceasing Top",
            "Torii",
            "StoneCalendar",
            "Shovel",
            "WingedGreaves",
            "Thread and Needle",
            "Turnip",
            "Ice Cream",
            "Calipers",
            "Lizard Tail",
            "Prayer Wheel",
            "Girya",
            "Dead Branch",
            "Du-Vu Doll",
            "Pocketwatch",
            "Mango",
            "Incense Burner",
            "Gambling Chip",
            "Peace Pipe",
            "CaptainsWheel",
            "FossilizedHelix",
            "TungstenRod"
        ));
    }

    protected ArrayList<String> getSharedBossRelicPool() {
        return new ArrayList<>(Arrays.asList(
            "Fusion Hammer",
            "Velvet Choker",
            "Runic Dome",
            "SlaversCollar",
            "Snecko Eye",
            "Pandora's Box",
            "Cursed Key",
            "Busted Crown",
            "Ectoplasm",
            "Tiny House",
            "Sozu",
            "Philosopher's Stone",
            "Astrolabe",
            "Black Star",
            "SacredBark",
            "Empty Cage",
            "Runic Pyramid",
            "Calling Bell",
            "Coffee Dripper"
        ));
    }

    protected ArrayList<String> getSharedShopRelicPool() {
        return new ArrayList<>(Arrays.asList(
            "Sling",
            "HandDrill",
            "Toolbox",
            "Chemical X",
            "Lee's Waffle",
            "Orrery",
            "DollysMirror",
            "OrangePellets",
            "PrismaticShard",
            "ClockworkSouvenir",
            "Frozen Eye",
            "TheAbacus",
            "Medical Kit",
            "Cauldron",
            "Strange Spoon",
            "Membership Card"
        ));
    }
}
