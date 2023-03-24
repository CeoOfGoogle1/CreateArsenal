package net.amik.createarsenal.content;

import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.content.contraptions.relays.encased.ShaftInstance;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.content.block.turret_base.TurretBaseBlockTileEntity;

public class ModBlockEntities {
    private static final CreateRegistrate REGISTRATE = CreateArsenal
            .registrate();


    public static final BlockEntityEntry<TurretBaseBlockTileEntity> TURRET_BASE_BLOCK_TILE_ENTITY = REGISTRATE
            .tileEntity("turret_base_block", TurretBaseBlockTileEntity::new)
            .instance(() -> ShaftInstance::new, true)
            .validBlocks(ModBlocks.TURRET_BASE_BLOCK)
            .renderer(() -> KineticTileEntityRenderer::new)
            .register();

    public static void register(){}
}
