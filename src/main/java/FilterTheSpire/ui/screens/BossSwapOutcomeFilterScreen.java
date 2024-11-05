package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.factory.CharacterPoolFactory;
import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.ui.components.CardDropdown;
import FilterTheSpire.ui.components.CharacterDropdown;
import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.config.FilterType;
import FilterTheSpire.utils.helpers.CharacterPool;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Astrolabe;
import com.megacrit.cardcrawl.relics.CallingBell;
import com.megacrit.cardcrawl.relics.PandorasBox;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;

import java.util.*;
import java.util.stream.Collectors;

public class BossSwapOutcomeFilterScreen extends BaseFilterScreen implements DropdownMenuListener {
    private DropdownMenu bossRelicDropdown;
    private CharacterDropdown characterDropdown;
    private CardDropdown cardDropdown;
    private CardDropdown cardDropdown2;
    private CardDropdown cardDropdown3;
    private DropdownMenu commonRelicDropdown;
    private DropdownMenu uncommonRelicDropdown;
    private DropdownMenu rareRelicDropdown;
    private String currentBossRelic;
    private List<AbstractRelic> allCommonRelics;
    private List<AbstractRelic> allUncommonRelics;
    private List<AbstractRelic> allRareRelics;

    public BossSwapOutcomeFilterScreen(ModPanel p) {
        super(FilterType.PandorasCard, p, true);
        populateRelicPools();
    }

    private void populateRelicPools() {
        TreeSet<AbstractRelic> commonRelics = new TreeSet<>(Comparator.comparing(r -> r.relicId));
        TreeSet<AbstractRelic> uncommonRelics = new TreeSet<>(Comparator.comparing(r -> r.relicId));
        TreeSet<AbstractRelic> rareRelics = new TreeSet<>(Comparator.comparing(r -> r.relicId));
        CharacterPool icPool = CharacterPoolFactory.getCharacterPool(AbstractPlayer.PlayerClass.IRONCLAD);
        CharacterPool silentPool = CharacterPoolFactory.getCharacterPool(AbstractPlayer.PlayerClass.THE_SILENT);
        CharacterPool defectPool = CharacterPoolFactory.getCharacterPool(AbstractPlayer.PlayerClass.DEFECT);
        CharacterPool watcherPool = CharacterPoolFactory.getCharacterPool(AbstractPlayer.PlayerClass.WATCHER);
        ArrayList<CharacterPool> pools = new ArrayList<>(Arrays.asList(icPool, silentPool, defectPool, watcherPool));
        for (CharacterPool pool : pools) {
            for (String commonRelicId: pool.commonRelicPool) {
                commonRelics.add(RelicLibrary.getRelic(commonRelicId));
            }
            for (String uncommonRelicId: pool.uncommonRelicPool) {
                uncommonRelics.add(RelicLibrary.getRelic(uncommonRelicId));
            }
            for (String rareRelicId: pool.rareRelicPool) {
                rareRelics.add(RelicLibrary.getRelic(rareRelicId));
            }
        }

        allCommonRelics = new ArrayList<>(commonRelics);
        allUncommonRelics = new ArrayList<>(uncommonRelics);
        allRareRelics = new ArrayList<>(rareRelics);
    }

    void resetUI() {
        bossRelicDropdown.setSelectedIndex(0);
        characterDropdown.setSelectedIndex(0);
    }

