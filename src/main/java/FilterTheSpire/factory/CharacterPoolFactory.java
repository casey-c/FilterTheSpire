package FilterTheSpire.factory;

import FilterTheSpire.utils.helpers.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
     * @param cardRarities: A map of the rarities we want to generate the pool for.
     *                    Also if the pool should be reversed or not
     * @return all cards in the chosen Character's pool in a deterministic order
     */
    public static List<String> getCardPool(AbstractPlayer.PlayerClass chosenClass, TreeMap<AbstractCard.CardRarity, Boolean> cardRarities){
        CharacterPool pool;
        ArrayList<String> cardPool = new ArrayList<>();
        if (ModHelper.isModEnabled("Diverse")){
            ArrayList<CharacterPool> colors = new ArrayList<>();
            colors.add(WatcherPool.getInstance());
            colors.add(DefectPool.getInstance());
            colors.add(SilentPool.getInstance());
            colors.add(IroncladPool.getInstance());
            cardPool.addAll(CardPoolHelper.getOrderedCardPoolForColors(colors, cardRarities));
        } else {
            pool = getCharacterPool(chosenClass);
            cardPool.addAll(pool.getCardPool(cardRarities));
        }
        return cardPool;
    }
}
