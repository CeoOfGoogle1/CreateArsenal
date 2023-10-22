package net.amik.createarsenal.block.staticTurret.modularGun;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class NormalGunBlock extends HorizontalDirectionBlock implements IBE<NormalGunBlockEntity> {
    public NormalGunBlock(Properties properties) {
        super(properties);
    }


    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if(pLevel.getBlockEntity(pPos) instanceof NormalGunBlockEntity be)
            return be.use(pPlayer,pHand);
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        BlockPos facing=pPos.relative(pState.getValue(FACING).getOpposite());
        if(pLevel.getBlockEntity(facing) instanceof GunBarrelBlockEntity)
            pLevel.removeBlock(facing,false);
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public Class<NormalGunBlockEntity> getBlockEntityClass() {
        return NormalGunBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends NormalGunBlockEntity> getBlockEntityType() {
        return ModBlockEntities.NORMAL_GUN_BLOCK_ENTITY.get();
    }
}
