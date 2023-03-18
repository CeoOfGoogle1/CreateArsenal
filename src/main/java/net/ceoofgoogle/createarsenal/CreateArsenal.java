package net.ceoofgoogle.createarsenal;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.ceoofgoogle.createarsenal.block.entity.ModBlockEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import net.ceoofgoogle.createarsenal.block.ModBlocks;
import net.ceoofgoogle.createarsenal.item.ModItems;
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


        ModItems.register(eventBus);
        ModBlocks.register();
        ModBlockEntities.register();

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
        return REGISTRATE.get();
    }

    @SuppressWarnings("removal")
    public static final NonNullSupplier<CreateRegistrate> REGISTRATE = CreateRegistrate.lazy(CreateArsenal.MOD_ID);

    public void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event)
    {

    }

}
