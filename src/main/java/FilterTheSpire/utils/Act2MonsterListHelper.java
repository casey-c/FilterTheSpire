package FilterTheSpire.utils;

import com.megacrit.cardcrawl.monsters.MonsterInfo;

import java.util.ArrayList;

public class Act2MonsterListHelper extends MonsterListHelper {
    public ArrayList<MonsterInfo> weakMonsterPool = new ArrayList<>();
    public ArrayList<MonsterInfo> strongMonsterPool = new ArrayList<>();
    public ArrayList<MonsterInfo> eliteMonsterPool = new ArrayList<>();
    public ArrayList<String> bossPool = new ArrayList<>();

    private static Act2MonsterListHelper singleton = null;

    public static Act2MonsterListHelper getInstance(){
        if (singleton == null){
            singleton = new Act2MonsterListHelper();
        }
        return singleton;
    }

    private Act2MonsterListHelper(){
        weakMonsterPool.add(new MonsterInfo("Spheric Guardian", 2.0F));
        weakMonsterPool.add(new MonsterInfo("Chosen", 2.0F));
        weakMonsterPool.add(new MonsterInfo("Shell Parasite", 2.0F));
        weakMonsterPool.add(new MonsterInfo("3 Byrds", 2.0F));
        weakMonsterPool.add(new MonsterInfo("2 Thieves", 2.0F));
        MonsterInfo.normalizeWeights(weakMonsterPool);

        strongMonsterPool.add(new MonsterInfo("Chosen and Byrds", 2.0F));
        strongMonsterPool.add(new MonsterInfo("Sentry and Sphere", 2.0F));
        strongMonsterPool.add(new MonsterInfo("Snake Plant", 6.0F));
        strongMonsterPool.add(new MonsterInfo("Snecko", 4.0F));
        strongMonsterPool.add(new MonsterInfo("Centurion and Healer", 6.0F));
        strongMonsterPool.add(new MonsterInfo("Cultist and Chosen", 3.0F));
        strongMonsterPool.add(new MonsterInfo("3 Cultists", 3.0F));
        strongMonsterPool.add(new MonsterInfo("Shelled Parasite and Fungi", 3.0F));
        MonsterInfo.normalizeWeights(strongMonsterPool);

        eliteMonsterPool.add(new MonsterInfo("Gremlin Leader", 1.0F));
        eliteMonsterPool.add(new MonsterInfo("Slavers", 1.0F));
        eliteMonsterPool.add(new MonsterInfo("Book of Stabbing", 1.0F));
        MonsterInfo.normalizeWeights(eliteMonsterPool);

        bossPool.add("Automaton");
        bossPool.add("Collector");
        bossPool.add("Champ");
    }

    public int getWeakMonsterCombats() { return 2; }

    public int getStrongMonsterCombats() { return 12; }

    public int getEliteMonsterCombats() { return 10; }

    public ArrayList<MonsterInfo> getWeakMonsterPool() {
        return weakMonsterPool;
    }

    public ArrayList<MonsterInfo> getStrongMonsterPool() {
        return strongMonsterPool;
    }

    public ArrayList<MonsterInfo> getEliteMonsterPool() {
        return eliteMonsterPool;
    }

    public ArrayList<String> getBossPool() {
        return bossPool;
    }

    public ArrayList<String> generateExclusions(ArrayList<String> monsterList) {
        ArrayList<String> retVal = new ArrayList<>();
        switch (monsterList.get(monsterList.size() - 1)) {
            case "3 Byrds": {
                retVal.add("Chosen and Byrds");
                break;
            }
            case "Spheric Guardian": {
                retVal.add("Sentry and Sphere");
                break;
            }
            case "Chosen": {
                retVal.add("Chosen and Byrds");
                retVal.add("Cultist and Chosen");
                break;
            }
        }
        return retVal;
    }
}
