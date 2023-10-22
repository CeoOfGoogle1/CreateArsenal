package net.amik.createarsenal.block.staticTurret.modularGun;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import net.amik.createarsenal.block.staticTurret.AbstractRotatingTurretTileEntity;
import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModItems;
import net.amik.createarsenal.shell.BulletEntity;
import net.amik.createarsenal.shell.ShellScale;
import net.amik.createarsenal.sound.ModSounds;
import net.minecraft.core.BlockPos;
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

    private static final int MAX_BARREL_LENGTH = 4;


    public NormalGunBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}




    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack=pPlayer.getItemInHand(pHand);
        if(!isBarrelItem(stack))  return InteractionResult.PASS;
        if(addBarrel(pPlayer, stack))
            return InteractionResult.SUCCESS;
        return InteractionResult.PASS;
    }

    private boolean addBarrel(Player pPlayer,ItemStack stack) {
        BlockPos barrelPos=getBlockPos().relative(getBlockState().getValue(FACING).getOpposite());
        level.setBlockAndUpdate(barrelPos, ModBlocks.BARREL_BLOCK.getDefaultState().setValue(FACING,getBlockState().getValue(FACING)));
        if(level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity barrel) {
            if(barrel.validBarrel(stack)) {
                barrel.addBarrel(ShellScale.getScaleFromItem(stack), getBlockPos(),pPlayer,stack);
                notifyUpdate();
                return true;
            }
            if(barrel.atMaxBarrelCount())
              return  barrel.use(pPlayer,stack).equals(InteractionResult.SUCCESS);
        }
        return false;
    }
    @Override
    protected float shootingInterval() {
        return 1;
    }


    @Override
    public float getSpeed() {
        return 32*getBarrelCount();
    }

    @Override
    protected boolean canShoot() {
        return super.canShoot()&&level.hasNeighborSignal(getBlockPos());
    }


    public int getBarrelCount(){
        BlockPos barrelPos=getBlockPos().relative(getBlockState().getValue(FACING).getOpposite());
        if(level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity barrel)
            return barrel.getBarrelCount();
        return 0;
    }

    private ShellScale getBarrelSize(){
        BlockPos barrelPos=getBlockPos().relative(getBlockState().getValue(FACING).getOpposite());
        if(level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity barrel)
            return barrel.getSize();
        return ShellScale.NONE;
    }

    @Override
    protected float getBarrelLength() {
        for (int i = 0; i < MAX_BARREL_LENGTH; i++) {
            BlockPos barrelPos = getBlockPos().relative(getBlockState().getValue(FACING).getOpposite(),i+1);
            if (!(level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity))
                return i;
        }
        return MAX_BARREL_LENGTH;
    }

    private boolean isBarrelItem(ItemStack stack) {
        return stack.is(ModItems.SMALL_BARREL.get())||stack.is(ModItems.MEDIUM_BARREL.get())||stack.is(ModItems.LARGE_BARREL.get());
    }

    @Override
    public boolean isValidBulletInserted(ItemStack stack) {
        return super.isValidBulletInserted(stack)&&(getBarrelSize().equals(ShellScale.getScaleFromItem(stack)));
    }


    @Override
    protected RegistryObject<SoundEvent> fireSoundName() {
        return ModSounds.CHAIN_GUN_FIRED;
    }

    @Override
    protected String getTooltipName() {
        if(getBarrelCount()>0)
            return StringUtils.capitalize(getBarrelSize().toString().toLowerCase())+" "+getBarrelCount()+" Barrel Gun";
        return super.getTooltipName();
    }

    @Override
    protected void playSoundAndParticles() {
        level.playLocalSound(getBlockPos().getX(),getBlockPos().getY(),getBlockPos().getZ(), fireSoundName().get(), SoundSource.BLOCKS, 5f, 1, true);
    }

    public boolean maxBarrelLength() {
        return getBarrelLength()>=MAX_BARREL_LENGTH;
    }

    @Override
    protected void whenBulletCreated(BulletEntity bullet) {
        bullet.setLife((int) (getBarrelLength()*20));
        bullet.setDamage(2*getBarrelSize().ordinal());
        bullet.setSize(getBarrelSize());
        super.whenBulletCreated(bullet);
    }


    @Override
    protected float getVelocity() {
        return 6+getBarrelLength();
    }

}
