package net.kaelos.apexfusion.blueprint;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public record BlueprintData(ResourceLocation result, Map<String, Integer> requiredMaterials) {}
