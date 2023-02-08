package net.ceoofgoogle.createarsenal;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CreateArsenalClient {

    public CreateArsenalClient(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.register(this);
    }

    @SubscribeEvent
    public void modelRegistry(final ModelRegistryEvent event) {

    }
}
