package FilterTheSpire.simulators;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CardTransformSimulator {
    private static CardTransformSimulator singleton = null;

    public static CardTransformSimulator getInstance(){
        if (singleton == null){
            singleton = new CardTransformSimulator();
        }
        return singleton;
    }

    private CardTransformSimulator(){

    }

    public boolean isValid(long seed, HashMap<String, Integer> searchCards, int transformCount) {
        Random cardRng = new Random(seed);
        return isValid(cardRng, searchCards, transformCount);
    }

    public boolean isValid(Random cardRng, HashMap<String, Integer> searchCards, int transformCount) {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.addAll(AbstractDungeon.srcCommonCardPool.group);
        list.addAll(AbstractDungeon.srcUncommonCardPool.group);
        list.addAll(AbstractDungeon.srcRareCardPool.group);

        ArrayList<AbstractCard> results = new ArrayList<>();

        Set<String> cardList = searchCards.keySet();
        HashMap<String, Integer> transformCounts = new HashMap<>();
        for (String cardId: cardList) {
            transformCounts.put(cardId, 0);
        }

        for (int i = 0; i < transformCount; ++i) {
            AbstractCard c = list.get(cardRng.random(list.size() - 1));
            results.add(c);
            if (cardList.contains(c.cardID)){
                transformCounts.computeIfPresent(c.cardID, (k, v) -> v + 1);
            }
        }

        boolean isValid = true;
        for (String cardId : cardList) {
            if (searchCards.get(cardId) > transformCounts.get(cardId)) {
                isValid = false;
            }
        }

        if (isValid) {
            System.out.println("------");
            System.out.println("The cards chosen in this seed are: ");
            for (AbstractCard c : results)
                System.out.print(c.name + " ");
            System.out.println("------");
        }
        return isValid;
    }
}
