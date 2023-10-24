package net.amik.createarsenal.registrate;


import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.block.big_radar.BigRadarBaseBlock;
import net.amik.createarsenal.block.big_radar.BigRadarDishBlock;
import net.amik.createarsenal.block.big_radar.BigRadarReceiverBlock;
import net.amik.createarsenal.block.monitor.MonitorBlock;
import net.amik.createarsenal.block.staticTurret.chainGunTurret.ChainGunStaticTurret;
import net.amik.createarsenal.block.staticTurret.eightBarrelTurret.EightBarrelStaticTurret;
import net.amik.createarsenal.block.staticTurret.fourBarrelTurret.FourBarrelStaticTurret;
import net.amik.createarsenal.block.staticTurret.modularGun.barrel.GunBarrelBlock;
import net.amik.createarsenal.block.staticTurret.modularGun.normalGun.NormalGunBlock;
import net.amik.createarsenal.block.staticTurret.modularGun.rotaryGun.RotaryGunBlock;
import net.amik.createarsenal.util.CreateUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;
import static net.amik.createarsenal.block.big_radar.BigRadarDishBlock.MULTIBLOCK;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class ModBlocks {



    public static final BlockEntry<FourBarrelStaticTurret> FOUR_BARREL_STATIC_TURRET =
            REGISTRATE.block("four_barrel_static_turret", FourBarrelStaticTurret::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 0))
                    .item()
                    .model(NonNullBiConsumer.noop())
                    .build()
                    .register();

    public static final BlockEntry<NormalGunBlock> NORMAL_GUN =
            REGISTRATE.block("normal_gun_bearing", NormalGunBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(properties -> properties.isRedstoneConductor((pState, pLevel, pPos) -> false))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 0))
                    .item()
                    .build()
                    .register();

    public static final BlockEntry<RotaryGunBlock> ROTARY_GUN =
            REGISTRATE.block("rotary_gun_bearing", RotaryGunBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(properties -> properties.isRedstoneConductor((pState, pLevel, pPos) -> false))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 0))
                    .item()
                    .build()
                    .register();

    public static final BlockEntry<GunBarrelBlock> BARREL_BLOCK =
            REGISTRATE.block("barrel_block", GunBarrelBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate((c, p) -> p.getVariantBuilder(c.get())
                            .forAllStatesExcept(state -> ConfiguredModel.builder()
                                    .modelFile(p.models()
                                            .getExistingFile(p.mcLoc("block/air")))
                                    .build(), GunBarrelBlock.FACING))
                    .item()
                    .build()
                    .register();

    public static final BlockEntry<MonitorBlock> MONITOR =
            REGISTRATE.block("monitor", MonitorBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(NonNullBiConsumer.noop())
                    .item()
                    .model(NonNullBiConsumer.noop())
                    .build()
                    .register();


    public static final BlockEntry<EightBarrelStaticTurret> EIGHT_BARREL_STATIC_TURRET =
            REGISTRATE.block("eight_barrel_static_turret", EightBarrelStaticTurret::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 0))
                    .item()
                    .model(NonNullBiConsumer.noop())
                    .build()
                    .register();

    public static final BlockEntry<ChainGunStaticTurret> CHAINGUN_STATIC_TURRET =
            REGISTRATE.block("chain_gun_static_turret", ChainGunStaticTurret::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 0))
                    .item()
                    .model(NonNullBiConsumer.noop())
                    .build()
                    .register();

    public static final BlockEntry<BigRadarBaseBlock> BIG_RADAR_BASE_BLOCK =
            REGISTRATE.block("big_radar_base_block", BigRadarBaseBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(CreateUtil.horizontalDirectionalBlockProvider(false, 180))
                    .simpleItem()
                    .register();

    public static final BlockEntry<BigRadarDishBlock> BIG_RADAR_DISH_BLOCK =
            REGISTRATE.block("big_radar_dish_block", BigRadarDishBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate((c, p) -> p.getVariantBuilder(c.get())
                            .forAllStates(state -> {
                                Direction dir = state.getValue(BlockStateProperties.FACING);
                                if(state.getValue(MULTIBLOCK))
                                    return ConfiguredModel.builder()
                                            .modelFile(p.models().getExistingFile(p.mcLoc("block/air")))
                                            .build();
                                return ConfiguredModel.builder()
                                        .modelFile(p.models().getExistingFile(CreateArsenal.resource("block/big_radar_dish_block")))
                                        .rotationY((int) dir.toYRot())
                                        .build();
                            }))
                    .simpleItem()
                    .register();

    public static final BlockEntry<BigRadarReceiverBlock> BIG_RADAR_RECEIVER_BLOCK =
            REGISTRATE.block("big_radar_receiver_block", BigRadarReceiverBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate((c, p) -> p.getVariantBuilder(c.get())
                            .forAllStates(state -> {
                                Direction dir = state.getValue(HORIZONTAL_FACING);
                                if(state.getValue(MULTIBLOCK))
                                    return ConfiguredModel.builder()
                                            .modelFile(p.models().getExistingFile(p.mcLoc("block/air")))
                                            .build();
                                return ConfiguredModel.builder()
                                        .modelFile(p.models().getExistingFile(CreateArsenal.resource("block/big_radar_receiver_block")))
                                        .rotationY((int) dir.toYRot())
                                        .build();
                            }))
                    .simpleItem()
                    .register();


    public static void register() {
    }
}
