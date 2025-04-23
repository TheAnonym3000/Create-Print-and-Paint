package xyz.anonym.create_print_and_paint.worldgen;

import static net.minecraft.data.worldgen.placement.PlacementUtils.register;
import com.simibubi.create.infrastructure.worldgen.AllConfiguredFeatures;
import com.simibubi.create.infrastructure.worldgen.ConfigPlacementFilter;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import xyz.anonym.create_print_and_paint.Create_Print_and_Paint;

import java.util.List;

public class PlacedFeatures {
    public static final ResourceKey<PlacedFeature>
            ALUMINUM_ORE = key("aluminum_ore");

    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Create_Print_and_Paint.MODID, name));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> featureLookup = ctx.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> aluminumOre = featureLookup.getOrThrow(ConfiguredFeatures.ALUMINUM_ORE);

        register(ctx, ALUMINUM_ORE, aluminumOre, placement(CountPlacement.of(8), -63, 70));
    }

    private static List<PlacementModifier> placement(PlacementModifier frequency, int minHeight, int maxHeight) {
        return List.of(
                frequency,
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)),
                ConfigPlacementFilter.INSTANCE
        );
    }
}
