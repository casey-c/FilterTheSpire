package FilterTheSpire.utils;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ModHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardPoolHelper {
    public static ArrayList<String> getOrderedCardPoolForColors(ArrayList<CharacterPool> colors, boolean shouldReverseCardOrder){
        ArrayList<AbstractCard.CardRarity> cardRarities = new ArrayList<>();
        cardRarities.add(AbstractCard.CardRarity.COMMON);
        cardRarities.add(AbstractCard.CardRarity.UNCOMMON);
        cardRarities.add(AbstractCard.CardRarity.RARE);
        ArrayList<String> cardPool = new ArrayList<>();

        boolean hasColorlessEnabled = ModHelper.isModEnabled("Colorless Cards");
        for (AbstractCard.CardRarity rarity: cardRarities) {
            for (int i = 0; i < colors.size(); i++) {
                CharacterPool color = colors.get(i);
                switch (rarity){
                    case COMMON:
                        cardPool.addAll(getCardsInReversedOrderIfNeeded(color.commonCardPool, shouldReverseCardOrder));
                        break;
                    case UNCOMMON:
                        cardPool.addAll(getCardsInReversedOrderIfNeeded(color.uncommonCardPool, shouldReverseCardOrder));

                        if (i == (colors.size() - 1) && hasColorlessEnabled) {
                            cardPool.addAll(getCardsInReversedOrderIfNeeded(getUncommonColorlessCards(), shouldReverseCardOrder));
                        }
                        break;
                    case RARE:
                        cardPool.addAll(getCardsInReversedOrderIfNeeded(color.rareCardPool, shouldReverseCardOrder));

                        if (i == (colors.size() - 1) && hasColorlessEnabled) {
                            cardPool.addAll(getCardsInReversedOrderIfNeeded(getRareColorlessCards(), shouldReverseCardOrder));

                            cardPool.addAll(getRareColorlessCards());
                        }
                        break;
                }
            }
        }
        return cardPool;
    }

    private static List<String> getCardsInReversedOrderIfNeeded(List<String> cards, boolean shouldReverseCardOrder){
        ArrayList<String> resultingCardList = new ArrayList<>(cards);
        if (shouldReverseCardOrder) {
            Collections.reverse(resultingCardList);
        }
        return resultingCardList;
    }

    private static ArrayList<String> getUncommonColorlessCards(){
        return new ArrayList<>(Arrays.asList(
            "Madness",
            "Mind Blast",
            "Jack of All Trades",
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
            "Panic Button",
            "Dark Shackles"
        ));
    }

    private static ArrayList<String> getRareColorlessCards(){
        return new ArrayList<>(Arrays.asList(
            "Thinking Ahead",
            "Metamorphosis",
            "Master of Strategy",
            "Magnetism",
            "Chrysalis",
            "Transmutation",
            "Hand of Greed",
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
}
