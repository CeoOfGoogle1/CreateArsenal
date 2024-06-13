package net.amik.createarsenal.registrate;


import com.simibubi.create.content.contraptions.bearing.BearingInstance;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.amik.createarsenal.block.aerialBombs.AerialBombBlockEntity;
import net.amik.createarsenal.block.aerialBombs.AerialBombBlockEntityRenderer;
import net.amik.createarsenal.block.monitor.MonitorBlockEntity;
import net.amik.createarsenal.block.monitor.MonitorRenderer;
import net.amik.createarsenal.block.radar.base.RadarBaseBlockTileEntity;
import net.amik.createarsenal.block.seaMine.SeaMineBlockEntity;
import net.amik.createarsenal.block.staticTurret.modularGun.barrel.GunBarrelBlockEntity;
import net.amik.createarsenal.block.staticTurret.modularGun.barrel.GunBarrelBlockRenderer;
import net.amik.createarsenal.block.staticTurret.modularGun.normalGun.NormalGunBlockEntity;
import net.amik.createarsenal.block.staticTurret.modularGun.rotaryGun.RotaryGunBlockEntity;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;

public class ModBlockEntities {


    public static final BlockEntityEntry<NormalGunBlockEntity> NORMAL_GUN_BLOCK_ENTITY = REGISTRATE
            .blockEntity("normal_gun_bearing", NormalGunBlockEntity::new)
            .validBlocks(ModBlocks.NORMAL_GUN)
            .renderer(() -> SmartBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<SeaMineBlockEntity> SEA_MINE = REGISTRATE
            .blockEntity("sea_mine", SeaMineBlockEntity::new)
            .validBlocks(ModBlocks.SEA_MINE)
            .renderer(() -> SmartBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<AerialBombBlockEntity> AERIAL_BOMB = REGISTRATE
            .blockEntity("aerial_bomb", AerialBombBlockEntity::new)
            .validBlocks(ModBlocks.BIG_BOMB, ModBlocks.INCENDIARY_BOMB, ModBlocks.CLUSTER_BOMB, ModBlocks.ARMOR_PIERCING_BOMB, ModBlocks.SHRAPNEL_BOMB)
            .renderer(() -> AerialBombBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<RotaryGunBlockEntity> ROTARY_GUN_BLOCK_ENTITY = REGISTRATE
            .blockEntity("rotary_gun_bearing", RotaryGunBlockEntity::new)
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(ModBlocks.ROTARY_GUN)
            .renderer(() -> ShaftRenderer::new)
            .register();
    public static final BlockEntityEntry<GunBarrelBlockEntity> BARREL_BLOCK_ENTITY = REGISTRATE
            .blockEntity("barrel_block", GunBarrelBlockEntity::new)
            .validBlocks(ModBlocks.BARREL_BLOCK)
            .renderer(() -> GunBarrelBlockRenderer::new)
            .register();

    public static final BlockEntityEntry<MonitorBlockEntity> MONITOR = REGISTRATE
            .blockEntity("monitor", MonitorBlockEntity::new)
            .validBlocks(ModBlocks.MONITOR)
            .renderer(() -> MonitorRenderer::new)
            .register();


    public static final BlockEntityEntry<RadarBaseBlockTileEntity> RADAR_BASE_BLOCK_TILE_ENTITY = REGISTRATE
            .blockEntity("radar_base_block", RadarBaseBlockTileEntity::new)
            .instance(() -> BearingInstance::new, true)
            .validBlocks(ModBlocks.RADAR_BEARING_BLOCK)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();



    public static void register(){}
}
