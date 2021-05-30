package FilterTheSpire.utils;

import com.megacrit.cardcrawl.monsters.MonsterInfo;

import java.util.ArrayList;

public class Act3MonsterListHelper extends MonsterListHelper {
    private static Act3MonsterListHelper singleton = null;

    public static Act3MonsterListHelper getInstance(){
        if (singleton == null){
            singleton = new Act3MonsterListHelper();
        }
        return singleton;
    }

    private Act3MonsterListHelper(){
        weakMonsterCombats = 2;
        strongMonsterCombats = 12;
        eliteMonsterCombats = 10;

        weakMonsterPool.add(new MonsterInfo("3 Darklings", 2.0F));
        weakMonsterPool.add(new MonsterInfo("Orb Walker", 2.0F));
        weakMonsterPool.add(new MonsterInfo("3 Shapes", 2.0F));
        MonsterInfo.normalizeWeights(weakMonsterPool);

        strongMonsterPool.add(new MonsterInfo("Spire Growth", 1.0F));
        strongMonsterPool.add(new MonsterInfo("Transient", 1.0F));
        strongMonsterPool.add(new MonsterInfo("4 Shapes", 1.0F));
        strongMonsterPool.add(new MonsterInfo("Maw", 1.0F));
        strongMonsterPool.add(new MonsterInfo("Sphere and 2 Shapes", 1.0F));
        strongMonsterPool.add(new MonsterInfo("Jaw Worm Horde", 1.0F));
        strongMonsterPool.add(new MonsterInfo("3 Darklings", 1.0F));
        strongMonsterPool.add(new MonsterInfo("Writhing Mass", 1.0F));
        MonsterInfo.normalizeWeights(strongMonsterPool);

        eliteMonsterPool.add(new MonsterInfo("Giant Head", 2.0F));
        eliteMonsterPool.add(new MonsterInfo("Nemesis", 2.0F));
        eliteMonsterPool.add(new MonsterInfo("Reptomancer", 2.0F));
        MonsterInfo.normalizeWeights(eliteMonsterPool);

        bossPool.add("Awakened One");
        bossPool.add("Time Eater");
        bossPool.add("Donu and Deca");
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
}
