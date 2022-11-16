package FilterTheSpire.filters;

import FilterTheSpire.patches.EventHelperPatch;
import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.random.Random;

import java.util.Arrays;

public class Act1EventFilter extends AbstractFilter {
    private String event;

    public Act1EventFilter(String event){
        this.event = event;
    }

    public boolean isSeedValid(long seed) {
        Random rng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.EVENT);
        float roll = rng.random();

        float MONSTER_CHANCE = 0.1F;
        float SHOP_CHANCE = 0.03F;
        float TREASURE_CHANCE = 0.02F;
        int monsterSize = (int)(MONSTER_CHANCE * 100.0F);
        int shopSize = (int)(SHOP_CHANCE * 100.0F);
        int treasureSize = (int)(TREASURE_CHANCE * 100.0F);

        int fillIndex = 0;
        EventHelper.RoomResult[] possibleResults = new EventHelper.RoomResult[100];
        Arrays.fill(possibleResults, EventHelper.RoomResult.EVENT);
        Arrays.fill(possibleResults, Math.min(99, fillIndex), Math.min(100, fillIndex + monsterSize), EventHelper.RoomResult.MONSTER);
        fillIndex += monsterSize;
        Arrays.fill(possibleResults, Math.min(99, fillIndex), Math.min(100, fillIndex + shopSize), EventHelper.RoomResult.SHOP);
        fillIndex += shopSize;
        Arrays.fill(possibleResults, Math.min(99, fillIndex), Math.min(100, fillIndex + treasureSize), EventHelper.RoomResult.TREASURE);
        EventHelper.RoomResult choice = possibleResults[(int)(roll * 100.0F)];

        if (choice != EventHelper.RoomResult.EVENT) {
            return false;
        }
        AbstractDungeon.generateEvent(rng);
        return EventHelperPatch.eventName.equals(this.event);
    }
}
