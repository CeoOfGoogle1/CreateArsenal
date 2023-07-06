package net.amik.createarsenal.util;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public abstract class HorizontalDirectionalKineticBlock extends DirectionalKineticBlock {
    public HorizontalDirectionalKineticBlock(Properties properties) {
        super(properties);
    }

    public Direction getPreferredFacing(BlockPlaceContext context) {
        return super.getPreferredFacing(context) == null || !super.getPreferredFacing(context).getAxis().isHorizontal() ? context.getHorizontalDirection() : super.getPreferredFacing(context).getOpposite();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction direction) {
        return direction == state.getValue(FACING);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING)
                .getAxis();
    }
}
