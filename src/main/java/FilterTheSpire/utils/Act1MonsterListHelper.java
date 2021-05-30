package FilterTheSpire.utils;

import com.megacrit.cardcrawl.monsters.MonsterInfo;

import java.util.ArrayList;

public class Act1MonsterListHelper extends MonsterListHelper {
    public ArrayList<MonsterInfo> weakMonsterPool = new ArrayList<>();
    public ArrayList<MonsterInfo> strongMonsterPool = new ArrayList<>();
    public ArrayList<MonsterInfo> eliteMonsterPool = new ArrayList<>();
    public ArrayList<String> bossPool = new ArrayList<>();

    private static Act1MonsterListHelper singleton = null;

    public static Act1MonsterListHelper getInstance(){
        if (singleton == null){
            singleton = new Act1MonsterListHelper();
        }
        return singleton;
    }

    private Act1MonsterListHelper(){
        weakMonsterPool.add(new MonsterInfo("Cultist", 2.0F));
        weakMonsterPool.add(new MonsterInfo("Jaw Worm", 2.0F));
        weakMonsterPool.add(new MonsterInfo("2 Louse", 2.0F));
        weakMonsterPool.add(new MonsterInfo("Small Slimes", 2.0F));
        MonsterInfo.normalizeWeights(weakMonsterPool);

        strongMonsterPool.add(new MonsterInfo("Blue Slaver", 2.0F));
        strongMonsterPool.add(new MonsterInfo("Gremlin Gang", 1.0F));
        strongMonsterPool.add(new MonsterInfo("Looter", 2.0F));
        strongMonsterPool.add(new MonsterInfo("Large Slime", 2.0F));
        strongMonsterPool.add(new MonsterInfo("Lots of Slimes", 1.0F));
        strongMonsterPool.add(new MonsterInfo("Exordium Thugs", 1.5F));
        strongMonsterPool.add(new MonsterInfo("Exordium Wildlife", 1.5F));
        strongMonsterPool.add(new MonsterInfo("Red Slaver", 1.0F));
        strongMonsterPool.add(new MonsterInfo("3 Louse", 2.0F));
        strongMonsterPool.add(new MonsterInfo("2 Fungi Beasts", 2.0F));
        MonsterInfo.normalizeWeights(strongMonsterPool);

        eliteMonsterPool.add(new MonsterInfo("Gremlin Nob", 1.0F));
        eliteMonsterPool.add(new MonsterInfo("Lagavulin", 1.0F));
        eliteMonsterPool.add(new MonsterInfo("3 Sentries", 1.0F));
        MonsterInfo.normalizeWeights(eliteMonsterPool);

        bossPool.add("The Guardian");
        bossPool.add("Hexaghost");
        bossPool.add("Slime Boss");
    }

    @Override
    public ArrayList<MonsterInfo> getWeakMonsterPool() {
        return weakMonsterPool;
    }

    @Override
    public ArrayList<MonsterInfo> getStrongMonsterPool() {
        return strongMonsterPool;
    }

    @Override
    public ArrayList<MonsterInfo> getEliteMonsterPool() {
        return eliteMonsterPool;
    }

    @Override
    public ArrayList<String> getBossPool() {
        return bossPool;
    }

    @Override
    public ArrayList<String> generateExclusions(ArrayList<String> monsterList) {
        ArrayList<String> retVal = new ArrayList<>();
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
}
