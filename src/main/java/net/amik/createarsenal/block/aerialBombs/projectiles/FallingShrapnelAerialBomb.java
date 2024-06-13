package net.amik.createarsenal.block.aerialBombs.projectiles;

import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModProjectiles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FallingShrapnelAerialBomb extends FallingAerialBomb {

    int shrapnelCount = 32;

    public FallingShrapnelAerialBomb(EntityType<? extends FallingAerialBomb> entityType, Level level) {
        super(entityType, level);
        this.setBlockState(ModBlocks.SHRAPNEL_BOMB.getDefaultState());
        this.explosionRadius = 2;
    }

    public FallingShrapnelAerialBomb(Level level) {
        super(ModProjectiles.FALLING_SHRAPNEL_AERIAL_BOMB.get(), level);
        this.shrapnelCount = 0;
    }


    @Override
    protected void triggerOnHitEffects() {
        super.triggerOnHitEffects();
        this.triggerFragExplosion();
    }

    private void triggerFragExplosion() {
        if (this.shrapnelCount > 0) {
            for (int i = 0; i < this.shrapnelCount; ++i) {
                ShrapnelProjectile bomb = new ShrapnelProjectile(this.level());
                bomb.setPos(this.getX(), this.getY(), this.getZ());
                double r = 1; // radius
                double theta = 2 * Math.PI * random.nextDouble(); // azimuthal angle
                double phi = Math.acos(1 - 2 * random.nextDouble()); // polar angle
                double x = r * Math.sin(phi) * Math.cos(theta);
                double y = r * Math.sin(phi) * Math.sin(theta);
                double z = r * Math.cos(phi);
                bomb.setDeltaMovement(new Vec3(x, y * .5f + .1f, z));
                this.level().addFreshEntity(bomb);
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("shrapnelCount"))
            this.shrapnelCount = compound.getInt("shrapnelCount");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("shrapnelCount", shrapnelCount);
    }


    public static void fragbuild(EntityType.Builder<FallingShrapnelAerialBomb> fallingBombBuilder) {
        fallingBombBuilder.sized(0.98F, 0.98F);
    }

    public void setShrapnelCount(int shrapnelCount) {
        this.shrapnelCount = shrapnelCount;
    }
}
