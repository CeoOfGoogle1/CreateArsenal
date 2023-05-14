package net.amik.createarsenal.registrate;


import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.block.staticTurret.FourBarrelStaticTurret;
import net.amik.createarsenal.block.turret_base.TurretBaseBlock;
import net.amik.createarsenal.block.staticTurret.ChainGunStaticTurret;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class ModBlocks {

    private static final CreateRegistrate REGISTRATE = CreateArsenal
            .registrate()
            .creativeModeTab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL);

    public static final BlockEntry<TurretBaseBlock> TURRET_BASE_BLOCK =
            REGISTRATE.block("turret_base_block", TurretBaseBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(BlockStressDefaults.setImpact(0))
                    .item()
                    .transform(customItemModel())
                    .register();

    public static final BlockEntry<FourBarrelStaticTurret> FOUR_BARREL_STATIC_TURRET =
            REGISTRATE.block("four_barrel_static_turret", FourBarrelStaticTurret::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .item()
                    .transform(customItemModel())
                    .register();


    public static void register() {
    }
}
