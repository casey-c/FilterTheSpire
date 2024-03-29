package FilterTheSpire.utils.cache;

import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RunInfoCache {
    public static AbstractPlayer.PlayerClass currentCharacter;
    public static List<String> modList = new ArrayList<>();
    public static HashMap<Integer, ArrayList<String>> rarityMapCardPool = new HashMap<>();

    public static void clear(){
        currentCharacter = null;
        modList.clear();
        rarityMapCardPool.clear();
    }
}
