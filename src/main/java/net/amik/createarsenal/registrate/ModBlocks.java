package net.amik.createarsenal.registrate;


import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.amik.createarsenal.block.big_radar.BigRadarBaseBlock;
import net.amik.createarsenal.block.big_radar.BigRadarDishBlock;
import net.amik.createarsenal.block.big_radar.BigRadarReceiverBlock;
import net.amik.createarsenal.block.monitor.MonitorBlock;
import net.amik.createarsenal.block.staticTurret.EightBarrelTurret.EightBarrelStaticTurret;
import net.amik.createarsenal.block.staticTurret.FourBarrelTurret.FourBarrelStaticTurret;
import net.amik.createarsenal.block.turretBase.TurretBaseBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;

public class ModBlocks {


    public static final BlockEntry<TurretBaseBlock> TURRET_BASE_BLOCK =
            REGISTRATE.block("turret_base_block", TurretBaseBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .simpleItem()
                    .register();

    public static final BlockEntry<FourBarrelStaticTurret> FOUR_BARREL_STATIC_TURRET =
            REGISTRATE.block("four_barrel_static_turret", FourBarrelStaticTurret::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .simpleItem()
                    .register();

    public static final BlockEntry<MonitorBlock> MONITOR =
            REGISTRATE.block("monitor", MonitorBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .simpleItem()
                    .register();

    public static final BlockEntry<EightBarrelStaticTurret> EIGHT_BARREL_STATIC_TURRET =
            REGISTRATE.block("eight_barrel_static_turret", EightBarrelStaticTurret::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .simpleItem()
                    .register();

    public static final BlockEntry<BigRadarBaseBlock> BIG_RADAR_BASE_BLOCK =
            REGISTRATE.block("big_radar_base_block", BigRadarBaseBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .simpleItem()
                    .register();

    public static final BlockEntry<BigRadarDishBlock> BIG_RADAR_DISH_BLOCK =
            REGISTRATE.block("big_radar_dish_block", BigRadarDishBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .simpleItem()
                    .register();

    public static final BlockEntry<BigRadarReceiverBlock> BIG_RADAR_RECEIVER_BLOCK =
            REGISTRATE.block("big_radar_receiver_block", BigRadarReceiverBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .simpleItem()
                    .register();


    public static void register() {
    }
}
