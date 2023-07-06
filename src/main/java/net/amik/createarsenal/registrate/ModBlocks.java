package net.amik.createarsenal.registrate;


import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.amik.createarsenal.block.big_radar.BigRadarBaseBlock;
import net.amik.createarsenal.block.big_radar.BigRadarDishBlock;
import net.amik.createarsenal.block.big_radar.BigRadarReceiverBlock;
import net.amik.createarsenal.block.staticTurret.ChaingunTurret.ChaingunStaticTurret;
import net.amik.createarsenal.block.staticTurret.EightBarrelTurret.EightBarrelStaticTurret;
import net.amik.createarsenal.block.staticTurret.FourBarrelTurret.FourBarrelStaticTurret;
import net.amik.createarsenal.block.turretBase.TurretBaseBlock;
import net.amik.createarsenal.util.CreateUtil;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;

public class ModBlocks {


    public static final BlockEntry<TurretBaseBlock> TURRET_BASE_BLOCK =
            REGISTRATE.block("turret_base_block", TurretBaseBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 180))
                    .item()
                    .model(NonNullBiConsumer.noop())
                    .build()
                    .register();

    public static final BlockEntry<FourBarrelStaticTurret> FOUR_BARREL_STATIC_TURRET =
            REGISTRATE.block("four_barrel_static_turret", FourBarrelStaticTurret::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 0))
                    .item()
                    .model(NonNullBiConsumer.noop())
                    .build()
                    .register();

//    public static final BlockEntry<MonitorBlock> MONITOR =
//            REGISTRATE.block("monitor", MonitorBlock::new)
//                    .initialProperties(SharedProperties::softMetal)
//                    .properties(BlockBehaviour.Properties::noOcclusion)
//                    .simpleItem()
//                    .register();

    // TODO: Fix this stupid system, implement multiblock @happysg

    public static final BlockEntry<EightBarrelStaticTurret> EIGHT_BARREL_STATIC_TURRET =
            REGISTRATE.block("eight_barrel_static_turret", EightBarrelStaticTurret::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 0))
                    .simpleItem()
                    .register();

    public static final BlockEntry<ChaingunStaticTurret> CHAINGUN_STATIC_TURRET =
            REGISTRATE.block("chaingun_static_turret", ChaingunStaticTurret::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
//                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 0))
                    // TODO: @Amik, uncomment this line when you added the model, and remove the line below
                    .blockstate(NonNullBiConsumer.noop())
                    .item()
                    .model(NonNullBiConsumer.noop())
                    .build()
                    // TODO: And then change the 3 lines above to .simpleItem()
                    .register();

    public static final BlockEntry<BigRadarBaseBlock> BIG_RADAR_BASE_BLOCK =
            REGISTRATE.block("big_radar_base_block", BigRadarBaseBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 180))
                    .item()
                    .model(NonNullBiConsumer.noop())
                    .build()
                    .register();

    public static final BlockEntry<BigRadarDishBlock> BIG_RADAR_DISH_BLOCK =
            REGISTRATE.block("big_radar_dish_block", BigRadarDishBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 180))
                    .simpleItem()
                    .register();

    public static final BlockEntry<BigRadarReceiverBlock> BIG_RADAR_RECEIVER_BLOCK =
            REGISTRATE.block("big_radar_receiver_block", BigRadarReceiverBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 180))
                    .simpleItem()
                    .register();


    public static void register() {
    }
}
