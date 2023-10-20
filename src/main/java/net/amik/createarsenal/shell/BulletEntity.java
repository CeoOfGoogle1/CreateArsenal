package net.amik.createarsenal.shell;

import com.jozufozu.flywheel.util.Color;
import com.simibubi.create.foundation.particle.AirParticleData;
import net.amik.createarsenal.registrate.ModProjectiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class BulletEntity extends AbstractHurtingProjectile {
    public static final AtomicInteger NEXT_BREAKER_ID = new AtomicInteger();

    protected int life=0;

    protected int breakerId = -NEXT_BREAKER_ID.incrementAndGet();

    protected int breakerLevel = 0;

    protected static Map<BlockPos, Float> breakProgress = new HashMap<>();

    public BulletEntity(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }
    public BulletEntity(Level p_36834_, int breakerLevel) {
        super(ModProjectiles.BULLET_ENTITY.get(), p_36834_);
        this.breakerLevel = breakerLevel;
    }
    @Override
    protected boolean shouldBurn() {
        return false;
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
            life++;
            if (life > 40)
                kill();
        }
    }

    public final void setPos(BlockPos pPos) {
        this.setPos(pPos.getX(), pPos.getY(), pPos.getZ());
    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return new AirParticleData(1, 10);
    }

    //TODO Damage Source
    protected void onHitEntity(@NotNull EntityHitResult p_37216_) {
        super.onHitEntity(p_37216_);
        if (!this.level.isClientSide) {
            Entity entity = p_37216_.getEntity();
            entity.hurt(DamageSource.GENERIC, 6.0F);
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
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    }


}