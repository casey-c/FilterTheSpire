package FilterTheSpire.filters;

import FilterTheSpire.simulators.CardTransformSimulator;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;

// This is different than other filters in that it's not taking in a List<String>
// We could always take in a List<String> for the cards and just make them a Hashmap in the constructor here
public class PandorasCardFilter extends AbstractFilter {
    private HashMap<String, Integer> searchCards;
    private int pandoraTransforms;

    public PandorasCardFilter(HashMap<String, Integer> searchCards){
        this.searchCards = searchCards;
        this.pandoraTransforms = AbstractDungeon.player.getStartingDeck().size() -
                ((AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.IRONCLAD) ? 1 : 2);
    }

    public boolean isSeedValid(long seed) {
        return CardTransformSimulator.getInstance().isValid(seed, searchCards, pandoraTransforms);
    }
}
