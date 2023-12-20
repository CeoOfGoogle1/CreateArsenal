package net.amik.createarsenal.registrate;

import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.*;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.block.monitor.MonitorDisplayBehavior;
import net.minecraft.resources.ResourceLocation;

public class ModDisplayBehaviors {
    private static ResourceLocation getResource(String name) {
        return new ResourceLocation(CreateArsenal.MOD_ID, name);
    }

    static {
        assignBlockEntity(register(getResource("monitor"), new MonitorDisplayBehavior()), ModBlockEntities.MONITOR.get());
        assignBlockEntity(register(getResource("radar"), new MonitorDisplayBehavior.RadarSource()), ModBlockEntities.RADAR_BASE_BLOCK_TILE_ENTITY.get());

    }

    public static void load() {}
}
