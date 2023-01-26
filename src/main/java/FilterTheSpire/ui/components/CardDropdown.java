package FilterTheSpire.ui.components;

import FilterTheSpire.utils.helpers.CardPoolHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;

import java.util.ArrayList;
import java.util.List;


public class CardDropdown extends DropdownMenu {
    public List<String> cards;

    private CardDropdown(DropdownMenuListener listener, ArrayList<String> options, BitmapFont font, Color textColor){
        super(listener, options, font, textColor);
        if (options.size() > 1){
            cards = options.subList(1, options.size() - 1);
        } else {
            cards = new ArrayList<>();
        }
    }

    public static CardDropdown create(DropdownMenuListener listener, List<String> values){
       return create(listener, new ArrayList<>(values));
    }

    public static CardDropdown create(DropdownMenuListener listener, ArrayList<String> values){
        List<String> sortedList = getFriendlyCardNames(values);
        sortedList.sort(String::compareTo);
        ArrayList<String> cardList = new ArrayList<>();
        cardList.add("Any Card");
        cardList.addAll(sortedList);
        return new CardDropdown(listener, cardList, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
    }

    private static ArrayList<String> getFriendlyCardNames(List<String> cardKeys) {
        ArrayList<String> friendlyNames = new ArrayList<>();
        for (String key: cardKeys) {
            AbstractCard c = CardLibrary.cards.get(key);
            friendlyNames.add(c.name);
            CardPoolHelper.cardNameToId.put(c.name, c.cardID);
        }
        return friendlyNames;
    }

    public int findIndexOfCard(String searchCard){
        for (int i = 0; i < this.cards.size(); i++) {
            String cardId = CardPoolHelper.cardNameToId.getOrDefault(this.cards.get(i), null);
            if (cardId.equals(searchCard)) {
                return i + 1; // Add one to account for "Any"
            }
        }
        return 0; // default to "Any" option
    }
}
