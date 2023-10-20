package net.amik.createarsenal;

import net.amik.createarsenal.shell.BulletModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateArsenal.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @Mod.EventBusSubscriber(modid = CreateArsenal.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(BulletModel.LAYER_LOCATION, BulletModel::createBodyLayer);

        }

    }

}
