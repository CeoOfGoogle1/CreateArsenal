package net.amik.createarsenal.block.dispenser;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionalKineticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ProjectileDispenserBlock extends HorizontalDirectionalKineticBlock implements IBE<ProjectileDispenserBlockEntity> {

    public ProjectileDispenserBlock(Properties properties) {
        super(properties);
    }


    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getClockWise().getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return state.getValue(FACING).getClockWise().getAxis() == face.getAxis();
    }

    @Override
    public Class getBlockEntityClass() {
        return ProjectileDispenserBlockEntity.class;
    }

    @Override
    public BlockEntityType getBlockEntityType() {
        return ModBlockEntities.PROJECTILE_DISPENSER.get();
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