    public void open() {
        ArrayList<String> bossRelicOptions = new ArrayList<>(Arrays.asList(Astrolabe.ID, PandorasBox.ID, CallingBell.ID));
        bossRelicDropdown = new DropdownMenu(this, bossRelicOptions, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
        currentBossRelic = Astrolabe.ID;
        characterDropdown = new CharacterDropdown(this, null);
        cardDropdown = CardDropdown.create(this, new ArrayList<>());
        cardDropdown2 = CardDropdown.create(this, new ArrayList<>());
        cardDropdown3 = CardDropdown.create(this, new ArrayList<>());
        ArrayList<String> commonRelics = allCommonRelics.stream().map(r -> r.name).collect(Collectors.toCollection(ArrayList::new));
        commonRelics.add(0, "Any Relic");

        ArrayList<String> uncommonRelics = allUncommonRelics.stream().map(r -> r.name).collect(Collectors.toCollection(ArrayList::new));
        uncommonRelics.add(0, "Any Relic");

        ArrayList<String> rareRelics = allRareRelics.stream().map(r -> r.name).collect(Collectors.toCollection(ArrayList::new));
        rareRelics.add(0, "Any Relic");
        commonRelicDropdown = new DropdownMenu(this, commonRelics, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
        uncommonRelicDropdown = new DropdownMenu(this, uncommonRelics, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
        rareRelicDropdown = new DropdownMenu(this, rareRelics, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
        isShowing = true;
    }

    public void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Boss Swap Outcome Filters",
                titleLeft * Settings.xScale, titleBottom * Settings.yScale, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose random outcomes due to a Boss Relic choice. There are only 3 " +
                        "card rewards available for Pandora's Box because of how much more searching it adds, " +
                        "especially when factoring in the additional filters. These filters will not be active unless " +
                        "the specified Boss Relic is filtering from a Neow Boss Swap.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN + 100F) * Settings.yScale,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);

        final float xPosition = 550.0F;
        float yPosition = 400.0F;
        final float spacingIncrement = 65F;

        if (currentBossRelic != null) {
            if (currentBossRelic.equals(Astrolabe.ID) || currentBossRelic.equals(PandorasBox.ID)) {
                if (characterDropdown.getSelectedIndex() > 0){
                    cardDropdown.render(sb, xPosition * Settings.xScale, (yPosition + spacingIncrement * 2) * Settings.yScale);
                    cardDropdown2.render(sb, xPosition * Settings.xScale, (yPosition + spacingIncrement * 3) * Settings.yScale);
                    cardDropdown3.render(sb, xPosition * Settings.xScale, (yPosition + spacingIncrement * 4) * Settings.yScale);
                }
                characterDropdown.render(sb, (xPosition + 300.0f) * Settings.xScale, (yPosition + spacingIncrement * 5) * Settings.yScale);
            } else if (currentBossRelic.equals(CallingBell.ID)) {
                rareRelicDropdown.render(sb, xPosition * Settings.xScale, (yPosition + spacingIncrement * 2) * Settings.yScale);
                FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, "Rare Relic",
                        (xPosition - 200.0f) * Settings.xScale,
                        (yPosition - 5.0f + spacingIncrement * 2) * Settings.yScale,
                        200.0f * Settings.xScale,
                        30.0f * Settings.yScale,
                        Settings.CREAM_COLOR);

                uncommonRelicDropdown.render(sb, xPosition * Settings.xScale, (yPosition + spacingIncrement * 3) * Settings.yScale);
                FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, "Uncommon Relic",
                        (xPosition - 200.0f) * Settings.xScale,
                        (yPosition - 5.0f + spacingIncrement * 3) * Settings.yScale,
                        200.0f * Settings.xScale,
                        30.0f * Settings.yScale,
                        Settings.CREAM_COLOR);

                commonRelicDropdown.render(sb, xPosition * Settings.xScale, (yPosition + spacingIncrement * 4) * Settings.yScale);
                FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, "Common Relic",
                        (xPosition - 200.0f) * Settings.xScale,
                        (yPosition - 5.0f + spacingIncrement * 4) * Settings.yScale,
                        200.0f * Settings.xScale,
                        30.0f * Settings.yScale,
                        Settings.CREAM_COLOR);
            }
        }

        bossRelicDropdown.render(sb, xPosition * Settings.xScale, (yPosition + spacingIncrement * 5) * Settings.yScale);

        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, "Boss Relic Swap",
                (xPosition - 200.0f) * Settings.xScale,
                (yPosition - 5.0f + spacingIncrement * 5) * Settings.yScale,
                200.0f * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);
    }

    void update() {
        this.addButton.update();
        this.returnButton.update();

        if (bossRelicDropdown.isOpen) {
            bossRelicDropdown.update();
        } else if (characterDropdown.isOpen) {
            characterDropdown.update();
        } else if (cardDropdown.isOpen) {
            cardDropdown.update();
        } else if (cardDropdown2.isOpen) {
            cardDropdown2.update();
        } else if (cardDropdown3.isOpen) {
            cardDropdown3.update();
        } else if (commonRelicDropdown.isOpen) {
            commonRelicDropdown.update();
        } else if (uncommonRelicDropdown.isOpen) {
            uncommonRelicDropdown.update();
        } else if (rareRelicDropdown.isOpen) {
            rareRelicDropdown.update();
        } else {
            bossRelicDropdown.update();
            characterDropdown.update();
            cardDropdown.update();
            cardDropdown2.update();
            cardDropdown3.update();
            commonRelicDropdown.update();
            uncommonRelicDropdown.update();
            rareRelicDropdown.update();
        }

        if (this.returnButton.hb.clickStarted) {
            this.isShowing = false;
        }

        if (isShowing) {
            this.returnButton.show();
        } else {
            this.returnButton.hide();
        }
    }

    public void changedSelectionTo(DropdownMenu dropdownMenu, int i, String selectedItem) {
        if (dropdownMenu == bossRelicDropdown) {
            currentBossRelic = selectedItem;
        } else if (dropdownMenu == characterDropdown) {
            AbstractPlayer.PlayerClass character = characterDropdown.getCharacterFromIndex(i);
            if (character != null) {
                setDropdownsFromCharacter(character);
            }
        }
    }

    private void setDropdownsFromCharacter(AbstractPlayer.PlayerClass character) {
        CharacterPool characterPool = CharacterPoolFactory.getCharacterPool(character);
        cardDropdown = CardDropdown.create(this, characterPool.getCardPool(false));
        cardDropdown2 = CardDropdown.create(this, characterPool.getCardPool(false));
        cardDropdown3 = CardDropdown.create(this, characterPool.getCardPool(false));
    }

    public void setFilterObjectForAddOrUpdate() {
        FilterType filterType;
        ArrayList<String> possibleValues = new ArrayList<>();
        switch (bossRelicDropdown.getSelectedIndex()){
            case 0:
                filterType = FilterType.AstrolabeCard;
                possibleValues = getSelectedCardList();
                break;
            case 1:
                filterType = FilterType.PandorasCard;
                possibleValues = getSelectedCardList();
                break;
            case 2:
                filterType = FilterType.CallingBellRelic;
                possibleValues = getSelectedRelicList();
                break;
            default:
                filterType = null;
        }
        if (filterType != null){
            filterObject = new FilterObject(filterType);
            if (possibleValues.size() > 0) {
                if (filterType == FilterType.AstrolabeCard ||
                    filterType == FilterType.PandorasCard)
                {
                    HashMap<String, Integer> cardCounts = new HashMap<>();
                    for (String s : possibleValues) {
                        cardCounts.put(s, cardCounts.getOrDefault(s, 0) + 1);
                    }
                    filterObject.searchCards = cardCounts;
                    filterObject.character = characterDropdown.getSelectedCharacter();
                } else {
                    filterObject.possibleValues = possibleValues;
                }
            }
        }
    }

    public ArrayList<String> getSelectedCardList(){
        ArrayList<String> possibleValues = new ArrayList<>();
        if (cardDropdown.getSelectedIndex() > 0){
            possibleValues.add(FilterTheSpire.localizedCardNameToId.get(cardDropdown.getSelectedCard()));
        }
        if (cardDropdown2.getSelectedIndex() > 0){
            possibleValues.add(FilterTheSpire.localizedCardNameToId.get(cardDropdown2.getSelectedCard()));
        }
        if (cardDropdown3.getSelectedIndex() > 0){
            possibleValues.add(FilterTheSpire.localizedCardNameToId.get(cardDropdown3.getSelectedCard()));
        }
        return possibleValues;
    }

    public ArrayList<String> getSelectedRelicList(){
        ArrayList<String> possibleValues = new ArrayList<>();
        if (commonRelicDropdown.getSelectedIndex() > 0){
            possibleValues.add(allCommonRelics.get(commonRelicDropdown.getSelectedIndex() - 1).relicId);
        } else {
            possibleValues.add(null);
        }
        if (uncommonRelicDropdown.getSelectedIndex() > 0){
            possibleValues.add(allUncommonRelics.get(uncommonRelicDropdown.getSelectedIndex() - 1).relicId);
        } else {
            possibleValues.add(null);
        }
        if (rareRelicDropdown.getSelectedIndex() > 0){
            possibleValues.add(allRareRelics.get(rareRelicDropdown.getSelectedIndex() - 1).relicId);
        } else {
            possibleValues.add(null);
        }
        return possibleValues;
    }
}
