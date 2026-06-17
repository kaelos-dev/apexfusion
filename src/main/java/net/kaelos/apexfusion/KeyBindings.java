package net.kaelos.apexfusion;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String CATEGORY_APEXFUSION = "key.categories." + AF.MOD_ID + ".general";

    public static final KeyMapping TOOL_SETTINGS = new KeyMapping(
            "key." + AF.MOD_ID + ".tool_settings",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            CATEGORY_APEXFUSION
    );
}
