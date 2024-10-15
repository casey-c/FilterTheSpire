package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.factory.CharacterPoolFactory;
import FilterTheSpire.ui.components.CardDropdown;
import FilterTheSpire.ui.components.CharacterDropdown;
import FilterTheSpire.utils.helpers.CardPoolHelper;
import FilterTheSpire.utils.helpers.CharacterPool;
import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.config.FilterType;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;

import java.util.*;
import java.util.stream.Collectors;

public class NeowBonusBaseFilterScreen extends BaseFilterScreen implements DropdownMenuListener  {
    private DropdownMenu neowBonusDropdown;
    private DropdownMenu drawbackDropdown;
    private CharacterDropdown characterDropdown;
    private CardDropdown cardDropdown;
    private CardDropdown cardDropdown2;

    private ArrayList<NeowBonus> bonuses;
    private ArrayList<NeowDrawback> drawbacks;

    private NeowReward.NeowRewardType currentBonusValue;
    private NeowReward.NeowRewardDrawback currentDrawbackValue;
    private final String[] cards = new String[2];

    private final List<NeowReward.NeowRewardType> characterCardBonuses = Arrays.asList(
            NeowReward.NeowRewardType.ONE_RANDOM_RARE_CARD,
            NeowReward.NeowRewardType.THREE_CARDS,
            NeowReward.NeowRewardType.TRANSFORM_CARD,
            NeowReward.NeowRewardType.TRANSFORM_TWO_CARDS,
            NeowReward.NeowRewardType.THREE_RARE_CARDS
    );

    private final HashMap<NeowReward.NeowRewardType, NeowReward.NeowRewardDrawback> illegalCombinations = new HashMap<>();

    private final ArrayList<NeowReward.NeowRewardType> cardRewardBonuses = new ArrayList<>();

    // dropdowns don't support a default selected value, so it calls the callback when setting the config saved value
    private boolean isInitialLoad;

    public NeowBonusBaseFilterScreen(ModPanel p) {
        super(FilterType.NeowBonus, p);
    }

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

