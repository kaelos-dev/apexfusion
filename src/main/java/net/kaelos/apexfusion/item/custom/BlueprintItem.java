package net.kaelos.apexfusion.item.custom;

import net.kaelos.apexfusion.blueprint.BlueprintData;
import net.kaelos.apexfusion.blueprint.BlueprintManager;
import net.kaelos.apexfusion.component.AFDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlueprintItem extends Item {
    public BlueprintItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (stack.has(AFDataComponents.BLUEPRINT_ID.get())) {
            ResourceLocation blueprintId = stack.get(AFDataComponents.BLUEPRINT_ID.get());

            assert blueprintId != null;
            tooltip.add(Component.literal("Blueprint: " + blueprintId.getPath()));

            if (BlueprintManager.BLUEPRINTS.containsKey(blueprintId)) {
                BlueprintData data = BlueprintManager.BLUEPRINTS.get(blueprintId);

                tooltip.add(Component.literal("Material list:"));

                data.requiredMaterials().forEach((material, count) -> {
                    tooltip.add(Component.literal("-" + material + " x" + count));
                });
            }
        }
    }
}
