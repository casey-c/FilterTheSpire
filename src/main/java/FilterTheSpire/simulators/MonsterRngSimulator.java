package FilterTheSpire.simulators;

import FilterTheSpire.utils.MonsterListHelper;
import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Give the MonsterRngSimulator a seed, and it will output the monsters for the given act.
// Simply passing an Act2MonsterListHelper probably won't work since I'd assume it needs the same RNG seed to first generate Act 1
// So maybe the solution is to pass in a list of MonsterListHelpers and keep generating the enemies for each MonsterListHelper
public class MonsterRngSimulator {
    private static MonsterRngSimulator singleton = null;

    public static MonsterRngSimulator getInstance(){
        if (singleton == null){
            singleton = new MonsterRngSimulator();
        }
        return singleton;
    }

    private MonsterRngSimulator(){

    }

//    public void print() {
//        System.out.println("The seed is: " + SeedHelper.getString(seed));
//        System.out.println("Monster List: " + monsterList.toString());
//        System.out.println("Elite List: " + eliteMonsterList.toString());
//        System.out.println("Bosses: " + bossList.toString());
//    }

    public String firstBoss(long seed, MonsterListHelper monsterListHelper) {
        ArrayList<String> monsterList = new ArrayList<>();
        Random monsterRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.MONSTER);;

        generateWeakEnemies(monsterList, monsterRng, monsterListHelper);
        generateStrongEnemies(monsterList, monsterRng, monsterListHelper);
        generateElites(new ArrayList<>(), monsterRng, monsterListHelper);
        List<String> bossList = initializeBoss(monsterRng, monsterListHelper);

        return bossList.get(0);
    }

    public String nthElite(long seed, int encounterIndex, MonsterListHelper monsterListHelper) {
        ArrayList<String> monsterList = new ArrayList<>();
        ArrayList<String> eliteMonsterList = new ArrayList<>();
        Random monsterRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.MONSTER);

        generateWeakEnemies(monsterList, monsterRng, monsterListHelper);
        generateStrongEnemies(monsterList, monsterRng, monsterListHelper);
        generateElites(eliteMonsterList, monsterRng, monsterListHelper);

        return eliteMonsterList.get(encounterIndex);
    }

    public String nthCombat(long seed, int encounterIndex, MonsterListHelper monsterListHelper) {
        ArrayList<String> monsterList = new ArrayList<>();
        Random monsterRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.MONSTER);

        generateWeakEnemies(monsterList, monsterRng, monsterListHelper);
        generateStrongEnemies(monsterList, monsterRng, monsterListHelper);

        return monsterList.get(encounterIndex);
    }

    protected List<String> initializeBoss(Random monsterRng, MonsterListHelper monsterListHelper) {
        List<String> bossList = new ArrayList<>(monsterListHelper.bossPool);
        Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        return bossList;
    }

    protected void populateMonsterList(ArrayList<String> monsterList, ArrayList<MonsterInfo> monsterPool, int numMonsters, Random monsterRng) {
        for (int i = 0; i < numMonsters; ++i) {
            if (monsterList.isEmpty()) {
                monsterList.add(MonsterInfo.roll(monsterPool, monsterRng.random()));
                continue;
            }
            String randomMonster = MonsterInfo.roll(monsterPool, monsterRng.random());
            if (!randomMonster.equals(monsterList.get(monsterList.size() - 1))) {
                if (monsterList.size() > 1 && randomMonster.equals(monsterList.get(monsterList.size() - 2))) {
                    --i;
                    continue;
                }
                monsterList.add(randomMonster);
                continue;
            }
            --i;
        }
    }

    // Weak enemies are the first enemies in the act.
    private void generateWeakEnemies(ArrayList<String> monsterList, Random monsterRng, MonsterListHelper monsterListHelper) {
        populateMonsterList(monsterList, monsterListHelper.weakMonsterPool, monsterListHelper.weakMonsterCombats, monsterRng);
    }

    // Strong enemies are the next monsters in the act.
    private void generateStrongEnemies(ArrayList<String> monsterList, Random monsterRng, MonsterListHelper monsterListHelper) {
        populateFirstStrongEnemy(monsterList, monsterListHelper.strongMonsterPool, monsterListHelper.generateExclusions(monsterList), monsterRng);
        populateMonsterList(monsterList, monsterListHelper.strongMonsterPool, monsterListHelper.strongMonsterCombats, monsterRng);
    }

    private void generateElites(ArrayList<String> eliteMonsterList, Random monsterRng, MonsterListHelper monsterListHelper) {
        populateEliteMonsterList(eliteMonsterList, monsterRng, monsterListHelper);
    }

    private void populateFirstStrongEnemy(ArrayList<String> monsterList, ArrayList<MonsterInfo> monsterPool, ArrayList<String> exclusions, Random monsterRng) {
        String m = MonsterInfo.roll(monsterPool, monsterRng.random());
        while (exclusions.contains(m)) {
            m = MonsterInfo.roll(monsterPool, monsterRng.random());
        }
        monsterList.add(m);
    }

    private void populateEliteMonsterList(ArrayList<String> eliteMonsterList, Random monsterRng, MonsterListHelper monsterListHelper) {
        ArrayList<MonsterInfo> eliteMonsterPool = monsterListHelper.eliteMonsterPool;
        for (int i = 0; i < monsterListHelper.eliteMonsterCombats; ++i) {
            if (eliteMonsterList.isEmpty()) {
                String monsterInfo = MonsterInfo.roll(eliteMonsterPool, monsterRng.random());
                eliteMonsterList.add(monsterInfo);
                continue;
            }
            String eliteMonster = MonsterInfo.roll(eliteMonsterPool, monsterRng.random());
            if (!eliteMonster.equals(eliteMonsterList.get(eliteMonsterList.size() - 1))) {
                eliteMonsterList.add(eliteMonster);
                continue;
            }
            --i;
        }
    }
}
