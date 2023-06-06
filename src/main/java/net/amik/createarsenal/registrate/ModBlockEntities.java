package net.amik.createarsenal.registrate;


import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.amik.createarsenal.block.staticTurret.EightBarrelTurret.EightBarrelStaticTurretTileEntity;
import net.amik.createarsenal.block.staticTurret.FourBarrelTurret.FourBarrelStaticTurretInstance;
import net.amik.createarsenal.block.staticTurret.FourBarrelTurret.FourBarrelStaticTurretRenderer;
import net.amik.createarsenal.block.staticTurret.FourBarrelTurret.FourBarrelStaticTurretTileEntity;
import net.amik.createarsenal.block.staticTurret.EightBarrelTurret.EightBarrelStaticTurretInstance;
import net.amik.createarsenal.block.staticTurret.EightBarrelTurret.EightBarrelStaticTurretRenderer;
import net.amik.createarsenal.block.staticTurret.EightBarrelTurret.EightBarrelStaticTurretTileEntity;
import net.amik.createarsenal.block.turret_base.TurretBaseBlockTileEntity;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;

public class ModBlockEntities {


    public static final BlockEntityEntry<TurretBaseBlockTileEntity> TURRET_BASE_BLOCK_TILE_ENTITY = REGISTRATE
            .blockEntity("turret_base_block", TurretBaseBlockTileEntity::new)
            .instance(() -> ShaftInstance::new, true)
            .validBlocks(ModBlocks.TURRET_BASE_BLOCK)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<FourBarrelStaticTurretTileEntity> FOUR_BARREL_STATIC_TURRET_TILE_ENTITY = REGISTRATE
            .blockEntity("four_barrel_static_turret", FourBarrelStaticTurretTileEntity::new)
            .instance(() -> FourBarrelStaticTurretInstance::new, true)
            .validBlocks(ModBlocks.FOUR_BARREL_STATIC_TURRET)
            .renderer(() -> FourBarrelStaticTurretRenderer::new)
            .register();

    public static final BlockEntityEntry<EightBarrelStaticTurretTileEntity> EIGHT_BARREL_STATIC_TURRET_TILE_ENTITY = REGISTRATE
            .blockEntity("eight_barrel_static_turret", EightBarrelStaticTurretTileEntity::new)
            .instance(() -> EightBarrelStaticTurretInstance::new, true)
            .validBlocks(ModBlocks.EIGHT_BARREL_STATIC_TURRET)
            .renderer(() -> EightBarrelStaticTurretRenderer::new)
            .register();

    public static void register(){}
}
