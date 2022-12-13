package FilterTheSpire.ui.screens;

import FilterTheSpire.ui.components.RelicUIObject;
import FilterTheSpire.utils.ExtraFonts;
import FilterTheSpire.utils.FilterType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;

import java.util.Arrays;

public class NthRelicFilterScreen extends RelicFilterScreen implements ScrollBarListener {
    private static final int RELICS_PER_ROW = 6;
    private static final float SPACING = 84.0f;
    private static final int VIEW_WINDOW = 400;

    // Scrolling
    private ScrollBar scrollBar = null;
    private boolean grabbedScreen = false;
    private float grabStartY = 0.0F;
    private float scrollTargetY = 0.0F;
    private float scrollY = 0.0F;
    private float scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
    private float scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;

    // Position
    public float x;
    public float y;

    public NthRelicFilterScreen(){
        super(Arrays.asList(AbstractRelic.RelicTier.COMMON, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.RelicTier.RARE), FilterType.NthRelic);

        // Setup scrollbar
        if (this.scrollBar == null) {
            calculateScrollBounds();
            this.scrollBar = new ScrollBar(this, 0, 0, 400.0F * Settings.yScale);
        }

        this.move(0, 0);
    }

    void postRelicSetup() {

    }

    void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        int viewStart = 200;
        for (RelicUIObject relic : relicUIObjects.values()){
            if (relic.getScrollPosition() > viewStart * Settings.yScale &&
                    relic.getScrollPosition() < (viewStart + VIEW_WINDOW) * Settings.yScale){
                relic.render(sb);
            }
        }

        this.returnButton.render(sb);
        this.scrollBar.render(sb);

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb,
                ExtraFonts.configTitleFont(),
                "Relic Filter",
                titleLeft * Settings.xScale,
                titleBottom * Settings.yScale,
                Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose which Relics will appear from the first encounter. If no relics are selected, it will choose from the entire pool.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN * Settings.yScale) + 100,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "Treasure chest size may make this incorrect. This filter is mainly used for random relics from events and elites.",
                INFO_LEFT * Settings.xScale,
                (INFO_TOP_MAIN * Settings.yScale) - 40,
                INFO_WIDTH * Settings.xScale,
                30.0f * Settings.yScale,
                Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "Controls: NL Click to toggle NL Right+Click to select just one NL NL Shift+Click to select all NL Shift+Right+Click to clear all NL Alt+Click to invert all",
                INFO_LEFT * Settings.scale,
                INFO_TOP_CONTROLS * Settings.scale,
                INFO_WIDTH * Settings.scale,
                30.0f * Settings.scale,
                Color.GRAY);
    }

    void update() {
        this.returnButton.update();
        this.enableHitboxes(true);

        for (RelicUIObject relic : relicUIObjects.values()){
            relic.update();
            relic.scroll(this.scrollY);
        }

        if (this.returnButton.hb.clickStarted){
            this.enableHitboxes(false);
        }

        boolean isDraggingScrollBar = this.scrollBar.update();
        if (!isDraggingScrollBar){
            updateScrolling();
        }
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;

        scrollBar.setCenter(x + 980f * Settings.xScale, y + 465f * Settings.yScale);
    }

    //  Begin scroll functions
    private void updateScrolling() {
        int y = InputHelper.mY;
        if (!this.grabbedScreen) {
            if (InputHelper.scrolledDown) {
                this.scrollTargetY += Settings.SCROLL_SPEED;
            } else if (InputHelper.scrolledUp) {
                this.scrollTargetY -= Settings.SCROLL_SPEED;
            }
            if (InputHelper.justClickedLeft) {
                this.grabbedScreen = true;
                this.grabStartY = y - this.scrollTargetY;
            }
        } else if (InputHelper.isMouseDown) {
            this.scrollTargetY = y - this.grabStartY;
        } else {
            this.grabbedScreen = false;
        }
        this.scrollY = MathHelper.scrollSnapLerpSpeed(this.scrollY, this.scrollTargetY);
        resetScrolling();
        updateBarPosition();
    }

    public void scrolledUsingBar(float newPercent) {
        this.scrollY = MathHelper.valueFromPercentBetween(this.scrollLowerBound, this.scrollUpperBound, newPercent);
        this.scrollTargetY = this.scrollY;
        updateBarPosition();
    }

    private void updateBarPosition() {
        float percent = MathHelper.percentFromValueBetween(this.scrollLowerBound, this.scrollUpperBound, this.scrollY);
        this.scrollBar.parentScrolledToPercent(percent);
    }

    private void calculateScrollBounds() {
        int rows = (this.relicUIObjects.size() + RELICS_PER_ROW - 1) / RELICS_PER_ROW;
        this.scrollUpperBound = (SPACING * rows) - VIEW_WINDOW;
        this.scrollLowerBound = 0F;
    }

    private void resetScrolling() {
        if (this.scrollTargetY < this.scrollLowerBound) {
            this.scrollTargetY = MathHelper.scrollSnapLerpSpeed(this.scrollTargetY, this.scrollLowerBound);
        } else if (this.scrollTargetY > this.scrollUpperBound) {
            this.scrollTargetY = MathHelper.scrollSnapLerpSpeed(this.scrollTargetY, this.scrollUpperBound);
        }
    }
    //  End scroll functions
}
