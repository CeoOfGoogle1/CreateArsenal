package net.amik.createarsenal.block.staticTurret.gunBearing;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

import static net.amik.createarsenal.util.HorizontalDirectionBlock.FACING;

public class NormalGunBlockEntity extends SmartBlockEntity {

    private static final int MAX_BARREL_COUNT=4;

    Size size=Size.NONE;
    int barrelCount=0;

    enum Size{
        NONE,
        SMALL,
        MEDIUM,
        LARGE;
    }
    public NormalGunBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack=pPlayer.getItemInHand(pHand);
        if(!isBarrelItem(stack))  return InteractionResult.PASS;
        if(!isCorrectSize(stack)&&!atMaxBarrelCount()) return InteractionResult.PASS;
        addBarrel(pPlayer, stack);

        return InteractionResult.SUCCESS;
    }

    private void addBarrel(Player pPlayer,ItemStack stack) {
        if(size.equals(Size.NONE)){
            if(stack.is(ModItems.SMALL_BARREL.get()))
                size=Size.SMALL;
            if(stack.is(ModItems.MEDIUM_BARREL.get()))
                size=Size.MEDIUM;
            if(stack.is(ModItems.LARGE_BARREL.get()))
                size=Size.LARGE;
        }
        if(!pPlayer.isCreative())
            stack.shrink(1);
        barrelCount++;
        updateBarrelBlock();
    }

    private void updateBarrelBlock() {
        BlockPos barrelPos=getBlockPos().relative(getBlockState().getValue(FACING));
        level.setBlockAndUpdate(barrelPos, ModBlocks.BARREL_BLOCK.getDefaultState().setValue(FACING,getBlockState().getValue(FACING)));
        if(level.getBlockEntity(barrelPos) instanceof BarrelBlockEntity barrel)
            barrel.addBarrel(size,barrelCount);
    }


    private boolean isCorrectSize(ItemStack stack) {
        return size.equals(Size.NONE) ||
                (stack.is(ModItems.SMALL_BARREL.get())&&size.equals(Size.SMALL)) ||
                (stack.is(ModItems.MEDIUM_BARREL.get())&&size.equals(Size.MEDIUM)) ||
                (stack.is(ModItems.LARGE_BARREL.get())&&size.equals(Size.LARGE));
    }

    private boolean atMaxBarrelCount(){
        return barrelCount<MAX_BARREL_COUNT;
    }
    private boolean isBarrelItem(ItemStack stack) {
        return stack.is(ModItems.SMALL_BARREL.get())||stack.is(ModItems.MEDIUM_BARREL.get())||stack.is(ModItems.LARGE_BARREL.get());
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
