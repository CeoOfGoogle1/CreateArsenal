package net.amik.createarsenal.registrate;


import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.amik.createarsenal.block.monitor.MonitorBlock;
import net.amik.createarsenal.block.radar.base.RadarBaseBlock;
import net.amik.createarsenal.block.radar.dish.RadarDishBlock;
import net.amik.createarsenal.block.radar.plate.RadarPlateBlock;
import net.amik.createarsenal.block.radar.receiver.RadarReceiverBlock;
import net.amik.createarsenal.block.seaMine.SeaMineBlock;
import net.amik.createarsenal.block.staticTurret.chainGunTurret.ChainGunStaticTurret;
import net.amik.createarsenal.block.staticTurret.eightBarrelTurret.EightBarrelStaticTurret;
import net.amik.createarsenal.block.staticTurret.fourBarrelTurret.FourBarrelStaticTurret;
import net.amik.createarsenal.block.staticTurret.modularGun.barrel.GunBarrelBlock;
import net.amik.createarsenal.block.staticTurret.modularGun.normalGun.NormalGunBlock;
import net.amik.createarsenal.block.staticTurret.modularGun.rotaryGun.RotaryGunBlock;
import net.amik.createarsenal.util.CreateUtil;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;

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

    public static final BlockEntry<SeaMineBlock> SEA_MINE =
            REGISTRATE.block("sea_mine", SeaMineBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.standardModel(c, p)))
                    .item()
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

    public static final BlockEntry<RadarBaseBlock> RADAR_BASE_BLOCK =
            REGISTRATE.block("radar_base_block", RadarBaseBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate((c, p) -> p.directionalBlock(c.getEntry(), p.models()
                            .getExistingFile(c.getId()), 180))
                    .item()
                    .model(AssetLookup.customBlockItemModel("_", "item"))
                    .build()
                    .register();

    @SuppressWarnings("unused")
    public static final BlockEntry<RadarDishBlock> RADAR_DISH_BLOCK =
            REGISTRATE.block("radar_dish_block", RadarDishBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate((ctx, prov) -> prov.directionalBlock(ctx.getEntry(), prov.models()
                            .getExistingFile(ctx.getId()), 0))
                    .simpleItem()
                    .register();

    @SuppressWarnings("unused")
    public static final BlockEntry<RadarPlateBlock> RADAR_PLATE_BLOCK =
            REGISTRATE.block("radar_plate_block", RadarPlateBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate((ctx, prov) -> prov.directionalBlock(ctx.getEntry(), prov.models()
                            .getExistingFile(ctx.getId()), 0))
                    .simpleItem()
                    .register();

    @SuppressWarnings("unused")
    public static final BlockEntry<RadarReceiverBlock> RADAR_RECEIVER_BLOCK =
            REGISTRATE.block("radar_receiver_block", RadarReceiverBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(BlockStressDefaults.setImpact(0))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate((ctx, prov) -> prov.directionalBlock(ctx.getEntry(), prov.models()
                            .getExistingFile(ctx.getId()), 0))
                    .simpleItem()
                    .register();


    public static void register() {
    }
}
