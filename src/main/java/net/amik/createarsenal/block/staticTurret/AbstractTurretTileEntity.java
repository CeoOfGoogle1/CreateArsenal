package net.amik.createarsenal.block.staticTurret;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;
import net.amik.createarsenal.registrate.ModTags;
import net.amik.createarsenal.registrate.network.ItemStackSyncS2CPacket;
import net.amik.createarsenal.shell.BulletEntity;
import net.amik.createarsenal.util.IntUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;

public abstract class AbstractTurretTileEntity extends KineticBlockEntity implements IHaveGoggleInformation, ItemStackSyncS2CPacket.ItemStackSyncBlockEntity {
    protected LazyOptional<TurretItemHandler> itemCapability;
    ItemStack renderStack=ItemStack.EMPTY;

    public AbstractTurretTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        itemCapability=LazyOptional.of(TurretItemHandler::new);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        getItemHandler().deserializeNBT(compound.getCompound("inv"));
        itemCapability.ifPresent(itemCapability->renderStack=itemCapability.getStackInSlot(0));
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.put("inv",getItemHandler().serializeNBT());
    }

    protected int torqueCounter; // Shoot every 256 torque

    @Override
    public void tick() {
        super.tick();
        if (canShoot() && hasAmmo()) {
            playSoundAndParticles();
            shoot();
            consumeAmmo();
        }
    }


    @Override
    public void destroy() {
        super.destroy();
        dropAmmo();
    }

    public void dropAmmo(){
        if(hasAmmo())
            ItemHelper.dropContents(level, worldPosition, getItemHandler());
        notifyUpdate();
    }

    private void consumeAmmo() {
        itemCapability.ifPresent(itemCapability->itemCapability.extractItem(0,1,false));
    }

    private boolean hasAmmo() {
        return getItemHandler().getStackInSlot(0).getCount()>0;
    }

    @Override
    public void setItem(ItemStack stack, BlockPos blockPos) {
        renderStack=stack;
    }

    protected abstract SoundEvent fireSoundName();

    protected abstract float shootingInterval();

    protected boolean canShoot() {
        if (level == null)
            return false;

        torqueCounter += Math.abs(getSpeed());
        if (torqueCounter < shootingInterval() * 256) {
            return false;
        }

        torqueCounter %= 256;
        return true;
    }

    protected abstract float getBarrelLength();

    protected int getBreakerLevel() {
        return (int) getBarrelLength();
    }

    protected void shoot() {
        if (level == null || level.isClientSide) return;

        BulletEntity bullet = new BulletEntity(getLevel(), getBreakerLevel());

        Direction direction = getBlockState().getValue(FACING).getOpposite();

        bullet.setPos(
                new Vec3(
                        getBlockPos().getX() +
                                IntUtil.toInt(direction.getStepX() < 0)
                                + Math.abs(direction.getStepZ()/2F)
                                + direction.getStepX() * (getBarrelLength() + 2)
                        ,
                        getBlockPos().relative(direction, 4).getY()
                                + 1/4F
                        ,
                        getBlockPos().getZ() +
                                IntUtil.toInt(direction.getStepZ() < 0)
                                + Math.abs(direction.getStepX() / 2F)
                                + direction.getStepZ() * (getBarrelLength() + 2 + getBulletSizeModifier())
                )
        );

        bullet.shoot(direction.getStepX(), 0, direction.getStepZ(), getBulletVelocity(), 0F);

        whenBulletCreated(bullet);

        level.addFreshEntity(bullet);
    }

    protected float getBulletSizeModifier() {
        return 0;
    }


    @Override
    public void invalidate() {
        super.invalidate();
        itemCapability.invalidate();
    }

    public boolean isValidBulletInserted(ItemStack stack) {
        return stack.is(ModTags.BULLET_TAG);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return itemCapability.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        tooltip.add(Components.literal("    "+getTooltipName()));

        if (!renderStack.isEmpty())
            Lang.text("")
                    .add(Components.translatable(renderStack.getDescriptionId())
                            .withStyle(ChatFormatting.GRAY))
                    .add(Lang.text(" x" + renderStack.getCount())
                            .style(ChatFormatting.GREEN))
                    .forGoggles(tooltip, 1);
        return true;
    }


    protected String getTooltipName(){
        String key=this.getBlockState().getBlock().getDescriptionId();
        return Components.translatable(key).getString();
    }

    protected void whenBulletCreated(BulletEntity bullet) {}


    protected void playSoundAndParticles(){
        assert level != null;
        level.playSound(null, worldPosition, fireSoundName(), SoundSource.BLOCKS, 0.2F + ((float) Math.random()) * 0.2F, 0.9F + ((float) Math.random()) * 0.15F);
        Direction direction = getBlockState().getValue(FACING).getOpposite();
        level.addAlwaysVisibleParticle(ParticleTypes.FIREWORK, getBlockPos().getX() +
                IntUtil.toInt(direction.getStepX() < 0)
                + Math.abs(direction.getStepZ()/2F)
                + direction.getStepX() * (getBarrelLength() + 2),getBlockPos().relative(direction, 4).getY()
                + 1/4F,getBlockPos().getZ() +
                IntUtil.toInt(direction.getStepZ() < 0)
                + Math.abs(direction.getStepX()/2F)
                + direction.getStepZ() * (getBarrelLength() + 2), 0d, 0d, 0d);
        level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, getBlockPos().getX() +
                IntUtil.toInt(direction.getStepX() < 0)
                + Math.abs(direction.getStepZ()/2F)
                + direction.getStepX() * (getBarrelLength() + 2),getBlockPos().relative(direction, 4).getY()
                + 1/4F,getBlockPos().getZ() +
                IntUtil.toInt(direction.getStepZ() < 0)
                + Math.abs(direction.getStepX()/2F)
                + direction.getStepZ() * (getBarrelLength() + 2), 0d, 0d, 0d);
    }

    public ItemStack getBulletItem() {
        return getItemHandler().getStackInSlot(0);
    }
    protected float getBulletVelocity(){return 10;}

    TurretItemHandler getItemHandler(){
        return itemCapability.orElse(new TurretItemHandler());
    }

    private  class TurretItemHandler extends ItemStackHandler{

        public TurretItemHandler() {
            super(1);
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if(isValidBulletInserted(stack))
                return super.insertItem(slot, stack, simulate);
            return stack;
        }

        @Override
        protected void onContentsChanged(int slot) {
            syncItem(this.getStackInSlot(0),getBlockPos());
        }
    }

}
