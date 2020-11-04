package FilterTheSpire.utils;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;

// TODO: DEBUG ONLY / REMOVE
public class SeedTesting {
    protected static void generateWeakEnemies(ArrayList<String> monsterList, Random monsterRng) {
        ArrayList<MonsterInfo> monsters = new ArrayList<MonsterInfo>();
        monsters.add(new MonsterInfo("Cultist", 2.0f));
        monsters.add(new MonsterInfo("Jaw Worm", 2.0f));
        monsters.add(new MonsterInfo("2 Louse", 2.0f));
        monsters.add(new MonsterInfo("Small Slimes", 2.0f));
        MonsterInfo.normalizeWeights(monsters);

        populateMonsterList(monsters, 3, monsterList, monsterRng);
    }

    protected static void generateStrongEnemies(ArrayList<String> monsterList, Random monsterRng) {
        ArrayList<MonsterInfo> monsters = new ArrayList<MonsterInfo>();
        monsters.add(new MonsterInfo("Blue Slaver", 2.0f));
        monsters.add(new MonsterInfo("Gremlin Gang", 1.0f));
        monsters.add(new MonsterInfo("Looter", 2.0f));
        monsters.add(new MonsterInfo("Large Slime", 2.0f));
        monsters.add(new MonsterInfo("Lots of Slimes", 1.0f));
        monsters.add(new MonsterInfo("Exordium Thugs", 1.5f));
        monsters.add(new MonsterInfo("Exordium Wildlife", 1.5f));
        monsters.add(new MonsterInfo("Red Slaver", 1.0f));
        monsters.add(new MonsterInfo("3 Louse", 2.0f));
        monsters.add(new MonsterInfo("2 Fungi Beasts", 2.0f));
        MonsterInfo.normalizeWeights(monsters);

        // count = 12
        populateFirstStrongEnemy(monsters, generateExclusions(monsterList), monsterRng, monsterList);
        populateMonsterList(monsters, 12, monsterList, monsterRng);
    }

    protected static void generateElites(ArrayList<String> monsterList, Random monsterRng) {
        ArrayList<MonsterInfo> monsters = new ArrayList<MonsterInfo>();
        monsters.add(new MonsterInfo("Gremlin Nob", 1.0f));
        monsters.add(new MonsterInfo("Lagavulin", 1.0f));
        monsters.add(new MonsterInfo("3 Sentries", 1.0f));
        MonsterInfo.normalizeWeights(monsters);

        populateEliteMonsterList(monsters, 10, monsterList, monsterRng);
    }

    protected static ArrayList<String> generateExclusions(ArrayList<String> monsterList) {
        ArrayList<String> retVal = new ArrayList<String>();
        switch ((String)monsterList.get(monsterList.size() - 1)) {
            case "Looter": {
                retVal.add("Exordium Thugs");
                break;
            }
            case "Jaw Worm": {
                break;
            }
            case "Cultist": {
                break;
            }
            case "Blue Slaver": {
                retVal.add("Red Slaver");
                retVal.add("Exordium Thugs");
                break;
            }
            case "2 Louse": {
                retVal.add("3 Louse");
                break;
            }
            case "Small Slimes": {
                retVal.add("Large Slime");
                retVal.add("Lots of Slimes");
                break;
            }
        }
        return retVal;
    }

    protected static void populateFirstStrongEnemy(ArrayList<MonsterInfo> monsters, ArrayList<String> exclusions, Random monsterRng, ArrayList<String> monsterList) {
        String m;
        while (exclusions.contains(m = MonsterInfo.roll(monsters, monsterRng.random()))) {
        }
        monsterList.add(m);
    }

    protected static void populateEliteMonsterList(ArrayList<MonsterInfo> monsters, int numMonsters, ArrayList<String> eliteMonsterList, Random monsterRng) {
        for (int i = 0; i < numMonsters; ++i) {
            if (eliteMonsterList.isEmpty()) {
                eliteMonsterList.add(MonsterInfo.roll(monsters, monsterRng.random()));
                continue;
            }
            String toAdd = MonsterInfo.roll(monsters, monsterRng.random());
            if (!toAdd.equals(eliteMonsterList.get(eliteMonsterList.size() - 1))) {
                eliteMonsterList.add(toAdd);
                continue;
            }
            --i;
        }
    }

