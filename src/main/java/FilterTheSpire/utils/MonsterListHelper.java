package FilterTheSpire.utils;

import com.megacrit.cardcrawl.monsters.MonsterInfo;

import java.util.ArrayList;

public abstract class MonsterListHelper {
    public abstract ArrayList<MonsterInfo> getWeakMonsterPool();
    public abstract ArrayList<MonsterInfo> getStrongMonsterPool();
    public abstract ArrayList<MonsterInfo> getEliteMonsterPool();
    public abstract ArrayList<String> getBossPool();
    public abstract ArrayList<String> generateExclusions(ArrayList<String> monsterList);
}
