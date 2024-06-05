package net.amik.createarsenal.block.staticTurret.modularGun.normalGun;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.block.staticTurret.modularGun.barrel.GunBarrelBlockEntity;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class NormalGunBlock extends HorizontalDirectionBlock implements IBE<NormalGunBlockEntity> {
    public NormalGunBlock(Properties properties) {
        super(properties);
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
