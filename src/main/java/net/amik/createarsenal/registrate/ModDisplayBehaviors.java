package net.amik.createarsenal.registrate;

import com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours;
import com.simibubi.create.content.redstone.displayLink.DisplayBehaviour;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.block.radar.monitor.MonitorDisplayBehavior;
import net.amik.createarsenal.compat.Mods;
import net.amik.createarsenal.compat.cbc.CBC;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignBlockEntity;

public class ModDisplayBehaviors {


    static {
        register("monitor", new MonitorDisplayBehavior(), ModBlockEntities.MONITOR.get());
        register("radar", new MonitorDisplayBehavior.RadarSource(), ModBlockEntities.RADAR_BASE_BLOCK_TILE_ENTITY.get());
        Mods.CREATEBIGCANNONS.executeIfInstalled(() -> CBC::registerDisplayBehaviors);
    }

    public static void register(String id, DisplayBehaviour behaviour, BlockEntityType<?> be) {
        assignBlockEntity(AllDisplayBehaviours.register(CreateArsenal.resource(id), behaviour), be);
    }

    public static void load() {}
}
