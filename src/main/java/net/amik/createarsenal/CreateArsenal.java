package net.amik.createarsenal;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.registrate.ModShellTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateArsenal.MOD_ID)

public class CreateArsenal
{
    // Directly reference a slf4j logger
    public static final String MOD_ID = "createarsenal";
    public static final Logger LOGGER = LogUtils.getLogger();
    public CreateArsenal()
    {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();


        ModItems.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModShellTypes.register();

        //Copied from CreateAddition
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ModBlockPartials.init());

        eventBus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TURRET_BASE_BLOCK.get(), RenderType.cutout());

    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateArsenal.MOD_ID);

    public void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event)
    {

    }

}
