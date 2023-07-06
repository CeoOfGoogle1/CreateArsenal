package net.amik.createarsenal.block.turretBase;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionalKineticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class TurretBaseBlock extends HorizontalDirectionalKineticBlock implements IBE<TurretBaseBlockTileEntity> {

    public TurretBaseBlock(Properties properties) {
        super(properties);

    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction direction) {
        return direction.getAxis() == Direction.Axis.Y;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }


    @Override
    public Class<TurretBaseBlockTileEntity> getBlockEntityClass() {
        return TurretBaseBlockTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends TurretBaseBlockTileEntity> getBlockEntityType() {
        return ModBlockEntities.TURRET_BASE_BLOCK_TILE_ENTITY.get();
    }
}

