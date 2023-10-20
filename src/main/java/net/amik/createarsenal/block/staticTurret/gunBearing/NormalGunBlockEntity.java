package net.amik.createarsenal.block.staticTurret.gunBearing;

import com.jozufozu.flywheel.util.StringUtil;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.amik.createarsenal.block.staticTurret.AbstractRotatingTurretTileEntity;
import net.amik.createarsenal.block.staticTurret.AbstractTurretTileEntity;
import net.amik.createarsenal.block.staticTurret.fourBarrelTurret.FourBarrelStaticTurretBlockEntity;
import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModItems;
import net.amik.createarsenal.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static net.amik.createarsenal.util.HorizontalDirectionBlock.FACING;

public class NormalGunBlockEntity extends AbstractRotatingTurretTileEntity {

    private static final int MAX_BARREL_COUNT=4;

    Size size=Size.NONE;
    int barrelCount=0;


    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        size=Size.values()[compound.getInt("size")];
        barrelCount=compound.getInt("barrelCount");

    }


    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putInt("size",size.ordinal());
        compound.putInt("barrelCount",barrelCount);
    }

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
        if(!isCorrectSize(stack)||atMaxBarrelCount()) return InteractionResult.PASS;
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
        updateBarrelBlock();
    }

    @Override
    protected void playSoundAndParticles() {
        level.playLocalSound(getBlockPos().getX(),getBlockPos().getY(),getBlockPos().getZ(), fireSoundName().get(), SoundSource.BLOCKS, 5f, 1, true);
    }

    private void updateBarrelBlock() {
        BlockPos barrelPos=getBlockPos().relative(getBlockState().getValue(FACING).getOpposite());
        level.setBlockAndUpdate(barrelPos, ModBlocks.BARREL_BLOCK.getDefaultState().setValue(FACING,getBlockState().getValue(FACING)));
        if(level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity barrel) {
            barrel.addBarrel(size);
            barrelCount=barrel.getBarrelCount();
        }
        notifyUpdate();
    }


    private boolean isCorrectSize(ItemStack stack) {
        return size.equals(Size.NONE) ||
                (stack.is(ModItems.SMALL_BARREL.get())&&size.equals(Size.SMALL)) ||
                (stack.is(ModItems.MEDIUM_BARREL.get())&&size.equals(Size.MEDIUM)) ||
                (stack.is(ModItems.LARGE_BARREL.get())&&size.equals(Size.LARGE));
    }

    @Override
    protected boolean canShoot() {
        return super.canShoot()&&level.hasNeighborSignal(getBlockPos());
    }

    @Override
    public float getSpeed() {
        return 16*barrelCount;
    }



    private boolean atMaxBarrelCount(){
        return barrelCount==MAX_BARREL_COUNT;
    }
    private boolean isBarrelItem(ItemStack stack) {
        return stack.is(ModItems.SMALL_BARREL.get())||stack.is(ModItems.MEDIUM_BARREL.get())||stack.is(ModItems.LARGE_BARREL.get());
    }

    @Override
    public boolean isValidBulletInserted(ItemStack stack) {
        return super.isValidBulletInserted(stack)&&(stack.is(ModItems.BULLET_SMALL.get())&&size.equals(Size.SMALL)) ||
                (stack.is(ModItems.BULLET_MEDIUM.get())&&size.equals(Size.MEDIUM)) ||
                (stack.is(ModItems.BULLET_LARGE.get())&&size.equals(Size.LARGE));
    }


    public void removeBarrel() {
        barrelCount=0;
        size=Size.NONE;
        notifyUpdate();
    }

    @Override
    protected RegistryObject<SoundEvent> fireSoundName() {
        return ModSounds.CHAIN_GUN_FIRED;
    }

    @Override
    protected float shootingInterval() {
        return 2;
    }

    @Override
    protected float getBarrelLength() {
        return 1f;
    }

    @Override
    protected String getTooltipName() {
        if(barrelCount>0)
            return StringUtils.capitalize(size.toString().toLowerCase())+" "+barrelCount+" Barrel Gun";
        return super.getTooltipName();
    }
}
