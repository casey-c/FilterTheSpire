package FilterTheSpire.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DropdownMenuPatch {
    @SpirePatch(
            clz = DropdownMenu.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {DropdownMenuListener.class, ArrayList.class, BitmapFont.class, Color.class, int.class}
    )
    public static class SetHitbox {
        @SpirePostfixPatch
        public static void setHitbox(DropdownMenu __instance, DropdownMenuListener listener, ArrayList<String> options, BitmapFont font, Color textColor, int maxRows) {
            // The hb is private so we have to use reflection here
            Object dropdownRow = ReflectionHacks.getPrivate(__instance, DropdownMenu.class, "selectionBox");
            Class<?> cls = dropdownRow.getClass();
            try {
                Field hitboxField = cls.getDeclaredField("hb");
                hitboxField.setAccessible(true);
                Hitbox hb = (Hitbox) hitboxField.get(dropdownRow);
                HitboxField.dropdownHitbox.set(__instance, hb);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                HitboxField.dropdownHitbox.set(__instance, null);
            }
        }
    }

    @SpirePatch(
            clz = DropdownMenu.class,
            method=SpirePatch.CLASS
    )
    public static class HitboxField {
        public static SpireField<Hitbox> dropdownHitbox = new SpireField<>(() -> null);
    }
}
