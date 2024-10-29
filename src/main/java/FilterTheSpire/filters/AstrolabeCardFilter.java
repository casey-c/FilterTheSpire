package FilterTheSpire.filters;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.simulators.CardTransformSimulator;
import FilterTheSpire.utils.config.FilterType;
import FilterTheSpire.utils.helpers.SeedHelper;
import FilterTheSpire.utils.types.RunCheckpoint;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.Astrolabe;

import java.util.*;

public class AstrolabeCardFilter extends AbstractFilter {
    private final HashMap<String, Integer> searchCards;
    private final TreeMap<AbstractCard.CardRarity, Boolean> cardRaritiesShouldReverse;

    public AstrolabeCardFilter(HashMap<String, Integer> searchCards){
        this.type = FilterType.AstrolabeCard;
        this.sortOrder = 1;
        this.searchCards = searchCards;
        this.displayName = "Astrolabe Card Filter";

        // generate all card rarities and reverse the common pool
        cardRaritiesShouldReverse = new TreeMap<>();
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.COMMON, true);
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.UNCOMMON, false);
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.RARE, false);
    }

    public boolean isSeedValid(long seed) {
        ArrayList<AbstractFilter> filterList = FilterManager.getFilters();
        boolean hasAstrolabeFilter = filterList
                .stream()
                .filter(f -> f.type == FilterType.NthBossRelic)
                .map(f -> (NthBossRelicFilter) f)
                .anyMatch(f -> f.bossRelicNames.contains(Astrolabe.ID) && f.pickupCheckpoint == RunCheckpoint.NEOW);
        if (hasAstrolabeFilter){
            Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.MISC);
            cardRng.random();
            return CardTransformSimulator.getInstance().isValid(cardRng, searchCards, 3, cardRaritiesShouldReverse);
        }
        return true;
    }

    public boolean shouldDisplay(){
        return searchCards.size() > 0;
    }

    public String generateHashKey() {
        return FilterTheSpire.config.generateHashKey(type, Collections.singletonList(0));
    }

    public String toString(){
        return "Card transforms: " + searchCards;
    }
}
