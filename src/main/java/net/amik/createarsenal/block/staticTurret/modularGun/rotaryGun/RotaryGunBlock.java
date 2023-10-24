package net.amik.createarsenal.block.staticTurret.modularGun.rotaryGun;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.block.staticTurret.modularGun.barrel.GunBarrelBlockEntity;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionalKineticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class RotaryGunBlock extends HorizontalDirectionalKineticBlock implements IBE<RotaryGunBlockEntity> {
    public RotaryGunBlock(Properties properties) {
        super(properties);
    }


    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pLevel.getBlockEntity(pPos) instanceof RotaryGunBlockEntity be)
            return be.use(pPlayer, pHand);
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
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
