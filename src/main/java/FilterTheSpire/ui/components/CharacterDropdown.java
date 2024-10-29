package FilterTheSpire.ui.components;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;

import java.util.ArrayList;
import java.util.Arrays;

public class CharacterDropdown extends DropdownMenu {
    public CharacterDropdown(DropdownMenuListener listener, AbstractPlayer.PlayerClass character) {
        super(listener, new ArrayList<>(Arrays.asList("Any Character", "Ironclad", "Silent", "Defect", "Watcher")), FontHelper.cardDescFont_N, Settings.CREAM_COLOR);

        int defaultCharacterIndex = 0;
        if (character != null){
            switch (character){
                case IRONCLAD:
                    defaultCharacterIndex = 1;
                    break;
                case THE_SILENT:
                    defaultCharacterIndex = 2;
                    break;
                case DEFECT:
                    defaultCharacterIndex = 3;
                    break;
                case WATCHER:
                    defaultCharacterIndex = 4;
                    break;
                default:
                    break;
            }
        }
        setSelectedIndex(defaultCharacterIndex);
    }

    public AbstractPlayer.PlayerClass getCharacterFromIndex(int i){
        switch (i){
            case 1:
                return AbstractPlayer.PlayerClass.IRONCLAD;
            case 2:
                return AbstractPlayer.PlayerClass.THE_SILENT;
            case 3:
                return AbstractPlayer.PlayerClass.DEFECT;
            case 4:
                return AbstractPlayer.PlayerClass.WATCHER;
            default:
                return null;
        }
    }

    public AbstractPlayer.PlayerClass getSelectedCharacter(){
        return getCharacterFromIndex(getSelectedIndex());
    }
}
