package FilterTheSpire.factory;

import FilterTheSpire.utils.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ModHelper;
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

    /**
     * Returns the card pool for the character.
     * @param chosenClass: class of the character pool
     * @param shouldReverseCardOrder: should be TRUE if the card pool is reversed, seems very rare
     * @return all cards in the chosen Character's pool in a deterministic order
     */
    public static List<String> getCardPool(AbstractPlayer.PlayerClass chosenClass, boolean shouldReverseCardOrder){
        CharacterPool pool;
        ArrayList<String> cardPool = new ArrayList<>();
        if (ModHelper.isModEnabled("Diverse")){
            ArrayList<CharacterPool> colors = new ArrayList<>();
            colors.add(WatcherPool.getInstance());
            colors.add(DefectPool.getInstance());
            colors.add(SilentPool.getInstance());
            colors.add(IroncladPool.getInstance());
            cardPool.addAll(CardPoolHelper.getOrderedCardPoolForColors(colors, shouldReverseCardOrder));
        } else {
            pool = getCharacterPool(chosenClass);
            cardPool.addAll(pool.getCardPool(shouldReverseCardOrder));
        }
        return cardPool;
    }
}
