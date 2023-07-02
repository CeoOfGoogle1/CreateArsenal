package net.amik.createarsenal.block.staticTurret.FourBarrelTurret;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FourBarrelStaticTurret extends DirectionalKineticBlock implements IBE<FourBarrelStaticTurretTileEntity> {
    public FourBarrelStaticTurret(Properties properties) {
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

    @Override
    public Class<FourBarrelStaticTurretTileEntity> getBlockEntityClass() {
        return FourBarrelStaticTurretTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends FourBarrelStaticTurretTileEntity> getBlockEntityType() {
        return ModBlockEntities.FOUR_BARREL_STATIC_TURRET_TILE_ENTITY.get();
    }
}
