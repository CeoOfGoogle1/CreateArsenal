package net.amik.createarsenal.block.staticTurret.modularGun.barrel;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.amik.createarsenal.block.staticTurret.modularGun.normalGun.NormalGunBlockEntity;
import net.amik.createarsenal.block.staticTurret.modularGun.rotaryGun.RotaryGunBlockEntity;
import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModItems;
import net.amik.createarsenal.registrate.ModPartials;
import net.amik.createarsenal.shell.ShellScale;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

import static net.amik.createarsenal.util.HorizontalDirectionBlock.FACING;


public class GunBarrelBlockEntity extends SmartBlockEntity {


    public static final int MAX_NORMAL_GUN_BARREL_COUNT = 4;
    public static final int MAX_ROTARY_GUN_BARREL_COUNT = 8;

    ShellScale size = ShellScale.NONE;
    BlockPos gunBearing = BlockPos.ZERO;


    int barrelCount;

    public GunBarrelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }



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


    public InteractionResult useOn(UseOnContext pContext){
        if (validBarrel(pContext.getItemInHand())) {

            addBarrel(ShellScale.getScaleFromItem(pContext.getItemInHand()), gunBearing, pContext.getPlayer(), pContext.getItemInHand());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public InteractionResult use(Player player, ItemStack stack) {
        if (!isBarrelItem(stack) || !isCorrectSize(stack) || level == null) return InteractionResult.PASS;

        BlockPos barrelPos = getBlockPos().relative(getBlockState().getValue(FACING).getOpposite());
        if(!maxBarrelLength()&&this.barrelCount==getPrimaryBarrelCount())
            level.setBlockAndUpdate(barrelPos, ModBlocks.BARREL_BLOCK.getDefaultState().setValue(FACING, getBlockState().getValue(FACING)));

        if (level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity barrel) {
            if (barrel.validBarrel(stack) && barrel.barrelCount < this.barrelCount) {
                barrel.addBarrel(size, gunBearing, player, stack);
                return InteractionResult.SUCCESS;
            }
            if (this.barrelCount == getPrimaryBarrelCount() && this.barrelCount > 0)
                return barrel.use(player, stack);

        }
        return InteractionResult.PASS;
    }

    public void addBarrel(ShellScale size, BlockPos gunBearing, @Nullable Player player, ItemStack stack) {
        this.gunBearing = gunBearing;
        this.size = size;
        this.barrelCount++;
        if (player != null && !player.isCreative())
            stack.shrink(1);
        notifyUpdate();
    }

    public boolean validBarrel(ItemStack stack) {
        if (!isCorrectSize(stack)) return false;
        if (atMaxBarrelCount()) return false;
        return barrelBehindMatches();
    }

    boolean isCorrectSize(ItemStack stack) {
        return (size == ShellScale.NONE || size == ShellScale.getScaleFromItem(stack));
    }

    private boolean maxBarrelLength() {
        assert level != null;
        if (level.getBlockEntity(gunBearing) instanceof NormalGunBlockEntity gun)
            return gun.atMaxBarrelLength();
        return true;
    }

    private boolean barrelBehindMatches() {
        BlockPos barrelPos = getBlockPos().relative(getBlockState().getValue(FACING));
        assert level != null;
        if (level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity barrel)
            return barrel.barrelCount > this.barrelCount;
        return true;
    }


    public int getPrimaryBarrelCount(){
        if(isPrimary()) return barrelCount;
        if(getGunBE()!=null)
            return getGunBE().getBarrelCount();
        return 0;
    }

    public PartialModel getPartialModel() {
        if (size.equals(ShellScale.SMALL))
            return ModPartials.SMALL_BARREL;

        if (size.equals(ShellScale.MEDIUM))
            return ModPartials.MEDIUM_BARREL;

        if (size.equals(ShellScale.LARGE))
            return ModPartials.LARGE_BARREL;
        return null;
    }

    public PartialModel getPartialRotatingModel() {
        if (size.equals(ShellScale.SMALL))
            return ModPartials.SMALL_ROTAING_BARREL;

        if (size.equals(ShellScale.MEDIUM))
            return ModPartials.MEDIUM_ROTAING_BARREL;

        if (size.equals(ShellScale.LARGE))
            return ModPartials.LARGE_ROTAING_BARREL;
        return null;
    }

    private boolean isBarrelItem(ItemStack stack) {
        return stack.is(ModItems.SMALL_BARREL.get()) || stack.is(ModItems.MEDIUM_BARREL.get()) || stack.is(ModItems.LARGE_BARREL.get());
    }

    public int getBarrelCount() {
        return barrelCount;
    }

    public boolean atMaxBarrelCount() {
        return barrelCount >= (isSpinning() ? MAX_ROTARY_GUN_BARREL_COUNT : MAX_NORMAL_GUN_BARREL_COUNT);
    }

    public ShellScale getSize() {
        return size;
    }


    public boolean isPrimary() {
        BlockPos gunpos = getBlockPos().relative(getBlockState().getValue(FACING));
        assert level != null;
        return level.getBlockEntity(gunpos) instanceof NormalGunBlockEntity;
    }


    public NormalGunBlockEntity getGunBE(){
        assert level != null;
        if(level.getBlockEntity(gunBearing) instanceof NormalGunBlockEntity gun)
            return gun;
        return null;
    }

    public void dropItemEntity() {
        ItemStack barrel = new ItemStack(getBarrelItemFromSize(), getBarrelCount());
        assert level != null;
        level.addFreshEntity(new ItemEntity(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), barrel));
    }

    public boolean isSpinning() {
        assert level != null;
        return (level.getBlockEntity(gunBearing) instanceof RotaryGunBlockEntity);
    }


    public ItemLike getBarrelItemFromSize() {
        if (size.equals(ShellScale.MEDIUM))
            return ModItems.MEDIUM_BARREL.get();
        if (size.equals(ShellScale.LARGE))
            return ModItems.LARGE_BARREL.get();
        return ModItems.SMALL_BARREL.get();
    }
}
