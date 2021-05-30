package FilterTheSpire.utils;

import com.megacrit.cardcrawl.monsters.MonsterInfo;

import java.util.ArrayList;

public abstract class MonsterListHelper {
    public int weakMonsterCombats;
    public int strongMonsterCombats;
    public int eliteMonsterCombats;
    public ArrayList<MonsterInfo> weakMonsterPool = new ArrayList<>();
    public ArrayList<MonsterInfo> strongMonsterPool = new ArrayList<>();
    public ArrayList<MonsterInfo> eliteMonsterPool = new ArrayList<>();
    public ArrayList<String> bossPool = new ArrayList<>();
    public abstract ArrayList<String> generateExclusions(ArrayList<String> monsterList);
}
