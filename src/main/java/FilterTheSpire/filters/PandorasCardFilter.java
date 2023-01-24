package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardTransformSimulator;
import FilterTheSpire.utils.FilterType;
import FilterTheSpire.utils.SeedHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
        this.sortOrder = 2;
        this.totalTransformCount = AbstractDungeon.player.getStartingDeck().size() -
                ((AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.IRONCLAD) ? 1 : 2);

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
}
