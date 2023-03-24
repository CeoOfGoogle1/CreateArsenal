package net.amik.createarsenal.content;


import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.content.block.turret_base.TurretBaseBlock;
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


    public static void register() {
    }
}
