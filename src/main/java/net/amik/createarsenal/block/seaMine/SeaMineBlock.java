package net.amik.createarsenal.block.seaMine;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class SeaMineBlock extends Block implements IBE<SeaMineBlockEntity> {
    public SeaMineBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Class<SeaMineBlockEntity> getBlockEntityClass() {
        return SeaMineBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SeaMineBlockEntity> getBlockEntityType() {
        return ModBlockEntities.SEA_MINE.get();
    }
}
