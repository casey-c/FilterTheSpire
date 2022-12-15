package FilterTheSpire.utils;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class ActHelper {
    public ActHelper(){
        initializeWeakMonsters();
        initializeStrongMonsters();
        initializeElites();
        initializeBosses();
    }

    public int weakMonsterCombats;
    public int strongMonsterCombats;
    public int eliteMonsterCombats;
    public ArrayList<String> eventList = new ArrayList<>();
    public ArrayList<String> shrineList = new ArrayList<>();
    public ArrayList<MonsterInfo> weakMonsterPool = new ArrayList<>();
    public ArrayList<MonsterInfo> strongMonsterPool = new ArrayList<>();
    public ArrayList<MonsterInfo> eliteMonsterPool = new ArrayList<>();
    public ArrayList<String> bossPool = new ArrayList<>();
    public abstract ArrayList<String> generateExclusions(ArrayList<String> monsterList);
    protected abstract void initializeWeakMonsters();
    protected abstract void initializeStrongMonsters();
    protected abstract void initializeElites();
    protected abstract void initializeBosses();
}
