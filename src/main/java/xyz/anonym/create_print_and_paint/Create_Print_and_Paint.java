package xyz.anonym.create_print_and_paint;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.catnip.theme.Color;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Create_Print_and_Paint.MODID)
public class Create_Print_and_Paint {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "create_print_and_paint";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );
    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);
    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));
    public static final DeferredItem<Item> EMPTY_CAN = ITEMS.registerItem(
            "empty_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> RED_SPRAY_CAN = ITEMS.registerItem(
            "red_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> BLUE_SPRAY_CAN = ITEMS.registerItem(
            "blue_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> WHITE_SPRAY_CAN = ITEMS.registerItem(
            "white_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> GRAY_SPRAY_CAN = ITEMS.registerItem(
            "gray_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> LIGHT_GRAY_SPRAY_CAN = ITEMS.registerItem(
            "light_gray_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> BLACK_SPRAY_CAN = ITEMS.registerItem(
            "black_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> BROWN_SPRAY_CAN = ITEMS.registerItem(
            "brown_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> ORANGE_SPRAY_CAN = ITEMS.registerItem(
            "orange_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> YELLOW_SPRAY_CAN = ITEMS.registerItem(
            "yellow_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> LIME_SPRAY_CAN = ITEMS.registerItem(
            "lime_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> GREEN_SPRAY_CAN = ITEMS.registerItem(
            "green_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> CYAN_SPRAY_CAN = ITEMS.registerItem(
            "cyan_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> LIGHT_BLUE_SPRAY_CAN = ITEMS.registerItem(
            "light_blue_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> PURPLE_SPRAY_CAN = ITEMS.registerItem(
            "purple_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> MAGENTA_SPRAY_CAN = ITEMS.registerItem(
            "magenta_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> PINK_SPRAY_CAN = ITEMS.registerItem(
            "pink_spray_can",
            Item::new,
            new Item.Properties()
    );
    public static final FluidEntry<BaseFlowingFluid.Flowing> PAINT_INGREDIENT =
            REGISTRATE.standardFluid("paint_ingredient",
                           SolidRenderedPlaceableFluidType.create(0xFFFFFF,
                                    () -> 1f / 32f))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(BaseFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();
    @SuppressWarnings("unused")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_PRINT_AND_PAINT_CREATIVE_MODE_TAB = CREATIVE_MODE_TABS.register("create_print_and_paint", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.create_print_and_paint")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EMPTY_CAN.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EMPTY_CAN.get());
                output.accept(RED_SPRAY_CAN.get());
                output.accept(BLUE_SPRAY_CAN.get());
                output.accept(WHITE_SPRAY_CAN.get());
                output.accept(GRAY_SPRAY_CAN.get());
                output.accept(LIGHT_GRAY_SPRAY_CAN.get());
                output.accept(BLACK_SPRAY_CAN.get());
                output.accept(BROWN_SPRAY_CAN.get());
                output.accept(ORANGE_SPRAY_CAN.get());
                output.accept(YELLOW_SPRAY_CAN.get());
                output.accept(LIME_SPRAY_CAN.get());
                output.accept(GREEN_SPRAY_CAN.get());
                output.accept(CYAN_SPRAY_CAN.get());
                output.accept(LIGHT_BLUE_SPRAY_CAN.get());
                output.accept(PURPLE_SPRAY_CAN.get());
                output.accept(MAGENTA_SPRAY_CAN.get());
                output.accept(PINK_SPRAY_CAN.get());
                output.accept(PAINT_INGREDIENT.get().getBucket());
            }).build());
    static {
        REGISTRATE.setCreativeTab(CREATE_PRINT_AND_PAINT_CREATIVE_MODE_TAB);
    }
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Create_Print_and_Paint(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        REGISTRATE.registerEventListeners(modEventBus);
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        NeoForgeMod.enableMilkFluid();
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
    public static abstract class TintedFluidType extends FluidType {

        protected static final int NO_TINT = 0xffffffff;
        private ResourceLocation stillTexture;
        private ResourceLocation flowingTexture;

        public TintedFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
            super(properties);
            this.stillTexture = stillTexture;
            this.flowingTexture = flowingTexture;
        }
        @SuppressWarnings("removal")
        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {

                @Override
                public ResourceLocation getStillTexture() {
                    return stillTexture;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return flowingTexture;
                }

                @Override
                public int getTintColor(FluidStack stack) {
                    return TintedFluidType.this.getTintColor(stack);
                }

                @Override
                public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                    return TintedFluidType.this.getTintColor(state, getter, pos);
                }

                @Override
                public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level,
                                                        int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                    Vector3f customFogColor = TintedFluidType.this.getCustomFogColor();
                    return customFogColor == null ? fluidFogColor : customFogColor;
                }

                @Override
                public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick,
                                            float nearDistance, float farDistance, FogShape shape) {
                    float modifier = TintedFluidType.this.getFogDistanceModifier();
                    float baseWaterFog = 96.0f;
                    if (modifier != 1f) {
                        RenderSystem.setShaderFogShape(FogShape.CYLINDER);
                        RenderSystem.setShaderFogStart(-8);
                        RenderSystem.setShaderFogEnd(baseWaterFog * modifier);
                    }
                }

            });
        }

        protected abstract int getTintColor(FluidStack stack);

        protected abstract int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos);

        protected Vector3f getCustomFogColor() {
            return null;
        }

        protected float getFogDistanceModifier() {
            return 1f;
        }

    }

    private static class SolidRenderedPlaceableFluidType extends TintedFluidType {

        private Vector3f fogColor;
        private Supplier<Float> fogDistance;

        public static FluidBuilder.FluidTypeFactory create(int fogColor, Supplier<Float> fogDistance) {
            return (p, s, f) -> {
                SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(p, s, f);
                fluidType.fogColor = new Color(fogColor, false).asVectorF();
                fluidType.fogDistance = fogDistance;
                return fluidType;
            };
        }

        private SolidRenderedPlaceableFluidType(Properties properties, ResourceLocation stillTexture,
                                                ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return NO_TINT;
        }

        /*
         * Removing alpha from tint prevents optifine from forcibly applying biome
         * colors to modded fluids (this workaround only works for fluids in the solid
         * render layer)
         */
        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

        @Override
        protected Vector3f getCustomFogColor() {
            return fogColor;
        }

        @Override
        protected float getFogDistanceModifier() {
            return fogDistance.get();
        }

    }
}
