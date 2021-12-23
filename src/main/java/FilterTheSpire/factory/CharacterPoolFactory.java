package FilterTheSpire.factory;

import FilterTheSpire.utils.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        CharacterPool pool;
        if (ModHelper.isModEnabled("Diverse")){
//            ArrayList<AbstractPlayer.PlayerClass> classes = new ArrayList<>();
//            classes.add(AbstractPlayer.PlayerClass.WATCHER);
//            classes.add(AbstractPlayer.PlayerClass.DEFECT);
//            classes.add(AbstractPlayer.PlayerClass.THE_SILENT);
//            classes.add(AbstractPlayer.PlayerClass.IRONCLAD);
//
//            for (AbstractPlayer.PlayerClass character: classes) {
//                cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.IRONCLAD).cardPool);
//                cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.THE_SILENT).cardPool);
//                cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.DEFECT).cardPool);
//                cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.WATCHER).cardPool);
//            }

            ArrayList<String> cards = new ArrayList<>();
            cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.WATCHER).commonCardPool);
            cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.DEFECT).commonCardPool);
            cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.THE_SILENT).commonCardPool);
            cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.IRONCLAD).commonCardPool);
            cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.WATCHER).uncommonCardPool);
            cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.DEFECT).uncommonCardPool);
            cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.THE_SILENT).uncommonCardPool);
            cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.IRONCLAD).uncommonCardPool);
            cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.WATCHER).rareCardPool);
            cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.DEFECT).rareCardPool);
            cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.THE_SILENT).rareCardPool);
            cards.addAll(getCharacterPool(AbstractPlayer.PlayerClass.IRONCLAD).rareCardPool);
            return cards;
        } else {
            pool = getCharacterPool(chosenClass);
            return pool.getCardPool();
        }
    }
}
