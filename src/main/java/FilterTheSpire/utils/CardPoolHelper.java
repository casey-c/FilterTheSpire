package FilterTheSpire.utils;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ModHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardPoolHelper {
    public static ArrayList<String> getOrderedCardPoolForColors(ArrayList<CharacterPool> colors, boolean shouldReverseCommonCardPool){
        ArrayList<AbstractCard.CardRarity> cardRarities = new ArrayList<>();
        cardRarities.add(AbstractCard.CardRarity.COMMON);
        cardRarities.add(AbstractCard.CardRarity.UNCOMMON);
        cardRarities.add(AbstractCard.CardRarity.RARE);
        return getOrderedCardPoolForColors(colors, cardRarities, shouldReverseCommonCardPool);
    }

    public static ArrayList<String> getOrderedCardPoolForColors(ArrayList<CharacterPool> colors, List<AbstractCard.CardRarity> cardRarities, boolean shouldReverseCommonCardPool){
        ArrayList<String> cardPool = new ArrayList<>();

        boolean hasColorlessEnabled = ModHelper.isModEnabled("Colorless Cards");
        for (AbstractCard.CardRarity rarity: cardRarities) {
            for (int i = 0; i < colors.size(); i++) {
                CharacterPool color = colors.get(i);
                switch (rarity){
                    case COMMON:
                        if (shouldReverseCommonCardPool){
                            cardPool.addAll(color.getReversedCommonCardPool());
                        } else {
                            cardPool.addAll(color.commonCardPool);
                        }
                        break;
                    case UNCOMMON:
                        cardPool.addAll(color.uncommonCardPool);

                        if (i == (colors.size() - 1) && hasColorlessEnabled) {
                            cardPool.addAll(getUncommonColorlessCards());
                        }
                        break;
                    case RARE:
                        cardPool.addAll(color.rareCardPool);

                        if (i == (colors.size() - 1) && hasColorlessEnabled) {
                            cardPool.addAll(getRareColorlessCards());
                        }
                        break;
                }
            }
        }
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
