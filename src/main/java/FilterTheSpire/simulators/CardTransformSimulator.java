package FilterTheSpire.simulators;

import FilterTheSpire.factory.CharacterPoolFactory;
import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;

import java.util.*;

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
        Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.CARDRANDOM);
        return isValid(cardRng, searchCards, transformCount);
    }

    public boolean isValid(Random cardRng, HashMap<String, Integer> searchCards, int transformCount) {
        List<String> cardPool = CharacterPoolFactory.getCardPool(AbstractDungeon.player.chosenClass);
        ArrayList<String> results = new ArrayList<>();

        Set<String> cardList = searchCards.keySet();
        HashMap<String, Integer> transformCounts = new HashMap<>();
        for (String cardId: cardList) {
            transformCounts.put(cardId, 0);
        }

        for (int i = 0; i < transformCount; ++i) {
            String c = cardPool.get(cardRng.random(cardPool.size() - 1));
            results.add(c);
            if (cardList.contains(c)){
                transformCounts.computeIfPresent(c, (k, v) -> v + 1);
            }
        }

        boolean isValid = true;
        for (String cardId : cardList) {
            if (searchCards.get(cardId) > transformCounts.get(cardId)) {
                isValid = false;
                break;
            }
        }

        if (isValid) {
            System.out.println("------");
            System.out.println("The cards chosen in this seed are: ");
            for (String c : results)
                System.out.print(c + " ");
            System.out.println("------");
        }
        return isValid;
    }
}
