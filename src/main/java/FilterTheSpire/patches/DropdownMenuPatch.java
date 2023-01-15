package FilterTheSpire.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;

import java.util.ArrayList;

public class DropdownMenuPatch {
    @SpirePatch(
            clz = DropdownMenu.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {DropdownMenuListener.class, ArrayList.class, BitmapFont.class, Color.class, int.class}
    )
    public static class SetWidth {
        @SpirePostfixPatch
        public static void setWidth(DropdownMenu __instance, DropdownMenuListener listener, ArrayList<String> options, BitmapFont font, Color textColor, int maxRows) {
            float cachedMaxWidth = ReflectionHacks.getPrivate(__instance, DropdownMenu.class, "cachedMaxWidth");
            CachedWidth.cachedMaxWidth.set(__instance, cachedMaxWidth);
        }
    }

    @SpirePatch(
            clz = DropdownMenu.class,
            method=SpirePatch.CLASS
    )
    public static class CachedWidth {
        public static SpireField<Float> cachedMaxWidth = new SpireField<>(() -> 0.0F);
    }

    // This should be called in an override for render
    public static void renderTip(DropdownMenu dropdownMenu, float x, float y, String header, String message) {
        Hitbox hb = dropdownMenu.getHitbox();
        if (hb != null && hb.hovered) {
            float width = DropdownMenuPatch.CachedWidth.cachedMaxWidth.get(dropdownMenu);
            TipHelper.renderGenericTip(
                    (x + width + 10.0F) * Settings.xScale, (y - 20.0F) * Settings.yScale,
                    header,
                    message);
        }
    }
}
