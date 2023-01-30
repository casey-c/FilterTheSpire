package FilterTheSpire.ui.screens;

import FilterTheSpire.FilterManager;
import FilterTheSpire.FilterTheSpire;
import FilterTheSpire.factory.FilterObject;
import FilterTheSpire.ui.components.ActionButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

public abstract class FilterScreen {
    public FilterObject filterObject;
    public boolean isShowing = false;
    public final ActionButton returnButton = new ActionButton(256, 450, "Back");
    public static final float INFO_LEFT = 1120.0f;
    public static final float INFO_BOTTOM_CHECK = 670.0f;
    public static final float INFO_TOP_MAIN = INFO_BOTTOM_CHECK - 40.0f;
    public static final float INFO_TOP_CONTROLS = INFO_TOP_MAIN - 144.0f - 40.0f;
    public static final float INFO_WIDTH = 420.0f;

    private Texture TEX_BG = new Texture("FilterTheSpire/images/config_screen_bg.png");

    private void renderBg(SpriteBatch sb) {
        // Draw our screen texture in the center
        sb.setColor(Color.WHITE);
        sb.draw(TEX_BG,
                Math.round((Settings.WIDTH - (TEX_BG.getWidth() * Settings.xScale)) * 0.5f),
                Math.round((Settings.HEIGHT - (TEX_BG.getHeight() * Settings.yScale)) * 0.5f),
                Math.round(TEX_BG.getWidth() * Settings.xScale),
                Math.round(TEX_BG.getHeight() * Settings.yScale)
        );
    }

    public void render(SpriteBatch sb) {
        renderBg(sb);
        renderForeground(sb);
    }

    public void clearFilter(){
        filterObject = new FilterObject(filterObject.filterType);
        resetUI();
    }

    public void refreshFilters() {
        FilterTheSpire.config.updateFilter(filterObject);
        FilterManager.setFilter(filterObject);
    }

    abstract void renderForeground(SpriteBatch sb);
    abstract void update();
    abstract void resetUI();
}

