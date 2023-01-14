package FilterTheSpire.ui.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;

import java.util.ArrayList;
import java.util.Arrays;

public class RareCardFilter extends FilterScreen implements DropdownMenuListener {
    private DropdownMenu combatDropdown;
    private DropdownMenu characterDropdown;
    private DropdownMenu cardDropdown;

    public RareCardFilter() {

        int defaultCharacterIndex = 0;
        if (filterObject.character != null){
            switch (filterObject.character){
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

        ArrayList<String> characters = new ArrayList<>(Arrays.asList("Any Character", "Ironclad", "Silent", "Defect", "Watcher"));
        this.characterDropdown = new DropdownMenu(this, characters, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);

        this.characterDropdown.setSelectedIndex(defaultCharacterIndex);

    }

    void renderForeground(SpriteBatch sb) {
        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose a rare card that will drop from a hallway combat. Because this " +
                        "does not make any simulations on your path, it assumes that you take all combats " +
                        "without anything that would change the card RNG in-between, such as shops or elites.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN + 100F) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);
    }

    void update() {

    }

    void refreshFilters() {

    }

    void resetUI() {

    }

    public void changedSelectionTo(DropdownMenu dropdownMenu, int i, String s) {

    }
}