    protected static void populateMonsterList(ArrayList<MonsterInfo> monsters, int numMonsters, ArrayList<String> monsterList, Random monsterRng) {
        for (int i = 0; i < numMonsters; ++i) {
            if (monsterList.isEmpty()) {
                monsterList.add(MonsterInfo.roll(monsters, monsterRng.random()));
                continue;
            }
            String toAdd = MonsterInfo.roll(monsters, monsterRng.random());
            if (!toAdd.equals(monsterList.get(monsterList.size() - 1))) {
                if (monsterList.size() > 1 && toAdd.equals(monsterList.get(monsterList.size() - 2))) {
                    --i;
                    continue;
                }
                monsterList.add(toAdd);
                continue;
            }
            --i;
        }
    }

    protected static ArrayList<String> initializeBoss(Random monsterRng) {
        ArrayList<String> bossList = new ArrayList<>();
        bossList.add("The Guardian");
        bossList.add("Hexaghost");
        bossList.add("Slime Boss");
        Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));

        return bossList;
    }


    public static void bossTest() {
        // AbstractDungeon::generateSeeds (static)
        Random monsterRng = new Random(Settings.seed);

        // AbstractDungeon = new dungeon() [e.g. new exordium]

        // AbstractDungeon::generateMonsters() --> Exordium::generateMonsters
        // genWeak, genStrong, genElites

        // genWeak:
        // monsters = [Cultist, Jaw Worm, 2 Louse, Small Slimes]
        // populateMonsterList(monsters, count=3, false)
        // which calls MonsterInfo.roll(monsters, monsterRng) once per each in count (so 3 times, here)
        //    **except, if the roll is the same as the previously rolled monster (can't have em twice in a row)

        ArrayList<String> monsterList = new ArrayList<>();
        ArrayList<String> eliteMonsterList = new ArrayList<>();
        generateWeakEnemies(monsterList, monsterRng);
        // nice! these are indeed the easy fights

        System.out.println("If things are correct, you should be fighting " + monsterList.toString());

        generateStrongEnemies(monsterList, monsterRng);
        System.out.println("With Strong enemies, If things are correct, you should be fighting " + monsterList.toString());
        // still nice! these are indeed the hard fights

        generateElites(eliteMonsterList, monsterRng);
        System.out.println("With Elite enemies, If things are correct, you should be fighting " + eliteMonsterList.toString());
        // all set!

        // AbstractDungeon::initializeBoss() --> Exordium::initializeBoss
        ArrayList<String> bossList = initializeBoss(monsterRng);
        System.out.println("The boss you should be facing is: " + bossList.get(0));
        // this also works :)
        // time to clean this mess up



//        ArrayList<String> bossList = new ArrayList<>();
//        bossList.add("The Guardian");
//        bossList.add("Hexaghost");
//        bossList.add("Slime Boss");
//
//        Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
    }

    public static void bossSwapTest() {
        /*
        // AbstractDungeon::generateSeeds();
        relicRng = new Random(Settings.seed);

        // AbstractDungeon::initializeRelicList
        //   As far as I can tell, nothing happens to the rng until these shuffles
        Collections.shuffle(commonRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.shuffle(uncommonRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.shuffle(rareRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.shuffle(shopRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.shuffle(bossRelicPool, new java.util.Random(relicRng.randomLong()));

        // Idea: take the seed; build the boss relic pool as normal and try and see if the result of my shuffle
        //   is the same
         */

        Long seed = Settings.seed;
        Random relicRng = new Random(seed);

        // Skip past all these
        relicRng.randomLong(); // common
        relicRng.randomLong(); // uncommon
        relicRng.randomLong(); // rare
        relicRng.randomLong(); // shop
        //relicRng.randomLong(); // boss <- this is the one (we perform it below)

        ArrayList<String> bossRelicPool = new ArrayList<>();
        RelicLibrary.populateRelicPool(bossRelicPool, AbstractRelic.RelicTier.BOSS, AbstractDungeon.player.chosenClass);

        Collections.shuffle(bossRelicPool, new java.util.Random(relicRng.randomLong()));

        // These are identical - hooray!
        ArrayList<String> realPool = AbstractDungeon.bossRelicPool;
        System.out.println("**************************************");
        System.out.println("The real boss relic pool is: " + realPool.toString());
        System.out.println("My manufactured boss relic pool is: " + bossRelicPool.toString());
        System.out.println("**************************************\n");
    }
}
