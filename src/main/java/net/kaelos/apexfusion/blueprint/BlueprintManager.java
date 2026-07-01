package net.kaelos.apexfusion.blueprint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kaelos.apexfusion.AF;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BlueprintManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public static final Map<ResourceLocation, BlueprintData> BLUEPRINTS = new HashMap<>();

    public BlueprintManager() {
        super(GSON, "blueprints");
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> objectMap, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        BLUEPRINTS.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : objectMap.entrySet()) {
            ResourceLocation id = entry.getKey();
            try {
                JsonObject json = entry.getValue().getAsJsonObject();

                String resultString = json.get("result").getAsString();
                ResourceLocation resultBlockId = ResourceLocation.parse(resultString);

                JsonObject materialsJson = json.getAsJsonObject("materials");
                Map<String, Integer> materials = new HashMap<>();
                materialsJson.entrySet().forEach(e -> materials.put(e.getKey(), e.getValue().getAsInt()));

                BLUEPRINTS.put(id, new BlueprintData(resultBlockId, materials));
                AF.LOGGER.info("The blueprint has been successfully uploaded: {}", id);
            } catch (Exception e) {
                AF.LOGGER.error("Error loading the blueprint: {}", id);
            }
        }
    }
}
