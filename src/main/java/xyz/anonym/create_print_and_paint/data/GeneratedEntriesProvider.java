package xyz.anonym.create_print_and_paint.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xyz.anonym.create_print_and_paint.Create_Print_and_Paint;
import xyz.anonym.create_print_and_paint.worldgen.ConfiguredFeatures;
import xyz.anonym.create_print_and_paint.worldgen.PlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GeneratedEntriesProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifiers::bootstrap);

    public GeneratedEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Create_Print_and_Paint.MODID));
    }

    @Override
    public String getName() {
        return "Create: Print 'n Paint's Generated Registry Entries";
    }
}
