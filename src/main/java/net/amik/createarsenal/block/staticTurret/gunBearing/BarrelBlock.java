package net.amik.createarsenal.block.staticTurret.gunBearing;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BarrelBlock extends HorizontalDirectionBlock implements IBE<BarrelBlockEntity> {
    public BarrelBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public Class<BarrelBlockEntity> getBlockEntityClass() {
        return BarrelBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends BarrelBlockEntity> getBlockEntityType() {
        return ModBlockEntities.BARREL_BLOCK_ENTITY.get();
    }
}
