package net.amik.createarsenal.block.aerialBombs.projectiles;

import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModProjectiles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FallingClusterAerialBomb extends FallingAerialBomb {

    int clusterBombletCount = 16;
    int detonationAltitude = 32;


    public FallingClusterAerialBomb(EntityType<? extends FallingAerialBomb> entityType, Level level) {
        super(entityType, level);
        this.setBlockState(ModBlocks.CLUSTER_BOMB.getDefaultState());
    }

    public FallingClusterAerialBomb(Level level) {
        super(ModProjectiles.FALLING_CLUSTER_AERIAL_BOMB.get(), level);
        this.clusterBombletCount = 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (atClusterAltitude() && !level().isClientSide()) {
            triggerClusterExplosion();
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("clusterBombletCount"))
            this.clusterBombletCount = compound.getInt("clusterBombletCount");
        if (compound.contains("detonationAltitude"))
            this.detonationAltitude = compound.getInt("detonationAltitude");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("clusterBombletCount", clusterBombletCount);
        compound.putInt("detonationAltitude", detonationAltitude);
    }

    private boolean atClusterAltitude() {
        for (int i = 0; i < this.detonationAltitude; ++i) {
            if (!this.level().getBlockState(this.getOnPos().below(i)).isAir()) {
                return true;
            }
        }

        return false;
    }

    private void triggerClusterExplosion() {
        if (this.clusterBombletCount > 0) {
            for (int i = 0; i < this.clusterBombletCount; ++i) {
                ClusterBomblet bomb = new ClusterBomblet(this.level());
                bomb.setPos(this.getX(), this.getY(), this.getZ());
                bomb.setDeltaMovement(new Vec3(this.random.nextFloat() - .5f * .7f, -Math.abs(this.random.nextFloat()), this.random.nextFloat() - .5f * .7f));
                this.level().addFreshEntity(bomb);
            }
        }

    }

    public void setClusterBombletCount(int clusterBombletCount) {
        this.clusterBombletCount = clusterBombletCount;
    }

    public int getClusterBombletCount() {
        return clusterBombletCount;
    }

    public void setDetonationAltitude(int DetonationAltitude) {
        this.detonationAltitude = DetonationAltitude;
    }

    public int getDetonationAltitude() {
        return detonationAltitude;
    }

    public static void clusterbuild(EntityType.Builder<FallingClusterAerialBomb> fallingBombBuilder) {
        fallingBombBuilder.sized(0.98F, 0.98F);
    }
}
