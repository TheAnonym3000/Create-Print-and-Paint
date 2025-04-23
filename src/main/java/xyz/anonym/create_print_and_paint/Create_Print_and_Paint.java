package xyz.anonym.create_print_and_paint;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
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
import xyz.anonym.create_print_and_paint.data.DataGen;
import xyz.anonym.create_print_and_paint.registration.Common;
import xyz.anonym.create_print_and_paint.registration.Fluids;


@Mod(Create_Print_and_Paint.MODID)
public class Create_Print_and_Paint {

    public static final String MODID = "create_print_and_paint";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Create_Print_and_Paint(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);
        Fluids.REGISTRATE.registerEventListeners(modEventBus);
        Common.BLOCKS.register(modEventBus);
        Common.ITEMS.register(modEventBus);
        Common.CREATIVE_MODE_TABS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        NeoForgeMod.enableMilkFluid();
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modEventBus.addListener(EventPriority.LOWEST, DataGen::gatherData);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }


    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
    }

}
