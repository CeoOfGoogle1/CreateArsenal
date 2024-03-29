package net.amik.createarsenal.block.staticTurret.modularGun.barrel;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.block.staticTurret.modularGun.normalGun.NormalGunBlockEntity;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class GunBarrelBlock extends HorizontalDirectionBlock implements IBE<GunBarrelBlockEntity> {
    public GunBarrelBlock(Properties pProperties) {
        super(pProperties);
    }

    protected static final VoxelShape SHAPE_Z = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
    protected static final VoxelShape SHAPE_X = Block.box(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pLevel.getBlockEntity(pPos) instanceof GunBarrelBlockEntity be)
            return be.use(pPlayer, pPlayer.getItemInHand(pHand));
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public Class<GunBarrelBlockEntity> getBlockEntityClass() {
        return GunBarrelBlockEntity.class;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        if(pState.getValue(FACING).getAxis().equals(Direction.Axis.X))
            return SHAPE_X;
        return SHAPE_Z;
    }

    @Override
    public BlockEntityType<? extends GunBarrelBlockEntity> getBlockEntityType() {
        return ModBlockEntities.BARREL_BLOCK_ENTITY.get();
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab pTab, @NotNull NonNullList<ItemStack> pItems) {
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        Direction behind = pState.getValue(FACING).getOpposite();

        if (pLevel.getBlockEntity(pPos.relative(behind)) instanceof GunBarrelBlockEntity)
            pLevel.removeBlock(pPos.relative(behind), false);

        if (pLevel.getBlockEntity(pPos.relative(behind.getOpposite())) instanceof NormalGunBlockEntity gun)
            gun.dropAmmo();

        if (pLevel.getBlockEntity(pPos) instanceof GunBarrelBlockEntity barrel)
            barrel.dropItemEntity();
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        if (level.getBlockEntity(pos) instanceof GunBarrelBlockEntity barrel) {
            return new ItemStack(barrel.getBarrelItemFromSize());
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected void spawnDestroyParticles(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull BlockPos pPos, @NotNull BlockState pState) {
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        return false;
    }
}
