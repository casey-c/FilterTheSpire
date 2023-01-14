package FilterTheSpire.ui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CardDropdown extends DropdownMenu {
    private CardDropdown(DropdownMenuListener listener, ArrayList<String> options, BitmapFont font, Color textColor){
        super(listener, options, font, textColor);
    }

    public static CardDropdown create(DropdownMenuListener listener, ArrayList<String> values, HashMap<String, String> cardNameToId){
        List<String> sortedList = getFriendlyCardNames(values, cardNameToId);
        sortedList.sort(String::compareTo);
        ArrayList<String> cardList = new ArrayList<>();
        cardList.add("Any Card");
        cardList.addAll(sortedList);
        return new CardDropdown(listener, cardList, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
    }

    private static ArrayList<String> getFriendlyCardNames(List<String> cardKeys, HashMap<String, String> cardNameToId) {
        ArrayList<String> friendlyNames = new ArrayList<>();
        for (String key: cardKeys) {
            AbstractCard c = CardLibrary.cards.get(key);
            friendlyNames.add(c.name);
            cardNameToId.put(c.name, c.cardID);
        }
        return friendlyNames;
    }
}
