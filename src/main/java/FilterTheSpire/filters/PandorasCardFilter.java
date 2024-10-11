package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardTransformSimulator;
import FilterTheSpire.utils.cache.RunInfoCache;
import FilterTheSpire.utils.config.FilterType;
import FilterTheSpire.utils.helpers.SeedHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.random.Random;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class PandorasCardFilter extends AbstractFilter {
    private HashMap<String, Integer> searchCards;
    private int totalTransformCount;
    private TreeMap<AbstractCard.CardRarity, Boolean> cardRaritiesShouldReverse;

    public PandorasCardFilter(List<String> searchCards) {
        this();
        HashMap<String, Integer> cardCounts = new HashMap<>();
        for (String s : searchCards) {
            cardCounts.put(s, cardCounts.getOrDefault(s, 0) + 1);
        }
        this.searchCards = cardCounts;
    }

    public PandorasCardFilter(HashMap<String, Integer> searchCards){
        this();
        this.searchCards = searchCards;
    }

    private PandorasCardFilter(){
        type = FilterType.PandorasCard;
        HashMap<AbstractPlayer.PlayerClass, Integer> starterCardCounts = new HashMap<>();
        starterCardCounts.put(AbstractPlayer.PlayerClass.IRONCLAD, 9);
        starterCardCounts.put(AbstractPlayer.PlayerClass.THE_SILENT, 10);
        starterCardCounts.put(AbstractPlayer.PlayerClass.DEFECT, 8);
        starterCardCounts.put(AbstractPlayer.PlayerClass.WATCHER, 8);
        this.sortOrder = 2;
        this.totalTransformCount = starterCardCounts.get(RunInfoCache.currentCharacter);

        // Generate all card rarities and don't reverse any of them
        cardRaritiesShouldReverse = new TreeMap<>();
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.COMMON, false);
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.UNCOMMON, false);
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.RARE, false);
    }

    public boolean isSeedValid(long seed) {
        Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.CARDRANDOM);
        return CardTransformSimulator.getInstance().isValid(cardRng, searchCards, totalTransformCount, cardRaritiesShouldReverse);
    }

    public String generateHashKey() {
        return null;
    }
}
