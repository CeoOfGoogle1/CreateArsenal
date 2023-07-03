package net.amik.createarsenal.shell;

import com.simibubi.create.foundation.particle.AirParticleData;
import net.amik.createarsenal.registrate.ModItems;
import net.amik.createarsenal.registrate.ModProjectiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class BulletEntity extends AbstractHurtingProjectile implements ItemSupplier {
    int life=0;

    public BulletEntity(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }
    public BulletEntity(Level p_36834_) {
        super(ModProjectiles.BULLET_ENTITY.get(), p_36834_);
    }
    @Override
    protected boolean shouldBurn() {
        return false;
    }
    @SuppressWarnings("unchecked")
    public static void build(EntityType.Builder<?> builder) {
        EntityType.Builder<BulletEntity> entityBuilder = (EntityType.Builder<BulletEntity>) builder;
        entityBuilder.sized(.375f, .375f);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            life++;
            if (life > 40)
                discard();
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
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        this.kill();
    }

    //TODO dynamic bullet models
    @Override
    public @NotNull ItemStack getItem() {
        return ModItems.FOUR_BARREL_ROUND.get().getDefaultInstance();
    }
}