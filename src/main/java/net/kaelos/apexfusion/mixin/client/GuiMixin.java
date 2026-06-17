package net.kaelos.apexfusion.mixin.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;

import net.kaelos.apexfusion.client.ResourceRequirementOverlay;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void apexfusion$renderResourceRequirement(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        ResourceRequirementOverlay.render(guiGraphics);
    }
}
