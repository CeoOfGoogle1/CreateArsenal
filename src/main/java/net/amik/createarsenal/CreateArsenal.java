package net.amik.createarsenal;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.amik.createarsenal.datagen.ArsenalDataGen;
import net.amik.createarsenal.registrate.*;
import net.amik.createarsenal.registrate.network.ModMessages;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateArsenal.MOD_ID)

public class CreateArsenal
{
    // Directly reference a slf4j logger
    public static final String MOD_ID = "createarsenal";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateArsenal.MOD_ID).creativeModeTab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL);

    public CreateArsenal()
    {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ModItems.register();
        ModCraftingMaterials.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModProjectiles.register();
        ModTranslations.register();
        ModSoundEvents.prepare();
        REGISTRATE.registerEventListeners(eventBus);
        eventBus.addListener(CreateArsenal::init);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ModPartials::init);
        eventBus.addGenericListener(SoundEvent.class, ModSoundEvents::register);
        eventBus.addListener(EventPriority.LOWEST, ArsenalDataGen::gatherData);

    }


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModMessages::register);
        ModDisplayBehaviors.load();
    }

    public static ResourceLocation resource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
