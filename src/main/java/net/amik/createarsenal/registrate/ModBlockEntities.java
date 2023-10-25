package net.amik.createarsenal.registrate;


import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.amik.createarsenal.block.big_radar.BidRadarDishRenderer;
import net.amik.createarsenal.block.big_radar.BigRadarBaseBlockTileEntity;
import net.amik.createarsenal.block.big_radar.BigRadarDishBlockTileEntity;
import net.amik.createarsenal.block.monitor.MonitorBlockEntity;
import net.amik.createarsenal.block.monitor.MonitorRenderer;
import net.amik.createarsenal.block.seaMine.SeaMineBlockEntity;
import net.amik.createarsenal.block.staticTurret.chainGunTurret.ChainGunStaticTurretBlockEntity;
import net.amik.createarsenal.block.staticTurret.chainGunTurret.ChainGunStaticTurretInstance;
import net.amik.createarsenal.block.staticTurret.chainGunTurret.ChainGunStaticTurretRenderer;
import net.amik.createarsenal.block.staticTurret.eightBarrelTurret.EightBarrelStaticTurretBlockEntity;
import net.amik.createarsenal.block.staticTurret.eightBarrelTurret.EightBarrelStaticTurretInstance;
import net.amik.createarsenal.block.staticTurret.eightBarrelTurret.EightBarrelStaticTurretRenderer;
import net.amik.createarsenal.block.staticTurret.fourBarrelTurret.FourBarrelStaticTurretBlockEntity;
import net.amik.createarsenal.block.staticTurret.fourBarrelTurret.FourBarrelStaticTurretInstance;
import net.amik.createarsenal.block.staticTurret.fourBarrelTurret.FourBarrelStaticTurretRenderer;
import net.amik.createarsenal.block.staticTurret.modularGun.barrel.GunBarrelBlockEntity;
import net.amik.createarsenal.block.staticTurret.modularGun.barrel.GunBarrelBlockRenderer;
import net.amik.createarsenal.block.staticTurret.modularGun.normalGun.NormalGunBlockEntity;
import net.amik.createarsenal.block.staticTurret.modularGun.rotaryGun.RotaryGunBlockEntity;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;

public class ModBlockEntities {

    public static final BlockEntityEntry<FourBarrelStaticTurretBlockEntity> FOUR_BARREL_STATIC_TURRET_TILE_ENTITY = REGISTRATE
            .blockEntity("four_barrel_static_turret", FourBarrelStaticTurretBlockEntity::new)
            .instance(() -> FourBarrelStaticTurretInstance::new, true)
            .validBlocks(ModBlocks.FOUR_BARREL_STATIC_TURRET)
            .renderer(() -> FourBarrelStaticTurretRenderer::new)
            .register();

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

    public static final BlockEntityEntry<EightBarrelStaticTurretBlockEntity> EIGHT_BARREL_STATIC_TURRET_TILE_ENTITY = REGISTRATE
            .blockEntity("eight_barrel_static_turret", EightBarrelStaticTurretBlockEntity::new)
            .instance(() -> EightBarrelStaticTurretInstance::new, true)
            .validBlocks(ModBlocks.EIGHT_BARREL_STATIC_TURRET)
            .renderer(() -> EightBarrelStaticTurretRenderer::new)
            .register();


    public static final BlockEntityEntry<ChainGunStaticTurretBlockEntity> CHAINGUN_STATIC_TURRET_TILE_ENTITY = REGISTRATE
            .blockEntity("chain_gun_static_turret", ChainGunStaticTurretBlockEntity::new)
            .instance(() -> ChainGunStaticTurretInstance::new, true)
            .validBlocks(ModBlocks.CHAINGUN_STATIC_TURRET)
            .renderer(() -> ChainGunStaticTurretRenderer::new)
            .register();

    public static final BlockEntityEntry<BigRadarBaseBlockTileEntity> BIG_RADAR_BASE_BLOCK_TILE_ENTITY = REGISTRATE
            .blockEntity("big_radar_base_block", BigRadarBaseBlockTileEntity::new)
            .instance(() -> ShaftInstance::new, true)
            .validBlocks(ModBlocks.BIG_RADAR_BASE_BLOCK)
            .renderer(() -> KineticBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<BigRadarDishBlockTileEntity> BIG_RADAR_DISH_BLOCK_TILE_ENTITY = REGISTRATE
            .blockEntity("big_radar_dish_block", BigRadarDishBlockTileEntity::new)
            .validBlocks(ModBlocks.BIG_RADAR_DISH_BLOCK)
            .renderer(() -> BidRadarDishRenderer::new)
            .register();



    public static void register(){}
}
