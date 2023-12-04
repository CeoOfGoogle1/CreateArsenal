package net.amik.createarsenal.block.staticTurret.modularGun.normalGun;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import net.amik.createarsenal.block.staticTurret.chainGunTurret.ChainGunStaticTurretBlockEntity;
import net.amik.createarsenal.block.staticTurret.modularGun.barrel.GunBarrelBlockEntity;
import net.amik.createarsenal.item.ScaleItem;
import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModSoundEvents;
import net.amik.createarsenal.shell.BulletEntity;
import net.amik.createarsenal.shell.ShellScale;
import net.amik.createarsenal.shell.TracerColors;
import net.amik.createarsenal.util.IntUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;

import static net.amik.createarsenal.util.HorizontalDirectionBlock.FACING;

public class NormalGunBlockEntity extends ChainGunStaticTurretBlockEntity {

    private static final int MAX_BARREL_LENGTH = 4;


    public NormalGunBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public InteractionResult use(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if(!isBarrelItem(stack)) return InteractionResult.PASS;
        if(addBarrel(pPlayer, stack))
            return InteractionResult.SUCCESS;
        return InteractionResult.PASS;
    }


    private boolean addBarrel(Player pPlayer,ItemStack stack) {
        assert level != null;
        BlockPos barrelPos = getBlockPos().relative(getBlockState().getValue(FACING).getOpposite());
        level.setBlockAndUpdate(barrelPos, ModBlocks.BARREL_BLOCK.getDefaultState().setValue(FACING,getBlockState().getValue(FACING)));
        if(level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity barrel) {
            if(barrel.validBarrel(stack)) {
                barrel.addBarrel(ShellScale.getScaleFromItem(stack), getBlockPos(),pPlayer,stack);
                notifyUpdate();
                return true;
            }
            if(barrel.atMaxBarrelCount())
              return barrel.use(pPlayer,stack).equals(InteractionResult.SUCCESS);
        }
        return false;
    }
    @Override
    protected float shootingInterval() {
        return 1;
    }


    @Override
    public float getSpeed() {
        return 32 * getBarrelCount();
    }

    @Override
    protected boolean canShoot() {
        assert level != null;
        return super.canShoot() && level.hasNeighborSignal(getBlockPos());
    }


    public int getBarrelCount(){
        assert level != null;
        BlockPos barrelPos = getBlockPos().relative(getBlockState().getValue(FACING).getOpposite());
        if(level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity barrel)
            return barrel.getBarrelCount();
        return 0;
    }

    private ShellScale getBarrelSize(){
        assert level != null;
        BlockPos barrelPos = getBlockPos().relative(getBlockState().getValue(FACING).getOpposite());
        if(level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity barrel)
            return barrel.getSize();
        return null;
    }

    @Override
    protected float getBarrelLength() {
        assert level != null;
        for (int i = 0; i < MAX_BARREL_LENGTH; i++) {
            BlockPos barrelPos = getBlockPos().relative(getBlockState().getValue(FACING).getOpposite(),i+1);
            if (!(level.getBlockEntity(barrelPos) instanceof GunBarrelBlockEntity))
                return i;
        }
        return MAX_BARREL_LENGTH;
    }

    private boolean isBarrelItem(ItemStack stack) {
        return stack.getItem() instanceof ScaleItem;
    }

    @Override
    public boolean isValidBulletInserted(ItemStack stack) {
        return super.isValidBulletInserted(stack) && (getBarrelSize() == ShellScale.getScaleFromItem(stack));
    }


    @Override
    protected void playSoundAndParticles() {
        assert level != null;
        // TODO: This is repeated code (bad)
        if (getBarrelSize().equals(ShellScale.SMALL))
            ModSoundEvents.FIRE_SMALL_TURRET.playOnServer(level, getBlockPos());
        if (getBarrelSize().equals(ShellScale.MEDIUM))
            ModSoundEvents.FIRE_MEDIUM_TURRET.playOnServer(level, getBlockPos());
        if (getBarrelSize().equals(ShellScale.LARGE))
            ModSoundEvents.FIRE_LARGE_TURRET.playOnServer(level, getBlockPos());

        Direction direction = getBlockState().getValue(DirectionalKineticBlock.FACING).getOpposite();

        level.addAlwaysVisibleParticle(ParticleTypes.SMOKE,
                getBlockPos().getX() +
                        IntUtil.toInt(direction.getStepX() < 0)
                        + Math.abs(direction.getStepZ() / 2F)
                        + direction.getStepX() * (getBarrelLength() + 1.15f),
                getBlockPos().getY() + .6f,
                getBlockPos().getZ() +
                        IntUtil.toInt(direction.getStepZ() < 0)
                        + Math.abs(direction.getStepX() / 2F)
                        + direction.getStepZ() * (getBarrelLength() + 1.15f),
                0d, 0d, 0d);
    }

    @Override
    protected String getTooltipName() {
        if(getBarrelCount() > 0)
            return StringUtils.capitalize(getBarrelSize().toString().toLowerCase()) + " " + getBarrelCount() + " Barrel Gun";
        return super.getTooltipName();
    }


    public boolean atMaxBarrelLength() {
        return getBarrelLength() >= MAX_BARREL_LENGTH;
    }

    @Override
    protected void whenBulletCreated(BulletEntity bullet) {
        bullet.setLife((int) (getBarrelLength() * 20));
        bullet.setDamage(10 * getBarrelSize().ordinal());
        bullet.setSize(getBarrelSize());
        TracerColors.setBulletColor(bullet, getBulletItem());
        super.whenBulletCreated(bullet);
    }


    @Override
    protected float getBulletVelocity() {
        return 7 + getBarrelLength();
    }

    @Override
    protected float getBulletInaccuracy() {
        return .1f;
    }
}
