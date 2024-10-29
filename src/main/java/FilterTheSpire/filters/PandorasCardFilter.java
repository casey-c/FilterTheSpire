package FilterTheSpire.filters;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.simulators.CardTransformSimulator;
import FilterTheSpire.utils.config.FilterType;
import FilterTheSpire.utils.helpers.SeedHelper;
import FilterTheSpire.utils.types.RunCheckpoint;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.PandorasBox;

import java.util.*;

public class PandorasCardFilter extends AbstractFilter {
    private HashMap<String, Integer> searchCards;
    private int totalTransformCount;
    private final TreeMap<AbstractCard.CardRarity, Boolean> cardRaritiesShouldReverse;

    public PandorasCardFilter(HashMap<String, Integer> searchCards, AbstractPlayer.PlayerClass character){
        this(character);
        this.searchCards = searchCards;
        displayName = "Pandora's Box Card Filter";
    }

    private PandorasCardFilter(AbstractPlayer.PlayerClass character){
        type = FilterType.PandorasCard;
        this.sortOrder = 2;
        if (character != null){
            HashMap<AbstractPlayer.PlayerClass, Integer> starterCardCounts = new HashMap<>();
            starterCardCounts.put(AbstractPlayer.PlayerClass.IRONCLAD, 9);
            starterCardCounts.put(AbstractPlayer.PlayerClass.THE_SILENT, 10);
            starterCardCounts.put(AbstractPlayer.PlayerClass.DEFECT, 8);
            starterCardCounts.put(AbstractPlayer.PlayerClass.WATCHER, 8);
            this.totalTransformCount = starterCardCounts.get(character);
        }

        // Generate all card rarities and don't reverse any of them
        cardRaritiesShouldReverse = new TreeMap<>();
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.COMMON, false);
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.UNCOMMON, false);
        cardRaritiesShouldReverse.put(AbstractCard.CardRarity.RARE, false);
    }

    public boolean isSeedValid(long seed) {
        ArrayList<AbstractFilter> filterList = FilterManager.getFilters();
        boolean hasPandorasFilter = filterList
                .stream()
                .filter(f -> f.type == FilterType.NthBossRelic)
                .map(f -> (NthBossRelicFilter) f)
                .anyMatch(f -> f.bossRelicNames.contains(PandorasBox.ID) && f.pickupCheckpoint == RunCheckpoint.NEOW);
        if (hasPandorasFilter){
            Random cardRng = SeedHelper.getNewRNG(seed, SeedHelper.RNGType.CARDRANDOM);
            return CardTransformSimulator.getInstance().isValid(cardRng, searchCards, totalTransformCount, cardRaritiesShouldReverse);
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
