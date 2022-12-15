package FilterTheSpire.simulators;

import FilterTheSpire.utils.ActHelper;
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

    public String firstBoss(long seed, ActHelper actHelper) {
        ArrayList<String> monsterList = new ArrayList<>();
        Random monsterRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.MONSTER);;

        generateWeakEnemies(monsterList, monsterRng, actHelper);
        generateStrongEnemies(monsterList, monsterRng, actHelper);
        generateElites(new ArrayList<>(), monsterRng, actHelper);
        List<String> bossList = initializeBoss(monsterRng, actHelper);

        return bossList.get(0);
    }

    public String nthElite(long seed, int encounterIndex, ActHelper actHelper) {
        ArrayList<String> monsterList = new ArrayList<>();
        ArrayList<String> eliteMonsterList = new ArrayList<>();
        Random monsterRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.MONSTER);

        generateWeakEnemies(monsterList, monsterRng, actHelper);
        generateStrongEnemies(monsterList, monsterRng, actHelper);
        generateElites(eliteMonsterList, monsterRng, actHelper);

        return eliteMonsterList.get(encounterIndex);
    }

    public String nthCombat(long seed, int encounterIndex, ActHelper actHelper) {
        ArrayList<String> monsterList = new ArrayList<>();
        Random monsterRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.MONSTER);

        generateWeakEnemies(monsterList, monsterRng, actHelper);
        generateStrongEnemies(monsterList, monsterRng, actHelper);

        return monsterList.get(encounterIndex);
    }

    protected List<String> initializeBoss(Random monsterRng, ActHelper actHelper) {
        List<String> bossList = new ArrayList<>(actHelper.bossPool);
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
    private void generateWeakEnemies(ArrayList<String> monsterList, Random monsterRng, ActHelper actHelper) {
        populateMonsterList(monsterList, actHelper.weakMonsterPool, actHelper.weakMonsterCombats, monsterRng);
    }

    // Strong enemies are the next monsters in the act.
    private void generateStrongEnemies(ArrayList<String> monsterList, Random monsterRng, ActHelper actHelper) {
        populateFirstStrongEnemy(monsterList, actHelper.strongMonsterPool, actHelper.generateExclusions(monsterList), monsterRng);
        populateMonsterList(monsterList, actHelper.strongMonsterPool, actHelper.strongMonsterCombats, monsterRng);
    }

    private void generateElites(ArrayList<String> eliteMonsterList, Random monsterRng, ActHelper actHelper) {
        populateEliteMonsterList(eliteMonsterList, monsterRng, actHelper);
    }

    private void populateFirstStrongEnemy(ArrayList<String> monsterList, ArrayList<MonsterInfo> monsterPool, ArrayList<String> exclusions, Random monsterRng) {
        String m = MonsterInfo.roll(monsterPool, monsterRng.random());
        while (exclusions.contains(m)) {
            m = MonsterInfo.roll(monsterPool, monsterRng.random());
        }
        monsterList.add(m);
    }

    private void populateEliteMonsterList(ArrayList<String> eliteMonsterList, Random monsterRng, ActHelper actHelper) {
        ArrayList<MonsterInfo> eliteMonsterPool = actHelper.eliteMonsterPool;
        for (int i = 0; i < actHelper.eliteMonsterCombats; ++i) {
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
