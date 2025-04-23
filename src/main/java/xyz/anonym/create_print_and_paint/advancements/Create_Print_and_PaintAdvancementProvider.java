package xyz.anonym.create_print_and_paint.advancements;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.anonym.create_print_and_paint.Create_Print_and_Paint;
import xyz.anonym.create_print_and_paint.registration.Items;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class Create_Print_and_PaintAdvancementProvider extends AdvancementProvider {
    private static final String MODID = Create_Print_and_Paint.MODID;
    public Create_Print_and_PaintAdvancementProvider(PackOutput output,
                                                     CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, existingFileHelper, List.of(new Create_Print_and_PaintAdvancementGenerator()));
    }

    private static final class Create_Print_and_PaintAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            Advancement.Builder builder = Advancement.Builder.advancement();
            builder.display(
                    // The advancement icon. Can be an ItemStack or an ItemLike.
                    new ItemStack(Items.EMPTY_CAN.get()),
                    // The advancement title and description. Don't forget to add translations for these!
                    Component.translatable("advancements.create_print_and_paint.root"),
                    Component.translatable("advancements.create_print_and_paint.root"),
                    // The background texture. Use null if you don't want a background texture (for non-root advancements).
                    ResourceLocation.fromNamespaceAndPath(MODID, "textures/fluid/paint_ingredient_still"),
                    // The frame type. Valid values are AdvancementType.TASK, CHALLENGE, or GOAL.
                    AdvancementType.GOAL,
                    // Whether to show the advancement toast or not.
                    true,
                    // Whether to announce the advancement into chat or not.
                    true,
                    // Whether the advancement should be hidden or not.
                    false
            );
            builder.build(ResourceLocation.fromNamespaceAndPath(MODID, "root"));
            builder.save(saver, ResourceLocation.fromNamespaceAndPath(MODID, "root"), existingFileHelper);
        }
        }
    }