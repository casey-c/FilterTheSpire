package FilterTheSpire.rng;

import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.Collections;

public class MonsterRngHelper {
    public ArrayList<String> monsterList = new ArrayList<>();
    public ArrayList<String> eliteMonsterList = new ArrayList<>();
    public ArrayList<String> bossList = new ArrayList<>();

    private Random monsterRng;
    private long seed;

    public MonsterRngHelper(long seed) {
        setSeed(seed);
    }

    public void setSeed(long seed) {
        this.seed = seed;
        this.monsterRng = new Random(seed);

        generateWeakEnemiesAct1();
        generateStrongEnemiesAct1();
        generateElitesAct1();
        initializeBoss();
    }

    // --------------------------------------------------------------------------------

    private void generateWeakEnemiesAct1() {
        ArrayList<MonsterInfo> monsters = new ArrayList<MonsterInfo>();
        monsters.add(new MonsterInfo("Cultist", 2.0f));
        monsters.add(new MonsterInfo("Jaw Worm", 2.0f));
        monsters.add(new MonsterInfo("2 Louse", 2.0f));
        monsters.add(new MonsterInfo("Small Slimes", 2.0f));
        MonsterInfo.normalizeWeights(monsters);

        populateMonsterList(monsters, 3);
    }

    private void generateStrongEnemiesAct1() {
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

        populateFirstStrongEnemy(monsters, generateExclusions());
        populateMonsterList(monsters, 12);
    }

    private void generateElitesAct1() {
        ArrayList<MonsterInfo> monsters = new ArrayList<MonsterInfo>();
        monsters.add(new MonsterInfo("Gremlin Nob", 1.0f));
        monsters.add(new MonsterInfo("Lagavulin", 1.0f));
        monsters.add(new MonsterInfo("3 Sentries", 1.0f));
        MonsterInfo.normalizeWeights(monsters);

        populateEliteMonsterList(monsters, 10);
    }

    protected void initializeBoss() {
        bossList.add("The Guardian");
        bossList.add("Hexaghost");
        bossList.add("Slime Boss");
        Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));

    }

    // --------------------------------------------------------------------------------

    private ArrayList<String> generateExclusions() {
        ArrayList<String> retVal = new ArrayList<String>();
        switch (monsterList.get(monsterList.size() - 1)) {
            case "Looter": {
                retVal.add("Exordium Thugs");
                break;
            }
            case "Jaw Worm":
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

    private void populateFirstStrongEnemy(ArrayList<MonsterInfo> monsters, ArrayList<String> exclusions) {
        String m;
        while (exclusions.contains(m = MonsterInfo.roll(monsters, monsterRng.random()))) {
        }
        monsterList.add(m);
    }

    private void populateEliteMonsterList(ArrayList<MonsterInfo> monsters, int numMonsters) {
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

    protected void populateMonsterList(ArrayList<MonsterInfo> monsters, int numMonsters) {
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

    // --------------------------------------------------------------------------------

    public void print() {
        System.out.println("The seed is: " + SeedHelper.getString(seed));
        System.out.println("Monster List: " + monsterList.toString());
        System.out.println("Elite List: " + eliteMonsterList.toString());
        System.out.println("Boss: " + bossList.get(0));
    }

}
