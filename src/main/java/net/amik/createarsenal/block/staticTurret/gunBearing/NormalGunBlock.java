package net.amik.createarsenal.block.staticTurret.gunBearing;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionBlock;
import net.amik.createarsenal.util.HorizontalDirectionalKineticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class NormalGunBlock extends HorizontalDirectionBlock implements IBE<NormalGunBlockEntity> {
    public NormalGunBlock(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.getBlockEntity(pPos) instanceof NormalGunBlockEntity be)
            return be.use(pState,pLevel,pPos,pPlayer,pHand,pHit);
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
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