    public void open() {
        isInitialLoad = true;
        this.filterObject = FilterTheSpire.config.getFilter(FilterType.NeowBonus);

        cardRewardBonuses.addAll(characterCardBonuses);
        cardRewardBonuses.add(NeowReward.NeowRewardType.RANDOM_COLORLESS);
        cardRewardBonuses.add(NeowReward.NeowRewardType.RANDOM_COLORLESS_2);

        illegalCombinations.put(NeowReward.NeowRewardType.REMOVE_TWO, NeowReward.NeowRewardDrawback.CURSE);
        illegalCombinations.put(NeowReward.NeowRewardType.TWO_FIFTY_GOLD, NeowReward.NeowRewardDrawback.NO_GOLD);
        illegalCombinations.put(NeowReward.NeowRewardType.TWENTY_PERCENT_HP_BONUS, NeowReward.NeowRewardDrawback.TEN_PERCENT_HP_LOSS);

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
        if (filterObject.possibleValues.size() > 0){
            currentBonusValue = NeowReward.NeowRewardType.valueOf(filterObject.possibleValues.get(0));
            for (int i = 0; i < bonuses.size(); i++){
                if (currentBonusValue == bonuses.get(i).value){
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
        drawbacks.add(new NeowDrawback(NeowReward.NeowRewardDrawback.PERCENT_DAMAGE, "Take roughly 27% damage"));

        int defaultDrawbackIndex = 0;
        if (filterObject.secondaryValues.size() > 0){
            currentDrawbackValue = NeowReward.NeowRewardDrawback.valueOf(filterObject.secondaryValues.get(0));
            for (int i = 0; i < drawbacks.size(); i++){
                if (currentDrawbackValue == drawbacks.get(i).value){
                    defaultDrawbackIndex = i + 1;
                    break;
                }
            }
        }

        ArrayList<String> bonusStrings = bonuses.stream().map(b -> b.description).collect(Collectors.toCollection(ArrayList::new));
        bonusStrings.add(0, "Any Bonus");
        this.neowBonusDropdown = new DropdownMenu(this, bonusStrings, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);

        ArrayList<String> drawbackStrings = drawbacks.stream().map(b -> b.description).collect(Collectors.toCollection(ArrayList::new));
        drawbackStrings.add(0, "Any Drawback");
        this.drawbackDropdown = new DropdownMenu(this, drawbackStrings, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);

        this.characterDropdown = new CharacterDropdown(this, filterObject.character);

        this.cardDropdown = CardDropdown.create(this, new ArrayList<>());
        this.cardDropdown2 = CardDropdown.create(this, new ArrayList<>());
        setCardDropdownValues();

        this.neowBonusDropdown.setSelectedIndex(defaultBonusIndex);
        this.drawbackDropdown.setSelectedIndex(defaultDrawbackIndex);
        FilterManager.setFilter(filterObject);
        isInitialLoad = false;
        isShowing = true;
    }

    public void changedSelectionTo(DropdownMenu dropdownMenu, int i, String s) {
        if (isInitialLoad) {
            return;
        }

        if (dropdownMenu == this.neowBonusDropdown) {
            filterObject.possibleValues.clear();
            if (this.neowBonusDropdown.getSelectedIndex() > 0) {
                String bonusString = bonuses.get(i - 1).value.name();
                currentBonusValue = NeowReward.NeowRewardType.valueOf(bonusString);

                clearCharacterFilter();
                clearCardFilter();

                filterObject.possibleValues.add(bonusString);

                if (currentBonusValue != null && currentDrawbackValue != null &&
                        illegalCombinations.getOrDefault(currentBonusValue, null) == currentDrawbackValue){
                    this.drawbackDropdown.setSelectedIndex(0);
                    filterObject.secondaryValues.clear();
                    currentDrawbackValue = null;
                }

                setCardDropdownValues();
            } else {
                currentBonusValue = null;
            }
        } else if (dropdownMenu == this.drawbackDropdown) {
            filterObject.secondaryValues.clear();
            if (this.drawbackDropdown.getSelectedIndex() > 0) {
                String drawbackString = drawbacks.get(i - 1).value.name();
                currentDrawbackValue = NeowReward.NeowRewardDrawback.valueOf(drawbackString);
                filterObject.secondaryValues.add(drawbackString);

                if (currentBonusValue != null && currentDrawbackValue != null &&
                        illegalCombinations.getOrDefault(currentBonusValue, null) == currentDrawbackValue){
                    this.neowBonusDropdown.setSelectedIndex(0);
                    filterObject.possibleValues.clear();
                    currentBonusValue = null;
                }
            } else {
                currentDrawbackValue = null;
            }
        } else if (dropdownMenu == this.characterDropdown) {
            AbstractPlayer.PlayerClass character = this.characterDropdown.getCharacterFromIndex(i);
            if (character != null) {
                filterObject.character = character;
            }
            clearCardFilter();
            setCardDropdownValues();
        } else if (dropdownMenu == this.cardDropdown || dropdownMenu == this.cardDropdown2) {
            if (dropdownMenu == this.cardDropdown){
                cards[0] = i != 0 ? CardPoolHelper.cardNameToId.get(s) : null;
            } else {
                cards[1] = i != 0 ? CardPoolHelper.cardNameToId.get(s) : null;
            }
            filterObject.searchCards.clear();
            for (String cardId: cards) {
                if (cardId != null){
                    filterObject.searchCards.put(cardId, filterObject.searchCards.getOrDefault(cardId, 0) + 1);
                }
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
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Neow Bonuses", titleLeft * Settings.xScale, titleBottom * Settings.yScale, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose which bonuses Neow will offer at the start of the run. You are " +
                        "only able to filter on one bonus.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN + 100F) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "If you use this in conjunction with the Boss Swap filter, you can either Boss Swap or your chosen " +
                        "Boss relic will appear after the Act 1 boss.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN - 20F)  * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);

        float extraTextY = INFO_TOP_MAIN - 160.0F;
        if (currentBonusValue == NeowReward.NeowRewardType.THREE_SMALL_POTIONS ||
                currentBonusValue == NeowReward.NeowRewardType.RANDOM_COLORLESS_2 ||
                currentBonusValue == NeowReward.NeowRewardType.RANDOM_COLORLESS) {
            FontHelper.renderSmartText(sb, FontHelper.tipBodyFont,
                    "WARNING: Unexpected results may occur if you use this Neow Bonus with the Card Reward Filter.",
                    INFO_LEFT * Settings.xScale,
                    extraTextY * Settings.yScale,
                    INFO_WIDTH * Settings.xScale,
                    30.0f * Settings.yScale,
                    Settings.LIGHT_YELLOW_COLOR);
            extraTextY -= 100.0F;
        } else if (currentBonusValue == NeowReward.NeowRewardType.RANDOM_COMMON_RELIC ||
                currentBonusValue == NeowReward.NeowRewardType.ONE_RARE_RELIC) {
            FontHelper.renderSmartText(sb, FontHelper.tipBodyFont,
                    "You can combine the Relic filter with the Relic Neow Bonuses to get a specific relic from Neow.",
                    INFO_LEFT * Settings.xScale,
                    extraTextY * Settings.yScale,
                    INFO_WIDTH * Settings.xScale,
                    30.0f * Settings.yScale,
                    Settings.GREEN_TEXT_COLOR);
        }

        if (!filterObject.searchCards.keySet().isEmpty()){
            FontHelper.renderSmartText(sb, FontHelper.tipBodyFont,
                    "WARNING: If you filter on cards that are on a different character than the one you're " +
                            "playing, a seed will never be found.",
                    INFO_LEFT * Settings.xScale,
                    extraTextY * Settings.yScale,
                    INFO_WIDTH * Settings.xScale,
                    30.0f * Settings.yScale,
                    Settings.RED_TEXT_COLOR);
        }

        final float xPosition = 400.0F;
        float yPosition = 530.0F;
        final float spacingIncrement = 65F;

        if (characterCardBonuses.contains(currentBonusValue)) {
            if (this.characterDropdown.getSelectedIndex() > 0) {
                this.cardDropdown.render(sb, (xPosition * Settings.xScale), yPosition * Settings.yScale);
                if (currentBonusValue == NeowReward.NeowRewardType.TRANSFORM_TWO_CARDS){
                    this.cardDropdown2.render(sb, (xPosition  + 300.0F) * Settings.xScale, yPosition * Settings.yScale);
                }
            }
            this.characterDropdown.render(sb, xPosition * Settings.xScale, (yPosition  + spacingIncrement) * Settings.yScale);
        } else if (cardRewardBonuses.contains(currentBonusValue)) {
            // If is a card option, show card dropdown
            this.cardDropdown.render(sb, (xPosition * Settings.xScale), yPosition * Settings.yScale);
        }

        this.drawbackDropdown.render(sb, xPosition * Settings.xScale, (yPosition  + spacingIncrement * 2) * Settings.yScale);
        this.neowBonusDropdown.render(sb, xPosition * Settings.xScale, (yPosition + spacingIncrement * 3) * Settings.yScale);
    }

    public void update() {
        this.returnButton.update();

        if (neowBonusDropdown.isOpen) {
            this.neowBonusDropdown.update();
        } else if (drawbackDropdown.isOpen) {
            this.drawbackDropdown.update();
        } else if (characterDropdown.isOpen) {
            this.characterDropdown.update();
        } else if (cardDropdown.isOpen) {
            this.cardDropdown.update();
        } else if (cardDropdown2.isOpen) {
            this.cardDropdown2.update();
        } else {
            this.neowBonusDropdown.update();
            this.drawbackDropdown.update();
            this.characterDropdown.update();
            this.cardDropdown.update();
            this.cardDropdown2.update();
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

    public void resetUI(){
        neowBonusDropdown.setSelectedIndex(0);
        drawbackDropdown.setSelectedIndex(0);
        characterDropdown.setSelectedIndex(0);
        cardDropdown.setSelectedIndex(0);
        cardDropdown2.setSelectedIndex(0);
    }

    private void setCardDropdownValues() {
        CharacterPool characterPool = filterObject.character != null ? CharacterPoolFactory.getCharacterPool(filterObject.character) : null;
        if (currentBonusValue != null){
            switch (currentBonusValue) {
                case ONE_RANDOM_RARE_CARD:
                case THREE_RARE_CARDS:
                    // set dropdown to character rares
                    if (characterPool != null){
                        cardDropdown = CardDropdown.create(this, characterPool.rareCardPool);
                    }
                    break;
                case THREE_CARDS:
                    // set dropdown to character commons and uncommons
                    if (characterPool != null){
                        ArrayList<String> cards = new ArrayList<>();
                        cards.addAll(characterPool.commonCardPool);
                        cards.addAll(characterPool.uncommonCardPool);
                        cardDropdown = CardDropdown.create(this, cards);
                    }
                    break;
                case TRANSFORM_CARD:
                case TRANSFORM_TWO_CARDS:
                    // set dropdown to character entire card pool
                    if (characterPool != null){
                        cardDropdown = CardDropdown.create(this, characterPool.getCardPool(false));
                        if (currentBonusValue == NeowReward.NeowRewardType.TRANSFORM_TWO_CARDS){
                            cardDropdown2 = CardDropdown.create(this, characterPool.getCardPool(false));
                        }
                    }
                    break;
                case RANDOM_COLORLESS:
                    // set dropdown to uncommon colorless
                    cardDropdown = CardDropdown.create(this, CardPoolHelper.getUncommonColorlessCards());
                    break;
                case RANDOM_COLORLESS_2:
                    // set dropdown to rare colorless
                    cardDropdown = CardDropdown.create(this, CardPoolHelper.getRareColorlessCards());
                    break;
                default:
                    break;
            }

            if (isInitialLoad) {
                for (String searchCard: filterObject.searchCards.keySet()) {
                    int index = cardDropdown.findIndexOfCard(searchCard);
                    if (cardDropdown.getSelectedIndex() > 0){
                        cardDropdown2.setSelectedIndex(index);
                        cards[1] = searchCard;
                    } else {
                        cardDropdown.setSelectedIndex(index);
                        cards[0] = searchCard;
                        if (filterObject.searchCards.get(searchCard) > 1) {
                            cardDropdown2.setSelectedIndex(index);
                            cards[1] = searchCard;
                        }
                    }
                }
            }
        }
    }

    private void clearCharacterFilter(){
        filterObject.character = null;
        this.characterDropdown.setSelectedIndex(0);
    }

    private void clearCardFilter(){
        filterObject.searchCards.clear();
        this.cardDropdown.setSelectedIndex(0);
        this.cardDropdown2.setSelectedIndex(0);
    }
}
