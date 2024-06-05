package net.amik.createarsenal.block.staticTurret.modularGun.rotaryGun;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.block.staticTurret.modularGun.barrel.GunBarrelBlockEntity;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionalKineticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class RotaryGunBlock extends HorizontalDirectionalKineticBlock implements IBE<RotaryGunBlockEntity> {
    public RotaryGunBlock(Properties properties) {
        super(properties);
    }



    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        BlockPos facing = pPos.relative(pState.getValue(FACING).getOpposite());
        if (pLevel.getBlockEntity(facing) instanceof GunBarrelBlockEntity)
            pLevel.removeBlock(facing, false);
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public Class<RotaryGunBlockEntity> getBlockEntityClass() {
        return RotaryGunBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends RotaryGunBlockEntity> getBlockEntityType() {
        return ModBlockEntities.ROTARY_GUN_BLOCK_ENTITY.get();
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return pContext.getPlayer().isShiftKeyDown() ?
                this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()) : this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
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
