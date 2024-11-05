package FilterTheSpire.utils.helpers;

import FilterTheSpire.utils.cache.RunInfoCache;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.*;

public class CardPoolHelper {
    public static ArrayList<String> getOrderedCardPoolForColors(ArrayList<CharacterPool> colors, TreeMap<AbstractCard.CardRarity, Boolean> cardRaritiesAndShouldReverse){
        int hashKey = cardRaritiesAndShouldReverse.hashCode() + colors.hashCode();
        if (RunInfoCache.rarityMapCardPool.containsKey(hashKey)) {
            return RunInfoCache.rarityMapCardPool.get(hashKey);
        }
        ArrayList<String> cardPool = new ArrayList<>();
        ArrayList<String> rarityCardPool = new ArrayList<>();

        boolean hasColorlessEnabled = RunInfoCache.modList.contains("Colorless Cards");
        for (AbstractCard.CardRarity rarity: cardRaritiesAndShouldReverse.keySet()) {
            for (int i = 0; i < colors.size(); i++) {
                CharacterPool color = colors.get(i);
                switch (rarity){
                    case COMMON:
                        rarityCardPool.addAll(color.commonCardPool);
                        break;
                    case UNCOMMON:
                        rarityCardPool.addAll(color.uncommonCardPool);

                        if (i == (colors.size() - 1) && hasColorlessEnabled) {
                            rarityCardPool.addAll(getUncommonColorlessCards());
                        }
                        break;
                    case RARE:
                        rarityCardPool.addAll(color.rareCardPool);

                        if (i == (colors.size() - 1) && hasColorlessEnabled) {
                            rarityCardPool.addAll(getRareColorlessCards());
                        }
                        break;
                }
            }
            if (cardRaritiesAndShouldReverse.get(rarity)){
                Collections.reverse(rarityCardPool);
            }
            cardPool.addAll(rarityCardPool);
            rarityCardPool.clear();
        }
        RunInfoCache.rarityMapCardPool.put(hashKey, cardPool);
        return cardPool;
    }

    public static ArrayList<String> getUncommonColorlessCards(){
        return new ArrayList<>(Arrays.asList(
            "Madness",
            "Mind Blast",
            "Jack Of All Trades",
            "Swift Strike",
            "Good Instincts",
            "Finesse",
            "Discovery",
            "Panacea",
            "Purity",
            "Enlightenment",
            "Forethought",
            "Flash of Steel",
            "Deep Breath",
            "Bandage Up",
            "Blind",
            "Impatience",
            "Dramatic Entrance",
            "Trip",
            "PanicButton",
            "Dark Shackles"
        ));
    }

    public static ArrayList<String> getRareColorlessCards(){
        return new ArrayList<>(Arrays.asList(
            "Thinking Ahead",
            "Metamorphosis",
            "Master of Strategy",
            "Magnetism",
            "Chrysalis",
            "Transmutation",
            "HandOfGreed",
            "Mayhem",
            "Apotheosis",
            "Secret Weapon",
            "Panache",
            "Violence",
            "Secret Technique",
            "The Bomb",
            "Sadistic Nature"
        ));
    }

    // the card pools are set depending on the settings the player sets (custom runs usually), reset it when we find a seed for the next run
    public static void resetCharacterCardPoolsForSettings() {
        IroncladPool.getInstance().resetCardPoolsForSettings();
        SilentPool.getInstance().resetCardPoolsForSettings();
        DefectPool.getInstance().resetCardPoolsForSettings();
        WatcherPool.getInstance().resetCardPoolsForSettings();
    }
}
