package net.amik.createarsenal.shell;

import com.simibubi.create.foundation.particle.AirParticleData;
import net.amik.createarsenal.registrate.ModProjectiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;

public class BulletEntity extends AbstractHurtingProjectile {
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
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);

        //shitty implementation but works (barely), future me or anyone else, please fix it :thumbs_up:
        //TODO: make some thing to keep track of block damage
        double breakChance = this.getLevel().getBlockState(pResult.getBlockPos()).getDestroySpeed(this.getLevel(), pResult.getBlockPos()) / 2.5;

        if (!this.level.isClientSide) {
            if (Math.random() >= breakChance)
                //breaks the block
                this.getLevel().destroyBlock(pResult.getBlockPos(), false);
            else
                //sets the visual to half broken
                this.getLevel().destroyBlockProgress(0, pResult.getBlockPos(), 3);
            this.kill();
        }
    }
}