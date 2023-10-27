package net.amik.createarsenal.shell;

import com.jozufozu.flywheel.util.Color;
import net.amik.createarsenal.registrate.ModProjectiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.sounds.SoundEvents.FIREWORK_ROCKET_BLAST;

public class BulletEntity extends AbstractArrow {
    public static final AtomicInteger NEXT_BREAKER_ID = new AtomicInteger();

    private static final EntityDataAccessor<Integer> DATA_ID_SIZE =
            SynchedEntityData.defineId(BulletEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> DATA_ID_INSIDE_COLOR =
            SynchedEntityData.defineId(BulletEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_OUTSIDE_COLOR =
            SynchedEntityData.defineId(BulletEntity.class, EntityDataSerializers.INT);
    protected int life=20;

    protected int damage=2;




    protected int breakerId = -NEXT_BREAKER_ID.incrementAndGet();

    protected int breakerLevel = 0;

    protected static Map<BlockPos, Float> breakProgress = new HashMap<>();

    public BulletEntity(EntityType<? extends BulletEntity> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public BulletEntity(Level p_36834_, int breakerLevel) {
        super(ModProjectiles.BULLET_ENTITY.get(), p_36834_);
        this.breakerLevel = breakerLevel;
        setSoundEvent(FIREWORK_ROCKET_BLAST);
    }

    @SuppressWarnings("unchecked")
    public static void build(EntityType.Builder<?> builder) {
        EntityType.Builder<BulletEntity> entityBuilder = (EntityType.Builder<BulletEntity>) builder;
        entityBuilder.sized(1/2f, 1/2f);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            life--;
            if (life < 0)
                kill();
        }
    }

    public final void setPos(BlockPos pPos) {
        this.setPos(pPos.getX(), pPos.getY(), pPos.getZ());
    }

    //TODO Damage Source
    protected void onHitEntity(@NotNull EntityHitResult p_37216_) {
        super.onHitEntity(p_37216_);
        if (!this.level.isClientSide) {
            Entity entity = p_37216_.getEntity();
            entity.hurt(DamageSource.GENERIC, damage);
            this.kill();
        }
    }



    @Override
    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        super.onHitBlock(pResult);

        BlockState state = this.level.getBlockState(pResult.getBlockPos());

        breakProgress.putIfAbsent(pResult.getBlockPos(), 0F);
        breakProgress.get(pResult.getBlockPos());
        breakProgress.put(pResult.getBlockPos(), breakProgress.get(pResult.getBlockPos()) + 10 / state.getDestroySpeed(this.level, pResult.getBlockPos()));

        if (!this.level.isClientSide) {
            if (breakProgress.get(pResult.getBlockPos()) > 10) {
                this.getLevel().destroyBlock(pResult.getBlockPos(), false);
                breakProgress.put(pResult.getBlockPos(), 0F);
                this.getLevel().destroyBlockProgress(breakerId, pResult.getBlockPos(), 0);

            } else
                this.getLevel().destroyBlockProgress(breakerId, pResult.getBlockPos(), (int) (float) breakProgress.get(pResult.getBlockPos()));
            this.kill();
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("BreakerId", breakerId);
        pCompound.putInt("size", this.entityData.get(DATA_ID_SIZE));
        pCompound.putInt("insideColor", this.entityData.get(DATA_ID_INSIDE_COLOR));
        pCompound.putInt("outsideColor", this.entityData.get(DATA_ID_OUTSIDE_COLOR));

    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_ID_SIZE, tag.getInt("size"));
        this.entityData.set(DATA_ID_INSIDE_COLOR, tag.getInt("insideColor"));
        this.entityData.set(DATA_ID_OUTSIDE_COLOR, tag.getInt("outsideColor"));

    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }


    @Override
    protected float getWaterInertia() {
        return .99f;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_SIZE, ShellScale.SMALL.ordinal());
        this.entityData.define(DATA_ID_INSIDE_COLOR, new Color(255, 212, 0).getRGB());
        this.entityData.define(DATA_ID_OUTSIDE_COLOR, new Color(255, 72, 0).getRGB());
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setColor(int outside, int inside){
        this.entityData.set(DATA_ID_INSIDE_COLOR, inside);
        this.entityData.set(DATA_ID_OUTSIDE_COLOR, outside);
    }
    public Color getInsideColor() {
        return new Color(this.entityData.get(DATA_ID_INSIDE_COLOR));
    }

    public Color getOutsideColor() {
        return new Color(this.entityData.get(DATA_ID_OUTSIDE_COLOR));
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public ShellScale getSize() {
        return ShellScale.values()[this.entityData.get(DATA_ID_SIZE)];
    }

    public void setSize(ShellScale size) {
        this.entityData.set(DATA_ID_SIZE, size.ordinal());
    }


}