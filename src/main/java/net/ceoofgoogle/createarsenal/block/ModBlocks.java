package net.ceoofgoogle.createarsenal.block;


import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.ceoofgoogle.createarsenal.CreateArsenal;
import net.ceoofgoogle.createarsenal.block.custom.TurretBaseBlock;
import net.ceoofgoogle.createarsenal.item.ModCreativeModTab;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;


public class ModBlocks {

    private static final CreateRegistrate REGISTRATE = CreateArsenal
            .registrate()
            .creativeModeTab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL);

    public static final BlockEntry<TurretBaseBlock> TURRET_BASE_BLOCK =
            REGISTRATE.block("turret_base_block", TurretBaseBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(BlockStressDefaults.setImpact(0))
                    .item()
                    .transform(customItemModel())
                    .register();


    public static void register(){
    }
}
