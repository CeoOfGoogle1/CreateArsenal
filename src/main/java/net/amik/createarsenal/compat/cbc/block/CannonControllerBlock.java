package net.amik.createarsenal.compat.cbc.block;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CannonControllerBlock extends KineticBlock implements IBE<CannonControllerBlockEntity> {

    public CannonControllerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public Class<CannonControllerBlockEntity> getBlockEntityClass() {
        return CannonControllerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CannonControllerBlockEntity> getBlockEntityType() {
        return ModBlockEntities.CANNON_CONTROLLER.get();
    }
}
