package net.amik.createarsenal.block.staticTurret.modularGun;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModItems;
import net.amik.createarsenal.registrate.ModPartials;
import net.amik.createarsenal.shell.ShellScale;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;


import java.util.List;

import static net.amik.createarsenal.util.HorizontalDirectionBlock.FACING;


public class GunBarrelBlockEntity extends SmartBlockEntity {

    public static final int MAX_BARREL_COUNT=4;
    ShellScale size= ShellScale.NONE;
    BlockPos gunBearing=BlockPos.ZERO;
    int barrelCount;

    public GunBarrelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}



    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        size= ShellScale.readFromNBT(compound);
        barrelCount=compound.getInt("barrelCount");
        gunBearing=NbtUtils.readBlockPos(compound.getCompound("gunBlock"));
    }


    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        size.saveToNBT(compound);
        compound.putInt("barrelCount",barrelCount);
        compound.put("gunBlock",NbtUtils.writeBlockPos(gunBearing));
    }
    boolean validBarrel(ItemStack stack){
        if(size!=ShellScale.NONE&&size!=ShellScale.getScaleFromItem(stack)) return false;
        if(barrelCount>=MAX_BARREL_COUNT) return false;
        if(!barrelBehindMatches()) return false;
        return true;
    }

    private boolean maxBarrelLength() {
        if (level.getBlockEntity(gunBearing) instanceof NormalGunBlockEntity gun)
            if (gun.maxBarrelLength())
                return true;
        return false;
    }

    private boolean barrelBehindMatches() {
        BlockPos barrelPos = getBlockPos().relative(getBlockState().getValue(FACING));
        if (level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity barrel)
            if (barrel.barrelCount<=this.barrelCount)
                return false;
        return true;
    }

    public void addBarrel(ShellScale size,BlockPos gunBearing,Player player,ItemStack stack) {
        this.gunBearing=gunBearing;
        this.size=size;
        this.barrelCount++;
        if(!player.isCreative())
            stack.shrink(1);
        notifyUpdate();
    }

    public InteractionResult useOn(UseOnContext pContext){
        if (validBarrel(pContext.getItemInHand())) {
            addBarrel(ShellScale.getScaleFromItem(pContext.getItemInHand()), gunBearing, pContext.getPlayer(), pContext.getItemInHand());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if(!isBarrelItem(stack)) return InteractionResult.PASS;

        BlockPos barrelPos = getBlockPos().relative(getBlockState().getValue(FACING).getOpposite());
        if(!maxBarrelLength())
            level.setBlockAndUpdate(barrelPos, ModBlocks.BARREL_BLOCK.getDefaultState().setValue(FACING, getBlockState().getValue(FACING)));
        if (level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity barrel) {
            if (barrel.validBarrel(stack)&&barrel.barrelCount<this.barrelCount) {
                barrel.addBarrel(size,gunBearing,pPlayer,stack);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public PartialModel getPartialModel() {
        if(size.equals(ShellScale.SMALL))
            return ModPartials.SMALL_BARREL;

        if(size.equals(ShellScale.MEDIUM))
            return ModPartials.MEDIUM_BARREL;

        if(size.equals(ShellScale.LARGE))
            return ModPartials.LARGE_BARREL;
        return null;
    }
    private boolean isBarrelItem(ItemStack stack) {
        return stack.is(ModItems.SMALL_BARREL.get())||stack.is(ModItems.MEDIUM_BARREL.get())||stack.is(ModItems.LARGE_BARREL.get());
    }

    public int getBarrelCount() {
        return barrelCount;
    }

    public ShellScale getSize() {
        return size;
    }

    public BlockPos getGunBearing() {
        return gunBearing;
    }

    public boolean isPrimary(){
        BlockPos gunpos = getBlockPos().relative(getBlockState().getValue(FACING));
        return level.getBlockEntity(gunpos) instanceof NormalGunBlockEntity;
    }

    public int getBarrelPosition(){
        return Math.abs(getBlockPos().getX()-gunBearing.getX())+Math.abs(getBlockPos().getY()-gunBearing.getY());
    }
    public boolean isLastBarrel(){
        BlockPos gunpos = getBlockPos().relative(getBlockState().getValue(FACING).getOpposite());
        return !(level.getBlockEntity(gunpos) instanceof GunBarrelBlockEntity);
    }
    public NormalGunBlockEntity getGunBE(){
        if(level.getBlockEntity(gunBearing) instanceof NormalGunBlockEntity gun)
            return gun;
        return null;
    }



}
