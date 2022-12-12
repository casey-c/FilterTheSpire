package FilterTheSpire.ui.components;

import basemod.interfaces.TextReceiver;
import basemod.patches.com.megacrit.cardcrawl.helpers.input.ScrollInputProcessor.TextInput;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class FilterTextInput implements TextReceiver {
    private static final Texture inputArea = new Texture("FilterTheSpire/images/textInput.png");
    private static final Texture white = new Texture("FilterTheSpire/images/textIndicator.png");

    private static final int WIDTH = 666;
    public static final int HEIGHT = 60;

    private static final int TEXT_CAP = 30;

    private static final float TEXT_OFFSET_X = 30.0f * Settings.scale;
    private static final float TEXT_OFFSET_Y = HEIGHT / 2.0f * Settings.scale;

    private static final float MARKER_OFFSET = 18.0f * Settings.scale;
    private static final float MARKER_WIDTH = FontHelper.getWidth(FontHelper.tipBodyFont, "-", 1);
    private static final float MARKER_HEIGHT = 2.0f * Settings.scale;

    private boolean active;

    private String text, displayText;
    private int charLimit = TEXT_CAP;

    private Hitbox hb;

    private float textX;
    private float textY;
    private float markerY;

    private Runnable onEnter = null;

    public FilterTextInput(float x, float y)
    {
        this.hb = new Hitbox(x, y, WIDTH * Settings.scale, HEIGHT * Settings.scale);
        this.textX = x + TEXT_OFFSET_X;
        this.textY = y + TEXT_OFFSET_Y;
        this.markerY = y + MARKER_OFFSET;

        displayText = text = "";

        active = false;
    }

    public FilterTextInput setCharLimit(int limit) {
        this.charLimit = limit;
        return this;
    }

    public void reset()
    {
        this.text = "";
        this.onEnter = null;
        this.active = false;
    }
    public void cancel()
    {
        this.active = false;
    }

    public void setOnEnter(Runnable r) {
        // probably set the encounter index on the filter, delimited by ","
        this.onEnter = r;
    }

    @Override
    public boolean onPushEnter() {
        if (onEnter != null)
            onEnter.run();
        return true;
    }

    @Override
    public String getCurrentText() {
        return text;
    }

    public void setText(String t)
    {
        this.text = t;
        this.displayText = text;
    }

    @Override
    public boolean isDone() {
        return !active;
    }

    @Override
    public boolean acceptCharacter(char c) {
        return FontHelper.tipBodyFont.getData().hasGlyph(c);
    }

    @Override
    public int getCharLimit() {
        return charLimit;
    }

    public void update()
    {
        hb.update();
        if (hb.hovered && InputHelper.justReleasedClickLeft)
        {
            InputHelper.justReleasedClickLeft = false;
            active = true;
            TextInput.startTextReceiver(this);
        }
        else if (InputHelper.justReleasedClickLeft || InputHelper.isMouseDown)
        {
            TextInput.stopTextReceiver(this);
            active = false;
        }
    }

    private final Color renderColor = Color.WHITE.cpy();
    public void render(SpriteBatch sb)
    {
        sb.setColor(renderColor);
        sb.draw(inputArea, hb.x, hb.y, 0, 0, WIDTH, HEIGHT, Settings.scale, Settings.scale, 0, 0, 0, WIDTH, HEIGHT, false, false);

        FontHelper.renderFontLeft(sb, FontHelper.tipBodyFont, displayText, textX, textY, renderColor);

        if (active)
        {
            sb.draw(white, textX + FontHelper.getWidth(FontHelper.tipBodyFont, displayText, 1), markerY, 0, 0, MARKER_WIDTH, MARKER_HEIGHT, 1.0f, 1.0f, 0, 0, 0, 1, 1, false, false);
        }
    }
}