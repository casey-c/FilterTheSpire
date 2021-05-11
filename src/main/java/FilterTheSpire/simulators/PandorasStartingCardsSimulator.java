package FilterTheSpire.simulators;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

public class PandorasStartingCardsSimulator {
    private Random cardRandomRng;
    private long seed;

    private boolean isOk = false;

    public PandorasStartingCardsSimulator(long seed) {
        setSeed(seed);
    }

    public void setSeed(long seed) {
        this.seed = seed;
        this.cardRandomRng = new Random(seed);

        ArrayList<AbstractCard> list = new ArrayList<>();
        list.addAll(AbstractDungeon.srcCommonCardPool.group);
        list.addAll(AbstractDungeon.srcUncommonCardPool.group);
        list.addAll(AbstractDungeon.srcRareCardPool.group);

        ArrayList<AbstractCard> results = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            results.add(list.get(cardRandomRng.random(list.size() - 1)));
        }

        int bladeDanceCount = 0;
        for (AbstractCard c : results)
            if (c.cardID == "Blade Dance")
                ++bladeDanceCount;

        if (bladeDanceCount > 5) {
            isOk = true;

            System.out.println("------");
            System.out.println("The cards chosen in this seed are: ");
            for (AbstractCard c : results)
                System.out.print(c.name + " ");
            System.out.println("------");
        }
        //return (AbstractCard)list.get(cardRandomRng.random(list.size() - 1));
    }

    public boolean isOk() {
        return isOk;
    }
}
