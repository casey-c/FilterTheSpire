package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.FilterType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NeowBonusFilterScreen extends FilterScreen implements DropdownMenuListener  {
    private DropdownMenu neowBonusDropdown;
    private DropdownMenu drawbackDropdown;
    private ArrayList<NeowBonus> bonuses;
    private ArrayList<NeowDrawback> drawbacks;

    private static class NeowBonus {
        public NeowReward.NeowRewardType value;
        public String description;

        public NeowBonus(NeowReward.NeowRewardType value, String description){
            this.value = value;
            this.description = description;
        }
    }

    private static class NeowDrawback {
        public NeowReward.NeowRewardDrawback value;
        public String description;

        public NeowDrawback(NeowReward.NeowRewardDrawback value, String description){
            this.value = value;
            this.description = description;
        }
    }

    public NeowBonusFilterScreen(){
        this.filterObject = FilterTheSpire.config.getFilter(FilterType.NeowBonus);

        bonuses = new ArrayList<>();
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.THREE_CARDS, "Choose a Card to obtain"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.ONE_RANDOM_RARE_CARD, "Obtain a random rare Card"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.REMOVE_CARD, "Remove a Card from your deck"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.UPGRADE_CARD, "Upgrade a Card"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.TRANSFORM_CARD, "Transform a Card"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.THREE_SMALL_POTIONS, "Obtain 3 random Potions"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.RANDOM_COMMON_RELIC, "Obtain a random common Relic"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.TEN_PERCENT_HP_BONUS, "Gain 10% Max HP"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.HUNDRED_GOLD, "Obtain 100 Gold"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.THREE_ENEMY_KILL, "Enemies in your next three combats have 1 HP"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.RANDOM_COLORLESS, "Choose a colorless Card to obtain"));

        // Bonuses with drawbacks
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.REMOVE_TWO, "Remove 2 Cards"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.ONE_RARE_RELIC, "Obtain a random rare Relic"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.THREE_RARE_CARDS, "Choose a rare Card to obtain"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.TWO_FIFTY_GOLD, "Gain 250 Gold"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.TRANSFORM_TWO_CARDS, "Transform 2 Cards"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.TWENTY_PERCENT_HP_BONUS, "Gain 20% Max HP"));
        bonuses.add(new NeowBonus(NeowReward.NeowRewardType.RANDOM_COLORLESS_2, "Choose a rare colorless Card to obtain"));

        int defaultBonusIndex = 0;
        if (filterObject.anyOf.size() > 0){
            NeowReward.NeowRewardType defaultBonus = NeowReward.NeowRewardType.valueOf(filterObject.anyOf.get(0));
            for (int i = 0; i < bonuses.size(); i++){
                if (defaultBonus == bonuses.get(i).value){
                    defaultBonusIndex = i + 1;
                    break;
                }
            }
        }

        // Drawbacks
        drawbacks = new ArrayList<>();
        drawbacks.add(new NeowDrawback(NeowReward.NeowRewardDrawback.TEN_PERCENT_HP_LOSS, "Lose 10% Max HP"));
        drawbacks.add(new NeowDrawback(NeowReward.NeowRewardDrawback.NO_GOLD, "Lose all Gold"));
        drawbacks.add(new NeowDrawback(NeowReward.NeowRewardDrawback.CURSE, "Obtain a Curse"));
        drawbacks.add(new NeowDrawback(NeowReward.NeowRewardDrawback.PERCENT_DAMAGE, "Take 30% damage"));

        int defaultDrawbackIndex = 0;
        if (filterObject.noneOf.size() > 0){
            NeowReward.NeowRewardDrawback defaultDrawback  = NeowReward.NeowRewardDrawback.valueOf(filterObject.noneOf.get(0));
            for (int i = 0; i < drawbacks.size(); i++){
                if (defaultDrawback == drawbacks.get(i).value){
                    defaultDrawbackIndex = i + 1;
                    break;
                }
            }
        }

        ArrayList<String> bonusStrings = bonuses.stream().map(b -> b.description).collect(Collectors.toCollection(ArrayList::new));
        bonusStrings.add(0, "Any Bonus");
        this.neowBonusDropdown = new DropdownMenu(this, bonusStrings, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
        this.neowBonusDropdown.setSelectedIndex(defaultBonusIndex);

        ArrayList<String> drawbackStrings = drawbacks.stream().map(b -> b.description).collect(Collectors.toCollection(ArrayList::new));
        drawbackStrings.add(0, "Any Drawback");
        this.drawbackDropdown = new DropdownMenu(this, drawbackStrings, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
        this.drawbackDropdown.setSelectedIndex(defaultDrawbackIndex);
    }

    public void changedSelectionTo(DropdownMenu dropdownMenu, int i, String s) {
        if (dropdownMenu == this.neowBonusDropdown){
            filterObject.anyOf.clear();
            if (this.neowBonusDropdown.getSelectedIndex() > 0) {
                filterObject.anyOf.add(bonuses.get(i - 1).value.name());
            }
        } else if (dropdownMenu == this.drawbackDropdown){
            filterObject.noneOf.clear();
            if (this.drawbackDropdown.getSelectedIndex() > 0) {
                filterObject.noneOf.add(drawbacks.get(i - 1).value.name());
            }
        }

        refreshFilters();
    }

    void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        this.returnButton.render(sb);

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Neow Bonuses", titleLeft * Settings.scale, titleBottom * Settings.scale, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose which bonuses Neow will offer at the start of the run. You are " +
                        "only able to filter on one bonus.",
                INFO_LEFT * Settings.scale,
                (INFO_TOP_MAIN * Settings.scale) + 120F,
                INFO_WIDTH * Settings.scale,
                30.0f * Settings.scale,
                Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "If you use this in conjunction with the Boss Swap filter, you can either Boss Swap or your chosen " +
                        "Boss relic will appear after the Act 1 boss.",
                INFO_LEFT * Settings.scale,
                (INFO_TOP_MAIN * Settings.scale) - 20F,
                INFO_WIDTH * Settings.scale,
                30.0f * Settings.scale,
                Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "WARNING: Some bonuses and drawbacks can never pair together: " +
                        "NL Obtain a Curse - Remove 2 Cards NL Lose all Gold - Gain 250 Gold NL Lose 10% Max HP - Gain 20% Max HP",
                INFO_LEFT * Settings.scale,
                (INFO_TOP_MAIN * Settings.scale) - 170.0F,
                INFO_WIDTH * Settings.scale,
                30.0f * Settings.scale,
                Settings.RED_TEXT_COLOR);

        final float xPosition = 400.0F;
        float yPosition = 600.0F;
        this.drawbackDropdown.render(sb, xPosition, yPosition);
        this.neowBonusDropdown.render(sb, xPosition, yPosition + 60.0F);
    }

    public void update() {
        this.returnButton.update();

        if (neowBonusDropdown.isOpen) {
            this.neowBonusDropdown.update();
        } else if (drawbackDropdown.isOpen) {
            this.drawbackDropdown.update();
        } else {
            this.neowBonusDropdown.update();
            this.drawbackDropdown.update();
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

    public void refreshFilters(){
        FilterTheSpire.config.updateFilter(filterObject);
        FilterManager.setFilter(filterObject);
    }
}
