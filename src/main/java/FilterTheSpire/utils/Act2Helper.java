package FilterTheSpire.utils;

import com.megacrit.cardcrawl.monsters.MonsterInfo;

import java.util.ArrayList;

public class Act2Helper extends ActHelper {
    private static Act2Helper singleton = null;

    public static Act2Helper getInstance(){
        if (singleton == null){
            singleton = new Act2Helper();
        }
        return singleton;
    }

    private Act2Helper(){
        super();

        weakMonsterCombats = 2;
        strongMonsterCombats = 12;
        eliteMonsterCombats = 10;
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

    protected void initializeWeakMonsters() {
        weakMonsterPool.add(new MonsterInfo("Spheric Guardian", 2.0F));
        weakMonsterPool.add(new MonsterInfo("Chosen", 2.0F));
        weakMonsterPool.add(new MonsterInfo("Shell Parasite", 2.0F));
        weakMonsterPool.add(new MonsterInfo("3 Byrds", 2.0F));
        weakMonsterPool.add(new MonsterInfo("2 Thieves", 2.0F));
        MonsterInfo.normalizeWeights(weakMonsterPool);
    }

    protected void initializeStrongMonsters() {
        strongMonsterPool.add(new MonsterInfo("Chosen and Byrds", 2.0F));
        strongMonsterPool.add(new MonsterInfo("Sentry and Sphere", 2.0F));
        strongMonsterPool.add(new MonsterInfo("Snake Plant", 6.0F));
        strongMonsterPool.add(new MonsterInfo("Snecko", 4.0F));
        strongMonsterPool.add(new MonsterInfo("Centurion and Healer", 6.0F));
        strongMonsterPool.add(new MonsterInfo("Cultist and Chosen", 3.0F));
        strongMonsterPool.add(new MonsterInfo("3 Cultists", 3.0F));
        strongMonsterPool.add(new MonsterInfo("Shelled Parasite and Fungi", 3.0F));
        MonsterInfo.normalizeWeights(strongMonsterPool);
    }

    protected void initializeElites() {
        eliteMonsterPool.add(new MonsterInfo("Gremlin Leader", 1.0F));
        eliteMonsterPool.add(new MonsterInfo("Slavers", 1.0F));
        eliteMonsterPool.add(new MonsterInfo("Book of Stabbing", 1.0F));
        MonsterInfo.normalizeWeights(eliteMonsterPool);
    }

    protected void initializeBosses() {
        bossPool.add("Automaton");
        bossPool.add("Collector");
        bossPool.add("Champ");
    }
}
