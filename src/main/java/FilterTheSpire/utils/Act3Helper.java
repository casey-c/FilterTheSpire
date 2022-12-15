package FilterTheSpire.utils;

import com.megacrit.cardcrawl.monsters.MonsterInfo;

import java.util.ArrayList;

public class Act3Helper extends ActHelper {
    private static Act3Helper singleton = null;

    public static Act3Helper getInstance(){
        if (singleton == null){
            singleton = new Act3Helper();
        }
        return singleton;
    }

    private Act3Helper(){
        super();

        weakMonsterCombats = 2;
        strongMonsterCombats = 12;
        eliteMonsterCombats = 10;
    }

    public ArrayList<String> generateExclusions(ArrayList<String> monsterList) {
        ArrayList<String> retVal = new ArrayList<>();
        switch (monsterList.get(monsterList.size() - 1)) {
            case "3 Shapes": {
                retVal.add("4 Shapes");
                break;
            }
            case "3 Darklings": {
                retVal.add("3 Darklings");
                break;
            }
            case "Orb Walker": {
                retVal.add("Orb Walker");
                break;
            }
        }
        return retVal;
    }

    protected void initializeWeakMonsters() {
        weakMonsterPool.add(new MonsterInfo("3 Darklings", 2.0F));
        weakMonsterPool.add(new MonsterInfo("Orb Walker", 2.0F));
        weakMonsterPool.add(new MonsterInfo("3 Shapes", 2.0F));
        MonsterInfo.normalizeWeights(weakMonsterPool);
    }

    protected void initializeStrongMonsters() {
        strongMonsterPool.add(new MonsterInfo("Spire Growth", 1.0F));
        strongMonsterPool.add(new MonsterInfo("Transient", 1.0F));
        strongMonsterPool.add(new MonsterInfo("4 Shapes", 1.0F));
        strongMonsterPool.add(new MonsterInfo("Maw", 1.0F));
        strongMonsterPool.add(new MonsterInfo("Sphere and 2 Shapes", 1.0F));
        strongMonsterPool.add(new MonsterInfo("Jaw Worm Horde", 1.0F));
        strongMonsterPool.add(new MonsterInfo("3 Darklings", 1.0F));
        strongMonsterPool.add(new MonsterInfo("Writhing Mass", 1.0F));
        MonsterInfo.normalizeWeights(strongMonsterPool);
    }

    protected void initializeElites() {
        eliteMonsterPool.add(new MonsterInfo("Giant Head", 2.0F));
        eliteMonsterPool.add(new MonsterInfo("Nemesis", 2.0F));
        eliteMonsterPool.add(new MonsterInfo("Reptomancer", 2.0F));
        MonsterInfo.normalizeWeights(eliteMonsterPool);
    }

    protected void initializeBosses() {
        bossPool.add("Awakened One");
        bossPool.add("Time Eater");
        bossPool.add("Donu and Deca");
    }
}
