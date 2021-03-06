package FilterTheSpire.factory;

import FilterTheSpire.utils.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.List;

public class CharacterPoolFactory {
    public static CharacterPool getCharacterPool(AbstractPlayer.PlayerClass chosenClass){
        switch (chosenClass){
            case IRONCLAD:
                return IroncladPool.getInstance();
            case THE_SILENT:
                return SilentPool.getInstance();
            case DEFECT:
                return DefectPool.getInstance();
            case WATCHER:
                return WatcherPool.getInstance();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static ArrayList<String> getRelicPool(AbstractPlayer.PlayerClass chosenClass, AbstractRelic.RelicTier relicTier){
        CharacterPool pool = getCharacterPool(chosenClass);
        switch (relicTier){
            case COMMON:
                return pool.commonRelicPool;
            case UNCOMMON:
                return pool.uncommonRelicPool;
            case RARE:
                return pool.rareRelicPool;
            case BOSS:
                return pool.bossRelicPool;
            case SHOP:
                return pool.shopRelicPool;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static List<String> getCardPool(AbstractPlayer.PlayerClass chosenClass){
        CharacterPool pool = getCharacterPool(chosenClass);
        return pool.cardPool;
    }
}
