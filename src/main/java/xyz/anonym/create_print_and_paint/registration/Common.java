package xyz.anonym.create_print_and_paint.registration;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.anonym.create_print_and_paint.Create_Print_and_Paint;

public class Common {
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(Create_Print_and_Paint.MODID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Create_Print_and_Paint.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Create_Print_and_Paint.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Create_Print_and_Paint.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_PRINT_AND_PAINT_CREATIVE_MODE_TAB = CREATIVE_MODE_TABS.register("create_print_and_paint", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.create_print_and_paint"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> Items.EMPTY_CAN.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(Items.EMPTY_CAN.get());
                output.accept(Items.RED_SPRAY_CAN.get());
                output.accept(Items.BLUE_SPRAY_CAN.get());
                output.accept(Items.WHITE_SPRAY_CAN.get());
                output.accept(Items.GRAY_SPRAY_CAN.get());
                output.accept(Items.LIGHT_GRAY_SPRAY_CAN.get());
                output.accept(Items.BLACK_SPRAY_CAN.get());
                output.accept(Items.BROWN_SPRAY_CAN.get());
                output.accept(Items.ORANGE_SPRAY_CAN.get());
                output.accept(Items.YELLOW_SPRAY_CAN.get());
                output.accept(Items.LIME_SPRAY_CAN.get());
                output.accept(Items.GREEN_SPRAY_CAN.get());
                output.accept(Items.CYAN_SPRAY_CAN.get());
                output.accept(Items.LIGHT_BLUE_SPRAY_CAN.get());
                output.accept(Items.PURPLE_SPRAY_CAN.get());
                output.accept(Items.MAGENTA_SPRAY_CAN.get());
                output.accept(Items.PINK_SPRAY_CAN.get());
                output.accept(Fluids.PAINT_INGREDIENT.get().getBucket());
                output.accept(Items.RAW_ALUMINUM.get());
                output.accept(Blocks.ALUMINUM_ORE.asItem());
                output.accept(Items.ALUMINUM_INGOT.get());
                output.accept(Items.ALUMINUM_SHEET.get());
            }).build());

}
