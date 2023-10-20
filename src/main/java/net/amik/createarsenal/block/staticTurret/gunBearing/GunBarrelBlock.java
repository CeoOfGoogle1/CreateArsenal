package net.amik.createarsenal.block.staticTurret.gunBearing;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GunBarrelBlock extends HorizontalDirectionBlock implements IBE<GunBarrelBlockEntity> {
    public GunBarrelBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public Class<GunBarrelBlockEntity> getBlockEntityClass() {
        return GunBarrelBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends GunBarrelBlockEntity> getBlockEntityType() {
        return ModBlockEntities.BARREL_BLOCK_ENTITY.get();
    }

    @Override
    public void fillItemCategory(CreativeModeTab pTab, NonNullList<ItemStack> pItems) {
    }

    @Override
    protected void spawnDestroyParticles(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState) {
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        Direction behind=pState.getValue(FACING);
        if(pLevel.getBlockEntity(pPos.relative(behind)) instanceof NormalGunBlockEntity gun)
            gun.removeBarrel();
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}
