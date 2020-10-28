package FilterTheSpire.utils;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

import java.util.HashMap;

import static com.megacrit.cardcrawl.ui.panels.ExhaustPanel.fontScale;

public class ExtraFonts {
    private static BitmapFont LARGE_NUMBER_FONT;

    public static BitmapFont largeNumberFont() {
        if (LARGE_NUMBER_FONT == null) {

            //BitmapFont font = FontHelper.tipBodyFont;
            //LARGE_NUMBER_FONT = new BitmapFont(FontHelper.tipBodyFont);
            float size = 210.0f;

            int shadowOffsetX = (int)(3.0F * Settings.scale);
            int shadowOffsetY = (int)(3.0F * Settings.scale);

            float gamma = 0.9F;
            float borderGamma = 0.9F;

            //Color borderColor = new Color(0.4F, 0.1F, 0.1F, 1.0F);
            Color borderColor = ExtraColors.PINK_BORDER_COLOR;
            float borderWidth = 8.0F;

            boolean borderStraight = false;
            int spaceX = 0;
            int spaceY = 0;
            Color shadowColor = Settings.QUARTER_TRANSPARENT_BLACK_COLOR.cpy();

            LARGE_NUMBER_FONT = prepFont(size,
                    false,
                    gamma,
                    spaceX,
                    spaceY,
                    borderColor,
                    borderStraight,
                    borderWidth,
                    borderGamma,
                    shadowColor,
                    shadowOffsetX,
                    shadowOffsetY);

            //tipBodyFont = prepFont(22.0F, false);
        }

        return LARGE_NUMBER_FONT;
    }

    private static BitmapFont prepFont(float size,
                                       boolean isLinearFiltering,
                                       float gamma,
                                       int spaceX,
                                       int spaceY,
                                       Color borderColor,
                                       boolean borderStraight,
                                       float borderWidth,
                                       float borderGamma,
                                       Color shadowColor,
                                       int shadowOffsetX,
                                       int shadowOffsetY
    ) {
        FreeTypeFontGenerator g;

        HashMap<String, FreeTypeFontGenerator> generators = (HashMap<String, FreeTypeFontGenerator>) ReflectionHacks.getPrivateStatic(FontHelper.class, "generators");
        //FileHandle fontFile = (FileHandle) ReflectionHacks.getPrivateStatic(FontHelper.class, "fontFile");

        FileHandle fontFile = Gdx.files.internal("font/Kreon-Bold.ttf");

        if (generators.containsKey(fontFile.path())) {
            g = (FreeTypeFontGenerator)generators.get(fontFile.path());
        } else {
            System.out.println("ERROR: this shouldn't occur!");
            // TODO: throw an exception I guess

            g = new FreeTypeFontGenerator(fontFile);
            generators.put(fontFile.path(), g);
        }

        if (Settings.BIG_TEXT_MODE) {
            size *= 1.2F;
        }

        return prepFont(g, size, isLinearFiltering, gamma, spaceX, spaceY, borderColor, borderStraight, borderWidth, borderGamma, shadowColor, shadowOffsetX, shadowOffsetY);
    }


    private static BitmapFont prepFont(FreeTypeFontGenerator g,
                                       float size,
                                       boolean isLinearFiltering,
                                       float gamma,
                                       int spaceX,
                                       int spaceY,
                                       Color borderColor,
                                       boolean borderStraight,
                                       float borderWidth,
                                       float borderGamma,
                                       Color shadowColor,
                                       int shadowOffsetX,
                                       int shadowOffsetY
                                       ) {
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.characters = "";
        p.incremental = true;
        p.size = Math.round(size * fontScale * Settings.scale);

        p.gamma = gamma;
        p.spaceX = spaceX;
        p.spaceY = spaceY;

        p.borderColor = borderColor;
        p.borderStraight = borderStraight;
        p.borderWidth = borderWidth;
        p.borderGamma = borderGamma;

        p.shadowColor = shadowColor;
        p.shadowOffsetX = shadowOffsetX;
        p.shadowOffsetY = shadowOffsetY;

        if (isLinearFiltering) {
            p.minFilter = Texture.TextureFilter.Linear;
            p.magFilter = Texture.TextureFilter.Linear;
        } else {
            p.minFilter = Texture.TextureFilter.Nearest;
            p.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        }

        g.scaleForPixelHeight(p.size);
        BitmapFont font = g.generateFont(p);
        font.setUseIntegerPositions(!isLinearFiltering);
        font.getData().markupEnabled = true;
        if (LocalizedStrings.break_chars != null) {
            font.getData().breakChars = LocalizedStrings.break_chars.toCharArray();
        }

        return font;
    }

}
