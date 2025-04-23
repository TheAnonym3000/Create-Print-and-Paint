package xyz.anonym.create_print_and_paint.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import xyz.anonym.create_print_and_paint.Create_Print_and_Paint;
import xyz.anonym.create_print_and_paint.registration.Blocks;

import java.util.List;

import static net.minecraft.data.worldgen.features.FeatureUtils.register;

public class ConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>>
            ALUMINUM_ORE = key("aluminum_ore");


    private static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Create_Print_and_Paint.MODID, name));
    }


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> ctx) {
        RuleTest stoneOreReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateOreReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> aluminumTargetStates = List.of(
                OreConfiguration.target(stoneOreReplaceables, Blocks.ALUMINUM_ORE.get()
                        .defaultBlockState())
        );

        register(ctx, ALUMINUM_ORE, Feature.ORE, new OreConfiguration(aluminumTargetStates, 12));
    }
}
