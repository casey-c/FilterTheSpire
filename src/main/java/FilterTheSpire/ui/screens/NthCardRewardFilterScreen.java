package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.factory.CharacterPoolFactory;
import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.patches.DropdownMenuPatch;
import FilterTheSpire.ui.components.CardDropdown;
import FilterTheSpire.ui.components.CharacterDropdown;
import FilterTheSpire.utils.helpers.CharacterPool;
import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.config.FilterType;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class NthCardRewardFilterScreen extends BaseFilterScreen implements DropdownMenuListener {
    private DropdownMenu combatDropdown;
    private CharacterDropdown characterDropdown;
    private CardDropdown cardDropdown;

    public NthCardRewardFilterScreen(ModPanel p) {
        super(FilterType.NthCardReward, p, true);
    }

    public void open() {
        this.characterDropdown = new CharacterDropdown(this, null);

        String[] combats = IntStream.range(1, 6).mapToObj(String::valueOf).toArray(String[]::new);
        this.combatDropdown = new DropdownMenu(this, combats, FontHelper.cardDescFont_N, Settings.CREAM_COLOR){
            // Override the update of the toggle button to add an informational tool tip when hovered
            public void render(SpriteBatch sb, float x, float y) {
                super.render(sb, x, y * Settings.yScale);
                DropdownMenuPatch.renderTip(this, x, y, "Info",
                        "This is the combat which will drop the searched card reward.");
            }
        };

        this.cardDropdown = CardDropdown.create(this, new ArrayList<>());
        isShowing = true;
    }

    void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Card Reward Filter", titleLeft * Settings.xScale, titleBottom * Settings.yScale, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose a card that will drop from a hallway combat. Because this " +
                        "does not make any simulations on your path, it assumes that you take all hallway combats " +
                        "without taking anything that would give different card rewards, such as shops or elites.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN + 100F) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "WARNING: Using this in combination with some Neow Bonuses may occasionally cause " +
                        "inconsistencies, specifically 3 Potions, or either of the Colorless card rewards.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN - 115.0F) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.LIGHT_YELLOW_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "WARNING: Rare cards will not appear from the first combat rewards unless there are very specific " +
                        "conditions. If you want to search for a rare card, you should start at the second combat.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_CONTROLS - 80.0F) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.RED_TEXT_COLOR);

        final float xPosition = 400.0F;
        float yPosition = 595.0F;
        final float spacingIncrement = 65F;

        if (this.characterDropdown.getSelectedIndex() > 0) {
            this.cardDropdown.render(sb, (xPosition * Settings.xScale), yPosition * Settings.yScale);
        }
        this.characterDropdown.render(sb, xPosition * Settings.xScale, (yPosition  + spacingIncrement) * Settings.yScale);
        this.combatDropdown.render(sb, (xPosition + 100.0F) * Settings.xScale, yPosition + (spacingIncrement * 2));
        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "Combat:",
                xPosition * Settings.xScale,
                (yPosition + spacingIncrement * 2) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);
    }

    void update() {
        this.addButton.update();
        this.returnButton.update();

        if (this.combatDropdown.isOpen) {
            this.combatDropdown.update();
        } else if (this.characterDropdown.isOpen){
            this.characterDropdown.update();
        } else if (this.cardDropdown.isOpen){
            this.cardDropdown.update();
        } else {
            this.combatDropdown.update();
            this.characterDropdown.update();
            this.cardDropdown.update();
        }

        if (this.returnButton.hb.clickStarted){
            this.isShowing = false;
        }

        if (isShowing){
            this.returnButton.show();
        } else{
            this.returnButton.hide();
        }
    }

    void resetUI() {
        this.combatDropdown.setSelectedIndex(0);
        this.characterDropdown.setSelectedIndex(0);
        this.cardDropdown.setSelectedIndex(0);
    }

    public void changedSelectionTo(DropdownMenu dropdownMenu, int i, String s) {
        if (dropdownMenu == characterDropdown){
            setCardDropdownValues();
        }
    }

    private void setCardDropdownValues() {
        CharacterPool characterPool = characterDropdown.getSelectedIndex() > 0 ? CharacterPoolFactory.getCharacterPool(characterDropdown.getCharacterFromIndex(characterDropdown.getSelectedIndex())) : null;
        if (characterPool != null){
            this.cardDropdown = CardDropdown.create(this, characterPool.getCardPool(false));
        }
    }

    public void setFilterObjectForAddOrUpdate(){
        filterObject = new FilterObject(FilterType.NthCardReward);
        if (cardDropdown.getSelectedIndex() > 0){
            filterObject.possibleEncounterIndices.clear();
            filterObject.character = characterDropdown.getCharacterFromIndex(characterDropdown.getSelectedIndex());
            filterObject.possibleValues.add(FilterTheSpire.localizedCardNameToId.get(cardDropdown.getSelectedCard()));
            filterObject.possibleEncounterIndices.add(combatDropdown.getSelectedIndex());
        }
    }
}
