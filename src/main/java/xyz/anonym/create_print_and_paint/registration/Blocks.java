package xyz.anonym.create_print_and_paint.registration;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.simibubi.create.foundation.data.TagGen.tagBlockAndItem;
import static xyz.anonym.create_print_and_paint.registration.Common.CREATE_PRINT_AND_PAINT_CREATIVE_MODE_TAB;

public class Blocks {
    public static void init() {}
    private static final CreateRegistrate REGISTRATE = Common.REGISTRATE;
    static {
        REGISTRATE.setCreativeTab(CREATE_PRINT_AND_PAINT_CREATIVE_MODE_TAB);
    }
    public static final BlockEntry<Block> ALUMINUM_ORE = REGISTRATE.block("aluminum_ore", Block::new)
            .initialProperties(() -> net.minecraft.world.level.block.Blocks.GOLD_ORE)
            .properties(p -> p.mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
            .transform(pickaxeOnly())
            .loot((lt, b) ->  {
                HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup = lt.getRegistries().lookupOrThrow(Registries.ENCHANTMENT);

                lt.add(b,
                        lt.createSilkTouchDispatchTable(b,
                                lt.applyExplosionDecay(b, LootItem.lootTableItem(Items.RAW_ALUMINUM.get())
                                        .apply(ApplyBonusCount.addOreBonusCount(enchantmentRegistryLookup.getOrThrow(Enchantments.FORTUNE))))));
            })
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .tag(Tags.Blocks.ORES)
            .transform(tagBlockAndItem("ores/aluminum", "ores_in_ground/stone"))
            .tag(Tags.Items.ORES)
            .build()
            .register();
}
